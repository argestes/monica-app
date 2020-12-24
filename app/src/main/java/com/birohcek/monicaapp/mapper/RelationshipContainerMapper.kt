package com.birohcek.monicaapp.mapper

import com.birohcek.monicaapp.models.RelationMainType
import com.birohcek.monicaapp.models.RelationMainType.*
import com.birohcek.monicaapp.models.RelationshipModel
import com.birohcek.monicaapp.networking.models.RelationshipContacts
import com.birohcek.monicaapp.networking.models.RelationshipEntry
import com.birohcek.monicaapp.networking.models.RelationshipTypeGroups
import javax.inject.Inject

class RelationshipContainerMapper @Inject constructor() :
    Mapper<RelationshipTypeGroups?, List<RelationshipModel>> {


    private fun RelationshipContacts.deserialize(type: RelationMainType) =
        this.entries?.map { rel -> mapModel(rel, type) }.orEmpty()

    fun mapModel(entry: RelationshipEntry, relationType: RelationMainType): RelationshipModel {
        return RelationshipModel(
            relationType,
            relationName = entry.typeDto.name,
            contactFullName = entry.contactDto.completeName,
            contactId = entry.contactDto.id
        )
    }


    override fun deserialize(from: RelationshipTypeGroups?): List<RelationshipModel> {
        val from = from ?: return listOf()
        val friends = from.friendship.deserialize(FRIEND)
        val loves = from.loveRelationship.deserialize(LOVE)
        val families = from.family.deserialize(FAMILY)
        val works = from.workRelations.deserialize(WORK)
        return friends + loves + families + works
    }
}

interface Mapper<From, To> {

    fun deserialize(from: From): To

}
