package xyz.wingio.dimett.rest.dto.post

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.dimett.rest.dto.CustomEmoji
import xyz.wingio.dimett.rest.dto.FilterResult
import xyz.wingio.dimett.rest.dto.Poll
import xyz.wingio.dimett.rest.dto.PreviewCard
import xyz.wingio.dimett.rest.dto.user.User

// https://docs.joinmastodon.org/entities/Status
@Serializable
data class Post(
    val id: String,
    val uri: String,
    val url: String? = null,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("account") val author: User,
    val content: String? = null,
    val visibility: Visibility,
    val sensitive: Boolean,
    @SerialName("spoiler_text") val spoilerText: String,
    @SerialName("media_attachments") val media: List<MediaAttachment> = emptyList(),
    val application: Application? = null,
    val mentions: List<Mention> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val emojis: List<CustomEmoji> = emptyList(),
    @SerialName("reblogs_count") val boosts: Int,
    @SerialName("favourites_count") val favorites: Int,
    @SerialName("replies_count") val replies: Int,
    @SerialName("in_reply_to_id") val repliedTo: String? = null,
    @SerialName("in_reply_to_account_id") val userRepliedTo: String? = null,
    @SerialName("reblog") val boosted: Post? = null,
    val poll: Poll? = null,
    val card: PreviewCard? = null,
    val language: String? = null,
    val text: String? = null,
    @SerialName("edited_at") val editedAt: String? = null,
    @SerialName("favourited") val favorited: Boolean? = null,
    @SerialName("reblogged") val hasBoosted: Boolean? = null,
    val muted: Boolean? = null,
    val bookmarked: Boolean? = null,
    val pinned: Boolean? = null,
    val filtered: List<FilterResult> = emptyList()
) {

    @Serializable
    enum class Visibility {
        @SerialName("public")
        PUBLIC,

        @SerialName("unlisted")
        UNLISTED,

        @SerialName("private")
        PRIVATE,

        @SerialName("direct")
        DIRECT,

        @SerialName("local")
        LOCAL,
    }

}

// https://docs.joinmastodon.org/entities/Status/#application
@Serializable
data class Application(
    val name: String,
    val website: String? = null
)
