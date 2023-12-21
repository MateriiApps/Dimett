package xyz.wingio.fediapi.software.mastodon.model.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.Blurhash
import xyz.wingio.fediapi.URL

/**
 * Represents a file or media attachment that can be added to a status.
 *
 * @param id The ID of the attachment in the database.
 * @param type The type of the attachment.
 * @param url The location of the original full-size attachment.
 * @param previewUrl The location of a scaled-down preview of the attachment.
 * @param remoteUrl The location of the full-size original attachment on the remote website.
 * @param textUrl A shorter URL for the attachment.
 * @param meta Metadata returned by Paperclip.
 * @param description Alternate text that describes what is in the media attachment, to be used for the visually impaired or when media attachments do not load.
 * @param blurhash A hash computed by [the BlurHash algorithm](https://github.com/woltapp/blurhash), for generating colorful preview thumbnails when media has not been downloaded yet.
 */
@Serializable
public data class MediaAttachment(
    val id: String,
    val type: Type,
    val url: URL,
    @SerialName("preview_url") val previewUrl: URL,
    @SerialName("remote_url") val remoteUrl: URL?,
    @Deprecated("Deprecated since Mastodon 3.5.0") @SerialName("text_url") val textUrl: String? = null,
    val meta: MediaMetadata,
    val description: String?,
    val blurhash: Blurhash
) {

    /**
     * Type of attachment
     */
    @Serializable
    public enum class Type {
        /**
         * unsupported or unrecognized file type
         */
        @SerialName("unknown") UNKNOWN,

        /**
         * Static image
         */
        @SerialName("image") IMAGE,

        /**
         * Looping, soundless animation
         */
        @SerialName("gifv") GIFV,

        /**
         * Video clip
         */
        @SerialName("video") VIDEO,

        /**
         * Audio track
         */
        @SerialName("audio") AUDIO
    }

}