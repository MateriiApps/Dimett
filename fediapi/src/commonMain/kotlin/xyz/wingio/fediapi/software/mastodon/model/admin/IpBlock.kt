package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an IP address range that cannot be used to sign up.
 *
 * @param id The ID of the [DomainBlock] in the database.
 * @param ip The IP address range that is not allowed to federate.
 * @param severity The associated policy with this IP block.
 * @param comment The recorded reason for this IP block.
 * @param createdAt When the IP block was created.
 * @param expiresAt When the IP block will expire.
 */
@Serializable
public data class IpBlock(
    val id: String,
    val ip: String,
    val severity: Severity,
    val comment: String,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("expires_at") val expiresAt: Instant?
) {

    @Serializable
    public enum class Severity {
        /**
         * Any signup from this IP range will create a pending account
         */
        @SerialName("sign_up_requires_approval") SIGNUP_REQUIRES_APPROVAL,

        /**
         * Any signup from this IP range will be rejected
         */
        @SerialName("sign_up_block") SIGNUP_BLOCKED,

        /**
         * Any activity from this IP range will be rejected entirely
         */
        @SerialName("no_access") NO_ACCESS
    }

}
