package com.birohcek.monicaapp.screens.accounts

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.birohcek.monicaapp.models.AccountModel
import com.birohcek.monicaapp.persistance.AccountDao
import kotlinx.coroutines.flow.Flow

class AccountsViewModel @ViewModelInject constructor(private val accountsDao: AccountDao) : ViewModel() {

    fun getAccounts() : Flow<List<AccountModel>> {
        return accountsDao.getAll()
    }
}
