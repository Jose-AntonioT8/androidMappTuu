package com.example.mapptuu.data

import com.example.mapptuu.data.model.Users
import kotlinx.coroutines.flow.Flow

interface UsersDataSource {

    suspend fun addAll(usersList: List<Users>)
    fun observe(): Flow<Result<List<Users>>>
    suspend fun readAll(): Result<List<Users>>
    suspend fun readOne(id: String): Result<Users>
    suspend fun isError()
    suspend fun insert(users: Users)

    suspend fun delete(id:String)

    suspend fun update(user: Users)

    suspend fun updateProfilePicture(id: String?, uri: String)

    fun getProfilePicture(id: String?): Flow<String?>


}