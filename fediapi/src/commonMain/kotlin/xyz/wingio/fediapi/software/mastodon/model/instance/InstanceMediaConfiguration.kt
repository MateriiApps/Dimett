package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
public data class InstanceMediaConfiguration(
    @SerialName("supported_mime_types") val supportedMimeTypes: List<String>,
    @SerialName("image_size_limit") val imageSizeLimit: Int,
    @SerialName("image_matrix_limit") val imageMatrixLimit: Int,
    @SerialName("video_size_limit") val videoSizeLimit: Int,
    @SerialName("video_frame_rate_limit") val videoFrameRateLimit: Int,
    @SerialName("video_matrix_limit") val videoMatrixLimit: Int
)