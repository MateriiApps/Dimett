package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML
import xyz.wingio.fediapi.URL

/**
 * Represents an announcement set by an administrator.
 *
 * @param id The ID of the announcement in the database.
 * @param content The text of the announcement.
 * @param startsAt When the announcement will start.
 * @param endsAt When the announcement will end.
 * @param published Whether the announcement is currently active.
 * @param allDay Whether the announcement should start and end on dates only instead of datetimes. Will be false if there is no [startsAt] or [endsAt] time.
 * @param publishedAt When the announcement was published.
 * @param updatedAt When the announcement was last updated.
 * @param read Whether the announcement has been read by the current user.
 * @param mentions Accounts mentioned in the announcement text.
 * @param statuses Statuses linked in the announcement text.
 * @param tags Tags linked in the announcement text.
 * @param emojis Custom emoji used in the announcement text.
 * @param reactions Emoji reactions attached to the announcement.
 */
@Serializable
public data class Announcement(
    val id: String,
    val content: HTML,
    @SerialName("starts_at") val startsAt: Instant?,
    @SerialName("ends_at") val endsAt: Instant?,
    val published: Boolean,
    @SerialName("all_day") val allDay: Boolean,
    @SerialName("publishedAt") val publishedAt: Instant,
    @SerialName("updated_at") val updatedAt: Instant,
    val read: Boolean? = null,
    val mentions: List<Account>,
    val statuses: List<Status>,
    val tags: List<String>, // TODO: Model
    val emojis: List<CustomEmoji>,
    val reactions: List<Reaction>
) {

    /**
     * User mentioned in an [Announcement]
     *
     * @param id The account ID of the mentioned user.
     * @param username The username of the mentioned user.
     * @param url The location of the mentioned user’s profile.
     * @param acct The webfinger acct: URI of the mentioned user. Equivalent to [username] for local users, or `username@domain` for remote users.
     */
    @Serializable
    public data class Account(
        val id: String,
        val username: String,
        val url: URL,
        val acct: String
    )

    /**
     * Status linked in an [Announcement]'s text
     *
     * @param id The ID of an attached [Status] in the database.
     * @param url The URL of an attached [Status].
     */
    @Serializable
    public data class Status(
        val id: String,
        val url: URL
    )

}
