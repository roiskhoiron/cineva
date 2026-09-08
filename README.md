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
├── .docs/                  # PRD + TDD spec + APK + screenshots (lihat ## Docs)
├── .opencode/plans/        # KMP Clean Architecture plan
├── androidApp/             # MainActivity.kt (ComponentActivity) + res mipmap
├── iosApp/                 # ContentView.swift → MainViewController.kt → App()
├── shared/
│   ├── src/commonMain/kotlin/io/codingskuy/cineva/
│   │   ├── data/{datasources/{remote,local}, models, repositories/MovieRepositoryImpl}
│   │   ├── domain/{entities/{Movie,MovieDetail}, repositories/MovieRepository, usecases/{GetList,Search,GetDetail,GetFav,ToggleFav}}
│   │   ├── presentation/{model/UiState, viewmodel/*ViewModel, ui/{MovieList,Detail,Search,Favorite,CinevaApp}}
│   │   ├── di/AppContainer + App.kt (CinevaApp via createAppContainer) + Platform.kt (expect/actual)
│   │   └── sqldelight/Favorite.sq
│   ├── src/androidMain/    # AndroidSqliteDriver + OkHttp engine + DatabaseDriverFactory
│   ├── src/iosMain/        # NativeSqliteDriver + Darwin engine + MainViewController
│   ├── src/commonTest/     # Turbine TDD
│   └── build.gradle.kts
├── gradle/libs.versions.toml # agp 9.0.1, kotlin 2.4.10, compose 1.11.1, ktor 3.1.3, sqldelight 2.0.2, coil3, turbine
├── graphify-out/           # Knowledge graph (see below)
└── README.md
```

Flow: `SearchView → ViewModel(debounce 300ms) → SearchMoviesUseCase → MovieRepositoryImpl → Ktor Remote (OMDb s=/i=) / SQLDelight Local → StateFlow → Compose (CinevaApp TabRow + Detail navigation)`

Entry: `androidApp/MainActivity.kt:20` `App()` → `shared/App.kt:11` `CinevaApp(createAppContainer())` → 3 tabs + detail `onMovieClick`. `App.kt` lama `Button "Click me!"` sudah dihapus di `12f9719`.

## Docs — Isi Folder `.docs`

Folder `.docs/` berisi **spesifikasi, deliverable APK, dan screenshot** untuk submission:

| File | Deskripsi | Ukuran |
|------|-----------|--------|
| `PRD.md:1` | PRD Nice Movie — F-01 List, F-02 Detail, F-03 Live Search, F-04 Favorites (30 poin), OMDb API, evaluasi | 3.5 KB |
| `PRD_KMP_CleanArchitecture.md:1` | TDD KMP — Compose MP, Clean Architecture 3-layer, Ktor, Serialization, SQLDelight, Coroutines, Turbine | 5.8 KB |
| `Cineva-debug.apk` | **APK debug** `androidApp:assembleDebug` (14 MB) — build `2026-09-08 12:30`, `versionCode 1`, `targetSdk 36`, install `adb -s R9RWA01WLBA install -r .docs/Cineva-debug.apk` atau `adb shell pm path io.codingskuy.cineva` | 14 MB |
| `ss_list_movies.png` | Screenshot **Movie List** — LazyColumn trending Top 10 via `GetMovieListUseCase` (`i` parallel, Shawshank/Godfather/Dark Knight...) + pagination | 182 KB |
| `ss_search_movies.png` | Screenshot **Live Search** — TextField + debounce 300ms, clear, hasil search `batman` (example) | 150 KB |
| `ss_movie_detailed.png` | Screenshot **Movie Detail** — poster full, released/runtime/imdbRating, plot/genre/director/actors + fav toggle | 230 KB |
| `ss_favorited_movies.png` | Screenshot **Favorites** — SQLDelight persist, empty state `No favorites yet` | 91 KB |
| `JAWABAN ESSAY - PENGGUNAAN AI DALAM PEKERJAAN.md` | Essay penggunaan AI | 14 KB |

> Catatan: `.docs/Cineva-debug.apk` di-allow via `.gitignore:33` `!/.docs/*.apk` (global `*.apk` tetap ignore). Kunci OMDb `6f45ab5b` dari `.env:1` sudah ter-wire di `ApiConfig`.

## Knowledge Graph (graphify)

Generated `2026-09-08` via `graphify .` — full pipeline (AST + semantic), update via `graphify update .` after Fase 5:

```
Corpus: 70 files · ~15k words (Fase 1-5 scaffold)
  code: 45+ files | docs: 4 files | images: 11 files
Graph: 268 nodes · 426 edges · 39 communities
Extraction: ~80% EXTRACTED · ~20% INFERRED
Initial cost: 5,200 in · 1,800 out — update AST-only (no LLM)
```

Outputs di `graphify-out/`:

- `graph.html` — interactive viz (open in browser, search, filter community, physics cluster)
- `GRAPH_REPORT.md` — audit report (god nodes, surprising connections, cohesion)
- `graph.json` — GraphRAG-ready JSON (NetworkX)
- `manifest.json` + `cost.json` — incremental cache

**Graph health (initial):** `33 dangling, 1/3 collapsed` — minor dangling dari semantic edges — tidak blok. **Post Fase 5:** `Rebuilt: 268 nodes, 426 edges, 39 communities` via `graphify update .` (AST-only, no LLM).

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

OMDb membutuhkan API key. File `.env` di root (sudah ada, `API_KEY=""`, di-ignore via `.gitignore:1`) di-wire ke `shared/src/commonMain/kotlin/io/codingskuy/cineva/data/datasources/remote/ApiConfig.kt:1` (`expect fun getApiKey()`):

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
- Post-fix v2 (2026-09-08): `LocalDataSource.kt:0`, `SearchViewModel.kt:0`, `CinevaApp.kt:0` (prev 1 warning unused), `App.kt:0`, `OMDbRemoteDataSource.kt:0` — safe area & loading fix below
- Fix safe area: `CinevaApp.kt:33` `Scaffold(contentWindowInsets=WindowInsets(0,0,0,0))` + `Column.safeDrawingPadding().statusBarsPadding().padding(paddingValues)` untuk `enableEdgeToEdge` di `MainActivity.kt:13` — atasi batas atas tidak safe
- Fix loading: `CinevaApp.kt:53` `LaunchedEffect(vm){ vm.loadDefault() }` auto `GetMovieListUseCase` trending Top 10 via `i` parallel (sebelum `search("batman")` → Loading selamanya), `OMDbRemoteDataSource.kt:20` `HttpTimeout 10s/5s/10s` + OMDb `6f45ab5b` test `0.33s` OK
- Trending: `GetMovieListUseCase.kt:9` `TRENDING_IDS 10` (Shawshank tt0111161 ... Fellowship tt0120737) fallback `search("movie")` jika quota habis, pagination `hasMore=false` single page

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
