package xyz.wingio.dimett.ui.viewmodels.main

import cafe.adriel.voyager.core.model.ScreenModel
import xyz.wingio.dimett.domain.db.entities.Account
import xyz.wingio.dimett.domain.manager.AccountManager

/**
 * [ViewModel][ScreenModel] used by [xyz.wingio.dimett.ui.screens.main.MainScreen]
 */
class MainViewModel(
    private val accountManager: AccountManager
) : ScreenModel {

    /**
     * The currently logged in account
     */
    val account: Account?
        get() = accountManager.current

}