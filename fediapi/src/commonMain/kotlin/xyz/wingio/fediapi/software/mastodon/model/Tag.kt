package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.URL

/**
 *  Represents a hashtag used within the content of a status.
 *
 *  @param name The value of the hashtag after the # sign.
 *  @param url A link to the hashtag on the instance.
 *  @param history Usage statistics for given days (typically the past week).
 *  @param following Whether the current token’s authorized user is following this tag.
 *  @param id ([Admin::Tag][AdminTag] ONLY) The ID of the Tag in the database.
 *  @param trendable ([Admin::Tag][AdminTag] ONLY) Whether the hashtag has been approved to trend.
 *  @param usable ([Admin::Tag][AdminTag] ONLY) Whether the hashtag has not been disabled from auto-linking.
 *  @param requiresReview ([Admin::Tag][AdminTag] ONLY) Whether the hashtag has not been reviewed yet to approve or deny its trending.
 */
@Serializable
public data class Tag(
    val name: String,
    val url: URL,
    val history: List<HistoryDay>,
    val following: Boolean? = null,

    // Admin::Tag only
    val id: String? = null,
    val trendable: Boolean? = null,
    val usable: Boolean? = null,
    @SerialName("requires_review") val requiresReview: Boolean? = null
) {

    /**
     * Usage statistics for a given day
     *
     * @param day UNIX timestamp on midnight of the given day.
     * @param accounts The counted accounts using the link within that day.
     * @param uses The counted statuses using the link within that day.
     */
    @Serializable
    public data class HistoryDay(
        val day: Long,
        val accounts: Int,
        val uses: Int
    )

}

/**
 * Version of [Tag] with support for the [id][Tag.id], [trendable][Tag.trendable], [usable][Tag.usable], and [requiresReview][Tag.requiresReview] properties.
 */
public typealias AdminTag = Tag