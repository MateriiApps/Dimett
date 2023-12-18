package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a retention metric.
 *
 * @param period The timestamp for the start of the period, at midnight.
 * @param frequency The size of the bucket for the returned data.
 * @param data Retention data for users who registered during the given period.
 */
@Serializable
public data class Cohort(
    val period: Instant,
    val frequency: Frequency,
    val data: List<Data>
) {

    @Serializable
    public enum class Frequency {
        /**
         * Daily buckets
         */
        @SerialName("day") DAILY,

        /**
         * Monthly buckets
         */
        @SerialName("month") MONTHLY,
    }

    /**
     * Data for users who registered during a given period.
     *
     * @param date The timestamp for the start of the bucket, at midnight.
     * @param rate The percentage rate of users who registered in the specified [period] and were active for the given [date] bucket.
     * @param value How many users registered in the specified [period] and were active for the given [date] bucket.
     */
    @Serializable
    public data class Data(
        val date: Instant,
        val rate: Float,
        val value: Int
    )

}