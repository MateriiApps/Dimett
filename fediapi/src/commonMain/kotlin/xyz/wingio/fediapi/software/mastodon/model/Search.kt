package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.account.Account
import xyz.wingio.fediapi.software.mastodon.model.status.Status

/**
 * Represents the results of a search.
 *
 * @param accounts Accounts which match the given query
 * @param statuses Statuses which match the given query
 * @param hashtags Hashtags which match the given query
 */
@Serializable
public data class Search(
    val accounts: List<Account>,
    val statuses: List<Status>,
    val hashtags: List<Tag>
)