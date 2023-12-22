package xyz.wingio.fediapi.software.mastodon.model.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.Blurhash
import xyz.wingio.fediapi.HTML
import xyz.wingio.fediapi.URL

/**
 * Represents a rich preview card that is generated using OpenGraph tags from a URL.
 *
 * @param url Location of linked resource.
 * @param title Title of linked resource.
 * @param description Description of preview.
 * @param type The type of the preview card.
 * @param authorName The author of the original resource.
 * @param authorUrl A link to the author of the original resource.
 * @param providerName The provider of the original resource.
 * @param providerUrl A link to the provider of the original resource.
 * @param html HTML to be used for generating the preview card.
 * @param width Width of preview, in pixels.
 * @param height Height of preview, in pixels.
 * @param image Preview thumbnail.
 * @param embedUrl Used for photo embeds, instead of custom [html].
 * @param blurhash A hash computed by [the BlurHash algorithm](https://github.com/woltapp/blurhash), for generating colorful preview thumbnails when media has not been downloaded yet.
 * @param history (Trends::Link only) Usage statistics for given days (typically the past week).
 */
@Serializable
public data class PreviewCard(
    val url: URL,
    val title: String,
    val description: String,
    val type: Type,
    @SerialName("author_name") val authorName: String,
    @SerialName("author_url") val authorUrl: URL,
    @SerialName("provider_name") val providerName: String,
    @SerialName("provider_url") val providerUrl: URL,
    val html: HTML,
    val width: Int,
    val height: Int,
    val image: URL?,
    @SerialName("embed_url") val embedUrl: URL,
    val blurhash: Blurhash?,
    val history: List<HistoryDay>? = null
) {

    @Serializable
    public enum class Type {
        /**
         *  Link OEmbed
         */
        @SerialName("link") LINK,

        /**
         * Photo OEmbed
         */
        @SerialName("photo") PHOTO,

        /**
         * Video OEmbed
         */
        @SerialName("video") VIDEO,

        /**
         * iframe OEmbed. Not currently accepted, so won’t show up in practice.
         */
        @SerialName("rich") RICH
    }

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