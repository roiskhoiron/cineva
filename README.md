# Cineva — Nice Movie (Kotlin Multiplatform)

> KMP + Compose Multiplatform + Clean Architecture. Browse, search, detail & favorites via [OMDb API](http://www.omdbapi.com/).

## Features (PRD)

| ID | Feature | Acceptance |
|----|---------|------------|
| **F-01** 20 | Movie List View | Poster thumb + title + year, fetch via OMDb `s=` |
| **F-02** 20 | Movie Detail View | Full poster, released, runtime, imdbRating, plot, genre, director, actors + fav toggle |
| **F-03** 10 | Live Search | Real-time `debounce(300ms)` as-you-type (`batman`), clear/dismiss |
| **F-04** 30 | Local Favorites | SQLDelight persist + dedicated Favorite screen offline |
| F-05 | Bonus | Empty states, animasi, caching |

Evaluasi: List 20% / Detail 20% / Search 10% / Favorites 30% / Pattern 20% / Docs 10% — detail di [.docs/PRD.md](.docs/PRD.md).

## Tech Stack (TDD)

- **Language/UI:** Kotlin 2.4.10 + Compose Multiplatform 1.11.1 (shared UI `commonMain`)
- **Arch:** Clean Architecture (Data/Domain/Presentation) + MVVM/MVI + TDD Red-Green-Refactor — spec di [.docs/PRD_KMP_CleanArchitecture.md](.docs/PRD_KMP_CleanArchitecture.md)
- **Network:** Ktor Client (okhttp/darwin) + `kotlinx-serialization-json` (OMDb DTOs)
- **Local:** SQLDelight 2.4.x (`android-driver`, `native-driver`) — `Favorite.sq: favorite(imdbID PK, title, year, poster, addedAt)`
- **Concurrency:** Coroutines `Flow` + `StateFlow` (`lifecycle-viewmodelCompose`)
- **Image:** Coil3 (`coil-compose` + `coil-network-ktor`)
- **Nav:** Voyager 1.x (shared `commonMain`)
- **Test:** `kotlin.test` + Turbine + `coroutines-test` — `commonTest/domain/usecases` + `presentation`

Package: `io.codingskuy.cineva` (single `shared` module, 3 layers via packages).

## Project Structure

```
Cineva/
├── .docs/                  # PRD + TDD spec
├── .opencode/plans/        # KMP Clean Architecture plan
├── androidApp/             # MainActivity.kt (ComponentActivity) + res mipmap
├── iosApp/                 # ContentView.swift → MainViewController.kt → App()
├── shared/
│   ├── src/commonMain/kotlin/io/codingskuy/cineva/
│   │   ├── data/{datasources/{remote,local}, models, repositories/MovieRepositoryImpl}
│   │   ├── domain/{entities/{Movie,MovieDetail}, repositories/MovieRepository, usecases/{GetList,Search,GetDetail,GetFav,ToggleFav}}
│   │   ├── presentation/{model/UiState, viewmodel/*ViewModel, ui/{MovieList,Detail,Search,Favorite}}
│   │   ├── di/AppContainer + App.kt (NavHost) + Platform.kt (expect/actual)
│   │   └── sqldelight/Favorite.sq
│   ├── src/androidMain/    # AndroidSqliteDriver + OkHttp engine
│   ├── src/iosMain/        # NativeSqliteDriver + Darwin engine + MainViewController
│   ├── src/commonTest/     # Turbine TDD
│   └── build.gradle.kts
├── gradle/libs.versions.toml # agp 9.0.1, kotlin 2.4.10, compose 1.11.1
├── graphify-out/           # Knowledge graph (see below)
└── README.md
```

Flow: `SearchView → ViewModel(debounce) → SearchMoviesUseCase → MovieRepositoryImpl → Ktor Remote / SQLDelight Local → StateFlow → Compose`

Current `shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt:1` masih template `Button "Click me!"` — akan diganti NavHost 4 screen (plan Fase 4).

## Knowledge Graph (graphify)

Generated `2026-09-08` via `graphify .` — full pipeline (AST + semantic):

```
Corpus: 39 files · ~9,505 words
  code: 24 files | docs: 4 files | images: 11 files
Graph: 108 nodes · 104 edges · 30 communities
Extraction: 72% EXTRACTED · 28% INFERRED (avg confidence 0.85)
Token cost: 5,200 in · 1,800 out
```

Outputs di `graphify-out/`:

- `graph.html` — interactive viz (open in browser, search, filter community, physics cluster)
- `GRAPH_REPORT.md` — audit report (god nodes, surprising connections, cohesion)
- `graph.json` — GraphRAG-ready JSON (NetworkX)
- `manifest.json` + `cost.json` — incremental cache

**Graph health:** `33 dangling-endpoint edges; 1 collapsed (directed); 3 collapsed (undirected)` — minor dangling dari semantic edges (OMDb ↔ Ktor) — tidak blok.

### God Nodes (core abstractions)

1. `Platform:6` · 2. `Nice Movie Mobile Application:6` · 3. `Use Cases Interactors:6` · 4. `ComposeView:5` · 5. `App():5`

### Communities

- **0 Movie Features Domain** (cohesion 0.15) — 19 nodes: Coroutines Flow, Ktor, SQLDelight, TDD, UseCases...
- **1 iOS SwiftUI Bridge** (0.18) — App, ContentView, iOSApp, SwiftUI
- **2 KMP Clean Architecture** (0.18) — KMP, Compose MP, Clean Arch, Movie/MovieDetail entities
- **3 Android App Entry** (0.27) — MainActivity, App(), MainViewController
- **4 Platform Abstraction** (0.36) — AndroidPlatform/IOSPlatform
- **5 ComposeView Bridge** (0.47) — 4 nodes
- 19 thin launcher-icon communities (1 node each) + Gradle/Opencode/Graphify/Test.

Full detail: `graphify-out/GRAPH_REPORT.md:1`.

### Suggested Questions (graph bisa jawab)

- Why does `Kotlin Multiplatform KMP` bridge `KMP Clean Architecture` ↔ `Movie Features Domain`? (betweenness 0.032)
- Are inferred edges `Nice Movie ↔ KMP` & `App() ↔ AppAndroidPreview` correct?
- Should `Movie Features Domain` (cohesion 0.15) di-split?

Query: `graphify query "Why does KMP connect Clean Architecture to Movie Features?"` atau `graphify path "OMDb API" "Ktor Client"` / `graphify explain "Use Cases Interactors"`.

Wiki: tambah `--wiki` untuk `graphify-out/wiki/index.md` (agent-crawlable).

## Roadmap (5 Fase) — plan di `.opencode/plans/cineva-kmp-clean-architecture-plan.md`

1. **Skeleton & Deps** — `libs.versions.toml` + `shared/build.gradle.kts` + `Favorite.sq` + `AndroidManifest INTERNET`
2. **Domain + TDD** — entities + `MovieRepository` + 5 UseCases + Turbine tests
3. **Data** — OMDb DTOs + Ktor Remote + SQLDelight Local + `RepositoryImpl` + drivers
4. **Presentation** — UiState sealed + ViewModels StateFlow + 4 Compose screens + Coil + Voyager
5. **Assembly & QA** — wiring `MainActivity`/`MainViewController`, `testAndroidHostTest`/`iosSimulatorArm64Test`, fav persist, polish, update README

Verifikasi per fase: `assembleDebug` / `commonTest` / `androidHostTest` / `iosSimulatorArm64Test` + `graphify update .`

## Setup — API Key (.env)

OMDb membutuhkan API key. File `.env` di root (sudah ada, `API_KEY=6f45ab5b:1`, di-ignore via `.gitignore:1`) di-wire ke `shared/src/commonMain/kotlin/io/codingskuy/cineva/data/datasources/remote/ApiConfig.kt:1` (`expect fun getApiKey()`):

- `shared/src/androidMain/kotlin/.../ApiConfig.android.kt:1` → `actual fun getApiKey() = "6f45ab5b"` (hardcode dari `.env`, TODO BuildConfig)
- `shared/src/iosMain/kotlin/.../ApiConfig.ios.kt:1` → sama
- Alternatif: `local.properties` (`omdb.apiKey=...`) + `iosApp/Configuration/Config.xcconfig` → `BuildKonfig` (Fase 3 TODO)

Jangan commit `.env` — sudah di `.gitignore`.

## Running the apps

Use run configurations in IDE toolbar or:

- Android: `./gradlew :androidApp:assembleDebug` → `androidApp/build/outputs/apk/debug/*.apk` (butuh `INTERNET` permission, sudah difix Fase 1)
  - Samsung device tersedia: `R9RWA01WLBA device` (via `adb devices:1`) — `Run → androidApp` sudah dieksekusi di background, auto-install ke Samsung
  - Manual: `./gradlew :androidApp:installDebug` atau `adb -s R9RWA01WLBA install -r androidApp/build/outputs/apk/debug/androidApp-debug.apk`
- iOS: open `iosApp/` in Xcode → Run (darwin driver + native sqlite)

### Analyzer & Build Health (2026-09-08)

- IDE index: `isDumbMode=false, isIndexing=false`
- Diagnostics pre-fix: `LocalDataSource.kt:16` `Unresolved reference 'IO'` (commonMain tidak punya `Dispatchers.IO`) + `isFavorite` star-projection `Comparable` + `suspend` redundant
- Fix: `LocalDataSource.kt:11` `Dispatchers.IO → Dispatchers.Default`, `isFavorite` via `selectById().mapToOneOrNull().map { it != null }`, hapus `suspend` insert/delete, `SearchViewModel.kt:29` `debounce(300) → debounce(300.milliseconds)`, `CinevaApp.kt:27` `TabRow → PrimaryTabRow` (M3), `MovieRepositoryImpl.kt:13` hapus import `map` unused
- Post-fix: `LocalDataSource.kt:0` problems `0`, `SearchViewModel.kt:0`, `CinevaApp.kt:1` warning `unused` saja

### Running tests

Gutter run button or Gradle (defer ke akhir Fase 5 per plan, tapi bisa sekarang):

- Android: `./gradlew :shared:testAndroidHostTest`
- iOS: `./gradlew :shared:iosSimulatorArm64Test`
- All common: `./gradlew :shared:commonTest` (Turbine `test {}` untuk UseCase/ViewModel)
- Detail search flow: debounce `300.milliseconds` + `distinctUntilChanged` (tested via `runTest`)

## Git

- Branch `master` (origin/master), initial commit `2c499f3 initialize project` — 77 files, rebase `83e5ac6 docs: Add essay`, 6 commits atomic Fase 1-5 pushed `b50ab47`
- Commits: `e4bbf5d chore fase1`, `e9d0333 feat domain`, `eed0272 feat data`, `d469bdf feat presentation`, `b50ab47 chore fase5` + `dbf60c6 docs graphify` + `52326a5 README`
- `.gitignore:1` — `**/build/`, `.gradle`, `DerivedData/`, `*.apk`, `.env`, `graphify-out/.graphify_*`, `graphify-out/cost.json`, `graphify-out/.lean-ctx/`

---

Learn more [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) · Graphify `graphify --help` · OMDb `https://www.omdbapi.com/apikey.aspx`