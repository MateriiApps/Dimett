package xyz.wingio.dimett.rest.dto.post

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/Status/#Tag
@Stable
@Serializable
data class Tag(
    val name: String,
    val url: String
)
