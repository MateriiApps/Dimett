package xyz.wingio.dimett.domain.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import xyz.wingio.dimett.rest.dto.CustomEmoji
import xyz.wingio.dimett.rest.dto.user.Field

@Entity(
    foreignKeys = [ForeignKey(
        entity = Instance::class,
        parentColumns = ["url"],
        childColumns = ["instance"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Account(
    @PrimaryKey var id: String,
    var token: String,
    var instance: String,
    var username: String,
    var acct: String,
    var url: String,
    var displayName: String,
    var bio: String,
    var avatar: String,
    var staticAvatar: String,
    var banner: String,
    var staticBanner: String,
    var locked: Boolean, // Renamed from private to fix a compilation error
    var fields: List<Field> = emptyList(),
    var emojis: List<CustomEmoji> = emptyList(),
    var bot: Boolean,
    var group: Boolean? = null,
    var discoverable: Boolean? = null,
    var noIndex: Boolean? = null,
    var suspended: Boolean? = null,
    var limited: Boolean? = null,
    var createdAt: String,
    var lastStatusAt: String? = null,
    var statusCount: Int,
    var followers: Int,
    var following: Int,
    var muteExpiration: String? = null
)