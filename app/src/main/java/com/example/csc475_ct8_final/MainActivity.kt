package com.example.csc475_ct8_final

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: RecipeAdapter
    private var allRecipes: List<Recipe> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        allRecipes = RecipeRepository.getRecipes()
        
        adapter = RecipeAdapter { recipe ->
            RecipeRepository.toggleFavorite(recipe.id)
            // Update the list to reflect the favorite change
            updateList(findViewById<SearchView>(R.id.searchView).query.toString())
        }

        findViewById<RecyclerView>(R.id.recipeRecyclerView).adapter = adapter
        adapter.submitList(allRecipes)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                updateList(newText ?: "")
                return true
            }
        })
    }

    private fun updateList(query: String) {
        val filteredList = if (query.isEmpty()) {
            allRecipes
        } else {
            allRecipes.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
            }
        }
        // Create a copy to trigger DiffUtil correctly if needed, 
        // though here we are filtering and getting a new list anyway.
        adapter.submitList(filteredList.map { it.copy() })
    }
}
