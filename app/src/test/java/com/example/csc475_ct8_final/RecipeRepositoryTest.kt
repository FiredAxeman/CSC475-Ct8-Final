package com.example.csc475_ct8_final

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.mockito.Mockito.mock

class RecipeRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: RecipeApiService
    private val recipeDao: RecipeDao = mock(RecipeDao::class.java)

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val json = Json { ignoreUnknownKeys = true }
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RecipeApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetchRecipes should emit success state with correct data`() = runBlocking {
        // Prepare
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{
                "results": [
                    {
                        "id": 1, 
                        "title": "Mock Spaghetti", 
                        "summary": "Mock Summary", 
                        "readyInMinutes": 15
                    }
                ]
            }""")
        mockWebServer.enqueue(mockResponse)

        val repository = RecipeRepository(apiService, recipeDao)

        // Execute
        val flow = repository.fetchRecipes("spaghetti")
        
        // We take the first mission to verify loading
        val firstResult = flow.first()
        assertTrue(firstResult is NetworkResult.Loading)
        
        // We collect to get the final emission
        var finalResult: NetworkResult<List<Recipe>>? = null
        flow.collect { finalResult = it }

        // Verify
        assertTrue(finalResult is NetworkResult.Success)
        val data = (finalResult as NetworkResult.Success).data
        assertEquals(1, data?.size)
        assertEquals("Mock Spaghetti", data?.get(0)?.title)
        assertEquals("15 mins", data?.get(0)?.cookingTime)
    }

    @Test
    fun `fetchRecipes should emit error state on network failure`() = runBlocking {
        // Prepare
        val mockResponse = MockResponse().setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        val repository = RecipeRepository(apiService, recipeDao)

        // Execute
        var finalResult: NetworkResult<List<Recipe>>? = null
        repository.fetchRecipes("error").collect { finalResult = it }

        // Verify
        assertTrue(finalResult is NetworkResult.Error)
        assertTrue((finalResult as NetworkResult.Error).message != null)
    }
}
