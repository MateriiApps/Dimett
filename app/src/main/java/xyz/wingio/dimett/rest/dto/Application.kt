package xyz.wingio.dimett.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Application(
    val name: String,
    val website: String? = null,
    @SerialName("vapid_key") val vapidKey: String,
    @SerialName("client_id") val clientId: String? = null,
    @SerialName("client_secret") val clientSecret: String? = null,
    val id: String? = null
)