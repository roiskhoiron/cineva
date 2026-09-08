package io.codingskuy.cineva.data.datasources.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.codingskuy.cineva.db.CinevaDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(CinevaDatabase.Schema, "cineva.db")
}
