package xyz.wingio.fediapi.software.mastodon.model.instance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Limits related to polls.
 *
 * @param maxOptions Each poll is allowed to have up to this many options.
 * @param maxCharactersPerOption Each poll option is allowed to have this many characters.
 * @param minExpiration The shortest allowed poll duration, in seconds.
 * @param maxExpiration The longest allowed poll duration, in seconds.
 */
@Serializable
public data class InstancePollConfiguration(
    @SerialName("max_options") val maxOptions: Int,
    @SerialName("max_characters_per_option") val maxCharactersPerOption: Int,
    @SerialName("min_expiration") val minExpiration: Int,
    @SerialName("max_expiration") val maxExpiration: Int
)