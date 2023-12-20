package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.URL

/**
 * Represents a hashtag that is featured on a profile.
 *
 * @param id The internal ID of the featured tag in the database.
 * @param name The name of the hashtag being featured.
 * @param url A link to all statuses by a user that contain this hashtag.
 * @param statusesCount The number of authored statuses containing this hashtag.
 * @param lastStatusAt The timestamp of the last authored status containing this hashtag.
 */
@Serializable
public data class FeaturedTag(
    val id: String,
    val name: String,
    val url: URL,
    @SerialName("statuses_count") val statusesCount: Int,
    @SerialName("last_status_at") val lastStatusAt: Instant
)
