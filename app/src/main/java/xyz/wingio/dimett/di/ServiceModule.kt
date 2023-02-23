package xyz.wingio.dimett.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.wingio.dimett.rest.service.HttpService
import xyz.wingio.dimett.rest.service.MastodonService

val serviceModule = module {
    singleOf(::HttpService)
    singleOf(::MastodonService)
}