package com.abid.recipeapp.util.network

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>
}