package com.example.csc475_ct8_final

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private val onFavoriteClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view, onFavoriteClick)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RecipeViewHolder(
        itemView: View,
        private val onFavoriteClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.recipeTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.recipeDescription)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)

        fun bind(recipe: Recipe) {
            titleTextView.text = recipe.title
            descriptionTextView.text = recipe.description
            
            val icon = if (recipe.isFavorite) {
                android.R.drawable.btn_star_big_on
            } else {
                android.R.drawable.btn_star_big_off
            }
            favoriteButton.setImageResource(icon)

            favoriteButton.setOnClickListener {
                onFavoriteClick(recipe)
            }
        }
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}
