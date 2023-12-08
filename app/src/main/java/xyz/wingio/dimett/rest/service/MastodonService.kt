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

/**
 * Service for interacting with the [Mastodon API](https://docs.joinmastodon.org/methods)
 *
 * @param http Instance of [HttpService] used to make requests
 * @param accounts Used to obtain the correct credentials
 */
class MastodonService(
    private val http: HttpService,
    private val accounts: AccountManager
) {

    /**
     * Applies the instances base url to the desired [route]
     */
    private fun HttpRequestBuilder.route(route: String) =
        url("https://${accounts.current?.instance}$route")

    /**
     * Adds the correct authorization header to the request
     */
    private fun HttpRequestBuilder.authorize() =
        header(HttpHeaders.Authorization, "Bearer ${accounts.current?.token}")

    /**
     * Fetches the location of the instances Nodeinfo
     *
     * @see Routes.WELL_KNOWN.NODEINFO
     */
    suspend fun getNodeInfoLocation(instanceUrl: String) = http.request<NodeInfoLocation> {
        url("https://$instanceUrl${Routes.WELL_KNOWN.NODEINFO}")
    }

    /**
     * Fetches the Nodeinfo object from the route obtained from [getNodeInfoLocation]
     *
     * @see NodeInfo
     */
    suspend fun getNodeInfo(url: String) = http.request<NodeInfo> {
        url(url)
    }

    /**
     * Creates an [Application] used to authenticate on behalf of the user
     *
     * [Ref](https://docs.joinmastodon.org/methods/apps/)
     *
     * @see Application
     */
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

    /**
     * Obtains an api token to be used for future api calls, part of the OAuth flow
     *
     * [Ref](https://docs.joinmastodon.org/methods/oauth/#token)
     *
     * @see Token
     */
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

    /**
     * Obtains the user associated with the [token]
     *
     * [Ref](https://docs.joinmastodon.org/methods/accounts/#verify_credentials)
     *
     * @see CredentialUser
     */
    suspend fun verifyCredentials(
        instanceUrl: String = accounts.current?.instance ?: "",
        token: String? = accounts.current?.token
    ) =
        http.request<CredentialUser> {
            url("https://$instanceUrl${Routes.V1.Accounts.VERIFY_CREDENTIALS}")
            header("authorization", "Bearer $token")
        }

    /**
     * Gets the home feed for the logged in user
     *
     * [Ref](https://docs.joinmastodon.org/methods/timelines/#home)
     *
     * @see Post
     */
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

    /**
     * Favorites (likes) a post with the given [id]
     *
     * [Ref](https://docs.joinmastodon.org/methods/statuses/#favourite)
     *
     * @see Post
     */
    suspend fun favoritePost(id: String) = http.request<Post> {
        route(Routes.V1.Posts.FAVORITE(id))
        authorize()
        method = HttpMethod.Post
    }

    /**
     * Unfavorites (unlikes) a post with the given [id]
     *
     * [Ref](https://docs.joinmastodon.org/methods/statuses/#unfavourite)
     *
     * @see Post
     */
    suspend fun unfavoritePost(id: String) = http.request<Post> {
        route(Routes.V1.Posts.UNFAVORITE(id))
        authorize()
        method = HttpMethod.Post
    }

    /**
     * Boosts (reposts/retweets) a post with the given [id]
     *
     * [Ref](https://docs.joinmastodon.org/methods/statuses/#boost)
     *
     * @see Post
     */
    suspend fun boostPost(id: String) = http.request<Post> {
        route(Routes.V1.Posts.BOOST(id))
        authorize()
        method = HttpMethod.Post
    }

    /**
     * Unboosts (unreposts/unretweets) a post with the given [id]
     *
     * [Ref](https://docs.joinmastodon.org/methods/statuses/#boost)
     *
     * @see Post
     */
    suspend fun unboostPost(id: String) = http.request<Post> {
        route(Routes.V1.Posts.UNBOOST(id))
        authorize()
        method = HttpMethod.Post
    }

}