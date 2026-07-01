package com.example.csc475_ct8_final

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponseDto(
    val results: List<RecipeDto> = emptyList()
)

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val summary: String = "",
    val readyInMinutes: Int = 0,
    val image: String = "",
    val instructions: String? = null,
    val extendedIngredients: List<IngredientDto> = emptyList()
)

@Serializable
data class IngredientDto(
    val original: String
)

fun RecipeDto.toDomainModel(): Recipe {
    return Recipe(
        id = id,
        title = title,
        description = summary.replace(Regex("<[^>]*>"), ""), 
        ingredients = extendedIngredients.map { it.original },
        instructions = instructions?.replace(Regex("<[^>]*>"), "") ?: "",
        cookingTime = "$readyInMinutes mins",
        isFavorite = false
    )
}
