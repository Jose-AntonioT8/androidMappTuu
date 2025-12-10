package com.example.mapptuu.data.remote.activity

import com.example.mapptuu.data.ActivityDataSource
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.remote.activity.model.ActivityRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class ActivityRemoteDataSource  @Inject constructor(
    private val api: ActivityApi,
    private val scope: CoroutineScope

) : ActivityDataSource {
    override suspend fun addAll(activityList: List<Activity>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Activity>>> {
        return flow {
            emit(Result.success(listOf<Activity>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(): Result<List<Activity>> {
        try {
            val response = api.readAll()
            val finalList = mutableListOf<Activity>()
            return if (response.isSuccessful) {
                val body = response.body()!!
                for (result in body.items) {
                    val remoteActivity = readOne(id = result.id)
                    remoteActivity.let {
                        finalList.add(remoteActivity.toActivity())
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

    override suspend fun readOne(id: String): Result<Activity> {
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

    override suspend fun insert(activity: Activity) {
        api.insert(activity.toRemote())
    }

    override suspend fun delete(id: String) {
        api.delete(id)
    }

    override suspend fun update(
        id: String,
        activity: Activity
    ) {
        api.update(id, activity.toRemote())
    }

    private fun Activity.toRemote(): ActivityRemote {
        return ActivityRemote(
            id = this.id,
            activityTypeId = this.activityTypeId,
            createdAt = this.createdAt,
            description = this.description,
            imageRef = this.imageRef,
            latitude = this.latitude,
            longitude = this.longitude,
            name = this.name,
            ownerId = this.ownerId,
            rating = this.rating
        )
    }
    fun Result<Activity>.toActivity(): Activity {
        return Activity(
            id = this.getOrNull()!!.id,
            activityTypeId = this.getOrNull()!!.activityTypeId,
            createdAt = this.getOrNull()!!.createdAt,
            description = this.getOrNull()!!.description,
            imageRef = this.getOrNull()!!.imageRef,
            latitude = this.getOrNull()!!.latitude,
            longitude = this.getOrNull()!!.longitude,
            name = this.getOrNull()!!.name,
            ownerId = this.getOrNull()!!.ownerId,
            rating = this.getOrNull()!!.rating
        )

    }
    fun ActivityRemote.toExternal():Activity {
        return Activity(
            id = this.id,
            activityTypeId = this.activityTypeId,
            createdAt = this.createdAt,
            description = this.description,
            imageRef = this.imageRef,
            latitude = this.latitude,
            longitude = this.longitude,
            name = this.name,
            ownerId = this.ownerId,
            rating = this.rating
        )
    }

}