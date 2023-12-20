package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.SHA256

/**
 * Represents a domain that is blocked by the instance.
 *
 * @param domain The domain which is blocked. This may be obfuscated or partially censored.
 * @param digest The SHA256 hash digest of the domain string.
 * @param severity The level to which the domain is blocked.
 * @param comment  An optional reason for the domain block.
 */
@Serializable
public data class DomainBlock(
    val domain: String,
    val digest: SHA256,
    val severity: Severity,
    val comment: String? = null
) {

    @Serializable
    public enum class Severity {
        /**
         * Users from this domain will be hidden from timelines, threads, and notifications (unless you follow the user).
         */
        @SerialName("silence") SILENCE,

        /**
         * Incoming messages from this domain will be rejected and dropped entirely.
         */
        @SerialName("suspend") SUSPEND
    }

}
