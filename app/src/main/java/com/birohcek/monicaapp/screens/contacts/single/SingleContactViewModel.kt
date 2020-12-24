package com.birohcek.monicaapp.screens.contacts.single

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.birohcek.monicaapp.models.ContactModel
import com.birohcek.monicaapp.persistance.AccountDao
import com.birohcek.monicaapp.persistance.StoreModule
import com.birohcek.monicaapp.repository.ContactsRepository
import com.birohcek.monicaapp.screens.login.LoadingState
import com.dropbox.android.external.store4.StoreRequest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class SingleContactViewModel @ViewModelInject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    data class SingleContactState(val loadingState: LoadingState, val error: String?, val data: ContactModel?)

    fun getContact(accountId: Long, contactId: Long): LiveData<SingleContactState> {
        return contactsRepository.getSingleContactOf(accountId, contactId).map {
            SingleContactState(it.loadingState, it.error?.message ?: "", it.data)
        }.asLiveData()
    }
}