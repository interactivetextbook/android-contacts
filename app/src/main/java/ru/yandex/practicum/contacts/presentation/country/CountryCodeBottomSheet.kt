package ru.yandex.practicum.contacts.presentation.country

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
        CountryCodeOption(
            countryCode = countryCode,
            isSelected = isSelected
        )
    }
}

@Composable
private fun CountryCodeOption(
    countryCode: CountryCode,
    isSelected: Boolean
) {

}
