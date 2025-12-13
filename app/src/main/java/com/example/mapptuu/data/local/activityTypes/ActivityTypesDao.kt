package com.example.mapptuu.data.local.activityTypes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityTypesDao{
    @Insert(onConflict = REPLACE)
    suspend fun insert(activityType: ActivityTypesEntity)
    @Query("DELETE FROM activity WHERE id = :id")
    suspend fun delete(id: Long)
    @Query("SELECT * FROM activityType")
    fun getAll(): List<ActivityTypesEntity>

    @Query("SELECT * FROM activityType")
    fun observeAll(): Flow<List<ActivityTypesEntity>>

    @Query("SELECT * FROM activityType WHERE id = :id")
    suspend fun readActivityTypesById(id: Long): ActivityTypesEntity?

    @Update
    suspend fun update( activityType: ActivityTypesEntity)
}