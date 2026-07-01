package com.example.csc475_ct8_final

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: String,
    val cookingTime: String,
    val isFavorite: Boolean
)

fun RecipeEntity.toDomainModel(): Recipe {
    return Recipe(
        id = id,
        title = title,
        description = description,
        ingredients = ingredients,
        instructions = instructions,
        cookingTime = cookingTime,
        isFavorite = isFavorite
    )
}

fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        title = title,
        description = description,
        ingredients = ingredients,
        instructions = instructions,
        cookingTime = cookingTime,
        isFavorite = isFavorite
    )
}
