package xyz.wingio.dimett.rest.dto.meta

import kotlinx.serialization.Serializable

@Serializable
data class NodeInfo(
    val metadata: MetaData? = null,
    val openRegistrations: Boolean,
    val protocols: List<String>,
    val software: Software,
    val usage: Usage? = null
) {

    @Serializable
    data class MetaData(
        val features: List<String>?,
        val nodeDescription: String?,
        val nodeName: String?,
        val postFormats: List<String>?,
        val private: Boolean?
    )

    @Serializable
    data class Software(
        val name: String,
        val version: String
    )

    @Serializable
    data class Usage(
        val users: Users? = null,
        val localPosts: Long? = null
    ) {

        @Serializable
        data class Users(
            val total: Int? = null,
            val activeMonth: Int? = null,
            val activeHalfyear: Int? = null
        )

    }

}

@Serializable
data class NodeInfoLocation(
    val links: List<Link>
) {

    @Serializable
    data class Link(
        val href: String,
        val rel: String
    )

}
