package ru.yandex.practicum.contacts.presentation.messengers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import ru.yandex.practicum.contacts.R
import ru.yandex.practicum.contacts.data.models.MessagingApp
import ru.yandex.practicum.contacts.presentation.ui.components.CommonBottomSheet

@Composable
fun MessengersBottomSheet(
    selectedApps: Set<MessagingApp>,
    onAppsSelected: (Set<MessagingApp>) -> Unit,
    onDismiss: () -> Unit
) {
    CommonBottomSheet(
        title = stringResource(R.string.filter_by_messaging_app),
        items = MessagingApp.entries,
        selectedItems = selectedApps,
        onItemsSelected = onAppsSelected,
        onDismiss = onDismiss
    ) { app, _ ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onAppsSelected(setOf(app)) }
                .padding(16.dp)
        ) {
            Text(
                text = app.name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
