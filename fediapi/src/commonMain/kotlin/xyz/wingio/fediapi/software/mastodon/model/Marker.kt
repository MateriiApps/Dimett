package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the last read position within a user's timelines.
 *
 * @param lastReadId The ID of the most recently viewed entity.
 * @param version An incrementing counter, used for locking to prevent write conflicts.
 * @param updatedAt The timestamp of when the marker was set.
 */
@Serializable
public data class Marker(
    @SerialName("last_read_id") val lastReadId: String,
    val version: Int,
    @SerialName("updated_at") val updatedAt: Instant
)