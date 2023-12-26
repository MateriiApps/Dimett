package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.URL

/**
 * Represents a subscription to the push streaming server.
 *
 * @param id The ID of the Web Push subscription in the database.
 * @param endpoint Where push alerts will be sent to.
 * @param serverKey The streaming server’s VAPID key.
 * @param alerts Which alerts should be delivered to the [endpoint].
 */
@Serializable
public data class WebPushSubscription(
    val id: String,
    val endpoint: URL,
    @SerialName("server_key") val serverKey: String,
    val alerts: Alerts
) {

    /**
     * The enabled alerts for a [WebPushSubscription]
     *
     * @param mention Receive a push notification when someone else has mentioned you in a status?
     * @param status Receive a push notification when a subscribed account posts a status?
     * @param reblog Receive a push notification when a status you created has been boosted by someone else?
     * @param follow Receive a push notification when someone has followed you?
     * @param followRequest Receive a push notification when someone has requested to followed you?
     * @param favorite Receive a push notification when a status you created has been favorited by someone else?
     * @param poll Receive a push notification when a poll you voted in or created has ended?
     * @param update Receive a push notification when a status you interacted with has been edited?
     * @param adminSignUp (ADMIN ONLY) Receive a push notification when a new user has signed up?
     * @param adminReport (ADMIN ONLY) Receive a push notification when a new report has been filed?
     */
    @Serializable
    public data class Alerts(
        val mention: Boolean,
        val status: Boolean,
        val reblog: Boolean,
        val follow: Boolean,
        @SerialName("followRequest") val followRequest: Boolean,
        @SerialName("favourite") val favorite: Boolean,
        val poll: Boolean,
        val update: Boolean,
        @SerialName("admin.sign_up") val adminSignUp: Boolean,
        @SerialName("admin.report") val adminReport: Boolean
    )

}