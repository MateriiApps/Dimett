package xyz.wingio.dimett.rest.utils

import io.ktor.http.HttpStatusCode

/**
 * Wrapper for api responses that enables better error handling
 *
 * @param T The api model for the response
 */
sealed interface ApiResponse<out T> {
    /**
     * [Data][data] was returned without any issues
     */
    data class Success<T>(val data: T) : ApiResponse<T>

    /**
     * Represents a response that had no body
     */
    class Empty<T> : ApiResponse<T>

    /**
     * Represents an error returned by the server
     */
    data class Error<T>(val error: ApiError) : ApiResponse<T>

    /**
     * Represents an error on the client (Ex. deserialization problem or device is offline)
     */
    data class Failure<T>(val error: ApiFailure) : ApiResponse<T>
}

/**
 * Contains the error information returned by the server
 *
 * @param code A valid HTTP status code
 * @param body The response body as text
 */
class ApiError(code: HttpStatusCode, body: String?) : Error("HTTP Code $code, Body: $body")

/**
 * Contains the exact error caused by the client
 *
 * @param error The error thrown during the request
 * @param body The response body (Only available if the failure occurred after a successful request, usually due to an incorrect api model)
 */
class ApiFailure(error: Throwable, body: String?) : Error(body, error)

/**
 * Callbacks for every kind of response status
 */
inline fun <T> ApiResponse<T>.fold(
    success: (T) -> Unit = {},
    empty: () -> Unit = {},
    error: (ApiError) -> Unit = {},
    failure: (ApiFailure) -> Unit = {}
) = when (this) {
    is ApiResponse.Success -> success(data)
    is ApiResponse.Empty -> empty()
    is ApiResponse.Error -> error(this.error)
    is ApiResponse.Failure -> failure(this.error)
}

/**
 * Version of [fold] that treats both [ApiResponse.Error] and [ApiResponse.Failure] as an error
 */
inline fun <T> ApiResponse<T>.fold(
    success: (T) -> Unit = {},
    fail: (Error) -> Unit = {},
    empty: () -> Unit = {}
) = when (this) {
    is ApiResponse.Success -> success(data)
    is ApiResponse.Empty -> empty()
    is ApiResponse.Error -> fail(error)
    is ApiResponse.Failure -> fail(error)
}

/**
 * Only calls the provided [block] if the response was successful (returned data)
 */
inline fun <T> ApiResponse<T>.ifSuccessful(block: (T) -> Unit) {
    if (this is ApiResponse.Success) block(data)
}

/**
 * Only calls the provided [block] if the response was empty (returned no data)
 */
inline fun <T> ApiResponse<T>.ifEmpty(crossinline block: () -> Unit) {
    if (this is ApiResponse.Empty) block()
}

/**
 * If successful it returns the underlying [data][T] otherwise returns null
 */
fun <T> ApiResponse<T>.getOrNull() = when (this) {
    is ApiResponse.Success -> data
    is ApiResponse.Empty,
    is ApiResponse.Error,
    is ApiResponse.Failure -> null
}