package xyz.wingio.dimett.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Cached result of [defaultBrowserPackage]
 */
private var mDefaultBrowserPackage: String? = null

/**
 * Gets the package name for the default browser app on the users device
 */
private val Context.defaultBrowserPackage: String?
    @SuppressLint("QueryPermissionsNeeded")
    get() {
        return if (mDefaultBrowserPackage == null) {
            mDefaultBrowserPackage = packageManager
                .queryIntentActivities(
                    Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://")),
                    0
                )
                .firstOrNull()
                ?.activityInfo?.packageName

            mDefaultBrowserPackage
        } else mDefaultBrowserPackage
    }

/**
 * Open a Chrome custom tab
 *
 * @param url Url of the desired webpage
 * @param force Whether or not to force a custom tab, avoids certain links being opened in a non browser app
 */
fun Context.openCustomTab(url: String, force: Boolean) = CustomTabsIntent.Builder().build().run {
    if (force) intent.setPackage(defaultBrowserPackage)
    launchUrl(this@openCustomTab, Uri.parse(url))
}