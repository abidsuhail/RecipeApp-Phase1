package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abid.recipeapp.ui.state.SettingsUiState
import com.abid.recipeapp.ui.theme.AppThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() :
    ViewModel() {

    private val _state = MutableLiveData(SettingsUiState(AppThemeMode.SYSTEM))
    val state: LiveData<SettingsUiState> = _state

    fun setCurrentThemeOption(appThemeMode: AppThemeMode) {
        _state.value = _state.value?.copy(currentTheme = appThemeMode)
    }
}