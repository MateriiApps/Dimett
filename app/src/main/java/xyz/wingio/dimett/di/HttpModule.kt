package xyz.wingio.dimett.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.wingio.dimett.BuildConfig
import xyz.wingio.dimett.utils.Logger

@OptIn(ExperimentalSerializationApi::class)
val httpModule = module {

    fun provideHttpClient(json: Json, log: Logger) = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(json)
        }
        defaultRequest {
            header(
                HttpHeaders.UserAgent,
                "Dimett/${BuildConfig.APPLICATION_ID} v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})${if (BuildConfig.DEBUG) " - Debug" else ""}"
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    log.debug(message)
                }
            }
        }
    }

    fun provideJson() = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        explicitNulls = false
    }

    singleOf(::provideJson)
    single {
        provideHttpClient(get(), get(named("http")))
    }

}