package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.SHA256

/**
 * Represents a canonical email block (hashed).
 *
 * @param id The ID of the email block in the database.
 * @param canonicalEmailHash The SHA256 hash of the canonical email address.
 */
@Serializable
public data class CanonicalEmailBlock(
    val id: String,
    @SerialName("canonical_email_hash") val canonicalEmailHash: SHA256
)
