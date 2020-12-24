package com.birohcek.monicaapp.screens.login

import androidx.compose.foundation.ClickableText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*



enum class LoadingState {
    LOADING,
    ERROR,
    IDLE,
    SUCCESS
}

class JobState(val jobState: LoadingState, val error: Throwable?, val accountId: Long? = null)

@Composable
fun LoginScreen(navHostController: NavHostController, loginViewModel: LoginViewModel) {
    val state = loginViewModel.state

    state.value.data?.let {
        navHostController.navigate("account/$it/contacts")
        return
    }

    return Surface {
        Box {
            Column(modifier = Modifier.align(Alignment.Center)) {
                LoginView { username, password, serverAddress ->
                    loginViewModel.onLogin(
                        username = username,
                        password = password,
                        serverAddress = serverAddress
                    )
                }
                state.value.error?.let {
                    Text(text = it, color = Color.Red, modifier = Modifier.fillMaxWidth(0.5f))
                }
            }

            if (state.value.loadingState == LoadingState.LOADING) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}


@Composable
fun LoginView(loginRequestListener: ILoginRequestListener? = null) {
    var username: String by savedInstanceState { "" }
    var password: String by savedInstanceState { "" }
    var advanced: Boolean by savedInstanceState { false }
    var serverAddress: String by savedInstanceState { "http://app.monicahq.com" }

    Column {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            label = { Text(text = "Username") }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            label = { Text(text = "Password") }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = { loginRequestListener?.onLoginRequest(username, password, serverAddress) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {

        }

        if (advanced) {
            OutlinedTextField(
                value = serverAddress,
                onValueChange = { serverAddress = it },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                label = { Text(text = "Server Address") }
            )
        } else {
            Spacer(modifier = Modifier.height(12.dp))
            ClickableText(
                text = AnnotatedString("Advanced"),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { advanced = true }
            )
        }
    }
}