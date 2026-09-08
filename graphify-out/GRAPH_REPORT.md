# Graph Report - Cineva  (2026-09-08)

## Corpus Check
- 61 files · ~23,076 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 270 nodes · 440 edges · 35 communities (11 shown, 18 thin omitted)
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 40 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `7ca469f2`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Use Cases Interactors
- ContentView.swift
- Movie
- Platform
- CinevaApp
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
- AppContainer
- Cineva KMP — Clean Architecture Plan (Nice Movie)
- DatabaseDriverFactory.kt

## God Nodes (most connected - your core abstractions)
1. `Movie` - 41 edges
2. `Result` - 27 edges
3. `MovieRepository` - 19 edges
4. `AppContainer` - 15 edges
5. `CinevaApp()` - 13 edges
6. `MovieDetail` - 12 edges
7. `FakeMovieRepository` - 10 edges
8. `MovieRepositoryImpl` - 9 edges
9. `SearchMoviesUseCase` - 9 edges
10. `MovieListViewModel` - 9 edges

## Surprising Connections (you probably didn't know these)
- `AppAndroidPreview()` --calls--> `App()`  [INFERRED]
  androidApp/src/main/kotlin/io/codingskuy/cineva/MainActivity.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt
- `Graphify Knowledge Graph` --conceptually_related_to--> `Kotlin Multiplatform Project Cineva`  [INFERRED]
  AGENTS.md → README.md
- `Kotlin Multiplatform KMP` --conceptually_related_to--> `Kotlin Multiplatform Project Cineva`  [INFERRED]
  .docs/PRD_KMP_CleanArchitecture.md → README.md
- `MainViewController()` --calls--> `App()`  [INFERRED]
  shared/src/iosMain/kotlin/io/codingskuy/cineva/MainViewController.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt
- `CinevaApp()` --calls--> `MovieDetailView()`  [INFERRED]
  shared/src/commonMain/kotlin/io/codingskuy/cineva/presentation/ui/CinevaApp.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/presentation/ui/MovieDetailView.kt

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Movie Feature Flow List Detail Search Favorites** — docs_prd_movie_list_view, docs_prd_movie_detail_view, docs_prd_live_search, docs_prd_local_favorites [EXTRACTED 1.00]
- **TDD Use Case Validation Flow** — docs_prd_kmp_cleanarchitecture_tdd_workflow, docs_prd_kmp_cleanarchitecture_getmovielistusecase, docs_prd_kmp_cleanarchitecture_searchmoviesusecase, docs_prd_kmp_cleanarchitecture_getmoviedetailsusecase, docs_prd_kmp_cleanarchitecture_togglefavoriteusecase [INFERRED 0.75]
- **Clean Architecture Data Domain Presentation** — docs_prd_kmp_cleanarchitecture_clean_architecture, docs_prd_kmp_cleanarchitecture_movie_entity, docs_prd_kmp_cleanarchitecture_ktor_client, docs_prd_kmp_cleanarchitecture_sqldelight, docs_prd_kmp_cleanarchitecture_shared_viewmodel [INFERRED 0.85]

## Communities (35 total, 18 thin omitted)

### Community 0 - "Use Cases Interactors"
Cohesion: 0.09
Nodes (30): Graphify Knowledge Graph, Graphify Query Path Explain, Clean Architecture Layers, Compose Multiplatform Shared UI, Kotlin Coroutines Flow, GetMovieDetailsUseCase, GetMovieListUseCase, Kotlin Multiplatform KMP (+22 more)

### Community 1 - "ContentView.swift"
Cohesion: 0.14
Nodes (14): App, ComposeView, ContentView, .body, iOSApp, .body, Scene, Self (+6 more)

### Community 2 - "Movie"
Cohesion: 0.09
Nodes (21): Movie, Error, Flow, Loading, MovieRepository, Result, Success, Flow (+13 more)

### Community 4 - "Platform"
Cohesion: 0.36
Nodes (6): AndroidPlatform, getPlatform(), getPlatform(), Platform, getPlatform(), IOSPlatform

### Community 5 - "CinevaApp"
Cohesion: 0.12
Nodes (15): AppAndroidPreview(), MainActivity, Bundle, ComponentActivity, App(), createAppContainer(), CinevaApp(), FavoriteView() (+7 more)

### Community 6 - "MovieDetail"
Cohesion: 0.15
Nodes (10): OMDbRemoteDataSource, DetailResponse, SearchItem, SearchResponse, toEntity(), Flow, MovieRepositoryImpl, MovieDetail (+2 more)

### Community 7 - "gradlew"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 30 - "MovieDetailViewModel.kt"
Cohesion: 0.10
Nodes (18): Error, FavoriteUiState, Loading, MovieDetailUiState, MovieListUiState, Success, FavoriteViewModel, StateFlow (+10 more)

### Community 31 - "AppContainer"
Cohesion: 0.19
Nodes (8): Flow, LocalDataSource, AppContainer, GetFavoritesUseCase, GetMovieDetailUseCase, IsFavoriteUseCase, Flow, ToggleFavoriteUseCase

### Community 33 - "Cineva KMP — Clean Architecture Plan (Nice Movie)"
Cohesion: 0.25
Nodes (7): 1. Konteks & Tujuan, 2. Keputusan Arsitektur (Locked — user setuju), 3. Struktur Folder Akhir, 4. Fase Eksekusi (updated 2026-09-08 — gradlew defer), 5. Verifikasi (deferred to akhir), 6. Next, Cineva KMP — Clean Architecture Plan (Nice Movie)

### Community 35 - "DatabaseDriverFactory.kt"
Cohesion: 0.10
Nodes (12): android, DatabaseDriverFactory, initDatabaseContext(), SqlDriver, getApiKey(), createAppContainer(), DatabaseDriverFactory, SqlDriver (+4 more)

## Knowledge Gaps
- **35 isolated node(s):** `$schema`, `plugin`, `UIKit`, `Shared`, `SearchItem` (+30 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 67 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **18 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Movie` connect `Movie` to `MovieDetailViewModel.kt`, `CinevaApp`, `MovieDetail`, `AppContainer`?**
  _High betweenness centrality (0.126) - this node is a cross-community bridge._
- **Why does `AppContainer` connect `AppContainer` to `Movie`, `DatabaseDriverFactory.kt`, `CinevaApp`, `MovieDetail`, `MovieDetailViewModel.kt`?**
  _High betweenness centrality (0.100) - this node is a cross-community bridge._
- **Why does `CinevaApp()` connect `CinevaApp` to `Movie`, `MovieDetail`, `MovieDetailViewModel.kt`, `AppContainer`?**
  _High betweenness centrality (0.095) - this node is a cross-community bridge._
- **Are the 4 inferred relationships involving `CinevaApp()` (e.g. with `FavoriteView()` and `MovieDetailView()`) actually correct?**
  _`CinevaApp()` has 4 INFERRED edges - model-reasoned connections that need verification._
- **What connects `$schema`, `plugin`, `UIKit` to the rest of the system?**
  _35 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Use Cases Interactors` be split into smaller, more focused modules?**
  _Cohesion score 0.08505747126436781 - nodes in this community are weakly interconnected._
- **Should `ContentView.swift` be split into smaller, more focused modules?**
  _Cohesion score 0.13725490196078433 - nodes in this community are weakly interconnected._