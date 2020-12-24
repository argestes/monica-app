package com.birohcek.monicaapp.screens.contacts.single

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.birohcek.monicaapp.ui.monicaTypography

@Composable
fun InfoCard(title: String, infos: List<Info> = listOf(), onAdd: (() -> Unit)? = null) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSizeConstraints(minHeight = 120.dp)
    ) {
        Column {
            Row(modifier = Modifier.padding(PaddingValues(18.dp).copy(bottom = 6.dp))) {
                Text(text = title, style = monicaTypography.h5)

                onAdd?.let {

                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.background(Color(0xF2, 0xF3, 0xF6)).height(2.dp).fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))

            infos.forEach { info ->
                val modifier = if (info.onclick != null) {
                    Modifier.fillMaxWidth().clickable(onClick = info.onclick)
                } else {
                    Modifier.fillMaxWidth()
                }

                ListItem(
                    text = { Text(text = info.title) },
                    secondaryText = { Text(text = info.text) },
                    modifier = modifier
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}