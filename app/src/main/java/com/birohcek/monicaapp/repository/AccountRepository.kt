package com.birohcek.monicaapp.repository

import android.database.sqlite.SQLiteConstraintException
import com.birohcek.monicaapp.screens.login.JobState
import com.birohcek.monicaapp.screens.login.LoadingState
import com.birohcek.monicaapp.persistance.AccountDao
import com.birohcek.monicaapp.persistance.StoreModule
import com.dropbox.android.external.store4.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountRepository @Inject constructor(private val loginStoreProvider: StoreModule.LoginStoreProvider, val accountDao: AccountDao) {

    class UserAlreadyExistsException() : Exception()

    suspend fun addUser(username: String, password: String, serverAddress: String): Flow<JobState> {
        val loginStore = loginStoreProvider.provideLoginStore(serverAddress)
        val creds = StoreModule.LoginCred(serverAddress, username, password)

        return flow {
            emit(JobState(LoadingState.LOADING, null))
            try {
                val get = loginStore.get(creds)
                val account = accountDao.create(serverAddress, username, get.token)
                emit(JobState(LoadingState.SUCCESS, null, account?.id))
            } catch (e: SQLiteConstraintException) {
                val errorState = JobState(LoadingState.ERROR, UserAlreadyExistsException())
                emit(errorState)
            } catch (e: Exception) {
                val errorState = JobState(LoadingState.ERROR, e)
                emit(errorState)
            }
        }
    }
}