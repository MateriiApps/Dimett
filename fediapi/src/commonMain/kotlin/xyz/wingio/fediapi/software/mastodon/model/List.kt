package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a list of some users that the authenticated user follows.
 *
 * @param id The internal database ID of the list.
 * @param title The user-defined title of the list.
 * @param repliesPolicy Which replies should be shown in the list.
 */
@Serializable
public data class List(
    val id: String,
    val title: String,
    @SerialName("replies_policy") val repliesPolicy: ReplyPolicy
) {

    /**
     * Which replies should be shown in a list.
     */
    @Serializable
    public enum class ReplyPolicy {
        /**
         * Show replies to any followed user
         */
        @SerialName("followed") FOLLOWED,

        /**
         * Show replies to members of the list
         */
        @SerialName("list") LIST,

        /**
         * Show replies to no one
         */
        @SerialName("none") NONE
    }

}