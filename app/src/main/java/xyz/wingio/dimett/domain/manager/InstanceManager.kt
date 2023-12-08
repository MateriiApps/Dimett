package xyz.wingio.dimett.domain.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.wingio.dimett.domain.db.AppDatabase
import xyz.wingio.dimett.domain.db.entities.Instance
import xyz.wingio.dimett.utils.mainThread

/**
 * Managed saved instances and associated OAuth apps
 */
class InstanceManager(
    database: AppDatabase,
    val accountManager: AccountManager
) {

    // All db accesses need to be done on another thread
    private val managerScope = CoroutineScope(Dispatchers.IO)
    private val dao = database.instancesDao()

    /**
     * Cached version of the instances table for fast synchronous instance fetching
     */
    var instances: MutableList<Instance> = mutableListOf()
        private set

    /**
     * Gets the instance for the currently logged on account
     */
    val current: Instance?
        get() {
            val account = accountManager.current ?: return null

            return instances.find { it.url == account.url }
        }

    init {
        managerScope.launch {
            val _instances = dao.listInstances() // Fetch all accounts from the database
            mainThread { // Compose state needs to be altered on the main thread
                instances += _instances // Cache them all in memory
            }
        }
    }

    /**
     * Stores an [Instance]
     *
     * @param url Base url for the instance
     * @param clientId Client id for the associated OAuth app
     * @param clientSecret Client secret for the associated OAuth app
     * @param features Supported features that this instance has (Ex. reactions)
     */
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

    /**
     * Checks if the instance with the given [url] already has an OAuth app we could use
     */
    fun exists(url: String): Boolean {
        return instances.find { it.url == url } != null
    }

    /**
     * Obtains the instance with the given [url]
     */
    operator fun get(url: String) = instances.find { it.url == url }

}