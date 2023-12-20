package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents quantitative data about the server.
 *
 * @param key The unique keystring for the requested measure.
 * @param unit The units associated with this data item’s value, if applicable.
 * @param total The numeric total associated with the requested measure.
 * @param humanValue A human-readable formatted value for this data item.
 * @param previousTotal The numeric total associated with the requested measure, in the previous period. Previous period is calculated by subtracting the startAt and endAt dates, then offsetting both start and end dates backwards by the length of the time period.
 * @param data The data available for the requested measure, split into daily buckets.
 */
@Serializable
public data class Measure(
    val key: String,
    val unit: String?,
    val total: String,
    @SerialName("human_value") val humanValue: String? = null,
    @SerialName("previous_total") val previousTotal: String? = null,
    val data: List<Data>
) {

    /**
     * The data available for a single day
     *
     * @param date Midnight on the requested day in the time period.
     * @param value The numeric value for the requested measure.
     */
    @Serializable
    public data class Data(
        val date: Instant,
        val value: String,
    )

}
