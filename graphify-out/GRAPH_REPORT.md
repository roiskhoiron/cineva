# Graph Report - Cineva  (2026-09-08)

## Corpus Check
- 63 files · ~42,158 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 281 nodes · 465 edges · 38 communities (13 shown, 19 thin omitted)
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 40 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `ec1f9dbb`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Use Cases Interactors
- ContentView.swift
- Result
- Movie
- Platform
- CinevaApp
- OMDbRemoteDataSource
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
- MovieListViewModel
- FakeToggleRepo
- Cineva KMP — Clean Architecture Plan (Nice Movie)
- DatabaseDriverFactory.android.kt
- AppContainer
- DatabaseDriverFactory

## God Nodes (most connected - your core abstractions)
1. `Movie` - 37 edges
2. `Result` - 29 edges
3. `MovieRepository` - 20 edges
4. `AppContainer` - 16 edges
5. `CinevaApp()` - 13 edges
6. `MovieDetail` - 12 edges
7. `PaginatedMovies` - 12 edges
8. `MovieListViewModel` - 11 edges
9. `SearchViewModel` - 10 edges
10. `FakeMovieRepository` - 10 edges

## Surprising Connections (you probably didn't know these)
- `AppAndroidPreview()` --calls--> `App()`  [INFERRED]
  androidApp/src/main/kotlin/io/codingskuy/cineva/MainActivity.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt
- `Graphify Knowledge Graph` --conceptually_related_to--> `Kotlin Multiplatform Project Cineva`  [INFERRED]
  AGENTS.md → README.md
- `Kotlin Multiplatform KMP` --conceptually_related_to--> `Kotlin Multiplatform Project Cineva`  [INFERRED]
  .docs/PRD_KMP_CleanArchitecture.md → README.md
- `createAppContainer()` --calls--> `DatabaseDriverFactory`  [INFERRED]
  shared/src/androidMain/kotlin/io/codingskuy/cineva/di/AppContainerFactory.android.kt → shared/src/androidMain/kotlin/io/codingskuy/cineva/data/datasources/local/DatabaseDriverFactory.android.kt
- `MainViewController()` --calls--> `App()`  [INFERRED]
  shared/src/iosMain/kotlin/io/codingskuy/cineva/MainViewController.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Movie Feature Flow List Detail Search Favorites** — docs_prd_movie_list_view, docs_prd_movie_detail_view, docs_prd_live_search, docs_prd_local_favorites [EXTRACTED 1.00]
- **TDD Use Case Validation Flow** — docs_prd_kmp_cleanarchitecture_tdd_workflow, docs_prd_kmp_cleanarchitecture_getmovielistusecase, docs_prd_kmp_cleanarchitecture_searchmoviesusecase, docs_prd_kmp_cleanarchitecture_getmoviedetailsusecase, docs_prd_kmp_cleanarchitecture_togglefavoriteusecase [INFERRED 0.75]
- **Clean Architecture Data Domain Presentation** — docs_prd_kmp_cleanarchitecture_clean_architecture, docs_prd_kmp_cleanarchitecture_movie_entity, docs_prd_kmp_cleanarchitecture_ktor_client, docs_prd_kmp_cleanarchitecture_sqldelight, docs_prd_kmp_cleanarchitecture_shared_viewmodel [INFERRED 0.85]

## Communities (38 total, 19 thin omitted)

### Community 0 - "Use Cases Interactors"
Cohesion: 0.09
Nodes (30): Graphify Knowledge Graph, Graphify Query Path Explain, Clean Architecture Layers, Compose Multiplatform Shared UI, Kotlin Coroutines Flow, GetMovieDetailsUseCase, GetMovieListUseCase, Kotlin Multiplatform KMP (+22 more)

### Community 1 - "ContentView.swift"
Cohesion: 0.14
Nodes (14): App, ComposeView, ContentView, .body, iOSApp, .body, Scene, Self (+6 more)

### Community 2 - "Result"
Cohesion: 0.10
Nodes (17): Flow, MovieRepositoryImpl, MovieDetail, PaginatedMovies, Error, Flow, Loading, MovieRepository (+9 more)

### Community 3 - "Movie"
Cohesion: 0.19
Nodes (11): Movie, SearchMoviesUseCase, FavoriteView(), Modifier, Modifier, MovieListView(), MovieRow(), FakeMovieRepository (+3 more)

### Community 4 - "Platform"
Cohesion: 0.36
Nodes (6): AndroidPlatform, getPlatform(), getPlatform(), Platform, getPlatform(), IOSPlatform

### Community 5 - "CinevaApp"
Cohesion: 0.13
Nodes (14): AppAndroidPreview(), MainActivity, Bundle, ComponentActivity, App(), createAppContainer(), FavoriteUiState, CinevaApp() (+6 more)

### Community 6 - "OMDbRemoteDataSource"
Cohesion: 0.31
Nodes (5): OMDbRemoteDataSource, DetailResponse, SearchItem, SearchResponse, toEntity()

### Community 7 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 30 - "MovieListViewModel"
Cohesion: 0.11
Nodes (14): Error, Loading, MovieDetailUiState, MovieListUiState, Success, StateFlow, ViewModel, MovieDetailViewModel (+6 more)

### Community 31 - "FakeToggleRepo"
Cohesion: 0.31
Nodes (4): FakeToggleRepo, Flow, io, ToggleFavoriteUseCaseTest

### Community 33 - "Cineva KMP — Clean Architecture Plan (Nice Movie)"
Cohesion: 0.25
Nodes (7): 1. Konteks & Tujuan, 2. Keputusan Arsitektur (Locked — user setuju), 3. Struktur Folder Akhir, 4. Fase Eksekusi (updated 2026-09-08 — gradlew defer), 5. Verifikasi (deferred to akhir), 6. Next, Cineva KMP — Clean Architecture Plan (Nice Movie)

### Community 34 - "DatabaseDriverFactory.android.kt"
Cohesion: 0.40
Nodes (4): android, DatabaseDriverFactory, initDatabaseContext(), SqlDriver

### Community 35 - "AppContainer"
Cohesion: 0.09
Nodes (16): getApiKey(), createAppContainer(), DatabaseDriverFactory, SqlDriver, Flow, LocalDataSource, AppContainer, GetFavoritesUseCase (+8 more)

## Knowledge Gaps
- **35 isolated node(s):** `$schema`, `plugin`, `UIKit`, `Shared`, `SearchItem` (+30 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 68 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **19 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Movie` connect `Movie` to `Result`, `AppContainer`, `CinevaApp`, `OMDbRemoteDataSource`, `MovieListViewModel`, `FakeToggleRepo`?**
  _High betweenness centrality (0.119) - this node is a cross-community bridge._
- **Why does `AppContainer` connect `AppContainer` to `Result`, `Movie`, `CinevaApp`, `OMDbRemoteDataSource`, `MovieListViewModel`?**
  _High betweenness centrality (0.104) - this node is a cross-community bridge._
- **Why does `CinevaApp()` connect `CinevaApp` to `Movie`, `Result`, `AppContainer`, `MovieListViewModel`?**
  _High betweenness centrality (0.097) - this node is a cross-community bridge._
- **Are the 4 inferred relationships involving `CinevaApp()` (e.g. with `FavoriteView()` and `MovieDetailView()`) actually correct?**
  _`CinevaApp()` has 4 INFERRED edges - model-reasoned connections that need verification._
- **What connects `$schema`, `plugin`, `UIKit` to the rest of the system?**
  _35 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Use Cases Interactors` be split into smaller, more focused modules?**
  _Cohesion score 0.08505747126436781 - nodes in this community are weakly interconnected._
- **Should `ContentView.swift` be split into smaller, more focused modules?**
  _Cohesion score 0.13725490196078433 - nodes in this community are weakly interconnected._