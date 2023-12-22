package xyz.wingio.fediapi.software.mastodon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.wingio.fediapi.Language

/**
 * Represents the relationship between accounts, such as following / blocking / muting / etc.
 *
 * @param id The account ID.
 * @param following Are you following this user?
 * @param showingReblogs Are you receiving this user’s boosts in your home timeline?
 * @param notifying Have you enabled notifications for this user?
 * @param languages Which languages are you following from this user?
 * @param followedBy Are you followed by this user?
 * @param blocking Are you blocking this user?
 * @param blockedBy Is this user blocking you?
 * @param muting Are you muting this user?
 * @param mutingNotifications Are you muting notifications from this user?
 * @param requested Do you have a pending follow request for this user?
 * @param domainBlocking Are you blocking this user’s domain?
 * @param endorsed Are you featuring this user on your profile?
 * @param note This user’s profile bio
 */
@Serializable
public data class Relationship(
    val id: String,
    val following: Boolean,
    @SerialName("showing_reblogs") val showingReblogs: Boolean,
    val notifying: Boolean,
    val languages: List<Language>,
    @SerialName("followed_by") val followedBy: Boolean,
    val blocking: Boolean,
    @SerialName("blocked_by") val blockedBy: Boolean,
    val muting: Boolean,
    @SerialName("muting_notifications") val mutingNotifications: Boolean,
    val requested: Boolean,
    @SerialName("domain_blocking") val domainBlocking: Boolean,
    val endorsed: Boolean,
    val note: String
)