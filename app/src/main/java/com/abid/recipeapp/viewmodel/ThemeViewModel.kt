package com.abid.recipeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abid.recipeapp.ui.theme.AppThemeMode

class ThemeViewModel : ViewModel() {

    private val _state = MutableLiveData(AppThemeMode.SYSTEM)
    val state: LiveData<AppThemeMode> = _state
}