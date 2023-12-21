package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.account.Account

/**
 * Hints related to contacting a representative of the website.
 *
 * @param email An email address that can be messaged regarding inquiries or issues.
 * @param account An account that can be contacted natively over the network regarding inquiries or issues.
 */
@Serializable
public data class InstanceContact(
    val email: String,
    val account: Account
)