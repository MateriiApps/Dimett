package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.HTML
import xyz.wingio.fediapi.Language

/**
 * Represents the result of machine translating some status content.
 *
 * @param content HTML-encoded translated content of the status.
 * @param spoilerWarning The translated spoiler warning of the status.
 * @param poll The translated poll options of the status.
 * @param mediaAttachments The translated media descriptions of the status.
 * @param detectedSourceLanguage The language of the source text, as auto-detected by the machine translation provider.
 * @param provider The service that provided the machine translation.
 */
@Serializable
public data class Translation(
    val content: HTML,
    @SerialName("spoiler_warning") val spoilerWarning: String,
    val poll: List<Poll>,
    @SerialName("media_attachments") val mediaAttachments: List<MediaAttachment>,
    @SerialName("detected_source_language") val detectedSourceLanguage: Language,
    val provider: String
) {

    /**
     * Translated version of a poll
     *
     * @param id The ID of the poll in the database.
     * @param options List of translated options
     */
    @Serializable
    public data class Poll(
        val id: String,
        val options: List<Option>
    ) {

        /**
         * Option for a [Poll]
         *
         * @param title The text used for this option
         */
        @Serializable
        public data class Option(
            val title: String
        )

    }

    /**
     * An attachment with translated alt text
     *
     * @param id The ID of the attachment in the database.
     * @param description Translated alt text of the attachment
     */
    @Serializable
    public data class MediaAttachment(
        val id: String,
        val description: String
    )

}