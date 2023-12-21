package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML

/**
 * Information about registering for this website.
 *
 * @param enabled Whether registrations are enabled.
 * @param approvalRequired Whether registrations require moderator approval.
 * @param message A custom message to be shown when registrations are closed.
 */
@Serializable
public data class InstanceRegistrationConfiguration(
    val enabled: Boolean,
    @SerialName("approval_required") val approvalRequired: Boolean,
    val message: HTML?
)