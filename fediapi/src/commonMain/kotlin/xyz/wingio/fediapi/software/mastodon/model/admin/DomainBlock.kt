package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a domain limited from federating.
 *
 * @param id The ID of the [DomainBlock] in the database.
 * @param domain The domain that is not allowed to federate.
 * @param createdAt When the domain was blocked from federating.
 * @param severity The policy to be applied by this domain block.
 * @param rejectMedia Whether to reject media attachments from this domain
 * @param rejectReports Whether to reject reports from this domain
 * @param privateComment Reason for why this domain is blocked
 * @param publicComment Publicly shown reason for this block
 * @param obfuscate Whether to obfuscate public displays of this domain block
 */
@Serializable
public data class DomainBlock(
    val id: String,
    val domain: String,
    @SerialName("created_at") val createdAt: Instant,
    val severity: Severity,
    @SerialName("reject_media") val rejectMedia: Boolean,
    @SerialName("reject_reports") val rejectReports: Boolean,
    @SerialName("private_comment") val privateComment: String?,
    @SerialName("public_comment") val publicComment: String?,
    val obfuscate: Boolean
) {

    @Serializable
    public enum class Severity {
        /**
         * Account statuses from this domain will be hidden by default
         */
        @SerialName("silence") SILENCE,

        /**
         * All incoming data from this domain will be rejected
         */
        @SerialName("suspend") SUSPEND,

        /**
         * Do nothing. Allows for rejecting media or reports
         */
        @SerialName("noop") NOOP
    }

}
