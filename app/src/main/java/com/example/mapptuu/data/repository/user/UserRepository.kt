package com.example.mapptuu.data.repository.user

import com.example.mapptuu.data.model.Users
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun readAll(): Result<List<Users>>
    suspend fun readOne(id: String?): Result<Users>


    fun observe(): Flow<Result<List<Users>>>
    suspend fun delete(id:String)

    suspend fun refresh()

    suspend fun insert(users: Users)

    fun getProfilePicture(id: String?): Flow<String?>

    suspend fun updateProfilePicture(id: String?, uri: String)

    suspend fun readUserByEmail(email: String?): Users?








}