package xyz.wingio.dimett.rest.dto.user

import androidx.compose.runtime.Stable
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/Account/#Field
@Stable
@Serializable
data class Field(
    val name: String,
    val value: String,
    @SerialName("verified_at") val verifiedAt: Instant? = null
)
