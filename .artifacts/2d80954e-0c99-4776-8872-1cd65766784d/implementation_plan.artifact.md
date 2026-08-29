# Implementation Plan - Cart and Auth Improvements

This plan outlines the refactoring of the cart repository to use Firestore transactions, improving user identity handling in cart operations, and refining the UI feedback and observation logic.

## User Review Required

> [!IMPORTANT]
> The "Proceed to Checkout" button in the `CartScreen` will be disabled for now as the checkout flow is not yet implemented.
> I will implement unique anonymous authentication for guest users to ensure cart data is not shared between unauthenticated users.

## Proposed Changes

### [Data Layer]

#### [MODIFY] [CartRepositoryImpl.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/data/repository/CartRepositoryImpl.kt)
- Update `addToCart` to use `firestore.runTransaction` for atomic read-modify-write.
- Use product ID as the document ID for cart items to ensure determinism and prevent duplicates.
- Update `getCartItems` to move exception handling downstream using `catch` on the flow.

#### [MODIFY] [AuthRepository.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/domain/repository/AuthRepository.kt)
- Add `fun getAuthStateFlow(): Flow<String?>` to track the current user's UID.
- Add `suspend fun signInAnonymously(): Resource<String>` to support unique guest identities.

#### [MODIFY] [AuthRepositoryImpl.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/data/repository/AuthRepositoryImpl.kt)
- Implement `getAuthStateFlow` using `FirebaseAuth.AuthStateListener`.
- Implement `signInAnonymously` using `firebaseAuth.signInAnonymously()`.

---

### [UI Layer - Cart]

#### [MODIFY] [CartUiState.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/cart/CartUiState.kt)
- No changes needed to the state class itself, but logic in ViewModel will change.

#### [MODIFY] [CartViewModel.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/cart/CartViewModel.kt)
- Replace `FirebaseAuth` with `AuthRepository`.
- Update `observeCart` to use `flatMapLatest` on `getAuthStateFlow` to restart observation when the UID changes.
- Ensure anonymous authentication is established if no user is signed in.
- Clear `errorMessage` upon successful cart item load.

#### [MODIFY] [CartScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/cart/CartScreen.kt)
- Update rendering logic to display `errorMessage` before the empty-cart UI.

---

### [UI Layer - Product Details]

#### [MODIFY] [ProductDetailsUiState.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/details/ProductDetailsUiState.kt)
- Add `cartErrorMessage: String?` to separate cart-related errors from product-loading errors.

#### [MODIFY] [ProductDetailsViewModel.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/details/ProductDetailsViewModel.kt)
- Replace `FirebaseAuth` with `AuthRepository`.
- Update `addToCart` to:
    - Atomically reject concurrent calls using `isAddingToCart`.
    - Use a captured quantity for the success message.
    - Assign failures to `cartErrorMessage` instead of `errorMessage`.
- Ensure authenticated or anonymous user before cart operations.

#### [MODIFY] [ProductDetailsScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/details/ProductDetailsScreen.kt)
- Update to observe and display `cartErrorMessage` via Snackbar.
- Disable the "Add to Cart" button when `isAddingToCart` is true.

---

### [Navigation & Main]

#### [MODIFY] [MainScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/main/MainScreen.kt)
- The re-observation logic in `CartViewModel` will automatically satisfy the requirement for `MainScreen`.

#### [MODIFY] [NavGraph.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/navigation/NavGraph.kt)
- Disable the `onCheckoutClick` action until implemented.

---

### [Testing]

#### [NEW] [ProductDetailsViewModelTest.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/test/java/com/example/rigcraft/ui/feature/details/ProductDetailsViewModelTest.kt)
- Add a test case to verify that rapid consecutive calls to `addToCart` result in only one repository invocation.

## Verification Plan

### Automated Tests
- Run the new `ProductDetailsViewModelTest`.
- `gradlew test`

### Manual Verification
1. Open the app as a guest, add items to cart, verify cart is unique (not shared with "guest_user").
2. Log in, verify cart observation restarts and shows items for the logged-in user.
3. Rapidly tap "Add to Cart" and verify only one item/batch is added (via logs or UI feedback).
4. Simulate a cart load failure and verify `errorMessage` is shown before empty state.
5. Verify "Proceed to Checkout" button is disabled/removed.
