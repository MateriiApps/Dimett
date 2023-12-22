package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.URL

/**
 * Represents an emoji reaction to an [Announcement].
 *
 * @param name The emoji used for the reaction. Either a unicode emoji, or a custom emoji’s shortcode.
 * @param count The total number of users who have added this reaction.
 * @param me If there is a currently authorized user: Have you added this reaction?
 * @param url If the reaction is a custom emoji: A link to the custom emoji.
 * @param staticUrl If the reaction is a custom emoji: A link to a non-animated version of the custom emoji.
 */
@Serializable
public data class Reaction(
    val name: String,
    val count: String,
    val me: Boolean? = null,
    val url: URL? = null,
    @SerialName("static_url") val staticUrl: URL? = null
)