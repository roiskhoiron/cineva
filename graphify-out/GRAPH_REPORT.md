# Graph Report - Cineva  (2026-09-08)

## Corpus Check
- 59 files · ~13,557 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 268 nodes · 426 edges · 39 communities (13 shown, 19 thin omitted)
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 37 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `c419d4e0`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Use Cases Interactors
- ContentView.swift
- Movie
- App
- Platform
- AppContainer
- MovieDetail
- gradlew
- opencode.json
- graphify.js
- SharedLogicAndroidHostTest
- SharedCommonTest
- SharedLogicIOSTest
- Launcher Icon mipmap-hdpi ic_launcher.png
- Launcher Icon mipmap-hdpi ic_launcher_round.png
- Launcher Icon mipmap-mdpi ic_launcher.png
- Launcher Icon mipmap-mdpi ic_launcher_round.png
- Launcher Icon mipmap-xhdpi ic_launcher.png
- Launcher Icon mipmap-xhdpi ic_launcher_round.png
- Launcher Icon mipmap-xxhdpi ic_launcher.png
- Launcher Icon mipmap-xxhdpi ic_launcher_round.png
- Launcher Icon mipmap-xxxhdpi ic_launcher.png
- Launcher Icon mipmap-xxxhdpi ic_launcher_round.png
- Kotlinx Serialization
- Retrofit OkHttp Networking
- Launcher Icon AppIcon.appiconset app-icon-1024.png
- MovieDetailViewModel.kt
- AppContainer.kt
- FakeMovieRepository
- Cineva KMP — Clean Architecture Plan (Nice Movie)
- MovieListViewModel
- DatabaseDriverFactory.ios.kt

## God Nodes (most connected - your core abstractions)
1. `Movie` - 39 edges
2. `Result` - 27 edges
3. `MovieRepository` - 19 edges
4. `AppContainer` - 12 edges
5. `MovieDetail` - 12 edges
6. `FakeMovieRepository` - 10 edges
7. `MovieRepositoryImpl` - 9 edges
8. `SearchMoviesUseCase` - 9 edges
9. `MovieListViewModel` - 9 edges
10. `LocalDataSource` - 8 edges

## Surprising Connections (you probably didn't know these)
- `AppAndroidPreview()` --calls--> `App()`  [INFERRED]
  androidApp/src/main/kotlin/io/codingskuy/cineva/MainActivity.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt
- `Graphify Knowledge Graph` --conceptually_related_to--> `Kotlin Multiplatform Project Cineva`  [INFERRED]
  AGENTS.md → README.md
- `Kotlin Multiplatform KMP` --conceptually_related_to--> `Kotlin Multiplatform Project Cineva`  [INFERRED]
  .docs/PRD_KMP_CleanArchitecture.md → README.md
- `App()` --calls--> `Greeting`  [INFERRED]
  shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/Greeting.kt
- `MainViewController()` --calls--> `App()`  [INFERRED]
  shared/src/iosMain/kotlin/io/codingskuy/cineva/MainViewController.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Movie Feature Flow List Detail Search Favorites** — docs_prd_movie_list_view, docs_prd_movie_detail_view, docs_prd_live_search, docs_prd_local_favorites [EXTRACTED 1.00]
- **TDD Use Case Validation Flow** — docs_prd_kmp_cleanarchitecture_tdd_workflow, docs_prd_kmp_cleanarchitecture_getmovielistusecase, docs_prd_kmp_cleanarchitecture_searchmoviesusecase, docs_prd_kmp_cleanarchitecture_getmoviedetailsusecase, docs_prd_kmp_cleanarchitecture_togglefavoriteusecase [INFERRED 0.75]
- **Clean Architecture Data Domain Presentation** — docs_prd_kmp_cleanarchitecture_clean_architecture, docs_prd_kmp_cleanarchitecture_movie_entity, docs_prd_kmp_cleanarchitecture_ktor_client, docs_prd_kmp_cleanarchitecture_sqldelight, docs_prd_kmp_cleanarchitecture_shared_viewmodel [INFERRED 0.85]

## Communities (39 total, 19 thin omitted)

### Community 0 - "Use Cases Interactors"
Cohesion: 0.09
Nodes (30): Graphify Knowledge Graph, Graphify Query Path Explain, Clean Architecture Layers, Compose Multiplatform Shared UI, Kotlin Coroutines Flow, GetMovieDetailsUseCase, GetMovieListUseCase, Kotlin Multiplatform KMP (+22 more)

### Community 1 - "ContentView.swift"
Cohesion: 0.14
Nodes (14): App, ComposeView, ContentView, .body, iOSApp, .body, Scene, Self (+6 more)

### Community 2 - "Movie"
Cohesion: 0.12
Nodes (17): Flow, MovieRepositoryImpl, Movie, Error, Flow, Loading, MovieRepository, Result (+9 more)

### Community 3 - "App"
Cohesion: 0.16
Nodes (10): android, AppAndroidPreview(), MainActivity, Bundle, ComponentActivity, DatabaseDriverFactory, initDatabaseContext(), SqlDriver (+2 more)

### Community 4 - "Platform"
Cohesion: 0.20
Nodes (8): AndroidPlatform, getPlatform(), Greeting, sayHello(), getPlatform(), Platform, getPlatform(), IOSPlatform

### Community 5 - "AppContainer"
Cohesion: 0.14
Nodes (15): AppContainer, GetFavoritesUseCase, Flow, FavoriteUiState, CinevaApp(), FavoriteView(), Modifier, Modifier (+7 more)

### Community 6 - "MovieDetail"
Cohesion: 0.16
Nodes (10): OMDbRemoteDataSource, DetailResponse, SearchItem, SearchResponse, toEntity(), MovieDetail, GetMovieDetailUseCase, Flow (+2 more)

### Community 7 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 30 - "MovieDetailViewModel.kt"
Cohesion: 0.17
Nodes (11): Error, Loading, MovieDetailUiState, MovieListUiState, Success, StateFlow, ViewModel, MovieDetailViewModel (+3 more)

### Community 31 - "AppContainer.kt"
Cohesion: 0.16
Nodes (6): DatabaseDriverFactory, SqlDriver, Flow, LocalDataSource, IsFavoriteUseCase, Flow

### Community 32 - "FakeMovieRepository"
Cohesion: 0.25
Nodes (5): SearchMoviesUseCase, FakeMovieRepository, Flow, io, SearchMoviesUseCaseTest

### Community 33 - "Cineva KMP — Clean Architecture Plan (Nice Movie)"
Cohesion: 0.25
Nodes (7): 1. Konteks & Tujuan, 2. Keputusan Arsitektur (Locked — user setuju), 3. Struktur Folder Akhir, 4. Fase Eksekusi (updated 2026-09-08 — gradlew defer), 5. Verifikasi (deferred to akhir), 6. Next, Cineva KMP — Clean Architecture Plan (Nice Movie)

### Community 34 - "MovieListViewModel"
Cohesion: 0.48
Nodes (3): StateFlow, ViewModel, MovieListViewModel

## Knowledge Gaps
- **35 isolated node(s):** `$schema`, `plugin`, `UIKit`, `Shared`, `SearchItem` (+30 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 70 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **19 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Movie` connect `Movie` to `FakeMovieRepository`, `AppContainer`, `MovieDetail`, `MovieDetailViewModel.kt`, `AppContainer.kt`?**
  _High betweenness centrality (0.090) - this node is a cross-community bridge._
- **Why does `Result` connect `Movie` to `FakeMovieRepository`, `MovieListViewModel`, `MovieDetail`, `MovieDetailViewModel.kt`?**
  _High betweenness centrality (0.050) - this node is a cross-community bridge._
- **Why does `MovieRepository` connect `Movie` to `FakeMovieRepository`, `AppContainer`, `MovieDetail`, `AppContainer.kt`?**
  _High betweenness centrality (0.033) - this node is a cross-community bridge._
- **What connects `$schema`, `plugin`, `UIKit` to the rest of the system?**
  _35 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Use Cases Interactors` be split into smaller, more focused modules?**
  _Cohesion score 0.08505747126436781 - nodes in this community are weakly interconnected._
- **Should `ContentView.swift` be split into smaller, more focused modules?**
  _Cohesion score 0.13725490196078433 - nodes in this community are weakly interconnected._
- **Should `Movie` be split into smaller, more focused modules?**
  _Cohesion score 0.11538461538461539 - nodes in this community are weakly interconnected._