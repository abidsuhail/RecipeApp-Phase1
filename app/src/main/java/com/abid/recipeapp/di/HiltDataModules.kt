package com.abid.recipeapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.abid.recipeapp.data.local.RecipeDao
import com.abid.recipeapp.data.local.RecipeDatabase
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.util.Constants
import com.abid.recipeapp.util.Constants.SETTINGS_DATASTORE_NAME
import com.abid.recipeapp.util.network.NetworkMonitor
import com.abid.recipeapp.util.network.NetworkMonitorImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideFirestoreDb(): FirebaseFirestore {
        val db = FirebaseFirestore.getInstance()
        return db
    }

    @Singleton
    @Provides
    fun provideRecipeRepository(db: FirebaseFirestore, recipeDao: RecipeDao): RecipeRepository {
        return RecipeRepository(db, recipeDao)
    }

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(SETTINGS_DATASTORE_NAME) }
    )


    @Provides
    @Singleton
    fun provideRecipeDatabase(
        @ApplicationContext context: Context
    ): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            Constants.RECIPE_DB_NAME
        )
            .fallbackToDestructiveMigration()   // remove for production
            // .addMigrations(MIGRATION_1_2)        // add real migrations here instead
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao {
        return recipeDatabase.recipeDao()
    }


    @Provides
    @Singleton
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor {
        return NetworkMonitorImpl(context)
    }
}