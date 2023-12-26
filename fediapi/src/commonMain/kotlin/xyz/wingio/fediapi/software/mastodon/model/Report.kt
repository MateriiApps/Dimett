package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.software.mastodon.model.account.Account

/**
 * Reports filed against users and/or statuses, to be taken action on by moderators.
 *
 * @param id The ID of the report in the database.
 * @param actionTaken Whether an action was taken yet.
 * @param actionTakenAt When an action was taken against the report.
 * @param category The generic reason for the report.
 * @param comment The reason for the report.
 * @param forwarded Whether the report was forwarded to a remote domain.
 * @param createdAt When the report was created.
 * @param statusIds IDs of statuses that have been attached to this report for additional context.
 * @param ruleIds IDs of the rules that have been cited as a violation by this report.
 * @param targetAccount The account that was reported.
 */
@Serializable
public data class Report(
    val id: String,
    @SerialName("action_taken") val actionTaken: Boolean,
    @SerialName("action_taken_at") val actionTakenAt: Instant?,
    val category: Category,
    val comment: String,
    val forwarded: Boolean,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("status_ids") val statusIds: List<String>?,
    @SerialName("rule_ids") val ruleIds: List<String>?,
    @SerialName("target_account") val targetAccount: Account
) {

    /**
     * Generic reason for a report.
     */
    public enum class Category {
        /**
         * Unwanted or repetitive content
         */
        @SerialName("spam") SPAM,
        /**
         * A specific rule was violated
         */
        @SerialName("violation") VIOLATION,
        /**
         * Some other reason
         */
        @SerialName("other") OTHER
    }

}