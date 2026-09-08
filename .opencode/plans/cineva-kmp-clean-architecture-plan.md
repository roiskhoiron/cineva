# Cineva KMP — Clean Architecture Plan (Nice Movie)

**Repo:** `/Users/roishoiron/IdeaProjects/Cineva` | **Branch:** `colossal-pondering-loom` | **Date:** 2026-09-08
**Docs:** `.docs/PRD.md`, `.docs/PRD_KMP_CleanArchitecture.md` | **Graph:** `graphify-out/graph.json` (109 nodes, 140 edges)
**Stack Target:** Kotlin 2.4.10, Compose MP 1.11.1, Ktor 3.1.x, Serialization 1.7.x, SQLDelight 2.4.x, Coil3 3.x, Coroutines 1.8.x, Turbine 1.1.x

## 1. Konteks & Tujuan

Membangun aplikasi **Nice Movie** (time-box 4 jam) sesuai PRD:
- F-01 (20): Movie List (poster thumb+title+year, fetch OMDb)
- F-02 (20): Movie Detail (poster full, released, runtime, imdbRating, plot, genre, director, actors + fav toggle)
- F-03 (10): Live Search (real-time debounce)
- F-04 (30): Local Favorites (persist + dedicated Favorite screen offline) — bobot tertinggi
- F-05: Bonus (empty state, animasi, cache)

Arsitektur wajib: **KMP Clean Architecture (Data/Domain/Presentation) + MVVM/MVI + TDD Red-Green-Refactor** (`shared/` shared logic + shared UI via Compose MP).

**Status sekarang:** Vanilla KMP template — `shared/src/commonMain/kotlin/io/codingskuy/cineva/App.kt:1` hanya `Button "Click me!" + Greeting`, 0% fitur, 0/7 library TDD terinstall, `AndroidManifest.xml:1` missing `INTERNET`. Build jalan tapi kosong. Commit awal `2c499f3 initialize project` di `main` sudah done.

## 2. Keputusan Arsitektur (Locked — user setuju)

| Keputusan | Pilihan |
|-----------|---------|
| Package | `io.codingskuy.cineva` (keep) |
| Module | Single `shared` dengan 3 package layer |
| Networking | Ktor `core + okhttp + darwin + contentNegotiation + logging` |
| Serialization | `kotlinx-serialization-json` + plugin `kotlin("plugin.serialization")` |
| DB | **SQLDelight 2.4.x** (`android-driver`, `native-driver`) — schema `favorite(imdbID PK, title, year, poster, addedAt)` |
| DI | Manual Service Locator |
| Image | **Coil3** |
| Navigation | **Voyager 1.x** |
| Coroutines | `1.8.x` explicit |
| Testing | `turbine`, `coroutines-test` |
| API Key | `local.properties` + `Config.xcconfig` + `expect getApiKey()` |
| Permissions | `INTERNET` + `https://` OMDb |

## 3. Struktur Folder Akhir

```
shared/src/commonMain/kotlin/io/codingskuy/cineva/
├── data/datasources/remote, local + models + repositories/MovieRepositoryImpl
├── domain/entities (Movie, MovieDetail) + repositories/interface + usecases (5)
├── presentation/model(UiState) + viewmodel(StateFlow) + ui(4 Compose screens)
├── di/AppContainer + App.kt(NavHost) + Platform.kt
shared/src/commonMain/sqldelight/Favorite.sq
shared/src/androidMain (AndroidSqliteDriver, OkHttp) / iosMain (NativeSqliteDriver, Darwin)
shared/src/commonTest (Turbine TDD)
```

## 4. Fase Eksekusi (updated 2026-09-08 — gradlew defer)

**Fase 1 Skeleton (done, atomic commit):** Update `libs.versions.toml`, `shared/build.gradle.kts` (serialization + sqldelight), `Favorite.sq`, fix `AndroidManifest`, skeleton `domain/data/presentation/di` + `ApiConfig` expect/actual — **no gradlew per fase**.
**Fase 2 Domain+TDD:** Entities, `MovieRepository` interface, 5 UseCases + `commonTest` Turbine — git atomic.
**Fase 3 Data:** DTO `@Serializable`, Ktor Remote, SQLDelight Local, `RepositoryImpl`, drivers — git atomic.
**Fase 4 Presentation:** UiState sealed, ViewModels StateFlow, 4 Compose UI + Coil + Voyager, `App.kt` NavHost — git atomic.
**Fase 5 QA:** Wiring `MainActivity`/`MainViewController`, Polish, README — git atomic.

## 5. Verifikasi (deferred to akhir)

- **Per fase:** hanya `git add . && git commit` atomic + `graphify update` (AST-only, ringan). **Tidak ada `./gradlew` tiap fase.**
- **Akhir Fase 5 sekali:** `./gradlew :shared:commonTest :shared:androidHostTest :shared:iosSimulatorArm64Test :androidApp:assembleDebug` + `graphify update` + `git push`.

## 6. Next

OMDb API key placeholder `getApiKey()=""` Fase 1 — isi real key di Fase 3 via `local.properties`/`Config.xcconfig`. Eksekusi secepatnya tanpa gate build.
