package xyz.wingio.fediapi.software.mastodon.model.v1

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Configured values and limits for an [instance][V1Instance].
 *
 * @param accounts Limits related to accounts.
 * @param statuses Limits related to authoring statuses.
 * @param mediaAttachments Hints for which attachments will be accepted.
 * @param polls Limits related to polls.
 */
@Serializable
public data class V1InstanceConfiguration(
    val accounts: Accounts,
    val statuses: Statuses,
    @SerialName("media_attachments") val mediaAttachments: MediaAttachments,
    val polls: Polls
) {

    /**
     * Limits related to accounts
     *
     * @param maxFeaturedTags The maximum number of featured tags allowed for each account.
     */
    @Serializable
    public data class Accounts(
        @SerialName("max_featured_tags") val maxFeaturedTags: Int
    )

    /**
     * Limits related to authoring statuses.
     *
     * @param maxCharacters The maximum number of allowed characters per status.
     * @param maxMediaAttachments The maximum number of media attachments that can be added to a status.
     * @param charactersReservedPerUrl Each URL in a status will be assumed to be exactly this many characters.
     */
    @Serializable
    public data class Statuses(
        @SerialName("max_characters") val maxCharacters: Int,
        @SerialName("max_media_attachments") val maxMediaAttachments: Int,
        @SerialName("characters_reserved_per_url") val charactersReservedPerUrl: Int
    )

    /**
     * Hints for which attachments will be accepted.
     *
     * @param supportedMimeTypes Contains MIME types that can be uploaded.
     * @param imageSizeLimit The maximum size of any uploaded image, in bytes.
     * @param imageMatrixLimit The maximum number of pixels (width times height) for image uploads.
     * @param videoSizeLimit The maximum size of any uploaded video, in bytes.
     * @param videoFrameRateLimit The maximum frame rate for any uploaded video.
     * @param videoMatrixLimit The maximum number of pixels (width times height) for video uploads.
     */
    @Serializable
    public data class MediaAttachments(
        @SerialName("supported_mime_types") val supportedMimeTypes: List<String>,
        @SerialName("image_size_limit") val imageSizeLimit: Int,
        @SerialName("image_matrix_limit") val imageMatrixLimit: Int,
        @SerialName("video_size_limit") val videoSizeLimit: Int,
        @SerialName("video_frame_rate_limit") val videoFrameRateLimit: Int,
        @SerialName("video_matrix_limit") val videoMatrixLimit: Int
    )

    /**
     * Limits related to polls.
     *
     * @param maxOptions Each poll is allowed to have up to this many options.
     * @param maxCharactersPerOption Each poll option is allowed to have this many characters.
     * @param minExpiration The shortest allowed poll duration, in seconds.
     * @param maxExpiration The longest allowed poll duration, in seconds.
     */
    @Serializable
    public data class Polls(
        @SerialName("max_options") val maxOptions: Int,
        @SerialName("max_characters_per_option") val maxCharactersPerOption: Int,
        @SerialName("min_expiration") val minExpiration: Int,
        @SerialName("max_expiration") val maxExpiration: Int
    )

}