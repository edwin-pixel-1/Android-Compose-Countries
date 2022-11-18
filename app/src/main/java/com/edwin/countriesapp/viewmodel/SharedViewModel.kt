package com.edwin.countriesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.edwin.countriesapp.viewmodel.uistate.TopBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _topBarState: MutableStateFlow<TopBarState> = MutableStateFlow(TopBarState())
    val topBarState: StateFlow<TopBarState>
        get() = _topBarState.asStateFlow()

    fun updateTopBar(title: String, navigationIconEnabled: Boolean) {
        _topBarState.value = _topBarState.value.copy(
            title = title,
            navigationIconEnabled = navigationIconEnabled
        )
    }
}