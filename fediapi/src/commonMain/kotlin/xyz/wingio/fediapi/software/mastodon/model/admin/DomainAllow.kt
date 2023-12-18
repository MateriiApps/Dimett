package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a domain allowed to federate.
 *
 * @param id The ID of the [DomainAllow] in the database.
 * @param domain The domain that is allowed to federate.
 * @param createdAt When the domain was allowed to federate.
 */
@Serializable
public data class DomainAllow(
    val id: String,
    val domain: String,
    @SerialName("created_at") val createdAt: Instant
)