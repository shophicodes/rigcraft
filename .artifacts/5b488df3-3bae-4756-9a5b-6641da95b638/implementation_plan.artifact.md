# Atomic Checkout and UI Enhancements

This plan addresses the requirements for implementing an atomic checkout process, improving error handling in the order flow, and fixing UI/resource issues.

## User Review Required

> [!IMPORTANT]
> The checkout process is now fully atomic at the repository level using a Firestore transaction. This ensures that stock validation and order creation happen together, preventing race conditions.

## Proposed Changes

---

### Domain and Data Layers

#### [MODIFY] [OrderRepository.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/domain/repository/OrderRepository.kt)
- Replace `saveOrder` with `checkout(order: OrderDto): Resource<Unit>`.

#### [MODIFY] [OrderRepositoryImpl.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/data/repository/OrderRepositoryImpl.kt)
- Implement `checkout` using a Firestore transaction.
- The transaction will:
    1. Validate stock for each item in the order.
    2. Update product stock levels.
    3. Save the order document.

---

### UI Layer - Feature: Order

#### [MODIFY] [OrderViewModel.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/order/OrderViewModel.kt)
- Update `placeOrder` to call `orderRepository.checkout`.
- Implement a guard to prevent multiple concurrent checkout attempts.
- Improve `loadCheckoutData` to handle `Resource.Error` from cart and address loads correctly, propagating errors to the UI state.
- Ensure the cart is cleared only after a successful `checkout` transaction.

#### [MODIFY] [OrderDetailScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/order/OrderDetailScreen.kt)
- Update the `Icon` in `OrderDetailScreen` to use `R.string.content_desc_back_navigation` for its `contentDescription`.

---

### UI Layer - Feature: Profile & Resources

#### [MODIFY] [ProfileScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/profile/ProfileScreen.kt)
- Replace the incorrect `msg_no_products_found` resource with `msg_no_orders_found` in the empty orders section.

#### [MODIFY] [strings.xml](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/res/values/strings.xml)
- Add the `msg_no_orders_found` string resource.

## Verification Plan

### Automated Tests
- N/A (Manual verification on device preferred for Firestore interactions).

### Manual Verification
1. **Atomic Checkout**:
    - Add items to cart.
    - Attempt to checkout.
    - Verify that if stock is insufficient (simulate by manually changing stock in Firestore before clicking), the order fails and no stock is deducted.
    - Verify that on success, the order is created, stock is deducted, and the cart is cleared.
2. **Concurrent Checkout**:
    - Rapidly tap the "Place Order" button and verify only one checkout operation is performed.
3. **Error Handling**:
    - Simulate a cart load error and verify the error message is displayed in the checkout screen.
4. **UI Fixes**:
    - Check accessibility description for the back button in `OrderDetailScreen`.
    - Check the empty orders message in the Profile screen.
