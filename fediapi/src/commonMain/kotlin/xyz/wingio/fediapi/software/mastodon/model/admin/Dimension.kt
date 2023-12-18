package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents qualitative data about the server.
 *
 * @param key The unique keystring for the requested dimension.
 * @param data The data available for the requested dimension.
 */
@Serializable
public data class Dimension(
    val key: String,
    val data: List<Data>
) {

    /**
     * Data available for a given dimension
     *
     * @param key The unique keystring for this data item.
     * @param humanKey A human-readable key for this data item.
     * @param value The value for this data item.
     * @param unit The units associated with this data item’s value, if applicable.
     * @param humanValue A human-readable formatted value for this data item.
     */
    @Serializable
    public data class Data(
        val key: String,
        @SerialName("human_key") val humanKey: String,
        val value: String,
        val unit: String? = null,
        @SerialName("human_value") val humanValue: String? = null
    )

}
