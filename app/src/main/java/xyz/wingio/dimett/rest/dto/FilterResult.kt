package xyz.wingio.dimett.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// https://docs.joinmastodon.org/entities/FilterResult/
@Serializable
data class FilterResult(
    val filter: Filter,
    @SerialName("keyword_matches") val keywordMatches: List<String> = emptyList(),
    @SerialName("status_matches") val statusMatches: List<String> = emptyList()
)
