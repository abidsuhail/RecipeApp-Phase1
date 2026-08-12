package com.abid.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.util.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val repository: RecipeRepository
) : ViewModel() {

    init {
        observeConnectivityAndSync()
    }

    private fun observeConnectivityAndSync() {
        viewModelScope.launch {
            networkMonitor.observeConnectivity()
                .filter { it }
                .collect {
                    Timber.e("Doing sync")
                    repository.syncPendingFavs()
                }
        }
    }
}