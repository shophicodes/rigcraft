# Fix Wishlist Authentication and Error Handling

Improve robustness of wishlist functionality by handling unauthenticated states in `WishlistRepository` and `ProductDetailsViewModel`, and adding error handling/retry logic to `WishlistViewModel`.

## Proposed Changes

### Wishlist Repository

#### [MODIFY] [WishlistRepositoryImpl.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/data/repository/WishlistRepositoryImpl.kt)
- Update `currentUserId` to be nullable and remove the exception throw.
- Update `getWishlistCollection` to return a nullable `CollectionReference`.
- Modify `getWishlistItems`, `isProductInWishlist`, `addToWishlist`, and `removeFromWishlist` to handle the unauthenticated state gracefully.
- Return an empty flow/false or do nothing instead of throwing when no user is logged in.

### Product Details

#### [MODIFY] [ProductDetailsViewModel.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/details/ProductDetailsViewModel.kt)
- Update `toggleWishlist` to check for authentication using `authRepository.getCurrentUser()`.
- Expose login feedback via `cartErrorMessage` if the user is unauthenticated.
- Ensure `isWishlisted` resolves correctly (handled by repository change, but verified here).

### Wishlist Feature

#### [MODIFY] [WishlistUiState.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/wishlist/WishlistUiState.kt)
- Add `errorMessage` field to `WishlistUiState`.

#### [MODIFY] [WishlistViewModel.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/wishlist/WishlistViewModel.kt)
- Update `uiState` to catch errors from the repository flow.
- Update `removeFromWishlist` to catch exceptions and update UI state.
- Implement a `retry` function that re-collects the wishlist flow.

## Verification Plan

### Automated Tests
- Run existing unit tests if available (none found so far).
- I will verify the build succeeds after changes.

### Manual Verification
1. **Unauthenticated Wishlist**:
   - Open the app without logging in.
   - Navigate to the Wishlist screen. It should show an empty list (no crash).
   - Go to a Product Details page. "Wishlist" icon should not be active.
   - Tap "Wishlist" icon. It should show a message "Prijavite se da biste upravljali listom želja".
2. **Error Handling**:
   - Simulate a network error in `WishlistRepository`.
   - Wishlist screen should show an error message and a retry button.
   - Verify "Retry" button reloads the wishlist.
   - Verify `removeFromWishlist` failures are reported.
