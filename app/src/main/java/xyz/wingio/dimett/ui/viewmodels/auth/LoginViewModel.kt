package xyz.wingio.dimett.ui.viewmodels.auth

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.navigator.Navigator
import io.ktor.http.URLBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.wingio.dimett.domain.db.entities.Instance
import xyz.wingio.dimett.domain.manager.AccountManager
import xyz.wingio.dimett.domain.manager.InstanceManager
import xyz.wingio.dimett.domain.manager.PreferenceManager
import xyz.wingio.dimett.domain.repository.MastodonRepository
import xyz.wingio.dimett.rest.dto.meta.NodeInfo
import xyz.wingio.dimett.rest.utils.fold
import xyz.wingio.dimett.rest.utils.ifSuccessful
import xyz.wingio.dimett.ui.screens.main.MainScreen
import xyz.wingio.dimett.utils.openCustomTab

/**
 * [ViewModel][ScreenModel] used by [xyz.wingio.dimett.ui.screens.auth.LoginScreen]
 */
class LoginViewModel(
    private val mastodonRepository: MastodonRepository,
    private val instanceManager: InstanceManager,
    private val accountManager: AccountManager,
    private val preferenceManager: PreferenceManager
) : ScreenModel {

    // The following are to be used in the UI
    var instance by mutableStateOf("")
    var didError by mutableStateOf(false)
    var nodeInfo by mutableStateOf(null as NodeInfo?)

    var loginLoading by mutableStateOf(false)
    var nodeInfoLoading by mutableStateOf(false)

    // Version of `instance` that requests are actually sent to, not to be shown in the ui
    private var instanceUrl: String? = null

    // Whether or not the desired instance supports the Mastodon api, as that is all Dimett will support for now
    val instanceIsMastodon: Boolean
        get() = nodeInfo?.metadata?.features?.contains("mastodon_api") == true || nodeInfo?.software?.name == "mastodon"

    /**
     * Opens a Chrome custom tab with the OAuth authorization url for the desired instance
     */
    fun login(context: Context) {
        if (instance.isEmpty()) return // In case the login button isn't disabled for whatever reason
        coroutineScope.launch(Dispatchers.IO) {
            verifyInstance()?.let {
                instanceUrl = it.url
                // Construct a url according to https://docs.joinmastodon.org/client/authorized/#login
                val url = URLBuilder("https://${it.url}/oauth/authorize").also { url ->
                    url.parameters.apply {
                        append("client_id", it.clientId)
                        append("redirect_uri", "dimett://oauth") // Dimett should be the only app that supports this scheme
                        append("response_type", "code")
                        append("force_login", "true")
                    }
                }.buildString() + "&scope=read+write+push"
                context.openCustomTab(url, force = true) // We don't want this link opening up in a non-browser app
            }
        }
    }

    /**
     * Loads some basic information about the desired instance using /.well-known/nodeinfo
     */
    fun loadDetails() {
        if (instance.isBlank()) return // Can't load anything from a blank url
        val url = fixUrl(instance)
        nodeInfoLoading = true // Let the UI know that we're loading an instance's information

        coroutineScope.launch(Dispatchers.IO) {
            // Initial request is to /.well-known/nodeinfo, this only gives us the link to the actual node info
            mastodonRepository.getNodeInfoLocation(url).fold(
                success = {
                    it.links.firstOrNull()?.let { link -> // We only need the first link
                        mastodonRepository.getNodeInfo(link.href).fold(
                            success = { node ->
                                nodeInfo = node // Let the user know that we found this instance
                                nodeInfoLoading = false
                            },
                            fail = {
                                nodeInfoLoading = false
                            }
                        )
                    }
                },
                fail = {
                    nodeInfoLoading = false
                }
            )
        }
    }

    /**
     * Creates the OAuth application on this instance, required to properly login
     * ---
     * *All created OAuth apps are saved to a local db*
     */
    private suspend fun verifyInstance(): Instance? {
        val url = fixUrl(instance)
        if (instanceManager.exists(url)) return instanceManager[url] // Use the already existing OAuth app if it exists

        var instance: Instance? = null
        mastodonRepository.createApp(url).fold(
            success = { app ->
                instance = instanceManager.addInstance(
                    url = url,
                    clientId = app.clientId!!,
                    clientSecret = app.clientSecret!!,
                    features = nodeInfo?.metadata?.features ?: emptyList()
                )
                didError = false
            },
            fail = {
                didError = true // Update the user if something goes wrong
            }
        )
        return instance
    }

    /**
     * Handles all incoming intents for the LoginScreen, most importantly it handles the OAuth callback
     */
    fun handleIntent(intent: Intent, navigator: Navigator) {
        val data = intent.data ?: return // If the intent contains no data then it can be discarded
        if (data.scheme != "dimett") return // Only respond to dimett://
        val code = data.getQueryParameter("code") ?: return // This is necessary to generate the authorization token
        val url = instanceUrl ?: return

        coroutineScope.launch {
            val instance = instanceManager[url] ?: return@launch // Make sure we have an OAuth app for this instance

            loginLoading = true // Let the user know we're trying to log in

            mastodonRepository.getToken(
                instanceUrl = instance.url,
                clientId = instance.clientId,
                clientSecret = instance.clientSecret,
                code = code
            ).ifSuccessful { token ->
                mastodonRepository.verifyCredentials(instance.url, token.accessToken) // Fetch the current user in order to see if the OAuth flow was successful
                    .ifSuccessful { user ->
                        accountManager.addAccount(
                            user = user,
                            token = token.accessToken, // Now all future requests can be authenticated for the user!! :)
                            instance = instance.url
                        )

                        preferenceManager.currentAccount = user.id // Switch to the newly added account
                        navigator.replaceAll(MainScreen()) // Replace the login screen with the main screen, don't let users shoot themselves in the foot
                    }
            }

            loginLoading = false
            instanceUrl = null // Don't let duplicate intents happen
        }
    }

    /**
     * Returns a more standard url string
     *  - Removes the http(s) scheme
     *  - Extracts the domain from a user@domain pair
     *  - Trims non-ascii characters
     */
    private fun fixUrl(url: String): String {
        return url
            .replaceFirst("http://", "")
            .replaceFirst("https://", "")
            .let {
                // Only use the destination part of the url (The example.com from user@example.com)
                val at = it.lastIndexOf('@')
                if (at != -1) {
                    it.substring(at + 1)
                } else it
            }
            .trim { it <= ' ' } // Trim off any non-ascii characters
    }

}