package com.example.mapptuu.data.repository.user

import com.example.mapptuu.data.UsersDataSource
import com.example.mapptuu.data.model.Users
import com.example.mapptuu.di.LocalDataSource
import com.example.mapptuu.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl@Inject constructor(
    @RemoteDataSource private val remoteDataSource: UsersDataSource,
    @LocalDataSource private val localDataSource: UsersDataSource
): UserRepository {
    override suspend fun readAll(): Result<List<Users>> {
        return remoteDataSource.readAll()
    }

    override suspend fun readOne(id: String): Result<Users> {
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
    }
}
