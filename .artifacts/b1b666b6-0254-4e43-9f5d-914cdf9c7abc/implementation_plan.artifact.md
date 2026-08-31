# Profile Feature Fixes

This plan addresses requested changes in `ProfileRepositoryImpl`, `ProfileScreen`, and `ProfileViewModel` to improve error handling, clean up imports, and ensure UI state consistency.

## Proposed Changes

### [Component: Data Repository]

#### [MODIFY] [ProfileRepositoryImpl.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/data/repository/ProfileRepositoryImpl.kt)
- Add `kotlin.coroutines.cancellation.CancellationException` import.
- Update `updateName`, `updatePassword`, and `deleteAccount` to rethrow `CancellationException` in their `catch` blocks.

### [Component: UI Feature Profile]

#### [MODIFY] [ProfileScreen.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/profile/ProfileScreen.kt)
- Remove unused imports `android.app.AlertDialog` and `androidx.compose.material3.SegmentedButtonDefaults.Icon`.

#### [MODIFY] [ProfileViewModel.kt](file:///C:/Users/Filip/AndroidStudioProjects/RigCraft/app/src/main/java/com/example/rigcraft/ui/feature/profile/ProfileViewModel.kt)
- Update `updateName` and `updatePassword` to clear `errorMessage` on success and clear `message` on error.

## Verification Plan

### Automated Tests
- Build the project to ensure no regression or syntax errors.
- (If available) Run repository unit tests to verify `CancellationException` propagation.

### Manual Verification
- Verify that changing name and password in the profile screen shows success snackbars and clears previous error messages.
- Verify that the profile screen still builds after removing conflicting imports.
