package xyz.wingio.dimett.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/PreviewCard/
@Serializable
data class PreviewCard(
    val url: String,
    val title: String,
    val description: String? = null,
    val type: Type,
    @SerialName("author_name") val authorName: String? = null,
    @SerialName("author_url") val authorUrl: String? = null,
    val provider: String? = null,
    @SerialName("provider_url") val providerUrl: String? = null,
    val html: String? = null,
    val width: Int? = null,
    val height: Int? = null,
    val image: String? = null,
    @SerialName("embed_url") val embedUrl: String? = null,
    val blurhash: String? = null
) {

    @Serializable
    enum class Type {
        @SerialName("link")
        LINK,

        @SerialName("photo")
        PHOTO,

        @SerialName("video")
        VIDEO,

        @SerialName("rich")
        RICH,
    }

}
