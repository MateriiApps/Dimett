package xyz.wingio.fediapi.software.mastodon.model.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a filter whose keywords matched a given status.
 *
 * @param filter The filter that was matched.
 * @param keywordMatches The keyword within the filter that was matched.
 * @param statusMatches The status ID within the filter that was matched.
 */
@Serializable
public data class FilterResult(
    val filter: Filter,
    @SerialName("keyword_matches") val keywordMatches: List<String>?,
    @SerialName("status_matches") val statusMatches: List<String>?
)
