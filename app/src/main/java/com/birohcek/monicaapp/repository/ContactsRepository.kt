package com.birohcek.monicaapp.repository

import android.util.Log
import com.birohcek.monicaapp.mapper.ContactMapper
import com.birohcek.monicaapp.models.AccountModel
import com.birohcek.monicaapp.models.ContactModel
import com.birohcek.monicaapp.models.PetModel
import com.birohcek.monicaapp.persistance.AccountDao
import com.birohcek.monicaapp.persistance.StoreModule
import com.birohcek.monicaapp.screens.login.LoadingState
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val contactsStoreProvider: StoreModule.ContactsStoreProvider,
    private val singleContactStoreProvider: StoreModule.SingleContactStoreProvider,
    private val petsStoreProvider: StoreModule.PetStoreProvider,
    private val contactMapper: ContactMapper,
    private val accountDao: AccountDao
) {

    class JobState(
        val loadingState: LoadingState,
        val error: Throwable?,
        val data: List<ContactModel>?
    )

    class SingleContactJobState(
        val loadingState: LoadingState,
        val error: Throwable?,
        val data: ContactModel?
    )

    class PetsJobState(
        val loadingState: LoadingState,
        val error: Throwable?,
        val data: List<PetModel>?
    )

    fun getContactsOf(accountId: Long): Flow<JobState> {
        return accountDao.getById(accountId).flatMapConcat { getContactsOf(it) }
    }


    fun getContactsOf(account: AccountModel): Flow<JobState> {
        val contactsStore = contactsStoreProvider.provideContactsStore(account)

        val stream = contactsStore.stream(StoreRequest.cached(0, true))

        return stream.map {
            val data = it.dataOrNull()?.map(contactMapper::deserialize)
            val exception = (it as? StoreResponse.Error.Exception)?.error

            exception?.let { e ->
                Log.e("ContactRepository", "Error while getting contacts", e)
            }

            JobState(it.asLoadingState(), exception, data)
        }
    }

    private fun StoreResponse<Any>.asLoadingState(): LoadingState {
        return when (this) {
            is StoreResponse.Loading -> LoadingState.LOADING
            is StoreResponse.Data -> LoadingState.SUCCESS
            is StoreResponse.NoNewData -> LoadingState.SUCCESS
            is StoreResponse.Error.Exception -> LoadingState.ERROR
            else -> LoadingState.IDLE
        }
    }

    fun getSingleContactOf(accountId: Long, contactId: Long): Flow<SingleContactJobState> {
        return accountDao.getById(accountId).flatMapConcat { getSingleContactOf(it, contactId) }
    }

    fun getPetsOf(account: AccountModel, contactId: Long): Flow<PetsJobState> {
        val petStore = petsStoreProvider.providePetStore(account)

        return petStore.stream(StoreRequest.cached(contactId, true)).map {
            val data =
                it.dataOrNull()?.map { pet -> PetModel(pet.id, pet.name, pet.petCategoryDto.name) }
            val exception = (it as? StoreResponse.Error.Exception)?.error
            PetsJobState(it.asLoadingState(), exception, data)
        }
    }

    fun getSingleContactOf(account: AccountModel, contactId: Long): Flow<SingleContactJobState> {
        val contactStore = singleContactStoreProvider.provideSingleContactStore(account)
        val petStore = petsStoreProvider.providePetStore(account)


        val contactStream = contactStore.stream(StoreRequest.cached(contactId, true))
        val petStream = petStore.stream(StoreRequest.cached(contactId, true))


        return contactStream.combine(petStream) { contactResponse, petResponse ->
            val petModel = petResponse.dataOrNull()?.let { pets ->
                pets.map { pet ->
                    PetModel(
                        pet.id,
                        pet.name,
                        pet.petCategoryDto.name
                    )
                }
            }

            val effectiveLoadingState = calculateLoadingState(contactResponse, petResponse)

            val contactModel = contactResponse.dataOrNull()?.let(contactMapper::deserialize)?.copy(petModels = petModel.orEmpty())
            val exception = (contactResponse as? StoreResponse.Error.Exception)?.error
            SingleContactJobState(effectiveLoadingState, exception, contactModel)
        }
    }

    private fun calculateLoadingState(
        firstResponse: StoreResponse<Any>,
        secondResponse: StoreResponse<Any>
    ): LoadingState {

        val firstLoadingState = firstResponse.asLoadingState()
        val secondLoadingState = secondResponse.asLoadingState()

        if (firstLoadingState == LoadingState.ERROR || secondLoadingState == LoadingState.ERROR) {
            return LoadingState.ERROR
        }

        if (firstLoadingState == LoadingState.LOADING && secondLoadingState == LoadingState.LOADING) {
            return LoadingState.LOADING
        }

        if (firstLoadingState == LoadingState.SUCCESS && secondLoadingState == LoadingState.SUCCESS) {
            return LoadingState.SUCCESS
        }

        return LoadingState.IDLE
    }
}
