package com.abid.recipeapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.abid.recipeapp.data.repository.RecipeRepository
import com.abid.recipeapp.util.Constants.SETTINGS_DATASTORE_NAME
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
    fun provideRecipeRepository(db: FirebaseFirestore): RecipeRepository {
        return RecipeRepository(db)
    }

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(SETTINGS_DATASTORE_NAME) }
    )


}