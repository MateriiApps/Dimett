package xyz.wingio.fediapi.software.mastodon.model.v1

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML
import xyz.wingio.fediapi.Language
import xyz.wingio.fediapi.URL
import xyz.wingio.fediapi.software.mastodon.model.Rule
import xyz.wingio.fediapi.software.mastodon.model.account.Account

/**
 * Represents the software instance of Mastodon running on this domain.
 *
 * @param uri The domain name of the instance.
 * @param title The title of the website.
 * @param shortDescription A short, plain-text description defined by the admin.
 * @param description An HTML-permitted description of the Mastodon site.
 * @param email An email that may be contacted for any inquiries.
 * @param version The version of Mastodon installed on the instance.
 * @param urls URLs of interest for clients apps.
 * @param stats Statistics about how much information the instance contains.
 * @param thumbnail Banner image for the website.
 * @param languages Primary languages of the website and its staff.
 * @param registrations Whether registrations are enabled.
 * @param approvalRequired Whether registrations require moderator approval.
 * @param invitesEnabled Whether invites are enabled.
 * @param configuration Configured values and limits for this website.
 * @param contactAccount  A user that can be contacted, as an alternative to [email].
 * @param rules An itemized list of rules for this website.
 */
@Serializable
public data class V1Instance(
    val uri: String,
    val title: String,
    @SerialName("short_description") val shortDescription: String,
    val description: HTML,
    val email: String,
    val version: String,
    val urls: Urls,
    val stats: Stats,
    val thumbnail: URL?,
    val languages: List<Language>,
    val registrations: Boolean,
    @SerialName("approval_required") val approvalRequired: Boolean,
    @SerialName("invites_enabled") val invitesEnabled: Boolean,
    val configuration: V1InstanceConfiguration,
    @SerialName("contact_account") val contactAccount: Account,
    val rules: List<Rule>
) {

    /**
     * URLs of interest for clients apps.
     *
     * @param streamingApi The Websockets URL for connecting to the streaming API.
     */
    @Serializable
    public data class Urls(
        @SerialName("streaming_api") val streamingApi: URL
    )

    /**
     * Statistics for an [instance][V1Instance]
     *
     * @param userCount Total users on this instance.
     * @param statusCount Total statuses on this instance.
     * @param domainCount Total domains discovered by this instance.
     */
    @Serializable
    public data class Stats(
        @SerialName("user_count") val userCount: Int,
        @SerialName("status_count") val statusCount: Int,
        @SerialName("domain_count") val domainCount: Int
    )

}