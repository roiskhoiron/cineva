package io.codingskuy.cineva.data.datasources.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.codingskuy.cineva.db.CinevaDatabase

// Context will be provided via AppContainer initialization
// For scaffold phase, creation deferred — runtime will inject context
private var appContext: android.content.Context? = null

fun initDatabaseContext(context: android.content.Context) {
    appContext = context
}

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val ctx = appContext ?: error("Database context not initialized. Call initDatabaseContext() in MainActivity")
        return AndroidSqliteDriver(CinevaDatabase.Schema, ctx, "cineva.db")
    }
}
