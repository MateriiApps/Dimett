package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.account.Account

/**
 * Represents a subset of your follows who also follow some other user.
 *
 * @param id The ID of the [Account] in the database.
 * @param accounts Accounts you follow that also follow this account.
 */
@Serializable
public data class FamiliarFollowers(
    val id: String,
    val accounts: List<Account>
)
