package xyz.wingio.fediapi.software.mastodon.model.v1

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a user-defined filter for determining which statuses should not be shown to the user. Contains a single keyword or phrase.
 *
 * @param id The ID of the filter in the database.
 * @param phrase The text to be filtered.
 * @param context The contexts in which the filter should be applied.
 * @param expiresAt When the filter should no longer be applied.
 * @param irreversible Should matching entities in home and notifications be dropped by the server? See [implementation guidelines for filters](https://docs.joinmastodon.org/api/guidelines/#filters).
 * @param wholeWord Should the filter consider word boundaries? See [implementation guidelines for filters](https://docs.joinmastodon.org/api/guidelines/#filters).
 */
@Serializable
public data class V1Filter(
    val id: String,
    val phrase: String,
    val context: Context,
    @SerialName("expires_at") val expiresAt: Instant?,
    val irreversible: Boolean,
    @SerialName("whole_word") val wholeWord: Boolean
) {

    public enum class Context{
        /**
         * home timeline and lists
         */
        @SerialName("home") HOME,

        /**
         * notifications timeline
         */
        @SerialName("notifications") NOTIFICATIONS,

        /**
         * public timelines
         */
        @SerialName("public") PUBLIC,

        /**
         * expanded thread of a detailed status
         */
        @SerialName("thread") THREAD,

        /**
         * when viewing a profile
         */
        @SerialName("account") ACCOUNT,
    }

}