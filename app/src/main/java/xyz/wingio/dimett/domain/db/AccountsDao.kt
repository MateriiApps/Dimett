package xyz.wingio.dimett.domain.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import xyz.wingio.dimett.domain.db.entities.Account

/**
 * Used to manage saved [Account]s
 */
@Dao
interface AccountsDao {

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun add(account: Account)

    @Update
    fun update(account: Account)

    @Delete
    fun delete(account: Account)

    @Query("SELECT * FROM Account WHERE id LIKE :id")
    fun getAccountById(id: String): Account

    @Query("SELECT * FROM Account ORDER BY id ASC")
    fun listAccounts(): List<Account>

}