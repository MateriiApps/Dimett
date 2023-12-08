package xyz.wingio.dimett.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/CustomEmoji/
@Serializable
data class CustomEmoji(
    val shortcode: String,
    val url: String,
    @SerialName("url_static") val staticUrl: String? = null,
    @SerialName("visible_in_picker") val visibleInPicker: Boolean,
    val category: String? = null
)
