package xyz.wingio.fediapi.software.mastodon.model.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a status's source as plain text.
 *
 * @param id ID of the status in the database.
 * @param text The plain text used to compose the status.
 * @param spoilerText The plain text used to compose the status’s subject or content warning.
 */
@Serializable
public data class StatusSource(
    val id: String,
    val text: String,
    @SerialName("spoiler_text") val spoilerText: String
)