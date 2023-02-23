package xyz.wingio.dimett.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import xyz.wingio.dimett.ui.viewmodels.auth.LoginViewModel
import xyz.wingio.dimett.ui.viewmodels.feed.FeedViewModel
import xyz.wingio.dimett.ui.viewmodels.main.MainViewModel

val viewModelModule = module {
    factoryOf(::LoginViewModel)
    factoryOf(::MainViewModel)
    factoryOf(::FeedViewModel)
}