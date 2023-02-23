package xyz.wingio.dimett.rest.service

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import xyz.wingio.dimett.rest.utils.ApiError
import xyz.wingio.dimett.rest.utils.ApiFailure
import xyz.wingio.dimett.rest.utils.ApiResponse

class HttpService(
    val json: Json,
    val http: HttpClient
) {

    suspend inline fun <reified T> request(crossinline builder: HttpRequestBuilder.() -> Unit = {}): ApiResponse<T> =
        withContext(Dispatchers.IO) {
            var body: String? = null
            val response = try {
                val response = http.request(builder)

                if (response.status.isSuccess()) {
                    body = response.bodyAsText()
                    if (response.status == HttpStatusCode.NoContent)
                        ApiResponse.Empty()
                    else if (T::class.java.isAssignableFrom("".javaClass))
                        ApiResponse.Success(body as T)
                    else
                        ApiResponse.Success(json.decodeFromString<T>(body))
                } else {
                    body = try {
                        response.bodyAsText()
                    } catch (t: Throwable) {
                        null
                    }
                    ApiResponse.Error(ApiError(response.status, body))
                }
            } catch (e: Throwable) {
                println(e.stackTraceToString())
                ApiResponse.Failure(ApiFailure(e, body))
            }
            return@withContext response
        }

}