package com.example.mapptuu.data.remote.activity

import com.example.mapptuu.data.ActivityDataSource
import com.example.mapptuu.data.model.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRemoteDataSource  @Inject constructor(
    private val api: ActivityApi,
    private val scope: CoroutineScope

) : ActivityDataSource {
    override suspend fun addAll(activityList: List<Activity>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Activity>>> {
        TODO("Not yet implemented")
    }

    override suspend fun readAll(): Result<List<Activity>> {
        TODO("Not yet implemented")
    }

    override suspend fun readOne(id: String): Result<Activity> {
        TODO("Not yet implemented")
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(activity: Activity) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun update(
        id: String,
        activity: Activity
    ) {
        TODO("Not yet implemented")
    }

}