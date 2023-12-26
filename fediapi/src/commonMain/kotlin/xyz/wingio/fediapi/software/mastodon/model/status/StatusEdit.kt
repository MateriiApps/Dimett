package xyz.wingio.fediapi.software.mastodon.model.status

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML
import xyz.wingio.fediapi.software.mastodon.model.CustomEmoji
import xyz.wingio.fediapi.software.mastodon.model.account.Account
import xyz.wingio.fediapi.software.mastodon.model.media.MediaAttachment

/**
 *  Represents a revision of a status that has been edited.
 *
 *  @param content The content of the status at this revision.
 *  @param spoilerText The content of the subject or content warning at this revision.
 *  @param sensitive Whether the status was marked sensitive at this revision.
 *  @param createdAt The timestamp of when the revision was published.
 *  @param account The account that published this revision.
 *  @param poll The current state of the poll options at this revision. Note that edits changing the poll options will be collapsed together into one edit, since this action resets the poll.
 *  @param mediaAttachments The current state of the media attachments at this revision.
 *  @param emojis Any custom emoji that are used in the current revision.
 */
@Serializable
public data class StatusEdit(
    val content: HTML,
    @SerialName("spoiler_text") val spoilerText: HTML,
    val sensitive: Boolean,
    @SerialName("created_at") val createdAt: Instant,
    val account: Account,
    val poll: Poll? = null,
    @SerialName("media_attachments") val mediaAttachments: List<MediaAttachment>,
    val emojis: List<CustomEmoji>
) {

    /**
     * The current state of the poll options in a revision.
     *
     * @param options The poll options at this revision.
     */
    @Serializable
    public data class Poll(
        val options: List<Option>
    ) {

        /**
         * Option in a poll
         *
         * @param title The text for a poll option.
         */
        @Serializable
        public data class Option(
            val title: String
        )

    }

}