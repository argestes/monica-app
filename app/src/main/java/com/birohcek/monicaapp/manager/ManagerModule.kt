package com.birohcek.monicaapp.manager

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class ManagerModule {

    @Binds
    abstract fun loginManager(loginManager: LoginManager): ILoginManager
}