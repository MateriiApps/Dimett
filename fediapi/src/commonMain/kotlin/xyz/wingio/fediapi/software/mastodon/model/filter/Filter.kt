package xyz.wingio.fediapi.software.mastodon.model.filter

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a user-defined filter for determining which statuses should not be shown to the user.
 *
 * @param id The ID of the [Filter] in the database.
 * @param title A title given by the user to name the filter.
 * @param context The contexts in which the filter should be applied.
 * @param expiresAt When the filter should no longer be applied.
 * @param filterAction The action to be taken when a status matches this filter.
 * @param keywords The keywords grouped under this filter.
 * @param statuses The statuses grouped under this filter.
 */
@Serializable
public data class Filter(
    val id: String,
    val title: String,
    val context: Context,
    @SerialName("expires_at") val expiresAt: Instant?,
    @SerialName("filter_action") val filterAction: FilterAction,
    val keywords: List<FilterKeyword>,
    val statuses: List<FilterStatus>
) {

    @Serializable
    public enum class Context {
        /**
         * Home timeline and lists
         */
        @SerialName("home") HOME,

        /**
         * Notifications timeline
         */
        @SerialName("notifications") NOTIFICATIONS,

        /**
         * Public timelines
         */
        @SerialName("public") PUBLIC,

        /**
         * Expanded thread of a detailed status
         */
        @SerialName("thread") THREAD,

        /**
         * When viewing a profile
         */
        @SerialName("account") ACCOUNT
    }

    @Serializable
    public enum class FilterAction {
        /**
         * Show a warning that identifies the matching filter by [title], and
         * allow the user to expand the filtered status.
         * This is the default (and unknown values should be
         * treated as equivalent to [WARN]).
         */
        @SerialName("warn") WARN,

        /**
         * Do not show this status if it is received
         */
        @SerialName("hide") HIDE
    }

}
