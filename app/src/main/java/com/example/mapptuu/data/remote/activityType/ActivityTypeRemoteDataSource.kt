package com.example.mapptuu.data.remote.activityType

import com.example.mapptuu.data.ActivityTypesDataSource
import com.example.mapptuu.data.model.ActivityTypes
import com.example.mapptuu.data.remote.activityType.model.ActivityTypesRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class ActivityTypeRemoteDataSource   @Inject constructor(
    private val api: ActivityTypeApi,
    private val scope: CoroutineScope

) : ActivityTypesDataSource {
    override suspend fun addAll(activityTypesList: List<ActivityTypes>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<ActivityTypes>>> {
        return flow {
            emit(Result.success(listOf<ActivityTypes>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )    }

    override suspend fun readAll(): Result<List<ActivityTypes>> {
        try {
            val response = api.readAll()
            return if (response.isSuccessful) {
                val body = response.body().orEmpty()
                Result.success(body.map { it.toExternal() })
            } else {
                Result.failure(RuntimeException("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }    }

    override suspend fun readOne(id: String): Result<ActivityTypes> {
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

    override suspend fun insert(activityTypes: ActivityTypes) {
        api.insert(activityTypes.toRemote())
    }

    override suspend fun delete(id: String) {
        api.delete(id)
    }

    override suspend fun update(
        activityType: ActivityTypes
    ) {
        api.update(activityType.id, activityType.toRemote())
    }

    private fun ActivityTypes.toRemote(): ActivityTypesRemote {
        return ActivityTypesRemote(
            id = this.id,
            color = this.color,
            description = this.description,
            name = this.name
        )
    }

    fun ActivityTypesRemote.toExternal():ActivityTypes {
        return ActivityTypes(
            id = this.id,
            color = this.color,
            description = this.description,
            name = this.name
        )
    }
}
