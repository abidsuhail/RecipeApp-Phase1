package com.abid.recipeapp.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.ui.state.SettingsUiState
import com.abid.recipeapp.ui.theme.AppThemeMode
import com.abid.recipeapp.ui.theme.toAppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val dataStore: DataStore<Preferences>) :
    ViewModel() {


    private val THEME_KEY = stringPreferencesKey("app_theme")
    private val _state = MutableStateFlow(SettingsUiState(AppThemeMode.SYSTEM))
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    init {
        initTheme()
    }

    private fun initTheme() {
        viewModelScope.launch {
            val currentTheme = dataStore.data.first()[THEME_KEY].toAppTheme()
            _state.update { it.copy(currentTheme = currentTheme) }
        }
    }

    fun setCurrentThemeOption(appThemeMode: AppThemeMode) {
        _state.update { it.copy(currentTheme = appThemeMode) }
    }
}