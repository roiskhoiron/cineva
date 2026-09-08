package io.codingskuy.cineva.di

import io.codingskuy.cineva.data.datasources.local.DatabaseDriverFactory
import io.codingskuy.cineva.data.datasources.remote.getApiKey

actual fun createAppContainer(): AppContainer {
    return AppContainer(DatabaseDriverFactory(), getApiKey())
}
