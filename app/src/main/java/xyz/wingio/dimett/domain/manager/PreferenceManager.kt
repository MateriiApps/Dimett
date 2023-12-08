package xyz.wingio.dimett.domain.manager

import android.content.Context
import xyz.wingio.dimett.domain.manager.base.BasePreferenceManager

/**
 * Manage general app preferences
 */
class PreferenceManager(context: Context) :
    BasePreferenceManager(context.getSharedPreferences("prefs", Context.MODE_PRIVATE)) {

    /**
     * Id for the currently logged in account
     */
    var currentAccount by stringPreference("current_account")

}