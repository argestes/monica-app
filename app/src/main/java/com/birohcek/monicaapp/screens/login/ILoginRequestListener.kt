package com.birohcek.monicaapp.screens.login

public fun interface ILoginRequestListener {
    fun onLoginRequest(username: String, password: String, serverAddress: String)
}
