# Populate Firestore Mock Data Implementation Plan

This plan outlines the steps to add mock product data to Firestore and trigger the seeding process from the `HomeScreenViewModel`.

## Proposed Changes

### Utility

#### [MODIFY] [MockDataSeeder.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/util/MockDataSeeder.kt)
- Add a `products` list containing the requested 11 products.
- Each product will be associated with the appropriate `categoryId` (sub-category ID) and `parentCategory` (main category ID).
- `imageUrl` will be left empty as requested.

### Data Layer

#### [NEW] [SeederRepository.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/domain/repository/SeederRepository.kt)
- Define an interface for the seeder repository with a `seedData()` function.

#### [NEW] [SeederRepositoryImpl.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/data/repository/SeederRepositoryImpl.kt)
- Implement `SeederRepository` using `FirebaseFirestore`.
- Implement `seedData()` to upload categories and products from `MockDataSeeder` to Firestore.

#### [MODIFY] [RepositoryModule.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/di/RepositoryModule.kt)
- Add Hilt binding for `SeederRepository`.

### UI Layer

#### [NEW] [HomeScreenViewModel.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/home/HomeScreenViewModel.kt)
- Create a ViewModel for the Home screen.
- Inject `SeederRepository`.
- Call `seederRepository.seedData()` in the `init` block or via a specific trigger.

## Verification Plan

### Automated Tests
- N/A (Manual verification via Firestore Console)

### Manual Verification
- Run the app and check the Firestore Console to verify that "categories" and "products" collections are populated with the expected data.
