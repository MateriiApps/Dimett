package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Limits related to authoring statuses.
 *
 * @param maxCharacters The maximum number of allowed characters per status.
 * @param maxMediaAttachments The maximum number of media attachments that can be added to a status.
 * @param charactersReservedPerUrl Each URL in a status will be assumed to be exactly this many characters.
 */
@Serializable
public data class InstanceStatusConfiguration(
    @SerialName("max_characters") val maxCharacters: Int,
    @SerialName("max_media_attachments") val maxMediaAttachments: Int,
    @SerialName("characters_reserved_per_url") val charactersReservedPerUrl: Int
)