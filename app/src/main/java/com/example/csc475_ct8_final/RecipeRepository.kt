package com.example.csc475_ct8_final

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class RecipeRepository(
    private val apiService: RecipeApiService,
    private val recipeDao: RecipeDao
) {
    fun getLocalRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    fun fetchRecipes(query: String): Flow<NetworkResult<List<Recipe>>> = flow {
        emit(NetworkResult.Loading())
        
        val localRecipes = recipeDao.getAllRecipes().first()
        if (localRecipes.isNotEmpty() && query.isBlank()) {
            emit(NetworkResult.Success(localRecipes.map { it.toDomainModel() }))
        }

        try {
            val response = apiService.searchRecipes(query = query)
            if (response.isSuccessful && response.body() != null) {
                val recipes = response.body()!!.results.map { it.toDomainModel() }
                recipeDao.insertRecipes(recipes.map { it.toEntity() })
                emit(NetworkResult.Success(recipes))
            } else {
                emit(NetworkResult.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error("Network failure. Details: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

    fun fetchRecipeDetails(id: Int): Flow<NetworkResult<Recipe>> = flow {
        emit(NetworkResult.Loading())
        try {
            val response = apiService.getRecipeInformation(id)
            if (response.isSuccessful && response.body() != null) {
                val recipe = response.body()!!.toDomainModel()
                recipeDao.insertRecipes(listOf(recipe.toEntity()))
                emit(NetworkResult.Success(recipe))
            } else {
                emit(NetworkResult.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error("Failed to fetch details: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun toggleFavorite(recipeId: Int, isFavorite: Boolean) {
        recipeDao.updateFavorite(recipeId, !isFavorite)
    }

    suspend fun ensureSampleData() {
        val existing = recipeDao.getAllRecipes().first()
        if (existing.isEmpty()) {
            recipeDao.insertRecipes(SampleData.recipes.map { it.toEntity() })
        }
    }

    suspend fun syncFavorites() {
        val favorites = recipeDao.getAllRecipes().first().filter { it.isFavorite }
        favorites.forEach { entity ->
            try {
                val response = apiService.getRecipeInformation(entity.id)
                if (response.isSuccessful && response.body() != null) {
                    val updatedRecipe = response.body()!!.toDomainModel().copy(isFavorite = true)
                    recipeDao.insertRecipes(listOf(updatedRecipe.toEntity()))
                }
            } catch (e: Exception) {
                // Log error or handle retry
            }
        }
    }

    fun getGroceryItems(): Flow<List<GroceryItem>> = recipeDao.getAllGroceryItems()

    suspend fun addIngredientsToGroceryList(ingredients: List<String>) {
        val items = ingredients.map { GroceryItem(name = it) }
        recipeDao.insertGroceryItems(items)
    }

    suspend fun updateGroceryItem(id: Int, isChecked: Boolean) {
        recipeDao.updateGroceryItemStatus(id, isChecked)
    }

    suspend fun deleteGroceryItem(id: Int) {
        recipeDao.deleteGroceryItem(id)
    }
}
