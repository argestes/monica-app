package com.birohcek.monicaapp.repository

import com.birohcek.monicaapp.persistance.AccountDao
import com.birohcek.monicaapp.persistance.StoreModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    fun provideAccountRepository(loginStoreProvider: StoreModule.LoginStoreProvider, accountDao: AccountDao): AccountRepository {
        return AccountRepository(loginStoreProvider, accountDao)
    }
}