package xyz.wingio.dimett.rest.dto.post

import kotlinx.serialization.Serializable

@Serializable
data class Mention(
    val id: String,
    val username: String,
    val url: String,
    val acct: String
)
