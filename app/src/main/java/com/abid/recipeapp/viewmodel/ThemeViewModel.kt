package com.abid.recipeapp.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.ui.theme.AppThemeMode
import com.abid.recipeapp.ui.theme.toAppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(private val dataStore: DataStore<Preferences>) :
    ViewModel() {

    private val THEME_KEY = stringPreferencesKey("app_theme")

    private val _state = MutableLiveData(AppThemeMode.SYSTEM)
    val state: LiveData<AppThemeMode> = _state

    init {
        initCurrentTheme()
    }

    fun setTheme(appThemeMode: AppThemeMode) {
        viewModelScope.launch {
            _state.value = appThemeMode
            dataStore.edit { prefs ->
                prefs[THEME_KEY] = appThemeMode.name
            }
        }
    }

    private fun initCurrentTheme() {
        viewModelScope.launch {
            val currentTheme = dataStore.data.first()[THEME_KEY].toAppTheme()
            _state.value = currentTheme
        }
    }
}