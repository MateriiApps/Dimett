package xyz.wingio.dimett.utils

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.wingio.dimett.BuildConfig

/**
 * More readable logger implementation
 *
 * @param tag Tag to be shown in logcat
 */
class Logger(
    private val tag: String
) {

    /**
     * The lowest log level, usually used for very frequent logs
     *
     * @param msg The message to be printed
     * @param throwable Optional error to be printed alongside the message
     */
    fun verbose(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.VERBOSE)

    /**
     * Second lowest log level, used for intentional debug messages
     *
     * @param msg The message to be printed
     * @param throwable Optional error to be printed alongside the message
     */
    fun debug(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.DEBUG)

    /**
     * Middle log level, used for printing basic information
     *
     * @param msg The message to be printed
     * @param throwable Optional error to be printed alongside the message
     */
    fun info(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.INFO)

    /**
     * Second highest log level, used when something could potentially be going wrong
     *
     * @param msg The message to be printed
     * @param throwable Optional error to be printed alongside the message
     */
    fun warn(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.WARN)

    /**
     * Highest log level, used when something has gone wrong or an exception was thrown
     *
     * @param msg The message to be printed
     * @param throwable Optional error to be printed alongside the message
     */
    fun error(msg: String?, throwable: Throwable? = null) = log(msg, throwable, Level.ERROR)

    /**
     * Prints a JSON representation of an object
     *
     * @param obj The object to print, must be annotated with [Serializable]
     */
    inline fun <reified T> logSerializable(obj: @Serializable T) = log(
        msg = Json.Default.encodeToString(obj)
    )

    /**
     * Logs a message at the specified [level] if the app's build is debuggable
     *
     * @param msg The message to be printed
     * @param throwable Optional error to be printed alongside the message
     * @param level Level to log this message at, see [Level]
     */
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

    /**
     * Levels used when logging messages
     */
    enum class Level {
        VERBOSE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

}