package com.birohcek.monicaapp.screens.contacts.single

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.birohcek.monicaapp.models.ContactModel
import com.birohcek.monicaapp.models.RelationMainType
import java.util.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

private fun list(
    contactData: ContactModel,
    love: RelationMainType,
    onContactSelected: (Long) -> Unit
): List<Info> {
    return contactData.relationshipsContainer.filter { it.type == love }
        .map {
            Info(it.relationName.capitalize(Locale.getDefault()), it.contactFullName) {
                onContactSelected(
                    it.contactId
                )
            }
        }
}

data class Info(val title: String, val text: String, val onclick: (() -> Unit)? = null)


@Composable
fun SingleContactView(contactData: ContactModel, onContactSelected: (Long) -> Unit) {

    var selectedTab by remember { mutableStateOf(0) }
    val titles = listOf("Info", "Log")

    Column(Modifier.fillMaxSize()) {
        PaddingForCard { PersonCard(contactData) }
        TabRow(selectedTabIndex = selectedTab, backgroundColor = Color.Transparent) {
            titles.forEachIndexed { index, title ->
                Tab(text = { Text(text = title) },
                    selected = index == selectedTab,
                    onClick = { selectedTab = index })
            }
        }

        when (selectedTab) {
            0 -> {
                infoPanel(contactData, onContactSelected)
            }
            1 -> Column(Modifier.fillMaxSize()) {
                Text(text = "Info")
            }
        }
    }
}

@Composable
private fun infoPanel(
    contactData: ContactModel,
    onContactSelected: (Long) -> Unit
) {
    val love = RelationMainType.LOVE

    val loveRels = list(contactData, love, onContactSelected)
    val familyRels = list(contactData, RelationMainType.FAMILY, onContactSelected)
    val friends = list(contactData, RelationMainType.FRIEND, onContactSelected)
    val workFriends = list(contactData, RelationMainType.WORK, onContactSelected)
    val pets = contactData.petModels.map { Info(it.type.capitalize(), it.name) }


    val contactInfos = contactData.contactInfos.map {
        Info(it.contactFieldName.capitalize(), it.data)
    }

    val addresses = contactData.addresses.map {
        Info(it.name ?: "Unnamed", it.asText())
    }

    val howYouMetInfos: List<Info> = contactData.howYouMet?.let {
        listOfNotNull(
            it.through?.let {
                Info("Met through", it.firstName) {
                    onContactSelected(it.id)
                }
            },
            it.date?.let { date ->
                Info("Met on", date.asDate())
            },
            it.desc?.let { it1 -> Info("At", it1) }
        )
    }.orEmpty()

    val workInfos = contactData.career?.let {
        listOf(
            Info("Job", it.job),
            Info("Company", it.company)
        )
    }.orEmpty()


    ScrollableColumn(
        Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        PaddingForCard { InfoCard("Love Relations", loveRels) }
        PaddingForCard { InfoCard("Family Relations", familyRels) }
        PaddingForCard { InfoCard("Friends", friends) }
        PaddingForCard { InfoCard("Work Friends", workFriends) }
        PaddingForCard { InfoCard("Pets", pets) }
        PaddingForCard { InfoCard("Contact Information", contactInfos) }
        PaddingForCard { InfoCard("Addresses", addresses) }
        PaddingForCard { InfoCard("How You Met", howYouMetInfos) }
        PaddingForCard { InfoCard("Work Information ", workInfos) }
        PaddingForCard { InfoCard("Food Preferences") }
    }
}

@Composable
fun PaddingForCard(cardThing: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.padding(
            PaddingValues(
                16.dp,
                16.dp,
                16.dp
            )
        )
    ) {
        cardThing()
    }
}
