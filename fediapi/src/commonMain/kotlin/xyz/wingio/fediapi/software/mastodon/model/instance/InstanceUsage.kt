package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Usage data for an instance.
 *
 * @param users Usage data related to users on this instance.
 */
@Serializable
public data class InstanceUsage(
    val users: Users
) {

    /**
     *  Usage data related to users on an instance.
     *
     *  @param activeMonth The number of active users in the past 4 weeks.
     */
    @Serializable
    public data class Users(
        @SerialName("active_month") val activeMonth: Int
    )

}