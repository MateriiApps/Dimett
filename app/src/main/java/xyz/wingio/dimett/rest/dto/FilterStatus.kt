package xyz.wingio.dimett.rest.dto

import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/FilterStatus/
@Stable
@Serializable
data class FilterStatus(
    val id: String,
    @SerialName("status_id") val statusId: String
)
