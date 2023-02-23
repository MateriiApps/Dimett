package xyz.wingio.dimett.domain.repository

import xyz.wingio.dimett.rest.service.MastodonService

class MastodonRepository(
    private val service: MastodonService
) {

    suspend fun getNodeInfoLocation(instanceUrl: String) = service.getNodeInfoLocation(instanceUrl)

    suspend fun getNodeInfo(url: String) = service.getNodeInfo(url)

    suspend fun createApp(instanceUrl: String) = service.createApp(instanceUrl)

    suspend fun getToken(
        instanceUrl: String,
        clientId: String,
        clientSecret: String,
        code: String
    ) = service.getToken(instanceUrl, code, clientId, clientSecret)

    suspend fun verifyCredentials(instanceUrl: String, token: String) =
        service.verifyCredentials(instanceUrl, token)

    suspend fun getFeed(
        max: String? = null,
        since: String? = null,
        min: String? = null,
        limit: Int = 20
    ) = service.getFeed(max, since, min, limit)

    suspend fun favoritePost(id: String) = service.favoritePost(id)

    suspend fun unfavoritePost(id: String) = service.unfavoritePost(id)

    suspend fun boostPost(id: String) = service.boostPost(id)

    suspend fun unboostPost(id: String) = service.unboostPost(id)

}