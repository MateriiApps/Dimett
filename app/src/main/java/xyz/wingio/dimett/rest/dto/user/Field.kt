package xyz.wingio.dimett.rest.dto.user

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Field(
    val name: String,
    val value: String,
    @SerialName("verified_at") val verifiedAt: Instant? = null
)
