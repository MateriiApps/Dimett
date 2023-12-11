package xyz.wingio.dimett.rest.dto.post

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/Status/#Mention
@Stable
@Serializable
data class Mention(
    val id: String,
    val username: String,
    val url: String,
    val acct: String
)
