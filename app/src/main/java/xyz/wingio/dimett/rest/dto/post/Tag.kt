package xyz.wingio.dimett.rest.dto.post

import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/Status/#Tag
@Serializable
data class Tag(
    val name: String,
    val url: String
)
