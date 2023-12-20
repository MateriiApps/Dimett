package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.URL

/**
 * Represents an application that interfaces with the REST API to access accounts or post statuses.
 *
 * @param name The name of your application.
 * @param website The website associated with your application.
 * @param clientId Client ID key, to be used for obtaining OAuth tokens
 * @param clientSecret Client secret key, to be used for obtaining OAuth tokens
 * @param vapidKey Used for Push Streaming API. Returned with POST /api/v1/apps. Equivalent to WebPushSubscription#server_key and Instance#vapid_public_key
 */
@Serializable
public data class Application(
    val name: String,
    val website: URL? = null,
    @SerialName("client_id") val clientId: String? = null,
    @SerialName("client_secret") val clientSecret: String? = null,
    @SerialName("vapid_key") @Deprecated("pending removal") val vapidKey: String? = null
)
