package xyz.wingio.dimett.domain.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import xyz.wingio.dimett.domain.db.entities.Account
import xyz.wingio.dimett.domain.db.entities.Instance

/**
 * Manages complex persistent data (Such as accounts and instances)
 */
@Database(
    entities = [Account::class, Instance::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountsDao(): AccountsDao

    abstract fun instancesDao(): InstancesDao

}