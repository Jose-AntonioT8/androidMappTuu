package com.example.mapptuu.data.local.users

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(users: UsersEntity)
    @Delete
    suspend fun delete(id: String)
    @Query("SELECT * FROM users")
    fun getAll(): List<UsersEntity>

    @Query("SELECT * FROM users")
    fun observeAll(): Flow<List<UsersEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun readUserById(id: String): UsersEntity?

    @Update
    suspend fun update(id: String, user: UsersEntity)
}