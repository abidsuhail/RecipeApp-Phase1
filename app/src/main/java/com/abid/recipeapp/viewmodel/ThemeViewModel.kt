package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abid.recipeapp.ui.theme.AppThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData(AppThemeMode.SYSTEM)
    val state: LiveData<AppThemeMode> = _state

    fun setTheme(appThemeMode: AppThemeMode) {
        _state.value = appThemeMode
    }
}