package com.edwin.countriesapp.ui.common

sealed class UIEvent {
    data class NavigateTo(val destination: String) : UIEvent()
    data class UpdateTopBar(val title: String, val navigationIconEnabled: Boolean) : UIEvent()
    object NavigateUp : UIEvent()
}