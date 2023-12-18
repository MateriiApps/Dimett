package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an IP address associated with a user.
 *
 * @param ip The IP address.
 * @param usedAt The timestamp of when the IP address was last used for this account.
 */
@Serializable
public data class Ip(
    val ip: String,
    @SerialName("used_at") val usedAt: Instant
)
