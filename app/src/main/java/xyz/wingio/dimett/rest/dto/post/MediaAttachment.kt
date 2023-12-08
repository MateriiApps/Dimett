package xyz.wingio.dimett.rest.dto.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/MediaAttachment/
@Serializable
data class MediaAttachment(
    val id: String,
    val type: Type,
    val url: String,
    @SerialName("preview_url") val previewUrl: String? = null,
    @SerialName("remote_url") val remoteUrl: String? = null,
    val meta: Meta? = null,
    val description: String? = null,
    val blurhash: String? = null
) {

    @Serializable
    enum class Type {
        @SerialName("unknown")
        UNKNOWN,

        @SerialName("image")
        IMAGE,

        @SerialName("gifv")
        GIFV,

        @SerialName("video")
        VIDEO,

        @SerialName("audio")
        AUDIO
    }

    // This isn't really standardized so I had to find this via checking response json
    @Serializable
    data class Meta(
        val original: MetaData? = null,
        val large: MetaData? = null,
        val medium: MetaData? = null,
        val small: MetaData? = null
    ) {

        @Serializable
        data class MetaData(
            val width: Int? = null,
            val height: Int? = null,
            val size: String? = null,
            val aspect: Float? = null,
            @SerialName("frame_rate") val frameRate: String? = null,
            val duration: Float? = null,
            val bitrate: Long? = null
        )

    }

}
