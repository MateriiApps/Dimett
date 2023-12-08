package xyz.wingio.dimett.domain.manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.wingio.dimett.domain.db.AppDatabase
import xyz.wingio.dimett.domain.db.entities.Account
import xyz.wingio.dimett.rest.dto.user.CredentialUser
import xyz.wingio.dimett.utils.mainThread

/**
 * Manage all stored accounts (adding, switching, deleting)
 *
 * @param preferenceManager Used for obtaining the logged in id
 */
class AccountManager(
    db: AppDatabase,
    private val preferenceManager: PreferenceManager
) {

    // All db accesses need to be done on another thread
    private val managerScope = CoroutineScope(Dispatchers.IO)
    private val dao = db.accountsDao()

    /**
     * Whether or not all accounts have been loaded yet
     */
    var isInitialized by mutableStateOf(false)
        private set

    /**
     * Cached version of the accounts table for fast synchronous account fetching
     */
    var accounts: MutableList<Account> = mutableStateListOf()
        private set

    /**
     * Currently logged on account, null when not logged in to any account
     */
    val current: Account?
        get() = get(preferenceManager.currentAccount)

    init {
        managerScope.launch {
            val accs = dao.listAccounts() // Fetch all accounts from the database
            mainThread { // Compose state needs to be altered on the main thread
                accounts += accs // Cache them all in memory
                isInitialized = true // Mark us as ready
            }
        }
    }

    /**
     * Adds an account using a [CredentialUser]
     *
     * @param user User to add as an account
     * @param token The token for the [user]
     * @param instance Instance the [user] is on
     */
    fun addAccount(user: CredentialUser, token: String, instance: String) {
        managerScope.launch {
            val acct = with(user) {
                Account(
                    id = id,
                    token = token,
                    instance = instance,
                    username = username,
                    acct = acct,
                    url = url,
                    displayName = displayName,
                    bio = bio,
                    avatar = avatar,
                    staticAvatar = staticAvatar,
                    banner = banner,
                    staticBanner = staticBanner,
                    locked = private,
                    fields = fields,
                    emojis = emojis,
                    bot = bot,
                    group = group,
                    discoverable = discoverable,
                    noIndex = noIndex,
                    suspended = suspended,
                    limited = limited,
                    createdAt = createdAt.toString(),
                    lastStatusAt = lastStatusAt,
                    statusCount = statusCount,
                    followers = followers,
                    following = following,
                    muteExpiration = muteExpiration
                )
            }
            dao.add(acct)
            accounts.add(acct)
        }
    }

    /**
     * Updates an account
     */
    fun updateAccount(account: Account) {
        managerScope.launch {
            if (account.id.isNotBlank()) {
                dao.update(account)
            }
        }
    }

    /**
     * Switches to another account
     */
    fun switchAccount(id: String) {
        val otherAccount = accounts.find { it.id == id } ?: return

        preferenceManager.currentAccount = otherAccount.id
    }

    /**
     * Retrieves an account by its id or null if one doesn't exist
     */
    operator fun get(id: String): Account? {
        return accounts.find { it.id == id }
    }

}