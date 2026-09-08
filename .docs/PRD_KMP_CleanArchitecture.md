# Technical Design Document (TDD): Nice Movie KMP (Kotlin Multiplatform)

## 1. Introduction & Architectural Vision
This document outlines the architectural and technical specification for modernizing and rebuilding the **Nice Movie** application using **Kotlin Multiplatform (KMP)** with **Compose Multiplatform (Shared UI)**, adhering strictly to **Clean Architecture** principles and **Test-Driven Development (TDD)** practices.

---

## 2. Technology Stack & Frameworks
* **Language:** Kotlin (Shared Core Logic & Shared UI via Compose Multiplatform)
* **UI Framework:** Compose Multiplatform (`androidx.compose`) for Android, Desktop, and iOS unified presentation.
* **Architecture:** Clean Architecture (Presentation, Domain, and Data Layers) + MVVM / MVI pattern.
* **Networking:** Ktor Client (Multiplatform HTTP engine)
* **Serialization:** Kotlinx Serialization (`kotlinx-serialization-json`)
* **Local Storage / Caching:** SQLDelight or Realm Kotlin / Multiplatform Settings for local favorites.
* **Concurrency:** Kotlin Coroutines & Flow (`kotlinx.coroutines`)
* **Testing Stack:** 
  * Unit Testing: `kotlin.test`, Turbine (for Coroutines Flow testing), Mockative / MockK (Kotlin Multiplatform mocking where applicable).
  * UI Testing: Compose UI Test framework.

---

## 3. Clean Architecture Detailed Layers (Shared Module)

The project will reside in a shared KMP module (`shared/`) structured as follows:

```text
shared/
│
├── src/
    ├── commonMain/kotlin/com/nicemovie/
    │   ├── data/
    │   │   ├── datasources/        # Remote (Ktor) & Local (SQLDelight/Settings)
    │   │   ├── models/             # DTOs with kotlinx.serialization
    │   │   └── repositories/       # Repository implementations
    │   ├── domain/
    │   │   ├── entities/           # Pure Kotlin business models (Movie, MovieDetail)
    │   │   ├── repositories/       # Repository interfaces (contracts)
    │   │   └── usecases/           # Interactors (GetMoviesUseCase, SearchMoviesUseCase, ToggleFavoriteUseCase)
    │   └── presentation/
    │       ├── model/              # UI State & UiModels
    │       └── viewmodel/          # Common ViewModel using StateFlow
    │
    └── commonTest/kotlin/com/nicemovie/
        ├── domain/usecases/        # Unit tests for Use Cases (TDD approach)
        └── presentation/           # Unit tests for ViewModels / Presenters
```

### 3.1. Domain Layer (The Core Business Logic)
* **Entities:** Core immutable data classes representing business concepts (`Movie`, `MovieDetail`). Completely free from framework dependencies (no Android/iOS imports).
* **Repository Interfaces:** Abstract contracts defining data operations (`MovieRepository`).
* **Use Cases (Interactors):** Single-responsibility classes encapsulating specific user actions:
  * `GetMovieListUseCase`
  * `SearchMoviesUseCase`
  * `GetMovieDetailsUseCase`
  * `GetFavoriteMoviesUseCase`
  * `ToggleFavoriteMovieUseCase`

### 3.2. Data Layer (The Infrastructure)
* **Remote Data Source:** Implements network requests using **Ktor Client** targeting the OMDb API (`http://www.omdbapi.com/`).
* **Local Data Source:** Manages local persistence for favorite movies using SQLDelight or Multiplatform Settings.
* **Repository Implementation:** Coordinates data flow between Remote and Local data sources, handling mapping from DTOs to Domain Entities.

### 3.3. Presentation Layer (Shared UI - Compose Multiplatform)
* **ViewModel:** Cross-platform ViewModel exposing immutable `StateFlow<MovieScreenState>` to the UI.
* **UI Components (Shared UI):** Composable functions written once in `commonMain` and rendered across platforms:
  * `MovieListView`: Displays list items with poster thumbnails, titles, and years.
  * `MovieDetailView`: Displays detailed metadata, plot summary, and favorite toggle button.
  * `SearchView`: Live search bar supporting real-time reactive queries via Coroutine Flows (`debounce`, `distinctUntilChanged`).
  * `FavoriteView`: Displays locally stored favorite movies.

---

## 4. Test-Driven Development (TDD) Workflow

Every component in the domain and presentation layers will be built following the **Red-Green-Refactor** TDD cycle:

1. **Write Failing Test (Red):** Define expected behavior in `commonTest` before writing implementation code.
   * *Example:* Writing a test for `SearchMoviesUseCase` verifying that empty queries return an empty list or trigger proper error handling.
2. **Write Minimal Code (Green):** Implement the minimum code required in `commonMain` to make the test pass.
3. **Refactor:** Clean up code, optimize architecture, and maintain test coverage.

### TDD Focus Areas:
* **Use Cases:** Thoroughly test business rules, filtering logic, and error handling.
* **ViewModels:** Test state transitions (`Loading`, `Success`, `Error`) under various repository responses using Turbine.
* **Repositories:** Test caching logic and data synchronization between remote APIs and local storage.

---

## 5. Implementation Roadmap & Milestones

1. **Phase 1: Project Setup & Core KMP Skeleton**
   * Configure Gradle build scripts, Ktor client, serialization, and Compose Multiplatform targets.
2. **Phase 2: Domain Layer & TDD Implementation**
   * Define entities and repository contracts.
   * Write unit tests and implement Use Cases.
3. **Phase 3: Data Layer Integration**
   * Set up Ktor client for OMDb API integration.
   * Implement local database/storage for favorites.
4. **Phase 4: Shared UI & Presentation Layer**
   * Implement ViewModels with TDD state validation.
   * Build shared Compose UI screens (List, Detail, Search, Favorites).
5. **Phase 5: Platform Assembly & QA**
   * Wire up entry points for Android (`androidMain`) and desktop/iOS targets.
   * Verify UI smoothness and offline favorite persistence.
