package xyz.wingio.dimett.rest.dto.post

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val name: String,
    val url: String
)
