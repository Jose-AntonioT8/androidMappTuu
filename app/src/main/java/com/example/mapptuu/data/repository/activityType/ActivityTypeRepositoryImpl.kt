package com.example.mapptuu.data.repository.activityType

import com.example.mapptuu.data.ActivityTypesDataSource
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.model.ActivityTypes
import com.example.mapptuu.di.LocalDataSource
import com.example.mapptuu.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.addAll
import kotlin.text.insert

class ActivityTypeRepositoryImpl  @Inject constructor(
    @RemoteDataSource private val remoteDataSource: ActivityTypesDataSource,
    @LocalDataSource private val localDataSource: ActivityTypesDataSource
): ActivityTypeRepository {
    override suspend fun readAll(): Result<List<ActivityTypes>> {
        return remoteDataSource.readAll()
    }

    override suspend fun readOne(id: String): Result<ActivityTypes> {
        return localDataSource.readOne(id)
    }

    override fun observe(): Flow<Result<List<ActivityTypes>>> {
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

    override suspend fun insert(activityTypes: ActivityTypes) {
        localDataSource.insert(activityTypes)
    }
}
