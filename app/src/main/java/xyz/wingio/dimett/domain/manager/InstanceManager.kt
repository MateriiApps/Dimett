package xyz.wingio.dimett.domain.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.wingio.dimett.domain.db.AppDatabase
import xyz.wingio.dimett.domain.db.entities.Instance

class InstanceManager(database: AppDatabase, val accountManager: AccountManager) {
    private val dao = database.instancesDao()
    private val managerScope = CoroutineScope(Dispatchers.IO)

    var instances: MutableList<Instance> = mutableListOf()
        private set

    val current: Instance?
        get() {
            val account = accountManager.current ?: return null

            return instances.find { it.url == account.url }
        }

    init {
        managerScope.launch {
            instances = dao.listInstances().toMutableList()
        }
    }

    fun addInstance(
        url: String,
        clientId: String,
        clientSecret: String,
        features: List<String> = emptyList()
    ): Instance {
        val i = Instance(url, clientId, clientSecret, features)
        managerScope.launch {
            dao.add(i)
        }
        instances.add(i)
        return i
    }

    fun exists(url: String): Boolean {
        return instances.find { it.url == url } != null
    }

    operator fun get(url: String) = instances.find { it.url == url }

}