package com.example.mapptuu.data.local.users

import androidx.room.Dao
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
    suspend fun delete(id: String)
    @Query("SELECT * FROM users")
    fun getAll(): List<UsersEntity>

    @Query("SELECT * FROM users")
    fun observeAll(): Flow<List<UsersEntity>>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun readUserById(id: String): UsersEntity?

    @Update
    suspend fun update(user: UsersEntity)

    @Query("UPDATE users SET photoUri = :uri WHERE email = :email")
    suspend fun updateProfilePicture(email: String, uri: String)

    @Query("SELECT photoUri FROM users WHERE email = :email")
    fun getProfilePicture(email: String): Flow<String?>


}