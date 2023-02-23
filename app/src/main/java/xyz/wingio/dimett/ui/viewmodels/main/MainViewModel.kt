package xyz.wingio.dimett.ui.viewmodels.main

import cafe.adriel.voyager.core.model.ScreenModel
import xyz.wingio.dimett.domain.db.entities.Account
import xyz.wingio.dimett.domain.manager.AccountManager

class MainViewModel(
    private val accounts: AccountManager
) : ScreenModel {

    val account: Account?
        get() = accounts.current

}