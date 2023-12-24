package xyz.wingio.fediapi.software.mastodon.model.status

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML
import xyz.wingio.fediapi.Language
import xyz.wingio.fediapi.URL
import xyz.wingio.fediapi.software.mastodon.model.CustomEmoji
import xyz.wingio.fediapi.software.mastodon.model.Visibility
import xyz.wingio.fediapi.software.mastodon.model.account.Account
import xyz.wingio.fediapi.software.mastodon.model.filter.FilterResult
import xyz.wingio.fediapi.software.mastodon.model.media.MediaAttachment

/**
 * Represents a status posted by an account.
 *
 * @param id ID of the status in the database.
 * @param uri URI of the status used for federation.,
 * @param createdAt The date when this status was created.
 * @param account The account that authored this status.
 * @param content HTML-encoded status content.
 * @param visibility Visibility of this status.
 * @param sensitive Is this status marked as sensitive content?
 * @param spoilerText Subject or summary line, below which status content is collapsed until expanded.
 * @param mediaAttachments Media that is attached to this status.
 * @param application The application used to post this status.
 * @param mentions Mentions of users within the status content.
 * @param tags Hashtags used within the status content.
 * @param emojis Custom emoji to be used when rendering status content.
 * @param reblogCount How many boosts this status has received.
 * @param favoriteCount How many favourites this status has received.
 * @param replyCount How many replies this status has received.
 * @param url A link to the status’s HTML representation.
 * @param inReplyToId ID of the status being replied to.
 * @param inReplyToAccountId ID of the account that authored the status being replied to.
 * @param reblog The status being reblogged.
 * @param poll The poll attached to the status.
 * @param card Preview card for links included within status content.
 * @param language Primary language of this status.
 * @param text Plain-text source of a status. Returned instead of [content] when status is deleted, so the user may redraft from the source text without the client having to reverse-engineer the original text from the HTML content.
 * @param editedAt Timestamp of when the status was last edited.
 * @param favorited If the current token has an authorized user: Have you favourited this status?
 * @param reblogged If the current token has an authorized user: Have you boosted this status?
 * @param muted If the current token has an authorized user: Have you muted notifications for this status’s conversation?
 * @param bookmarked If the current token has an authorized user: Have you bookmarked this status?
 * @param pinned If the current token has an authorized user: Have you pinned this status? Only appears if the status is pinnable.
 * @param filtered If the current token has an authorized user: The filter and keywords that matched this status.
 */
@Serializable
public data class Status(
    val id: String,
    val uri: String,
    @SerialName("created_at") val createdAt: Instant,
    val account: Account,
    val content: HTML,
    val visibility: Visibility,
    val sensitive: Boolean,
    @SerialName("spoiler_text") val spoilerText: String,
    @SerialName("media_attachments") val mediaAttachments: List<MediaAttachment>,
    val application: Application? = null,
    val mentions: List<Mention>,
    val tags: List<Tag>,
    val emojis: List<CustomEmoji>,
    @SerialName("reblogs_count") val reblogCount: Int,
    @SerialName("favourites_count") val favoriteCount: Int,
    @SerialName("replies_count") val replyCount: Int,
    val url: URL?,
    @SerialName("in_reply_to_id") val inReplyToId: String?,
    @SerialName("in_reply_to_account_id") val inReplyToAccountId: String?,
    val reblog: Status?,
    val poll: Poll?,
    val card: PreviewCard?,
    val language: Language?,
    val text: String?,
    @SerialName("edited_at") val editedAt: Instant?,
    @SerialName("favourited") val favorited: Boolean? = null,
    val reblogged: Boolean? = null,
    val muted: Boolean? = null,
    val bookmarked: Boolean? = null,
    val pinned: Boolean? = null,
    val filtered: List<FilterResult>? = null
) {

    /**
     * he application used to post a status.
     *
     * @param name The name of the application that posted this status.
     * @param website The website associated with the application that posted this status.
     */
    @Serializable
    public data class Application(
        val name: String,
        val website: URL?
    )

    /**
     * Account mentioned in a [Status]
     *
     * @param id The account ID of the mentioned user.
     * @param username The username of the mentioned user.
     * @param url The location of the mentioned user’s profile.
     * @param acct The webfinger acct: URI of the mentioned user. Equivalent to [username] for local users, or `username@domain` for remote users.
     */
    @Serializable
    public data class Mention(
        val id: String,
        val username: String,
        val url: URL,
        val acct: String
    )

    /**
     * Hashtag used in a [Status]
     *
     * @param name The value of the hashtag after the # sign.
     * @param url A link to the hashtag on the instance.
     */
    @Serializable
    public data class Tag(
        val name: String,
        val url: URL
    )

}