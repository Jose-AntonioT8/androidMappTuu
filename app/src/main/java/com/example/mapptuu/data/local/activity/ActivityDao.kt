package com.example.mapptuu.data.local.activity
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(activity: ActivityEntity)

    @Query("DELETE FROM activity WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM activity")
    fun getAll(): List<ActivityEntity>

    @Query("SELECT * FROM activity")
    fun observeAll(): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity WHERE id = :id")
    suspend fun readActivityById(id: String): ActivityEntity?
    @Query("SELECT * FROM activity WHERE name = :name")
    suspend fun readActivityByName(name: String): List<ActivityEntity?>
    @Update
    suspend fun update( activity: ActivityEntity)

}