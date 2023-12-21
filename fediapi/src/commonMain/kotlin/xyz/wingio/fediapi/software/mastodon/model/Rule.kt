package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.Serializable

/**
 * Represents a rule that server users should follow.
 *
 * @param id An identifier for the rule.
 * @param text The rule to be followed.
 */
@Serializable
public data class Rule(
    val id: String,
    val text: String
)