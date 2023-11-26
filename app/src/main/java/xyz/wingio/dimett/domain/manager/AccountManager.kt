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

class AccountManager(
    db: AppDatabase,
    private val preferenceManager: PreferenceManager
) {

    private val managerScope = CoroutineScope(Dispatchers.IO)
    private val dao = db.accountsDao()

    var isInitialized by mutableStateOf(false)
        private set

    var accounts: MutableList<Account> = mutableStateListOf()
        private set

    val current: Account?
        get() = get(preferenceManager.currentAccount)

    init {
        managerScope.launch {
            val accs = dao.listAccounts()
            mainThread {
                accounts += accs
                isInitialized = true
            }
        }
    }

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

    fun updateAccount(account: Account) {
        managerScope.launch {
            if (account.id.isNotBlank()) {
                dao.update(account)
            }
        }
    }

    fun switchAccount(id: String) {
        val otherAccount = accounts.find { it.id == id } ?: return

        preferenceManager.currentAccount = otherAccount.id
    }

    operator fun get(id: String): Account? {
        return accounts.find { it.id == id }
    }

}