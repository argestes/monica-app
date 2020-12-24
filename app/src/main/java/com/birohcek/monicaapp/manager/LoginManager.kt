package com.birohcek.monicaapp.manager

import javax.inject.Inject

class LoginManager @Inject constructor() : ILoginManager {
    override fun isLoggedIn(): Boolean {
        return false
    }
}