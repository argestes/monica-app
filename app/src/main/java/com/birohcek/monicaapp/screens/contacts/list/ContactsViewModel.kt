package com.birohcek.monicaapp.screens.contacts.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.birohcek.monicaapp.models.ContactModel
import com.birohcek.monicaapp.repository.ContactsRepository
import com.birohcek.monicaapp.screens.login.LoadingState
import kotlinx.coroutines.flow.map

public class ContactsViewModel @ViewModelInject constructor(private val contactsRepository: ContactsRepository) :
    ViewModel() {

    data class ContactsState(
        val loadingState: LoadingState,
        val errorMessage: String?,
        val list: List<ContactModel>
    )

    var called = false
    fun getContactsOfId(id: Long): LiveData<ContactsState> {
        return contactsRepository.getContactsOf(id).map {
            ContactsState(it.loadingState, it.error?.message ?: "", it.data ?: listOf())
        }.asLiveData()
    }
}