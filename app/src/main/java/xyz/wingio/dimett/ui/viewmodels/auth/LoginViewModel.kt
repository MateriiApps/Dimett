package xyz.wingio.dimett.ui.viewmodels.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import io.ktor.http.URLBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.wingio.dimett.domain.db.entities.Instance
import xyz.wingio.dimett.domain.manager.AccountManager
import xyz.wingio.dimett.domain.manager.InstanceManager
import xyz.wingio.dimett.domain.repository.MastodonRepository
import xyz.wingio.dimett.rest.dto.meta.NodeInfo
import xyz.wingio.dimett.rest.utils.fold
import xyz.wingio.dimett.utils.openCustomTab

var instanceUrl: String? = null

class LoginViewModel(
    private val repo: MastodonRepository,
    private val instanceManager: InstanceManager,
    private val am: AccountManager
) : ScreenModel {

    var instance by mutableStateOf("")
    var didError by mutableStateOf(false)
    var nodeInfo by mutableStateOf(null as NodeInfo?)

    var nodeInfoLoading by mutableStateOf(false)

    fun login(context: Context) {
        if (instance.isEmpty()) return
        coroutineScope.launch(Dispatchers.IO) {
            verifyInstance()?.let {
                instanceUrl = it.url
                val url = URLBuilder("https://${it.url}/oauth/authorize").also { url ->
                    url.parameters.apply {
                        append("client_id", it.clientId)
                        append("redirect_uri", "dimett://oauth")
                        append("response_type", "code")
                        append("force_login", "true")
                    }
                }.buildString() + "&scope=read+write+push"
                context.openCustomTab(url, force = true)
            }
        }
    }

    fun loadDetails() {
        if (instance.isBlank()) return
        val url = fixUrl(instance)
        nodeInfoLoading = true

        coroutineScope.launch(Dispatchers.IO) {
            repo.getNodeInfoLocation(url).fold(
                success = {
                    it.links.firstOrNull()?.let { link ->
                        repo.getNodeInfo(link.href).fold(
                            success = { node ->
                                nodeInfo = node
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

    private suspend fun verifyInstance(): Instance? {
        val url = fixUrl(instance)
        if (instanceManager.exists(url)) return instanceManager[url]
        var instance: Instance? = null
        repo.createApp(url).fold(
            success = { app ->
                instance = instanceManager.addInstance(
                    url,
                    app.clientId!!,
                    app.clientSecret!!,
                    nodeInfo?.metadata?.features ?: emptyList()
                )
                didError = false
            },
            fail = {
                didError = true
            }
        )
        return instance
    }

    private fun fixUrl(url: String): String {
        var s = url.replaceFirst("http://", "")
        s = s.replaceFirst("https://", "")
        val at = s.lastIndexOf('@')
        if (at != -1) {
            s = s.substring(at + 1)
        }
        return s.trim { it <= ' ' }
    }

}