package com.example.rigcraft.ui.feature.details

import androidx.lifecycle.SavedStateHandle
import com.example.rigcraft.data.model.ProductDto
import com.example.rigcraft.domain.repository.AuthRepository
import com.example.rigcraft.domain.repository.CartRepository
import com.example.rigcraft.domain.repository.ProductRepository
import com.example.rigcraft.util.Resource
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModelTest {

    private lateinit var viewModel: ProductDetailsViewModel
    private val productRepository = mockk<ProductRepository>()
    private val cartRepository = mockk<CartRepository>()
    private val authRepository = mockk<AuthRepository>()
    private val testDispatcher = StandardTestDispatcher()

    private val productId = "test_product"
    private val product = ProductDto(
        id = productId,
        title = "Test Product",
        price = 100.0,
        images = listOf("image1"),
        stockQuantity = 10
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        every { productRepository.getProductById(productId) } returns flow {
            emit(Resource.Success(product))
        }

        viewModel = ProductDetailsViewModel(
            productRepository,
            cartRepository,
            authRepository,
            SavedStateHandle(mapOf("productId" to productId))
        )

        testDispatcher.scheduler.runCurrent()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addToCart should fail when user is not logged in`() = runTest {
        // Given
        every { authRepository.getCurrentUser() } returns null

        // When
        viewModel.addToCart()

        // Then
        assertEquals("Please sign in to add items to cart", viewModel.uiState.value.cartErrorMessage)
        coVerify(exactly = 0) { cartRepository.addToCart(any(), any(), any()) }
    }

    @Test
    fun `rapid addToCart calls should invoke repository only once`() = runTest {
        // Given
        val userId = "test_user"
        every { authRepository.getCurrentUser() } returns userId
        coEvery { cartRepository.addToCart(userId, product, 1) } coAnswers {
            delay(1000) // Simulate network delay
            Resource.Success(Unit)
        }

        // When: Call twice rapidly
        viewModel.addToCart()
        viewModel.addToCart()

        // Then: Advance time but not enough for first call to finish
        advanceTimeBy(500)
        coVerify(exactly = 1) { cartRepository.addToCart(userId, product, 1) }

        // Advance time for first call to finish
        advanceTimeBy(600)
        coVerify(exactly = 1) { cartRepository.addToCart(userId, product, 1) }
    }
}
