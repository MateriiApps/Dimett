package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.URL

/**
 * Represents a custom emoji.
 *
 * @param shortcode The name of the custom emoji.
 * @param url A link to the custom emoji.
 * @param staticUrl A link to a static copy of the custom emoji.
 * @param visibleInPicker Whether this Emoji should be visible in the picker or unlisted.
 * @param category Used for sorting custom emoji in the picker.
 */
@Serializable
public data class CustomEmoji(
    val shortcode: String,
    val url: URL,
    @SerialName("static_url") val staticUrl: URL,
    @SerialName("visible_in_picker") val visibleInPicker: Boolean,
    val category: String
)