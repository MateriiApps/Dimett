package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an OAuth token used for authenticating with the API and performing actions.
 *
 * @param accessToken An OAuth token to be used for authorization.
 * @param tokenType The OAuth token type. Mastodon uses `Bearer` tokens.
 * @param scope The OAuth scopes granted by this token, space-separated.
 * @param createdAt (UNIX timestamp) When the token was generated.
 */
@Serializable
public data class Token(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    val scope: String,
    @SerialName("created_at") val createdAt: Long
)