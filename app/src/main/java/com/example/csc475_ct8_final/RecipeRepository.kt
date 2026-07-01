package com.example.csc475_ct8_final

object RecipeRepository {
    private val recipes = mutableListOf(
        Recipe(
            1,
            "Spaghetti Carbonara",
            "Classic Italian pasta dish.",
            listOf("Spaghetti", "Eggs", "Pecorino Romano", "Guanciale", "Black Pepper"),
            "1. Boil pasta. 2. Fry guanciale. 3. Mix eggs and cheese. 4. Combine all with pasta water."
        ),
        Recipe(
            2,
            "Chicken Curry",
            "Flavorful and spicy curry.",
            listOf("Chicken", "Onion", "Garlic", "Ginger", "Curry Powder", "Coconut Milk"),
            "1. Sauté aromatics. 2. Add chicken and brown. 3. Add spices and coconut milk. 4. Simmer until cooked."
        ),
        Recipe(
            3,
            "Greek Salad",
            "Fresh and healthy salad.",
            listOf("Tomatoes", "Cucumber", "Red Onion", "Feta Cheese", "Olives", "Olive Oil"),
            "1. Chop vegetables. 2. Combine in a bowl. 3. Add feta and olives. 4. Drizzle with olive oil."
        ),
        Recipe(
            4,
            "Beef Tacos",
            "Quick and easy tacos.",
            listOf("Ground Beef", "Taco Shells", "Lettuce", "Cheese", "Salsa", "Sour Cream"),
            "1. Brown beef with taco seasoning. 2. Warm taco shells. 3. Assemble tacos with toppings."
        ),
        Recipe(
            5,
            "Pancakes",
            "Fluffy breakfast pancakes.",
            listOf("Flour", "Milk", "Egg", "Baking Powder", "Sugar", "Butter"),
            "1. Whisk wet ingredients. 2. Mix in dry ingredients. 3. Cook on a griddle until bubbly. 4. Flip and cook until golden."
        )
    )

    fun getRecipes(): List<Recipe> = recipes

    fun toggleFavorite(recipeId: Int) {
        recipes.find { it.id == recipeId }?.let {
            it.isFavorite = !it.isFavorite
        }
    }
}
