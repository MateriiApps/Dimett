package xyz.wingio.fediapi.software.mastodon.model.account

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML
import xyz.wingio.fediapi.Language
import xyz.wingio.fediapi.URL
import xyz.wingio.fediapi.software.mastodon.model.CustomEmoji
import xyz.wingio.fediapi.software.mastodon.model.Role
import xyz.wingio.fediapi.software.mastodon.model.Visibility

/**
 * Represents a user of Mastodon and their associated profile.
 *
 * @param id The account id.
 * @param username The username of the account, not including domain.
 * @param acct The Webfinger account URI. Equal to [username] for local users, or `username@domain` for remote users.
 * @param url The location of the user’s profile page.
 * @param displayName The profile’s display name.
 * @param note The profile’s bio or description.
 * @param avatar An image icon that is shown next to statuses and in the profile.
 * @param staticAvatar A static version of the avatar. Equal to [avatar] if its value is a static image; different if [avatar] is an animated GIF.
 * @param header An image banner that is shown above the profile and in profile cards.
 * @param staticHeader A static version of the header. Equal to [header] if its value is a static image; different if [header] is an animated GIF.
 * @param locked Whether the account manually approves follow requests.
 * @param fields Additional metadata attached to a profile as name-value pairs.
 * @param emojis Custom emoji entities to be used when rendering the profile.
 * @param bot Indicates that the account may perform automated actions, may not be monitored, or identifies as a robot.
 * @param group Indicates that the account represents a Group actor.
 * @param discoverable Whether the account has opted into discovery features such as the profile directory.
 * @param noIndex Whether the local user has opted out of being indexed by search engines.
 * @param moved Indicates that the profile is currently inactive and that its user has moved to a new account.
 * @param suspended An extra attribute returned only when an account is suspended.
 * @param limited An extra attribute returned only when an account is silenced. If true, indicates that the account should be hidden behind a warning screen.
 * @param createdAt When the account was created.
 * @param lastStatusAt When the most recent status was posted.
 * @param statusCount How many statuses are attached to this account.
 * @param followerCount The reported followers of this profile.
 * @param followingCount The reported follows of this profile.
 *
 * @param source ([CredentialAccount] only) An extra attribute that contains source values to be used with API methods that verify credentials and update credentials.
 * @param role ([CredentialAccount] only) The role assigned to the currently authorized user.
 *
 * @param muteExpiresAt ([MutedAccount] only) When a timed mute will expire, if applicable. `null` if the mute is indefinite
 */
@Serializable
public data class Account(
    public val id: String,
    public val username: String,
    public val acct: String,
    public val url: URL,
    @SerialName("display_name") public val displayName: String,
    public val note: HTML,
    public val avatar: URL,
    @SerialName("avatar_static") public val staticAvatar: URL,
    public val header: URL,
    @SerialName("header_static") public val staticHeader: URL,
    public val locked: Boolean,
    public val fields: List<Field>,
    public val emojis: List<CustomEmoji>,
    public val bot: Boolean,
    public val group: Boolean,
    public val discoverable: Boolean?,
    @SerialName("noindex") public val noIndex: Boolean? = null,
    public val moved: Account? = null,
    public val suspended: Boolean? = null,
    public val limited: Boolean? = null,
    @SerialName("created_at") public val createdAt: Instant,
    @SerialName("last_status_at") public val lastStatusAt: String?, // TODO: Resolve date
    @SerialName("statuses_count") public val statusCount: Int,
    @SerialName("followers_count") public val followerCount: Int,
    @SerialName("following_count") public val followingCount: Int,

    // CredentialAccount fields
    public val source: Source? = null,
    public val role: Role? = null,

    // MutedAccount fields
    @SerialName("mute_expires_at") public val muteExpiresAt: Instant? = null
) {

    /**
     * An extra attribute that contains source values to be used with API methods that verify credentials and update credentials.
     *
     * @param note Profile bio, in plain-text instead of in HTML.
     * @param fields Metadata about the account.
     * @param privacy The default post privacy to be used for new statuses.
     * @param sensitive Whether new statuses should be marked sensitive by default.
     * @param language The default posting language for new statuses.
     * @param followRequestCount The number of pending follow requests.
     */
    @Serializable
    public data class Source(
        public val note: String? = null,
        public val fields: List<Field>? = null,
        public val privacy: Visibility? = null,
        public val sensitive: Boolean? = null,
        public val language: Language? = null,
        @SerialName("follow_requests_count") public val followRequestCount: Int? = null
    )

}

/**
 * An [Account] with the [source][Account.source] attribute, usually the authenticated user
 */
public typealias CredentialAccount = Account

/**
 * An [Account] with the [muteExpiresAt][Account.muteExpiresAt] attribute
 */
public typealias MutedAccount = Account