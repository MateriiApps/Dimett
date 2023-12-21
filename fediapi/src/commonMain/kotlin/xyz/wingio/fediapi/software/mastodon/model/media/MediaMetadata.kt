package xyz.wingio.fediapi.software.mastodon.model.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Metadata for a given piece of media
 *
 * @param length Length of the media (VIDEO, GIFV, AUDIO).
 * @param duration Length of the media in seconds (VIDEO, GIFV, AUDIO).
 * @param fps Frames/second of the media (VIDEO, GIFV).
 * @param size Size of the media (WxH) (VIDEO, GIFV).
 * @param width Width of the media in pixels (VIDEO, GIFV).
 * @param height Height of the media in pixels (VIDEO, GIFV).
 * @param aspect Aspect ratio of the media (w/h) (VIDEO, GIFV).
 * @param frameRate Framerate of the given media (VIDEO, GIFV).
 * @param audioEncode The encoding used for the audio in this media (VIDEO, AUDIO).
 * @param audioBitrate The bitrate for the audio used in this media (VIDEO, AUDIO).
 * @param audioChannels Channels used for this media's audio (VIDEO, AUDIO).
 * @param bitrate Bits/sec sample rate for this media (VIDEO, GIFV, AUDIO)
 * @param original The original size/length (ALL)
 * @param small A smaller or static version of this media (IMAGE, VIDEO, GIFV).
 * @param focus Focal point for an image (IMAGE)
 */
@Serializable
public data class MediaMetadata(
    val length: String? = null,
    val duration: Float? = null,
    val fps: Int? = null,
    val size: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val aspect: Float? = null,
    @SerialName("frame_rate") val frameRate: String? = null,
    @SerialName("audio_encode") val audioEncode: String? = null,
    @SerialName("audio_bitrate") val audioBitrate: String? = null,
    @SerialName("audio_channels") val audioChannels: AudioChannels? = null,
    val bitrate: Int? = null,
    val original: MediaMetadata? = null,
    val small: MediaMetadata? = null,
    val focus: Focus? = null
) {

    @Serializable
    public enum class AudioChannels {
        @SerialName("mono") MONO,
        @SerialName("stereo") STEREO
    }

    /**
     * Focal point for an image, ranging from (-1, -1) to (1, 1) with (0, 0) being the center of the original image.
     *
     * [Additional information](https://docs.joinmastodon.org/api/guidelines/#focal-points)
     *
     * @param x Ranging from -1 to 1, the relative x coordinate for the focal point
     * @param y Ranging from -1 to 1, the relative y coordinate for the focal point
     */
    @Serializable
    public data class Focus(
        val x: Float,
        val y: Float
    )

}