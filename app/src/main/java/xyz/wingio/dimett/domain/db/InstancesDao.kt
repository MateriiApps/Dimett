package xyz.wingio.dimett.domain.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import xyz.wingio.dimett.domain.db.entities.Instance

@Dao
interface InstancesDao {

    @Insert
    fun add(instance: Instance)

    @Update
    fun update(instance: Instance)

    @Delete
    fun delete(instance: Instance)

    @Query("SELECT * FROM Instance WHERE url LIKE :url")
    fun getInstanceFromUrl(url: String): Instance?

    @Query("SELECT * FROM Instance ORDER BY url")
    fun listInstances(): List<Instance>

}