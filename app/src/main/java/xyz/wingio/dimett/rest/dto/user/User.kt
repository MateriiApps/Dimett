package xyz.wingio.dimett.rest.dto.user

import androidx.compose.runtime.Stable
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.dimett.rest.dto.CustomEmoji

// https://docs.joinmastodon.org/entities/Account
@Stable
@Serializable
data class User(
    val id: String,
    val username: String,
    val acct: String,
    val url: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("note") val bio: String,
    val avatar: String,
    @SerialName("avatar_static") val staticAvatar: String,
    @SerialName("header") val banner: String,
    @SerialName("header_static") val staticBanner: String,
    @SerialName("locked") val private: Boolean,
    val fields: List<Field> = emptyList(),
    val emojis: List<CustomEmoji> = emptyList(),
    val bot: Boolean,
    val group: Boolean? = null,
    val discoverable: Boolean? = null,
    @SerialName("noindex") val noIndex: Boolean? = null,
    val moved: User? = null,
    val suspended: Boolean? = null,
    val limited: Boolean? = null,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("last_status_at") val lastStatusAt: String? = null,
    @SerialName("statuses_count") val statusCount: Int,
    @SerialName("followers_count") val followers: Int,
    @SerialName("following_count") val following: Int,
    @SerialName("mute_expires_at") val muteExpiration: Instant? = null
)
