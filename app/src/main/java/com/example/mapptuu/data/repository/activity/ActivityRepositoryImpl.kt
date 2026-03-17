package com.example.mapptuu.data.repository.activity

import com.example.mapptuu.data.ActivityDataSource
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.di.LocalDataSource
import com.example.mapptuu.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ActivityRepositoryImpl  @Inject constructor(
    @RemoteDataSource private val remoteDataSource: ActivityDataSource,
    @LocalDataSource private val localDataSource: ActivityDataSource,
    private val scope: CoroutineScope
): ActivityRepository {

    init {
        scope.launch {
            refresh()
        }
    }

    override suspend fun readAll(): Result<List<Activity>> {
        return localDataSource.readAll()
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
        remoteDataSource.delete(id)
        localDataSource.delete(id)
    }

    override suspend fun refresh() {
        android.util.Log.d("ActivityRepository", "refresh() iniciado")
        val resultRemoteActivity = remoteDataSource.readAll()
        if (resultRemoteActivity.isSuccess) {
            localDataSource.addAll(resultRemoteActivity.getOrNull()!!)
            android.util.Log.d("ActivityRepository", "refresh() bien")
        }
        else{
            android.util.Log.d("ActivityRepository", "refresh() mal")
        }
    }

    override suspend fun insert(activity: Activity) {
        remoteDataSource.insert(activity)
        localDataSource.insert(activity)
    }

    override suspend fun update(activity: Activity) {
        remoteDataSource.update(activity)
        localDataSource.update(activity)

    }
}