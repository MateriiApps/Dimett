package xyz.wingio.dimett.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.wingio.dimett.domain.manager.AccountManager
import xyz.wingio.dimett.domain.manager.InstanceManager
import xyz.wingio.dimett.domain.manager.PreferenceManager

val managerModule = module {
    singleOf(::PreferenceManager)
    singleOf(::AccountManager)
    singleOf(::InstanceManager)
}