package xyz.wingio.dimett.di

import android.content.Context
import androidx.room.Room
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.wingio.dimett.domain.db.AppDatabase
import xyz.wingio.dimett.domain.db.Converters

val databaseModule = module {

    fun provideAccountsDb(context: Context, json: Json) = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "dimett"
    ).addTypeConverter(Converters(json)).build()

    singleOf(::provideAccountsDb)

}