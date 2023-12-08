package xyz.wingio.dimett.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/FilterStatus/
@Serializable
data class FilterStatus(
    val id: String,
    @SerialName("status_id") val statusId: String
)
