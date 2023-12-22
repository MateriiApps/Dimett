package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.Language

/**
 * Represents a user's preferences.
 *
 * @param defaultStatusVisibility Default visibility for new posts.
 * @param defaultStatusSensitive Default sensitivity flag for new posts.
 * @param defaultLanguage Default language for new posts
 * @param mediaExpandPreference Whether media attachments should be automatically displayed or blurred/hidden.
 * @param expandSpoilers Whether CWs should be expanded by default.
 */
@Serializable
public data class Preferences(
    @SerialName("posting:default:visibility") val defaultStatusVisibility: Visibility,
    @SerialName("posting:default:sensitive") val defaultStatusSensitive: Boolean,
    @SerialName("posting:default:language") val defaultLanguage: Language?,
    @SerialName("reading:expand:media") val mediaExpandPreference: MediaExpandPreference,
    @SerialName("reading:expand:spoilers") val expandSpoilers: Boolean
) {

    @Serializable
    public enum class MediaExpandPreference {
        /**
         * Hide media marked as sensitive
         */
        @SerialName("default") DEFAULT,

        /**
         * Always show all media by default, regardless of sensitivity
         */
        @SerialName("show_all") SHOW_ALL,

        /**
         * Always hide all media by default, regardless of sensitivity
         */
        @SerialName("hide_all") HIDE_ALL,
    }

}