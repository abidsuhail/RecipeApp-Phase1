package com.abid.recipeapp

import com.abid.recipeapp.data.local.entities.RecipeCuisinesEntity
import com.abid.recipeapp.data.model.RecipeCuisinesDataModel
import com.abid.recipeapp.util.datamapper.toEntity
import com.abid.recipeapp.util.datamapper.toModel
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `RecipeCuisinesDataModel toEntity maps fields correctly`() {
        val model = RecipeCuisinesDataModel(id = "1", label = "Italian")

        val entity = model.toEntity()

        assertEquals("1", entity.id)
        assertEquals("Italians", entity.label)
    }

    @Test
    fun `RecipeCuisinesEntity toModel maps fields correctly`() {
        val entity = RecipeCuisinesEntity(id = "1", label = "Italian")

        val model = entity.toModel()

        assertEquals("1", model.id)
        assertEquals("Italian", model.label)
    }
}