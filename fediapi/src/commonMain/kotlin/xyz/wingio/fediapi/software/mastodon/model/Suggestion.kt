package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.account.Account

/**
 * Represents a suggested account to follow and an associated reason for the suggestion.
 *
 * @param source The reason this account is being suggested.
 * @param account The account being recommended to follow.
 */
@Serializable
public data class Suggestion(
    val source: Source,
    val account: Account
) {

    @Serializable
    public enum class Source {
        /**
         * This account was manually recommended by your administration team.
         */
        @SerialName("staff") STAFF,

        /**
         * You have interacted with this account previously.
         */
        @SerialName("past_interactions") PAST_INTERACTIONS,

        /**
         * This account has many reblogs, favourites, and active local followers within the last 30 days.
         */
        @SerialName("global") GLOBAL,
    }

}