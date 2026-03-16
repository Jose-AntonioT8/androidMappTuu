package com.example.mapptuu.data.repository.user

import com.example.mapptuu.data.UsersDataSource
import com.example.mapptuu.data.local.users.toModel
import com.example.mapptuu.data.model.Users
import com.example.mapptuu.di.LocalDataSource
import com.example.mapptuu.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepositoryImpl@Inject constructor(
    @RemoteDataSource private val remoteDataSource: UsersDataSource,
    @LocalDataSource private val localDataSource: UsersDataSource,
    private val scope: CoroutineScope
): UserRepository {

    init {
        scope.launch {
            refresh()
        }
    }
    override suspend fun readAll(): Result<List<Users>> {
        return remoteDataSource.readAll()
    }

    override suspend fun readOne(id: String?): Result<Users> {
        return localDataSource.readOne(id)
    }

    override fun observe(): Flow<Result<List<Users>>> {
        return localDataSource.observe()
    }

    override suspend fun delete(id: String) {
        localDataSource.delete(id)
    }

    override suspend fun refresh() {
        val resultRemoteActivityTypes = remoteDataSource.readAll()
        if (resultRemoteActivityTypes.isSuccess) {
            localDataSource.addAll(resultRemoteActivityTypes.getOrNull()!!)
        }
    }

    override suspend fun insert(users: Users) {
        localDataSource.insert(users)
        remoteDataSource.insert(users)
    }

    override fun getProfilePicture(id: String?): Flow<String?> {
        return localDataSource.getProfilePicture(id)
    }

    override suspend fun updateProfilePicture(id: String?, uri: String) {
        localDataSource.updateProfilePicture(id, uri)
    }

    override suspend fun readUserByEmail(email: String?): Users? {
        val entity = localDataSource.readUserByEmail(email)
        return entity?.toModel()
    }
}
