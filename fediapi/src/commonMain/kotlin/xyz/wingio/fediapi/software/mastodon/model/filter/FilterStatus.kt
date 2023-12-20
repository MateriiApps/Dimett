package xyz.wingio.fediapi.software.mastodon.model.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a status ID that, if matched, should cause the filter action to be taken.
 *
 * @param id The ID of the [FilterStatus] in the database.
 * @param statusId The ID of the [Status] that will be filtered.
 */
@Serializable
public data class FilterStatus(
    val id: String,
    @SerialName("status_id") val statusId: String
)