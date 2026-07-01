package com.example.csc475_ct8_final

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _networkResult = MutableStateFlow<NetworkResult<List<Recipe>>>(NetworkResult.Loading())
    val networkResult: StateFlow<NetworkResult<List<Recipe>>> = _networkResult

    private val _selectedRecipeResult = MutableStateFlow<NetworkResult<Recipe>>(NetworkResult.Loading())
    val selectedRecipeResult: StateFlow<NetworkResult<Recipe>> = _selectedRecipeResult

    val recipes: StateFlow<List<Recipe>> = repository.getLocalRecipes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val filteredRecipes: StateFlow<List<Recipe>> = combine(
        recipes,
        _searchQuery
    ) { recipes, query ->
        if (query.isBlank()) {
            recipes
        } else {
            recipes.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val favoriteRecipes: StateFlow<List<Recipe>> = recipes.map { list ->
        list.filter { it.isFavorite }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        searchRecipes("popular")
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            repository.fetchRecipes(query).collect { result ->
                _networkResult.value = result
            }
        }
    }

    fun selectRecipe(id: Int) {
        viewModelScope.launch {
            repository.fetchRecipeDetails(id).collect { result ->
                _selectedRecipeResult.value = result
            }
        }
    }

    fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            repository.toggleFavorite(recipe.id, recipe.isFavorite)
        }
    }
}

class RecipeViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
