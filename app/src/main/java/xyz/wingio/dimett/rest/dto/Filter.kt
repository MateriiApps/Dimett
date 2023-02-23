package xyz.wingio.dimett.rest.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Filter(
    val id: String,
    val title: String,
    val context: List<Context> = emptyList(),
    @SerialName("expires_at") val expiresAt: Instant? = null,
    @SerialName("filter_action") val action: Action,
    val keywords: List<FilterKeyword> = emptyList(),
    val statuses: List<FilterStatus> = emptyList()
) {

    @Serializable
    enum class Context {
        @SerialName("home")
        HOME,

        @SerialName("notifications")
        NOTIFICATIONS,

        @SerialName("public")
        PUBLIC,

        @SerialName("thread")
        THREAD,

        @SerialName("account")
        ACCOUNT
    }

    @Serializable
    enum class Action {
        @SerialName("warn")
        WARN,

        @SerialName("hide")
        HIDE
    }

}
