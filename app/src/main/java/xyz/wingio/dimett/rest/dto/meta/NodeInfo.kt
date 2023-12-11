package xyz.wingio.dimett.rest.dto.meta

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

// http://nodeinfo.diaspora.software/schema.html
@Stable
@Serializable
data class NodeInfo(
    val metadata: MetaData? = null,
    val openRegistrations: Boolean,
    val protocols: List<String>,
    val software: Software,
    val usage: Usage? = null
) {

    @Stable
    @Serializable
    data class MetaData(
        val features: List<String>?,
        val nodeDescription: String?,
        val nodeName: String?,
        val postFormats: List<String>?,
        val private: Boolean?
    )

    @Stable
    @Serializable
    data class Software(
        val name: String,
        val version: String
    )

    @Stable
    @Serializable
    data class Usage(
        val users: Users? = null,
        val localPosts: Long? = null
    ) {

        @Stable
        @Serializable
        data class Users(
            val total: Int? = null,
            val activeMonth: Int? = null,
            val activeHalfyear: Int? = null
        )

    }

}

// Response from /.well-known/nodeinfo
@Stable
@Serializable
data class NodeInfoLocation(
    val links: List<Link>
) {

    @Stable
    @Serializable
    data class Link(
        val href: String,
        val rel: String
    )

}
