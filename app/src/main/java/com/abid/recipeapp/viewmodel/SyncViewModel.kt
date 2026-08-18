package com.abid.recipeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.util.network.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val repository: RecipeRepository
) : ViewModel() {

    private val handler = CoroutineExceptionHandler { _, e ->
        Timber.e("Sync failed: $e")
    }

    val isConnected: StateFlow<Boolean> = networkMonitor.observeConnectivity()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = networkMonitor.isConnected()
        )
    
    init {
        observeConnectivityAndSync()
    }


    private fun observeConnectivityAndSync() {
        viewModelScope.launch {
            networkMonitor.observeConnectivity()
                .filter { it }
                .collect {
                    Timber.e("Doing sync")
                    supervisorScope {
                        launch(handler + CoroutineName("SyncPendingFav")) {
                            repository.syncPendingFavs()
                        }
                        launch(handler + CoroutineName("SyncPendingRecipes")) {
                            repository.syncAllPendingRecipes()
                        }
                    }
                }

        }
    }
}