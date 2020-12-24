package com.birohcek.monicaapp.persistance

import com.birohcek.monicaapp.Database
import com.birohcek.monicaapp.models.AccountModel
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountDao @Inject constructor(val database: Database) {

    fun getAll(): Flow<List<AccountModel>> {
        return database
            .accountQueries
            .selectAll { id, endpoint, username -> AccountModel(id, endpoint, username) }
            .asFlow()
            .mapToList()
    }

    fun create(endpoint: String, username: String, token: String) : AccountModel?{

        var accountId: Long? = null

        database.transaction {
            database
                .accountQueries
                .insertAccount(endpoint, username)

            val insertedAccount = database
                .accountQueries
                .lastInsertRowId()
                .executeAsOne()

            database.accountTokenQueries.insert(token, insertedAccount)
            accountId = insertedAccount
        }

        return database.accountQueries.selectById(accountId ?: return null) { id, e, u ->
            AccountModel(
                id,
                e,
                u
            )
        }.executeAsOneOrNull()
    }

    fun getUserTokenOf(account: AccountModel): String? {
        return database.accountTokenQueries.selectForAccount(account_id = account.id)
            .executeAsOneOrNull()?.token
    }

    fun getById(accountId: Long): Flow<AccountModel> {
        return database
            .accountQueries
            .selectById(accountId) { id, endpoint, username ->
                AccountModel(
                    id,
                    endpoint,
                    username
                )
            }
            .asFlow()
            .mapToOne()
    }
}