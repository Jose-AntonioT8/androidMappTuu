package com.example.mapptuu.data.repository.activity

import com.example.mapptuu.data.ActivityDataSource
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.di.LocalDataSource
import com.example.mapptuu.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.collections.addAll
import kotlin.text.insert


class ActivityRepositoryImpl  @Inject constructor(
    @RemoteDataSource private val remoteDataSource: ActivityDataSource,
    @LocalDataSource private val localDataSource: ActivityDataSource
): ActivityRepository {
    override suspend fun readAll(): Result<List<Activity>> {
        return remoteDataSource.readAll()
    }

    override suspend fun readOne(id: String): Result<Activity> {
        return localDataSource.readOne(id)
    }

    override suspend fun readdOneByName(name: String): Result<List<Activity>> {
        return localDataSource.readOneByName(name)
    }


    override fun observe(): Flow<Result<List<Activity>>> {
        return localDataSource.observe()
    }

    override suspend fun delete(id: String) {
        localDataSource.delete(id)
    }

    override suspend fun refresh() {
        val resultRemoteActivity = remoteDataSource.readAll()
        if (resultRemoteActivity.isSuccess) {
            localDataSource.addAll(resultRemoteActivity.getOrNull()!!)
        }
    }

    override suspend fun insert(activity: Activity) {
        localDataSource.insert(activity)
    }
}