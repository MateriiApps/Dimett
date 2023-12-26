package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.Language
import xyz.wingio.fediapi.software.mastodon.model.media.MediaAttachment

/**
 *  Represents a status that will be published at a future scheduled date.
 *
 *  @param id ID of the scheduled status in the database.
 *  @param scheduledAt The timestamp for when the status will be posted.
 *  @param params The parameters that were used when scheduling the status, to be used when the status is posted.
 *  @param mediaAttachments Media that will be attached when the status is posted.
 */
@Serializable
public data class ScheduledStatus(
    val id: String,
    @SerialName("scheduled_at") val scheduledAt: Instant,
    val params: Params,
    @SerialName("media_attachments") val mediaAttachments: List<MediaAttachment>
) {

    /**
     *  Parameters used when scheduling a status
     *
     *  @param poll Poll to be attached to the status.
     *  @param mediaIds IDs of the [MediaAttachment]s that will be attached to the status.
     *  @param sensitive Whether the status will be marked as sensitive.
     *  @param spoilerText The text of the content warning or summary for the status.
     *  @param visibility The visibility that the status will have once it is posted.
     *  @param inReplyToId ID of the Status that will be replied to.
     *  @param language The language that will be used for the status.
     *  @param applicationId ID of the Application that posted the status.
     *  @param scheduledAt When the status will be scheduled. This will be null because the status is only scheduled once.
     *  @param idempotency Idempotency key to prevent duplicate statuses.
     *  @param withRateLimit Whether the status should be rate limited .
     */
    @Serializable
    public data class Params(
        val poll: Poll?,
        @SerialName("media_ids") val mediaIds: List<String>?,
        val sensitive: Boolean?,
        @SerialName("spoiler_text") val spoilerText: String?,
        val visibility: Visibility,
        @SerialName("in_reply_to_id") val inReplyToId: String?,
        val language: Language?,
        @SerialName("application_id") val applicationId: String,
        @SerialName("scheduled_at") val scheduledAt: Instant? = null,
        val idempotency: String?,
        @SerialName("with_rate_limit") val withRateLimit: Boolean
    )

    /**
     * Poll to be attached to a status
     *
     * @param options The poll options to be used.
     * @param expiresIn How many seconds the poll should last before closing.
     * @param multiple Whether the poll allows multiple choices.
     * @param hideTotals Whether the poll should hide total votes until after voting has ended.
     */
    @Serializable
    public data class Poll(
        val options: List<String>,
        @SerialName("expires_in") val expiresIn: Int,
        val multiple: Boolean? = null,
        @SerialName("hide_totals") val hideTotals: Boolean? = null,
    )

}