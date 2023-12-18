package xyz.wingio.fediapi.software.mastodon.model.account

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML

/**
 * Name-value pair attached to an [Account].
 *
 * @param name The key of a given field’s key-value pair.
 * @param value The value associated with the [name] key.
 * @param verifiedAt Timestamp of when the server verified a URL value for a rel=“me” link, null if link isn't verified.
 */
@Serializable
public data class Field(
    val name: String,
    val value: HTML,
    @SerialName("verified_at") val verifiedAt: Instant?
)