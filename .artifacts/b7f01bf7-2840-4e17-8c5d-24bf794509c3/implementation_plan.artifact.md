# Implementation Plan - Catalog Features and Fixes

Enhance the catalog feature by improving error handling, ensuring robust product loading, adding price filtering, and fixing the "Newest" sort option.

## Proposed Changes

### UI Logic & State Management

#### [MODIFY] [CatalogViewModel.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/catalog/CatalogViewModel.kt)
- Add a `productLoadJob` property to manage the product loading coroutine.
- Update `loadCategories()` to capture and store error messages in `CatalogUiState`.
- Update `loadProducts()` to cancel any existing `productLoadJob` before starting a new one to prevent race conditions and stale data updates.
- Update `applySortingAndSearch()` to implement `SortOption.NEWEST` by sorting products by `createdAt` descending.

#### [MODIFY] [CatalogScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/catalog/CatalogScreen.kt)
- Update the UI to display error messages when they occur.
- Ensure the "No products found." message only appears when there's no error and the list is empty.
- Add Min/Max price input fields to allow users to filter products by price.
- Connect the price filter UI to `CatalogViewModel.updatePriceFilter()`.

## Verification Plan

### Automated Tests
- N/A (Manual verification on device/emulator is primary for UI changes).

### Manual Verification
1.  **Error Handling**: Simulate a network error in `getCategories` and verify the error message is shown on the screen.
2.  **Product Loading**: Rapidly change filters or search query and verify that only the results from the latest request are eventually displayed (no flickering between old and new results).
3.  **Price Filtering**: Enter Min and Max prices and verify the product list updates correctly.
4.  **Sorting**: Select "Newest" sort option and verify products are ordered by their creation date (newest first).
