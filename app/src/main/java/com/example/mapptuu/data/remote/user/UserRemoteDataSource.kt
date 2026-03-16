package com.example.mapptuu.data.remote.user

import com.example.mapptuu.data.UsersDataSource
import com.example.mapptuu.data.local.users.UsersEntity
import com.example.mapptuu.data.model.Users
import com.example.mapptuu.data.remote.user.model.UsersRemote
import com.google.firebase.Timestamp
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class UserRemoteDataSource @Inject constructor(
    private val api: UserApi,
    private val scope: CoroutineScope
): UsersDataSource {
    override suspend fun addAll(usersList: List<Users>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Users>>> {
        return flow {
            emit(Result.success(listOf<Users>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(): Result<List<Users>> {
        return try {
            val response = api.readAll()
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                val users = body.map { it.toExternal() }
                Result.success(users)
            } else {
                Result.failure(RuntimeException("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun readOne(id: String?): Result<Users> {
        try {
            val response = api.readOne(id)
            return response.body().let {
                Result.success(it!!.toExternal())
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(users: Users) {
        api.insert(users.toRemote())
    }

    override suspend fun delete(id: String) {
        api.delete(id)
    }

    override suspend fun update( user: Users) {
        api.update(user.id, user.toRemote())
    }

    override suspend fun updateProfilePicture(id: String?, uri: String) {
        TODO("Not yet implemented")
    }

    override fun getProfilePicture(id: String?): Flow<String?> {
        TODO("Not yet implemented")
    }



    override suspend fun readUserByEmail(email: String?): UsersEntity? {
        TODO("Not yet implemented")
    }

    private fun Users.toRemote(): UsersRemote {
        val millis = this.createdAt.seconds * 1000L + this.createdAt.nanoseconds / 1_000_000
        return UsersRemote(
            id = this.id,
            name = this.name,
            email = this.email,
            createdAt = millis,
            photoUri = this.photoUri,
        )
    }

    private fun UsersRemote.toExternal(): Users {
        val timestamp = Timestamp(Date(this.createdAt))
        return Users(
            id = this.id,
            name = this.name,
            email = this.email,
            createdAt = timestamp,
            photoUri = this.photoUri,
        )
    }
}