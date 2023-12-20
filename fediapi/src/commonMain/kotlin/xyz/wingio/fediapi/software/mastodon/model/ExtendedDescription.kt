package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML

/**
 * Represents an extended description for the instance, to be shown on its about page.
 *
 * @param updatedAt A timestamp of when the extended description was last updated.
 * @param content The rendered HTML content of the extended description.
 */
@Serializable
public data class ExtendedDescription(
    @SerialName("updated_at") val updatedAt: Instant,
    val content: HTML
)
