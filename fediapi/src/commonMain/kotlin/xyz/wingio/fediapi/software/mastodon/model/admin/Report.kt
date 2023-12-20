package xyz.wingio.fediapi.software.mastodon.model.admin

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Admin-level information about a filed report.
 *
 * @param id The ID of the report in the database.
 * @param actionTaken Whether an action was taken to resolve this report.
 * @param actionTakenAt When an action was taken, if this report is currently resolved.
 * @param category The category under which the report is classified.
 * @param comment An optional reason for reporting.
 * @param forwarded  Whether a report was forwarded to a remote instance.
 * @param createdAt The time the report was filed.
 * @param updatedAt The time of last action on this report.
 * @param account The account which filed the report.
 * @param targetAccount The account being reported.
 * @param assignedAccount The account of the moderator assigned to this report.
 * @param actionTakenByAccount The account of the moderator who handled the report.
 * @param statuses Statuses attached to the report, for context.
 * @param rules Rules attached to the report, for context.
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
    @SerialName("updated_at") val updatedAt: Instant,
    val account: AdminAccount,
    @SerialName("target_account") val targetAccount: AdminAccount,
    @SerialName("assigned_account") val assignedAccount: AdminAccount?,
    @SerialName("action_taken_by_account") val actionTakenByAccount: AdminAccount?,
    val statuses: List<String>, // TODO: Model
    val rules: List<String> // TODO: Model
) {

    @Serializable
    public enum class Category {
        /**
         * Malicious, fake, or repetitive content
         */
        @SerialName("spam") SPAM,

        /**
         * Violates one or more specific [rules]
         */
        @SerialName("violation") VIOLATION,

        /**
         * The default (catch-all) category
         */
        @SerialName("other") OTHER
    }

}
