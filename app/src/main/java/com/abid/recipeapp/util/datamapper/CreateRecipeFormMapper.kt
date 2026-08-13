import com.abid.recipeapp.data.local.entities.CreateRecipeFormStateEntity
import com.abid.recipeapp.data.local.entities.IngredientFormStateEntity
import com.abid.recipeapp.data.local.entities.NutritionFormStateEntity
import com.abid.recipeapp.data.model.CreateRecipeFormStateModel
import com.abid.recipeapp.data.model.IngredientFormStateModel
import com.abid.recipeapp.data.model.NutritionFormStateModel
import java.util.UUID

fun CreateRecipeFormStateModel.toEntity(): CreateRecipeFormStateEntity =
    CreateRecipeFormStateEntity(
        id = id.ifBlank { UUID.randomUUID().toString() },
        title = title,
        description = description,
        image = image,
        prepTime = prepTime,
        cookTime = cookTime,
        servings = servings,
        difficulty = difficulty,
        category = category,
        cuisine = cuisine,
        author = author,
        createdAt = createdAt,
        tags = tags,
        ingredients = ingredients.map { it.toEntity() },
        instructions = instructions,
        nutrition = nutrition.toEntity()
    )

fun CreateRecipeFormStateEntity.toModel(): CreateRecipeFormStateModel = CreateRecipeFormStateModel(
    id = id,
    title = title,
    description = description,
    image = image,
    prepTime = prepTime,
    cookTime = cookTime,
    servings = servings,
    difficulty = difficulty,
    category = category,
    cuisine = cuisine,
    author = author,
    createdAt = createdAt,
    tags = tags,
    ingredients = ingredients.map { it.toModel() },
    instructions = instructions,
    nutrition = nutrition.toModel()
)

fun IngredientFormStateModel.toEntity() = IngredientFormStateEntity(item, amount)
fun IngredientFormStateEntity.toModel() = IngredientFormStateModel(item, amount)

fun NutritionFormStateModel.toEntity() = NutritionFormStateEntity(calories, protein, carbs, fat)
fun NutritionFormStateEntity.toModel() = NutritionFormStateModel(calories, protein, carbs, fat)