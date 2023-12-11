package xyz.wingio.dimett.rest.dto

import androidx.compose.runtime.Stable
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/Role/
@Stable
@Serializable
data class Role(
    val id: String,
    val name: String,
    val color: String,
    val position: Int? = null,
    val permissions: Int,
    val highlighted: Boolean,
    @SerialName("created_at") val createdAt: Instant? = null,
    @SerialName("updated_at") val updatedAt: Instant? = null
)
