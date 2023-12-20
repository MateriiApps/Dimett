package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.Language
import xyz.wingio.fediapi.software.mastodon.model.Role
import xyz.wingio.fediapi.software.mastodon.model.account.Account

/**
 * Admin-level information about a given account.
 *
 * @param id The ID of the account in the database.
 * @param username The username of the account.
 * @param domain The domain of the account, if it is remote.
 * @param createdAt When the account was first discovered.
 * @param email The email address associated with the account.
 * @param ip The IP address last used to login to this account.
 * @param ips All known IP addresses associated with this account.
 * @param locale The locale of the account.
 * @param inviteRequest The reason given when requesting an invite (for instances that require manual approval of registrations)
 * @param role The current role of the account.
 * @param confirmed Whether the account has confirmed their email address.
 * @param approved Whether the account is currently approved.
 * @param disabled Whether the account is currently disabled.
 * @param silenced Whether the account is currently silenced.
 * @param suspended Whether the account is currently suspended.
 * @param account User-level information about the account.
 * @param createdByApplicationId The ID of the [Application] that created this account, if applicable
 * @param invitedByAccountId The ID of the [Account] that invited this user, if applicable.
 */
@Serializable
public data class AdminAccount(
    val id: String,
    val username: String,
    val domain: String?,
    @SerialName("created_at") val createdAt: Instant,
    val email: String,
    val ip: String?,
    val ips: List<Ip>,
    val locale: Language,
    @SerialName("invite_request") val inviteRequest: String?,
    val role: Role,
    val confirmed: Boolean,
    val approved: Boolean,
    val disabled: Boolean,
    val silenced: Boolean,
    val suspended: Boolean,
    val account: Account,
    @SerialName("created_by_application_id") val createdByApplicationId: String? = null,
    @SerialName("invited_by_account_id") val invitedByAccountId: String? = null
)
