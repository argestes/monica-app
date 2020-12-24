package com.birohcek.monicaapp.networking

import javax.inject.Inject

interface IBaseUrlProvider {

    fun provideBaseUrl(): String
}

class DefaultBaseUrlProvider @Inject constructor() : IBaseUrlProvider{
    override fun provideBaseUrl(): String {
        return "https://app.monicahq.com"
    }
}


