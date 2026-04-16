package com.abid.recipeapp.di

import com.abid.recipeapp.data.repository.RecipeRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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


}