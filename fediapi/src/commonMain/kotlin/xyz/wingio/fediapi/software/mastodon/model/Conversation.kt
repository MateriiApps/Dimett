package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.account.Account
import xyz.wingio.fediapi.software.mastodon.model.status.Status

/**
 * Represents a conversation with "direct message" visibility.
 *
 * @param id The ID of the conversation in the database.
 * @param unread Is the conversation currently marked as unread?
 * @param accounts Participants in the conversation.
 * @param lastStatus The last status in the conversation.
 */
@Serializable
public data class Conversation(
    val id: String,
    val unread: Boolean,
    val accounts: List<Account>,
    @SerialName("last_status") val lastStatus: Status?
)