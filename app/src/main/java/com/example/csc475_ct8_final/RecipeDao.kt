package com.example.csc475_ct8_final

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM recipes")
    suspend fun deleteAll()

    @Query("SELECT * FROM grocery_items")
    fun getAllGroceryItems(): Flow<List<GroceryItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroceryItem(item: GroceryItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroceryItems(items: List<GroceryItem>)

    @Query("UPDATE grocery_items SET isChecked = :isChecked WHERE id = :id")
    suspend fun updateGroceryItemStatus(id: Int, isChecked: Boolean)

    @Query("DELETE FROM grocery_items WHERE id = :id")
    suspend fun deleteGroceryItem(id: Int)

    @Query("DELETE FROM grocery_items")
    suspend fun deleteAllGroceryItems()
}
