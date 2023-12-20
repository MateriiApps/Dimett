package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an error message.
 *
 * @param error The error message.
 * @param errorDescription A longer description of the error, mainly provided with the OAuth API.
 */
@Serializable
public data class Error(
    val error: String,
    @SerialName("error_description") val errorDescription: String? = null
)
