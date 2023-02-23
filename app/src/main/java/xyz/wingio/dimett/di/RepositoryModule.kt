package xyz.wingio.dimett.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.wingio.dimett.domain.repository.MastodonRepository

val repositoryModule = module {
    singleOf(::MastodonRepository)
}