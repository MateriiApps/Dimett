package xyz.wingio.fediapi.software.mastodon.model.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a keyword that, if matched, should cause the filter action to be taken.
 *
 * @param id The ID of the [FilterKeyword] in the database.
 * @param keyword The phrase to be matched against.
 * @param wholeWord Should the filter consider word boundaries? See [implementation guidelines for filters](https://docs.joinmastodon.org/api/guidelines/#filters).
 */
@Serializable
public data class FilterKeyword(
    val id: String,
    val keyword: String,
    @SerialName("whole_word") val wholeWord: Boolean
)
