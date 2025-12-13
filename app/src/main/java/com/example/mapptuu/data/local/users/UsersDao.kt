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
    @Query("DELETE FROM activity WHERE id = :id")
    suspend fun delete(id: Long)
    @Query("SELECT * FROM users")
    fun getAll(): List<UsersEntity>

    @Query("SELECT * FROM users")
    fun observeAll(): Flow<List<UsersEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun readUserById(id: Long): UsersEntity?

    @Update
    suspend fun update(user: UsersEntity)
}