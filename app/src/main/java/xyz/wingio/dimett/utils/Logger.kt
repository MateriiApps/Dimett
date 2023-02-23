package xyz.wingio.dimett.utils

import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.wingio.dimett.BuildConfig

class Logger(
    private val tag: String
) {

    fun verbose(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.VERBOSE)
    fun debug(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.DEBUG)
    fun info(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.INFO)
    fun warn(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.WARN)
    fun error(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.ERROR)

    inline fun <reified T> logSerializable(obj: T) = log(
        msg = Json.Default.encodeToString(obj)
    )

    fun log(msg: String?, throwable: Throwable? = null, level: Level = Level.INFO) {
        if (!BuildConfig.DEBUG) return
        when (level) {
            Level.VERBOSE -> Log.v(tag, msg, throwable)
            Level.DEBUG -> Log.d(tag, msg, throwable)
            Level.INFO -> Log.i(tag, msg, throwable)
            Level.WARN -> Log.w(tag, msg, throwable)
            Level.ERROR -> Log.e(tag, msg, throwable)
        }
    }

    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

}