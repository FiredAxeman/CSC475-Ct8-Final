package com.example.csc475_ct8_final

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val apiService = NetworkModule.provideApiService()
            val database = RecipeDatabase.getDatabase(applicationContext)
            val repository = RecipeRepository(apiService, database.recipeDao())
            
            repository.syncFavorites()
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
