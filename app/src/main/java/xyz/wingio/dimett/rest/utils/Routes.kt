package xyz.wingio.dimett.rest.utils

@Suppress("FunctionName", "ClassName")
object Routes {

    object WELL_KNOWN {
        const val NODEINFO = "/.well-known/nodeinfo"
    }

    object OAUTH {
        const val TOKEN = "/oauth/token"
    }

    object V1 {
        private const val BASE = "/api/v1"
        const val APPS = "$BASE/apps"

        object Accounts {
            const val VERIFY_CREDENTIALS = "$BASE/accounts/verify_credentials"
        }

        object Timelines {
            const val FEED = "$BASE/timelines/home"
        }

        object Posts {
            fun FAVORITE(id: String) = "$BASE/statuses/$id/favourite"
            fun UNFAVORITE(id: String) = "$BASE/statuses/$id/unfavourite"

            fun BOOST(id: String) = "$BASE/statuses/$id/reblog"
            fun UNBOOST(id: String) = "$BASE/statuses/$id/unreblog"
        }
    }
}