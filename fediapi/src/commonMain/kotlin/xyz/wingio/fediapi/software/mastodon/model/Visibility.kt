package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The visibility of a Status (TODO: Link)
 */
@Serializable
public enum class Visibility {
    /**
     * Visible to everyone, shown in public timelines.
     */
    @SerialName("public") PUBLIC,

    /**
     * Visible to public, but not included in public timelines.
     */
    @SerialName("unlisted") UNLISTED,

    /**
     * Visible to followers only, and to any mentioned users.
     */
    @SerialName("private") PRIVATE,

    /**
     * Visible only to mentioned users.
     */
    @SerialName("direct") DIRECT
}