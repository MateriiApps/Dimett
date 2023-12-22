package xyz.wingio.fediapi.software.mastodon.model.status

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.CustomEmoji

/**
 * Represents a poll attached to a status.
 *
 * @param id The ID of the poll in the database.
 * @param expiresAt When the poll ends.
 * @param expired Is the poll currently expired?
 * @param multiple Does the poll allow multiple-choice answers?
 * @param voteCount How many votes have been received.
 * @param voterCount How many unique accounts have voted on a multiple-choice poll.
 * @param options Possible answers for the poll.
 * @param emojis Custom emoji to be used for rendering poll options.
 * @param voted When called with a user token, has the authorized user voted?
 * @param ownVotes When called with a user token, which options has the authorized user chosen? Contains an array of index values for [options].
 */
@Serializable
public data class Poll(
    val id: String,
    @SerialName("expires_at") val expiresAt: Instant?,
    val expired: Boolean,
    val multiple: Boolean,
    @SerialName("votes_count") val voteCount: Int,
    @SerialName("voters_count") val voterCount: Int,
    val options: List<Option>,
    val emojis: List<CustomEmoji>,
    val voted: Boolean? = null,
    @SerialName("own_votes") val ownVotes: List<Int>? = null
) {

    /**
     * An option in a [Poll]
     *
     * @param title The text value of the poll option.
     * @param voteCount The total number of received votes for this option, null if results are not published yet.
     */
    @Serializable
    public data class Option(
        val title: String,
        @SerialName("votes_count") val voteCount: Int?
    )

}