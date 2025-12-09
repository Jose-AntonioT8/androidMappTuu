package com.example.mapptuu.data.local.activityTypes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityTypesDao{
    @Insert(onConflict = REPLACE)
    suspend fun insert(activityType: ActivityTypesEntity)
    @Delete
    suspend fun delete(id: String)
    @Query("SELECT * FROM activityType")
    fun getAll(): List<ActivityTypesEntity>

    @Query("SELECT * FROM activityType")
    fun observeAll(): Flow<List<ActivityTypesEntity>>

    @Query("SELECT * FROM activityType WHERE id = :id")
    suspend fun readActivityTypesById(id: String): ActivityTypesEntity?
}