package com.example.mapptuu.data.repository.user

import com.example.mapptuu.data.model.Users
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun readAll(): Result<List<Users>>
    suspend fun readOne(id:Long): Result<Users>


    fun observe(): Flow<Result<List<Users>>>
    suspend fun delete(id:Long)

    suspend fun refresh()

    suspend fun insert(users: Users)
}