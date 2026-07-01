package com.example.csc475_ct8_final

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: String,
    var isFavorite: Boolean = false
)
