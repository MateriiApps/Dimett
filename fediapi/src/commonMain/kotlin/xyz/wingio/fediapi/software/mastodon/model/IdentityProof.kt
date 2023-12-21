package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.URL

/**
 * Represents a proof from an external identity provider.
 *
 * @param provider The name of the identity provider.
 * @param providerUsername The account owner’s username on the identity provider’s service.
 * @param updatedAt When the identity proof was last updated.
 * @param proofUrl A link to a statement of identity proof, hosted by the identity provider.
 * @param profileUrl The account owner’s profile URL on the identity provider.
 */
@Deprecated("""
    Identity proofs have been deprecated in Mastodon v3.5.0 and newer. 
    Previously, the only proof provider was Keybase, but development 
    on Keybase has stalled entirely since it was acquired by Zoom.
""")
@Serializable
public data class IdentityProof(
    val provider: String,
    @SerialName("provider_username") val providerUsername: String,
    @SerialName("updated_at") val updatedAt: Instant,
    @SerialName("proof_url") val proofUrl: URL,
    @SerialName("profile_url") val profileUrl: URL
)