package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.Blurhash
import xyz.wingio.fediapi.URL

/**
* An image used to represent an instance.
 *
 * @param url The URL for the thumbnail image.
 * @param blurhash A hash computed by [the BlurHash algorithm](https://github.com/woltapp/blurhash), for generating colorful preview thumbnails when media has not been downloaded yet.
 * @param versions Links to scaled resolution images, for high DPI screens.
*/
@Serializable
public data class InstanceThumbnail(
    val url: URL,
    val blurhash: Blurhash? = null,
    val versions: Versions? = null
) {

    /**
     * Links to scaled resolution images, for high DPI screens.
     *
     * @param single The URL for the thumbnail image at 1x resolution.
     * @param double The URL for the thumbnail image at 2x resolution.
     */
     @Serializable
     public data class Versions(
         @SerialName("@1x") val single: String? = null,
         @SerialName("@2x") val double: String? = null
     )

}