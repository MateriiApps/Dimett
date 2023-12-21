package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.URL

/**
 * Configured values and limits for this website.
 *
 * @param urls URLs of interest for clients apps.
 * @param vapid
 * @param accounts Limits related to accounts.
 * @param statuses Limits related to authoring statuses.
 * @param mediaAttachments Hints for which attachments will be accepted.
 * @param polls Limits related to polls.
 * @param translation Hints related to translation.
 */
@Serializable
public data class InstanceConfiguration(
    val urls: ConfigurationUrls,
    val vapid: Vapid? = null, // Not supported by most instances yet. TODO: Mark as non-null when wide support is reached
    val accounts: Accounts,
    val statuses: InstanceStatusConfiguration,
    @SerialName("media_attachments") val mediaAttachments: InstanceMediaConfiguration,
    val polls: InstancePollConfiguration,
    val translation: Translation
) {

    /**
     * URLs of interest for clients apps.
     *
     * @param streamingApi The Websockets URL for connecting to the streaming API.
     */
    @Serializable
    public data class ConfigurationUrls(
        @SerialName("streaming_api") val streamingApi: URL
    )

    /**
     * @param publicKey The instances VAPID public key
     */
    @Serializable
    public data class Vapid(
        @SerialName("public_key") val publicKey: String
    )

    /**
     * Limits related to accounts.
     *
     * @param maxFeaturedTags The maximum number of featured tags allowed for each account
     */
    @Serializable
    public data class Accounts(
        @SerialName("max_featured_tags") val maxFeaturedTags: Int
    )

    /**
     * Hints related to translation.
     *
     * @param enabled Whether the Translations API is available on this instance.
     */
    @Serializable
    public data class Translation(
        val enabled: Boolean
    )

}