package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.account.Account
import xyz.wingio.fediapi.software.mastodon.model.status.Status

/**
 * Represents a notification of an event relevant to the user.
 *
 * @param id The id of the notification in the database.
 * @param type The type of event that resulted in the notification.
 * @param createdAt The timestamp of the notification.
 * @param account The account that performed the action that generated the notification.
 * @param status Status that was the object of the notification. Attached when type of the notification is [favorite][Type.FAVORITE], [reblog][Type.REBLOG], [status][Type.STATUS], [mention][Type.MENTION], [poll][Type.POLL], or [update][Type.UPDATE].
 * @param report Report that was the object of the notification. Attached when type of the notification is [admin.report][Type.ADMIN_REPORT].
 */
@Serializable
public data class Notification(
    val id: String,
    val type: Type,
    @SerialName("created_at") val createdAt: Instant,
    val account: Account,
    val status: Status? = null,
    val report: Report? = null
) {

    @Serializable
    public enum class Type {
        /**
         * Someone mentioned you in their status
         */
        @SerialName("mention") MENTION,

        /**
         * Someone you enabled notifications for has posted a status
         */
        @SerialName("status") STATUS,

        /**
         * Someone boosted one of your statuses
         */
        @SerialName("reblog") REBLOG,

        /**
         * Someone followed you
         */
        @SerialName("follow") FOLLOW,

        /**
         * Someone requested to follow you
         */
        @SerialName("follow_request") FOLLOW_REQUEST,

        /**
         * Someone favorited one of your statuses
         */
        @SerialName("favourite") FAVORITE,

        /**
         * A poll you have voted in or created has ended
         */
        @SerialName("poll") POLL,

        /**
         *  A status you interacted with has been edited
         */
        @SerialName("update") UPDATE,

        /**
         * Someone signed up (optionally sent to admins)
         */
        @SerialName("admin.sign_up") ADMIN_SIGN_UP,

        /**
         * A new report has been filed
         */
        @SerialName("admin.report") ADMIN_REPORT
    }

}