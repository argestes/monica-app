package com.birohcek.monicaapp.persistance

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.birohcek.monicaapp.Database
import com.birohcek.monicaapp.networking.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContactDao @Inject constructor(val database: Database) {

    enum class RTLG(val groupId: Int) {
        love(1),
        family(2),
        friend(3),
        work(4)
    }

    fun readFull(contactId: Long): Flow<ContactDto?> {
        return flow {
            val contactDatabase = database.contactQueries.withId(contactId).executeAsOneOrNull()
            if (contactDatabase == null) {
                emit(null)
                return@flow
            }

            val contactFields =
                database.contactFieldQueries.forContactId(contactId).executeAsList().map {
                    val typeId = it.contactFieldType
                    val type =
                        database.contactFieldTypeQueries.selectForId(typeId) { id, name, proto, type ->
                            ContactFieldType(
                                id.toInt(),
                                name,
                                proto,
                                type
                            )
                        }.executeAsOne()

                    ContactFieldDto(it.remoteId.toInt(), it.data ?: "", type)
                }

            emit(contactDatabase.toDto(groupsOf(contactId), contactFields))
        }
    }

    private fun groupsOf(contactId: Long): RelationshipTypeGroups {
        val list = database.relationshipQueries.withContactIs(contactId).executeAsList()

        val listMappedToRelationshipTypes = list.groupBy { it.relationship_type }

        val allRelsMapped = listMappedToRelationshipTypes.map { (type, rel) ->
            val relationshipType = database.relationshipTypeQueries.withId(type).executeAsOne()
            val i = relationshipType.relationship_type_group_id.toInt()
            val relationshipTypeGroup = when (i) {
                1 -> {
                    RTLG.love
                }
                2 -> {
                    RTLG.family
                }
                3 -> {
                    RTLG.friend
                }
                4 -> {
                    RTLG.work
                }
                else -> {
                    RTLG.family
                }
            }

            val relsMapped = rel.map { relDb ->
                RelationshipEntry(
                    RelationshipTypeDto(
                        relationshipType.id.toInt(),
                        relationshipType.name ?: ""
                    ),
                    database.contactQueries.withId(relDb.of_contact).executeAsOne()
                        .toDto(null, null)!!
                )

            }

            relationshipTypeGroup to relsMapped
        }.associate { it.first to it.second }

        val loveRels = allRelsMapped[RTLG.love].orEmpty()
        val familyRels = allRelsMapped[RTLG.family].orEmpty()
        val friendRels = allRelsMapped[RTLG.friend].orEmpty()
        val workRels = allRelsMapped[RTLG.work].orEmpty()
        val groups = RelationshipTypeGroups(
            loveRelationship = RelationshipContacts(loveRels.count(), loveRels),
            family = RelationshipContacts(familyRels.count(), familyRels),
            friendship = RelationshipContacts(friendRels.count(), friendRels),
            workRelations = RelationshipContacts(workRels.count(), workRels),
        )

        return groups
    }

    suspend fun insertContactDetail(key: Any, contact: ContactDto) {
        database.transaction {
            database.contactQueries.performUpsert(
                contact.id,
                contact.firstName,
                contact.lastName,
                contact.completeName,
                contact.information?.avatar?.url,
                contact.information?.dates?.birthDate,
                contact.hashId,
                contact.nickName,
                contact.initials,
                contact.description,
                contact.isStarred,
                contact.isPartial,
                contact.isActive,
                contact.isDead,
                contact.lastCalled,
                contact.lastActivityTogether,
                contact.stayInTouchFrequency?.toLong(),
                contact.stayInTouchTriggerDate
            )
        }

        insertRelationGroups(contact.information?.relationshipTypeGroups, contact.id)
        insertAvatar(contact.id, contact.information?.avatar)
        insertContactFields(contact.id, contact.contactFields)
    }

    private fun insertContactFields(contactId: Long, contactFields: List<ContactFieldDto>?) {
        if (contactFields == null) {
            return
        }

        database.contactFieldQueries.deleteForContact(contactId)
        contactFields.forEach {
            maybeInsertContactFieldType(it.contactFieldType)
            database.contactFieldQueries.insert(
                it.id.toLong(),
                contactId,
                it.data,
                it.contactFieldType.id.toLong()
            )
        }
    }

    private fun maybeInsertContactFieldType(contactFieldType: ContactFieldType) {
        val type = database.contactFieldTypeQueries.selectForId(contactFieldType.id.toLong())
            .executeAsOneOrNull()
        if (type != null) {
            return
        }

        database.contactFieldTypeQueries.insert(
            contactFieldType.id.toLong(),
            contactFieldType.name,
            contactFieldType.protocol,
            contactFieldType.type
        )
    }

    private fun insertAvatar(contactId: Long, avatar: ContactAvatarDto?) {
        database.avatarQueries.deleteForContactId(contactId)
        val avatar = avatar ?: return
        database.avatarQueries.insert(contactId, avatar.url, null, null)
    }

    private suspend fun insertRelationGroups(
        relationshipTypeGroups: RelationshipTypeGroups?,
        contactId: Long
    ) {
        val family = relationshipTypeGroups?.family

        insertGroup(family, RTLG.family, contactId)
        insertGroup(relationshipTypeGroups?.friendship, RTLG.friend, contactId)
        insertGroup(relationshipTypeGroups?.loveRelationship, RTLG.love, contactId)
        insertGroup(relationshipTypeGroups?.workRelations, RTLG.work, contactId)
    }

    private suspend fun insertGroup(contacts: RelationshipContacts?, r: RTLG, contactId: Long) {
        contacts?.entries?.forEach {
            insertRelationshipEntry(contactId, it, r)
        }
    }

    private suspend fun insertRelationshipEntry(contactId: Long, it: RelationshipEntry, r: RTLG) {
        val typeDto = it.typeDto
        insertOrUpdateType(typeDto, r)
        insertContactBasic(it.contactDto)
        try {
            database.relationshipQueries.insert(
                null,
                contactId,
                it.typeDto.id.toLong(),
                it.contactDto.id
            )
        } catch (e: SQLiteConstraintException) {
            Log.d("ContactDAO", "Error inserting rel", e)
        }
    }

    private suspend fun insertContactBasic(contactDto: ContactDto) {
        val executeAsOneOrNull = database.contactQueries.withId(contactDto.id).executeAsOneOrNull()
        if (executeAsOneOrNull != null) {
            return
        }

        insertContactDetail("", contactDto)
    }

    private fun insertOrUpdateType(typeDto: RelationshipTypeDto, r: RTLG) {
        val typeId = typeDto.id
        val typeName = typeDto.name
        val dbType = database.relationshipTypeQueries.withId(typeId.toLong()).executeAsOneOrNull()
        if (dbType == null) {
            database.relationshipTypeQueries.insert(
                typeId.toLong(),
                typeName,
                null,
                r.groupId.toLong(),
                false
            )
        }
    }
}

private fun Contact.toDto(
    groups: RelationshipTypeGroups?,
    contactFields: List<ContactFieldDto>?
): ContactDto? {
    val it = this ?: return null

    return ContactDto(
        id = it.id,
        firstName = it.firstName,
        lastName = it.lastName,
        information = ContactInformationDto(
            avatar = ContactAvatarDto(it.pictureUrl ?: ""),
            dates = ContactDatesDto(
                birthDate = it.birthDate ?: SingleDateDto(null, null, null),
                deceasedDate = SingleDateDto(null, null, null)
            ),
            howYouMet = null,
            relationshipTypeGroups = groups,
            carreer = CareerDto(null, null)
        ),
        hashId = it.hashId,
        nickName = it.nickname,
        description = it.description,
        completeName = it.completeName,
        initials = it.initials,
        isActive = it.isActive,
        isDead = it.isDead,
        isMe = false,
        isPartial = it.isPartial,
        isStarred = it.isStarred,
        lastActivityTogether = it.lastActivityTogether,
        lastCalled = it.lastCalled,
        stayInTouchFrequency = it.stayInTouchFreq?.toInt(),
        stayInTouchTriggerDate = it.stayInTouchTriggerDate,
        contactFields = contactFields
    )
}

public fun String?.toBooleanNullable(): Boolean? {
    if (this == "null") {
        return null
    }

    return this.toBoolean()
}



