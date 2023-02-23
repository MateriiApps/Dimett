package xyz.wingio.dimett.domain.manager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.wingio.dimett.domain.db.AppDatabase
import xyz.wingio.dimett.domain.db.entities.Account
import xyz.wingio.dimett.rest.dto.user.CredentialUser

class AccountManager(
    db: AppDatabase,
    private val preferenceManager: PreferenceManager
) {

    private val managerScope = CoroutineScope(Dispatchers.IO)
    private val dao = db.accountsDao()

    var accounts: MutableList<Account> = mutableListOf()
        private set

    val current: Account?
        get() = get(preferenceManager.currentAccount)

    init {
        managerScope.launch {
            accounts = dao.listAccounts().toMutableList()
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