package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an email domain that cannot be used to sign up.
 *
 * @param id The ID of the [EmailDomainBlock] in the database.
 * @param domain The email domain that is not allowed to be used for signups.
 * @param createdAt When the email domain was disallowed from signups.
 * @param history Usage statistics for given days (typically the past week).
 */
@Serializable
public data class EmailDomainBlock(
    val id: String,
    val domain: String,
    @SerialName("created_at") val createdAt: Instant,
    val history: History
) {

    /**
     * Usage statistics for an [EmailDomainBlock]
     *
     * @param day UNIX timestamp on midnight of the given day.
     * @param accounts The counted accounts signup attempts using that email domain within that day.
     * @param uses The counted IP signup attempts of that email domain within that day.
     */
    @Serializable
    public data class History(
        val day: String? = null,
        val accounts: String? = null,
        val uses: String? = null
    )

}
