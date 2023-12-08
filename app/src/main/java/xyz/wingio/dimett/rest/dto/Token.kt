package xyz.wingio.dimett.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/Token/
@Serializable
data class Token(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val type: String,
    val scope: String,
    @SerialName("created_at") val createdAt: Long
)