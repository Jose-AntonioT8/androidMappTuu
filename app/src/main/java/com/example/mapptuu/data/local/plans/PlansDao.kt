package com.example.mapptuu.data.local.plans


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.mapptuu.data.local.activity.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlansDao{
    @Insert(onConflict = REPLACE)
    suspend fun insert(plans: PlansEntity)
    @Query("DELETE FROM plans WHERE id = :id")
    suspend fun delete(id: String)
    @Query("SELECT * FROM plans")
    fun getAll(): List<PlansEntity>

    @Query("SELECT * FROM plans")
    fun observeAll(): Flow<List<PlansEntity>>

    @Query("SELECT * FROM plans WHERE id = :id")
    suspend fun readPlanById(id: String): PlansEntity?
    @Query("SELECT * FROM plans WHERE name = :name")
    suspend fun readPlanByName(name: String): List<PlansEntity?>
    @Update
    suspend fun update(plan: PlansEntity)
}