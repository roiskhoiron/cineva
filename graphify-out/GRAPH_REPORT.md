# Graph Report - Cineva  (2026-09-08)

## Corpus Check
- Corpus is ~9,505 words - fits in a single context window. You may not need a graph.

## Summary
- 108 nodes · 104 edges · 30 communities (7 shown, 19 thin omitted)
- Extraction: 72% EXTRACTED · 28% INFERRED · 0% AMBIGUOUS · INFERRED: 29 edges (avg confidence: 0.85)
- Token cost: 5,200 input · 1,800 output

## Community Hubs (Navigation)
- Movie Features Domain
- iOS SwiftUI Bridge
- KMP Clean Architecture
- Android App Entry
- Platform Abstraction
- ComposeView Bridge
- Demo Greeting
- Gradle Wrapper
- Opencode Config
- Graphify Plugin
- Android Host Test
- Common Test
- iOS Test
- Launcher Icon Hdpi
- Launcher Icon Hdpi Round
- Launcher Icon Mdpi
- Launcher Icon Mdpi Round
- Launcher Icon Xhdpi
- Launcher Icon Xhdpi Round
- Launcher Icon Xxhdpi
- Launcher Icon Xxhdpi Round
- Launcher Icon Xxxhdpi
- Launcher Icon Xxxhdpi Round
- Serialization Config
- Legacy Networking
- iOS App Icon

## God Nodes (most connected - your core abstractions)
1. `Platform` - 6 edges
2. `Nice Movie Mobile Application` - 6 edges
3. `Use Cases Interactors` - 6 edges
4. `ComposeView` - 5 edges
5. `App()` - 5 edges
6. `ContentView` - 4 edges
7. `iOSApp` - 4 edges
8. `Greeting` - 4 edges
9. `Movie List View F-01` - 4 edges
10. `Movie Detail View F-02` - 4 edges

## Surprising Connections (you probably didn't know these)
- `AppAndroidPreview()` --calls--> `App()`  [INFERRED]
  androidApp/src/main/kotlin/io/codingskuy/cineva/MainActivity.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt
- `Kotlin Multiplatform KMP` --conceptually_related_to--> `Kotlin Multiplatform Project Cineva`  [INFERRED]
  .docs/PRD_KMP_CleanArchitecture.md → README.md
- `Graphify Knowledge Graph` --conceptually_related_to--> `Kotlin Multiplatform Project Cineva`  [INFERRED]
  AGENTS.md → README.md
- `App()` --calls--> `Greeting`  [INFERRED]
  shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/Greeting.kt
- `MainViewController()` --calls--> `App()`  [INFERRED]
  shared/src/iosMain/kotlin/io/codingskuy/cineva/MainViewController.kt → shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Clean Architecture Data Domain Presentation** — docs_prd_kmp_cleanarchitecture_clean_architecture, docs_prd_kmp_cleanarchitecture_movie_entity, docs_prd_kmp_cleanarchitecture_ktor_client, docs_prd_kmp_cleanarchitecture_sqldelight, docs_prd_kmp_cleanarchitecture_shared_viewmodel [INFERRED 0.85]
- **Movie Feature Flow List Detail Search Favorites** — docs_prd_movie_list_view, docs_prd_movie_detail_view, docs_prd_live_search, docs_prd_local_favorites [EXTRACTED 1.00]
- **TDD Use Case Validation Flow** — docs_prd_kmp_cleanarchitecture_tdd_workflow, docs_prd_kmp_cleanarchitecture_getmovielistusecase, docs_prd_kmp_cleanarchitecture_searchmoviesusecase, docs_prd_kmp_cleanarchitecture_getmoviedetailsusecase, docs_prd_kmp_cleanarchitecture_togglefavoriteusecase [INFERRED 0.75]

## Communities (30 total, 19 thin omitted)

### Community 0 - "Movie Features Domain"
Cohesion: 0.15
Nodes (19): Kotlin Coroutines Flow, GetMovieDetailsUseCase, GetMovieListUseCase, Ktor Client Networking, SearchMoviesUseCase, Shared ViewModel StateFlow, SQLDelight Local Storage, Test-Driven Development TDD (+11 more)

### Community 1 - "iOS SwiftUI Bridge"
Cohesion: 0.18
Nodes (10): App, ContentView, .body, iOSApp, .body, Scene, Shared, SwiftUI (+2 more)

### Community 2 - "KMP Clean Architecture"
Cohesion: 0.18
Nodes (11): Graphify Knowledge Graph, Graphify Query Path Explain, Clean Architecture Layers, Compose Multiplatform Shared UI, Kotlin Multiplatform KMP, Movie Entity Domain, MovieDetail Entity, Android Target androidMain (+3 more)

### Community 3 - "Android App Entry"
Cohesion: 0.27
Nodes (6): AppAndroidPreview(), MainActivity, Bundle, ComponentActivity, App(), MainViewController()

### Community 4 - "Platform Abstraction"
Cohesion: 0.36
Nodes (6): AndroidPlatform, getPlatform(), getPlatform(), Platform, getPlatform(), IOSPlatform

### Community 5 - "ComposeView Bridge"
Cohesion: 0.47
Nodes (4): ComposeView, Self, UIViewController, UIViewControllerRepresentable

### Community 7 - "Gradle Wrapper"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

## Knowledge Gaps
- **26 isolated node(s):** `$schema`, `plugin`, `UIKit`, `Shared`, `MVP MVVM Architecture` (+21 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 46 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **19 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Kotlin Multiplatform KMP` connect `KMP Clean Architecture` to `Movie Features Domain`?**
  _High betweenness centrality (0.032) - this node is a cross-community bridge._
- **Why does `Greeting` connect `Demo Greeting` to `Android App Entry`, `Platform Abstraction`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Why does `Nice Movie Mobile Application` connect `Movie Features Domain` to `KMP Clean Architecture`?**
  _High betweenness centrality (0.028) - this node is a cross-community bridge._
- **Are the 2 inferred relationships involving `Nice Movie Mobile Application` (e.g. with `MVP MVVM Architecture` and `Kotlin Multiplatform KMP`) actually correct?**
  _`Nice Movie Mobile Application` has 2 INFERRED edges - model-reasoned connections that need verification._
- **Are the 4 inferred relationships involving `App()` (e.g. with `AppAndroidPreview()` and `.onCreate()`) actually correct?**
  _`App()` has 4 INFERRED edges - model-reasoned connections that need verification._
- **What connects `$schema`, `plugin`, `UIKit` to the rest of the system?**
  _26 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Movie Features Domain` be split into smaller, more focused modules?**
  _Cohesion score 0.14619883040935672 - nodes in this community are weakly interconnected._