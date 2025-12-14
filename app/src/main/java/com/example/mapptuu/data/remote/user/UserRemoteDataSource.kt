package com.example.mapptuu.data.remote.user
import com.example.mapptuu.data.remote.user.model.UsersRemote
import com.example.mapptuu.data.UsersDataSource
import com.example.mapptuu.data.model.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

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
        try {
            val response = api.readAll()
            val finalList = mutableListOf<Users>()
            return if (response.isSuccessful) {
                val body = response.body()!!
                for (result in body.items) {
                    val remoteUsers = readOne(id = result.id)
                    remoteUsers.let {
                        finalList.add(remoteUsers.toUsers())
                    }
                }
                Result.success(finalList)
            } else {
                Result.failure(RuntimeException("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun readOne(id: String): Result<Users> {
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

    private fun Users.toRemote(): UsersRemote {
        return UsersRemote(
            id = this.id,
            name = this.name,
            email = this.email,
            createdAt = this.createdAt,
            )
    }
    private fun Result<Users>.toUsers(): Users {
        return Users(
            id = this.getOrNull()!!.id,
            name = this.getOrNull()!!.name,
            email = this.getOrNull()!!.email,
            createdAt = this.getOrNull()!!.createdAt,
        )
    }
    fun UsersRemote.toExternal():Users {
        return Users(
            id = this.id,
            name = this.name,
            email = this.email,
            createdAt = this.createdAt)}

}