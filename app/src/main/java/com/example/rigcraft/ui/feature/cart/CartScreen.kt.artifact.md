# Implementation Plan - Cart and Product Details Enhancements

Implement quantity limits in CartScreen and navigation shortcut in ProductDetailsScreen snackbar.

## Proposed Changes

### [app](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app)

#### [MODIFY] [strings.xml](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/res/values/strings.xml)
- Add `label_go_to_cart` string resource.

#### [MODIFY] [CartScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/cart/CartScreen.kt)
- Disable "Decrease" button if quantity is 1.
- Disable "Increase" button if quantity is 50.

#### [MODIFY] [ProductDetailsScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/details/ProductDetailsScreen.kt)
- Add `onNavigateToCart` parameter.
- Add "Go to Cart" action to the success snackbar.

#### [MODIFY] [NavGraph.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/navigation/NavGraph.kt)
- Pass navigation logic to `ProductDetailsScreen`.

## Verification Plan

### Automated Tests
- N/A (UI changes)

### Manual Verification
1.  Open Cart Screen.
2.  Verify "Decrease" button is disabled when quantity is 1.
3.  Verify "Increase" button is disabled when quantity is 50.
4.  Navigate to Product Details.
5.  Add a product to cart.
6.  Verify snackbar appears with "Go to Cart" button.
7.  Click "Go to Cart" and verify it navigates to the Cart screen.
