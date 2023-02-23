package xyz.wingio.dimett.rest.service

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import xyz.wingio.dimett.domain.manager.AccountManager
import xyz.wingio.dimett.rest.dto.Application
import xyz.wingio.dimett.rest.dto.Token
import xyz.wingio.dimett.rest.dto.meta.NodeInfo
import xyz.wingio.dimett.rest.dto.meta.NodeInfoLocation
import xyz.wingio.dimett.rest.dto.post.Post
import xyz.wingio.dimett.rest.dto.user.CredentialUser
import xyz.wingio.dimett.rest.utils.Routes
import xyz.wingio.dimett.rest.utils.setForm

class MastodonService(
    private val http: HttpService,
    private val accounts: AccountManager
) {

    private fun HttpRequestBuilder.route(route: String) =
        url("https://${accounts.current?.instance}$route")

    private fun HttpRequestBuilder.authorize() =
        header(HttpHeaders.Authorization, "Bearer ${accounts.current?.token}")

    suspend fun getNodeInfoLocation(instanceUrl: String) = http.request<NodeInfoLocation> {
        url("https://$instanceUrl${Routes.WELL_KNOWN.NODEINFO}")
    }

    suspend fun getNodeInfo(url: String) = http.request<NodeInfo> {
        url(url)
    }

    suspend fun createApp(instanceUrl: String) = http.request<Application> {
        url("https://$instanceUrl${Routes.V1.APPS}")

        setForm {
            append("client_name", "Dimett")
            append("redirect_uris", "dimett://oauth")
            append("website", "http://dimett.wingio.xyz")
            append(
                "scopes",
                "read read:accounts read:blocks read:bookmarks read:favourites read:filters read:follows read:lists read:mutes read:notifications read:search read:statuses write write:accounts write:blocks write:bookmarks write:conversations write:favourites write:filters write:follows write:lists write:media write:mutes write:notifications write:reports write:statuses follow push admin:read admin:read:accounts admin:read:reports admin:write admin:write:accounts admin:write:reports crypto"
            )
        }
        method = HttpMethod.Post
    }

    suspend fun getToken(
        instanceUrl: String,
        code: String,
        clientId: String,
        clientSecret: String
    ) = http.request<Token> {
        url("https://$instanceUrl${Routes.OAUTH.TOKEN}")
        method = HttpMethod.Post
        setForm {
            append("grant_type", "authorization_code")
            append("code", code)
            append("client_id", clientId)
            append("client_secret", clientSecret)
            append("redirect_uri", "dimett://oauth")
            append("scope", "read write push")
        }
    }

    suspend fun verifyCredentials(
        instanceUrl: String = accounts.current?.instance ?: "",
        token: String? = accounts.current?.token
    ) =
        http.request<CredentialUser> {
            url("https://$instanceUrl${Routes.V1.Accounts.VERIFY_CREDENTIALS}")
            header("authorization", "Bearer $token")
        }

    suspend fun getFeed(
        max: String? = null,
        since: String? = null,
        min: String? = null,
        limit: Int = 20
    ) = http.request<List<Post>> {
        route(Routes.V1.Timelines.FEED)
        authorize()

        max?.let { parameter("max_id", it) }
        since?.let { parameter("since_id", it) }
        min?.let { parameter("min_id", it) }
        parameter("limit", limit)
    }

    suspend fun favoritePost(id: String) = http.request<Post> {
        route(Routes.V1.Posts.FAVORITE(id))
        authorize()
        method = HttpMethod.Post
    }

    suspend fun unfavoritePost(id: String) = http.request<Post> {
        route(Routes.V1.Posts.UNFAVORITE(id))
        authorize()
        method = HttpMethod.Post
    }

    suspend fun boostPost(id: String) = http.request<Post> {
        route(Routes.V1.Posts.BOOST(id))
        authorize()
        method = HttpMethod.Post
    }

    suspend fun unboostPost(id: String) = http.request<Post> {
        route(Routes.V1.Posts.UNBOOST(id))
        authorize()
        method = HttpMethod.Post
    }

}