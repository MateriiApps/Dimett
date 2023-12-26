package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.status.Status

/**
 * Represents the tree around a given status. Used for reconstructing threads of statuses.
 *
 * @param ancestors Parents in the thread.
 * @param descendants Children in the thread.
 */
@Serializable
public data class Context(
    val ancestors: List<Status>,
    val descendants: List<Status>
)