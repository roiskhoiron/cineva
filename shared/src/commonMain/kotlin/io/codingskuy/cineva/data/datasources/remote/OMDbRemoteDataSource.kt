package io.codingskuy.cineva.data.datasources.remote

import io.codingskuy.cineva.data.models.DetailResponse
import io.codingskuy.cineva.data.models.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class OMDbRemoteDataSource(
    private val apiKey: String = getApiKey(),
    private val client: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; isLenient = true })
        }
        install(Logging) { level = LogLevel.INFO }
        install(HttpTimeout) {
            requestTimeoutMillis = 10_000
            connectTimeoutMillis = 5_000
            socketTimeoutMillis = 10_000
        }
    }
) {
    suspend fun search(query: String): SearchResponse {
        require(apiKey.isNotBlank()) { "OMDb API key missing" }
        return client.get(OMDb_BASE_URL) {
            url {
                parameters.append("apikey", apiKey)
                parameters.append("s", query)
            }
        }.body()
    }

    suspend fun getDetail(imdbID: String): DetailResponse {
        require(apiKey.isNotBlank()) { "OMDb API key missing" }
        return client.get(OMDb_BASE_URL) {
            url {
                parameters.append("apikey", apiKey)
                parameters.append("i", imdbID)
                parameters.append("plot", "full")
            }
        }.body()
    }
}
