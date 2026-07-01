package com.example.csc475_ct8_final

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val apiService = NetworkModule.provideApiService()
        val database = RecipeDatabase.getDatabase(this)
        val repository = RecipeRepository(apiService, database.recipeDao())
        val factory = RecipeViewModelFactory(repository)

        setupPeriodicSync()

        setContent {
            HCookBookTheme {
                HCookBookApp(factory)
            }
        }
    }

    private fun setupPeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "RecipeSync",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }
}

@Composable
fun HCookBookApp(factory: RecipeViewModelFactory) {
    val viewModel: RecipeViewModel = viewModel(factory = factory)
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "discovery",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("discovery") {
                DiscoveryScreen(viewModel, onRecipeClick = { id ->
                    navController.navigate("recipeDetail/$id")
                })
            }
            composable("favorites") {
                FavoritesScreen(viewModel, onRecipeClick = { id ->
                    navController.navigate("recipeDetail/$id")
                })
            }
            composable("grocery") {
                GroceryListScreen(viewModel)
            }
            composable(
                route = "recipeDetail/{recipeId}",
                arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("recipeId") ?: 0
                RecipeDetailScreen(id, viewModel, onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Discovery Tab") },
            label = { Text("Discovery") },
            selected = currentRoute == "discovery",
            onClick = {
                navController.navigate("discovery") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites Tab") },
            label = { Text("Favorites") },
            selected = currentRoute == "favorites",
            onClick = {
                navController.navigate("favorites") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Grocery List Tab") },
            label = { Text("Grocery") },
            selected = currentRoute == "grocery",
            onClick = {
                navController.navigate("grocery") {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun DiscoveryScreen(viewModel: RecipeViewModel, onRecipeClick: (Int) -> Unit) {
    val recipes by viewModel.filteredRecipes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val networkResult by viewModel.networkResult.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .semantics { contentDescription = "Search bar for recipes" },
            placeholder = { Text("Search recipes...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                Button(onClick = { viewModel.searchRecipes(searchQuery) }) {
                    Text("Fetch")
                }
            }
        )
        
        Box(modifier = Modifier.weight(1f)) {
            RecipeGrid(recipes = recipes, onRecipeClick = onRecipeClick, onFavoriteClick = { viewModel.toggleFavorite(it) })
            
            when (networkResult) {
                is NetworkResult.Loading -> {
                    if (recipes.isEmpty()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .semantics { contentDescription = "Loading recipes" }
                        )
                    }
                }
                is NetworkResult.Error -> {
                    val errorMessage = networkResult.message ?: "Unknown Error"
                    Log.e("DiscoveryScreen", "Network error: $errorMessage")
                    // Placeholder for FirebaseCrashlytics.getInstance().log(errorMessage)
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
                else -> {}
            }
        }
    }
}

@Composable
fun FavoritesScreen(viewModel: RecipeViewModel, onRecipeClick: (Int) -> Unit) {
    val favorites by viewModel.favoriteRecipes.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "My Favorites",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        RecipeGrid(recipes = favorites, onRecipeClick = onRecipeClick, onFavoriteClick = { viewModel.toggleFavorite(it) })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(recipeId: Int, viewModel: RecipeViewModel, onBack: () -> Unit) {
    val detailResult by viewModel.selectedRecipeResult.collectAsState()
    
    LaunchedEffect(recipeId) {
        viewModel.selectRecipe(recipeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to list")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            when (detailResult) {
                is NetworkResult.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is NetworkResult.Error -> {
                    Log.e("RecipeDetail", "Error fetching detail: ${detailResult.message}")
                    Text(text = detailResult.message ?: "Error", modifier = Modifier.align(Alignment.Center))
                }
                is NetworkResult.Success -> {
                    val recipe = detailResult.data!!
                    Column {
                        var selectedTab by remember { mutableIntStateOf(0) }
                        val tabs = listOf("Ingredients", "Instructions")
                        
                        Text(
                            text = recipe.title,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp)
                        )

                        TabRow(selectedTabIndex = selectedTab) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    text = { Text(title) }
                                )
                            }
                        }

                        when (selectedTab) {
                            0 -> IngredientsList(recipe.ingredients) {
                                viewModel.addIngredientsToGrocery(recipe.ingredients)
                            }
                            1 -> InstructionsView(recipe.instructions)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientsList(ingredients: List<String>, onAddAllToGrocery: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = onAddAllToGrocery,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Add All to Grocery List")
        }
        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            items(ingredients) { ingredient ->
                Text(text = "• $ingredient", modifier = Modifier.padding(vertical = 4.dp))
            }
            if (ingredients.isEmpty()) {
                item { Text("No ingredients listed.") }
            }
        }
    }
}

@Composable
fun InstructionsView(instructions: String) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = instructions.ifBlank { "No instructions available." })
    }
}

@Composable
fun GroceryListScreen(viewModel: RecipeViewModel) {
    val items by viewModel.groceryItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Grocery List", style = MaterialTheme.typography.headlineMedium)
        
        if (items.isEmpty()) {
            Text(text = "Your list is empty.", modifier = Modifier.padding(top = 16.dp))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
                items(items) { item ->
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material3.Checkbox(
                            checked = item.isChecked,
                            onCheckedChange = { viewModel.updateGroceryItem(item.id, it) }
                        )
                        Text(
                            text = item.name,
                            modifier = Modifier.weight(1f).padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(onClick = { viewModel.deleteGroceryItem(item.id) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RecipeGrid(recipes: List<Recipe>, onRecipeClick: (Int) -> Unit, onFavoriteClick: (Recipe) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipes) { recipe ->
            RecipeCard(recipe = recipe, onRecipeClick = onRecipeClick, onFavoriteClick = onFavoriteClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(recipe: Recipe, onRecipeClick: (Int) -> Unit, onFavoriteClick: (Recipe) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .semantics { contentDescription = "Recipe card for ${recipe.title}" },
        onClick = { onRecipeClick(recipe.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = recipe.cookingTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            IconButton(
                onClick = { onFavoriteClick(recipe) },
                modifier = Modifier.padding(top = 4.dp)
            ) {
                val isFavDesc = if (recipe.isFavorite) "Remove from favorites" else "Add to favorites"
                Icon(
                    imageVector = if (recipe.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = isFavDesc,
                    tint = if (recipe.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
