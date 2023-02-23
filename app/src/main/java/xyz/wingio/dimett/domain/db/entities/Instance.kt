package xyz.wingio.dimett.domain.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Instance(
    @PrimaryKey val url: String,
    @ColumnInfo(name = "client_id") val clientId: String,
    @ColumnInfo(name = "client_secret") val clientSecret: String,
    val features: List<String>
) {

    val supportsReactions: Boolean
        get() = features.contains("pleroma_emoji_reactions")

}