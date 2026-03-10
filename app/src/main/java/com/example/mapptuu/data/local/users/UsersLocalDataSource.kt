package com.example.mapptuu.data.local.users

import com.example.mapptuu.data.UsersDataSource
import com.example.mapptuu.data.model.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.forEach

class UsersLocalDataSource @Inject constructor(
    private val scope : CoroutineScope,
    private val usersDao : UsersDao
): UsersDataSource {
    override suspend fun addAll(usersList: List<Users>) {
        usersList.forEach { plans ->
            val entity = plans.toEntity()
            withContext(Dispatchers.IO) {
                usersDao.insert(entity)
            }
        }
    }

    override fun observe(): Flow<Result<List<Users>>> {
        val databaseFlow = usersDao.observeAll()
        return databaseFlow.map { entities ->
            Result.success(entities.toModel())
        }
    }

    override suspend fun readAll(): Result<List<Users>> {
        val result = Result.success(usersDao.getAll().toModel())
        return result
    }

    override suspend fun readOne(id: String): Result<Users> {
        val entity = usersDao.readUserById(id)
        return if (entity == null) {
            Result.failure(UsersNotFoundException())
        } else
            Result.success(entity.toModel())
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(users: Users) {
        val entity = users.toEntity()
        usersDao.insert(entity)
    }


    override suspend fun delete(id: String) {
        usersDao.delete(id)

    }

    override suspend fun update(user: Users) {
        usersDao.update(user.toEntity())
    }



    override suspend fun updateProfilePicture(id: String?, uri: String) {
        val email = id ?: return
        usersDao.updateProfilePicture(email, uri)

    }

    //Aqui se trata a email como id
    override fun getProfilePicture(id: String?): Flow<String?> {

        val email = id ?: return flowOf(null)
        return usersDao.getProfilePicture(email)
    }
}
