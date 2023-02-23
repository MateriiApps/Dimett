package xyz.wingio.dimett.domain.manager

import android.content.Context
import xyz.wingio.dimett.domain.manager.base.BasePreferenceManager

class PreferenceManager(context: Context) :
    BasePreferenceManager(context.getSharedPreferences("prefs", Context.MODE_PRIVATE)) {

    var currentAccount by stringPreference("current_account")

}