package com.birohcek.monicaapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.birohcek.monicaapp.screens.login.LoadingState

@Composable
public fun DefaultAppBar(title: String, loadingStatus: LoadingState?) {
    TopAppBar {
        Text(
            text = title,
            Modifier.padding(PaddingValues(start = 8.dp, top = 8.dp, bottom = 8.dp)).align(
                Alignment.CenterVertically
            )
        )

        if (loadingStatus == LoadingState.LOADING) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.padding(PaddingValues(end = 8.dp))
                    .align(Alignment.CenterVertically)
            )
        }
    }
}
