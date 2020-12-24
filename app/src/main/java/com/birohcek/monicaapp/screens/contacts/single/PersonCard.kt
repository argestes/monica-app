package com.birohcek.monicaapp.screens.contacts.single

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.birohcek.monicaapp.R
import com.birohcek.monicaapp.models.ContactModel
import com.birohcek.monicaapp.ui.monicaTypography
import dev.chrisbanes.accompanist.glide.GlideImage


@Composable
private fun PersonCardIconText(vectorRes: Int, text: String, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(32.dp)
    ) {
        Image(imageVector = vectorResource(id = vectorRes))
        Text(text = text)
    }
}

@Composable
private fun Avatar(image: String?) {
    Card(
        modifier = Modifier.preferredSize(56.dp),
        contentColor = Color.Black,
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp,
        backgroundColor = Color(55, 55, 55, 120)
    ) {
        image?.let { GlideImage(data = image) }
    }
}


@Composable
fun PersonCard(contactModel: ContactModel) {

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSizeConstraints(minHeight = 120.dp),
    ) {
        Column(modifier = Modifier.padding(PaddingValues(all = 18.dp))) {

            Row {
                Avatar(contactModel.pictureUrl)
                Text(
                    text = contactModel.fullName,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(PaddingValues(start = 12.dp)),
                    style = monicaTypography.h6.copy(fontWeight = FontWeight.Bold),
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(36.dp).padding(PaddingValues(top = 18.dp))
            ) {
                contactModel.birthDate?.let {
                    PersonCardIconText(
                        text = it.asBirthDay(),
                        vectorRes = R.drawable.ic_baseline_cake_24,
                        modifier = Modifier.Companion.weight(1f)
                    )
                }

                PersonCardIconText(
                    text = "12123123",
                    vectorRes = R.drawable.ic_baseline_cake_24,
                    modifier = Modifier.Companion.weight(1f)
                )

                PersonCardIconText(
                    text = "12123123",
                    vectorRes = R.drawable.ic_baseline_cake_24,
                    modifier = Modifier.Companion.weight(1f)
                )
            }
        }
    }
}
