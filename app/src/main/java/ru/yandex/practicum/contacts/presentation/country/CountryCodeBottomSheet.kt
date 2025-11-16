package ru.yandex.practicum.contacts.presentation.country

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.yandex.practicum.contacts.R
import ru.yandex.practicum.contacts.data.models.CountryCode
import ru.yandex.practicum.contacts.presentation.ui.components.CommonBottomSheet

@Composable
fun CountryCodeBottomSheet(
    selectedCodes: Set<CountryCode>,
    onCodesSelected: (Set<CountryCode>) -> Unit,
    onDismiss: () -> Unit
) {
    CommonBottomSheet(
        title = stringResource(R.string.filter_by_country_code),
        items = CountryCode.COMMON_CODES,
        selectedItems = selectedCodes,
        onItemsSelected = onCodesSelected,
        onDismiss = onDismiss
    ) { countryCode, isSelected ->

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(countryCode.code)
                Text(
                    countryCode.country,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
