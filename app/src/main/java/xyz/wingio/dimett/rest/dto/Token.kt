package xyz.wingio.dimett.rest.dto

import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/Token/
@Stable
@Serializable
data class Token(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val type: String,
    val scope: String,
    @SerialName("created_at") val createdAt: Long
)