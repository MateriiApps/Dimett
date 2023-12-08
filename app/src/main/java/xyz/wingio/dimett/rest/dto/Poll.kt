package xyz.wingio.dimett.rest.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/Poll/
@Serializable
data class Poll(
    val id: String,
    @SerialName("expires_at") val expiresAt: Instant? = null,
    val expired: Boolean,
    val multiple: Boolean,
    @SerialName("votes_count") val votes: Int,
    @SerialName("voters_count") val voters: Int? = null,
    val options: List<Option> = listOf(),
    val emojis: List<CustomEmoji> = listOf(),
    val voted: Boolean? = null,
    @SerialName("own_votes") val ownVotes: List<Int> = emptyList()
) {

    // https://docs.joinmastodon.org/entities/Poll/#Option
    @Serializable
    data class Option(
        val title: String,
        @SerialName("votes_count") val votes: Int? = null
    )

}
