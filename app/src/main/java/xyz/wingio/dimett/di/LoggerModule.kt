package xyz.wingio.dimett.di

import android.content.Context
import org.koin.core.qualifier.named
import org.koin.dsl.module
import xyz.wingio.dimett.R
import xyz.wingio.dimett.utils.Logger

val loggerModule = module {

    single(named("default")) {
        Logger(get<Context>().getString(R.string.app_name))
    }

    single(named("http")) {
        Logger("HTTP")
    }

}