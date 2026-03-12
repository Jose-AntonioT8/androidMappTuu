package com.example.mapptuu.data.remote.activity

import com.example.mapptuu.data.ActivityDataSource
import com.example.mapptuu.data.model.Activity
import com.example.mapptuu.data.remote.activity.model.ActivityRemote
import com.example.mapptuu.data.remote.activity.model.ActivityListItemRemote
import com.google.firebase.Timestamp
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import android.util.Log

const val TAG = "ActivityRemoteDataSource"

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
            Log.d(TAG, "Iniciando readAll()")
            val response = api.readAll()
            Log.d(TAG, "Response code: ${response.code()}, isSuccessful: ${response.isSuccessful}")
            return if (response.isSuccessful) {
                val body = response.body()!!
                Log.d(TAG, "Body obtenido, items: ${body.size}")
                val finalList = body.map { it.toActivity() }
                Log.d(TAG, "readAll() completado, final list size: ${finalList.size}")
                Result.success(finalList)
            } else {
                val errorMsg = "Error code: ${response.code()}"
                Log.e(TAG, errorMsg)
                Result.failure(RuntimeException(errorMsg))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción en readAll(): ${e.message}", e)
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

    override suspend fun readOneByName(name: String): Result<List<Activity>> {
        try {
            Log.d(TAG, "Iniciando readOneByName(name=$name)")
            val response = api.readOneByName(name)
            Log.d(TAG, "Response code: ${response.code()}, isSuccessful: ${response.isSuccessful}")
            return if (response.isSuccessful) {
                val body = response.body()!!
                Log.d(TAG, "Body obtenido, items: ${body.size}")
                val finalList = body.map { it.toActivity() }
                Log.d(TAG, "readOneByName() completado, final list size: ${finalList.size}")
                Result.success(finalList)
            } else {
                val errorMsg = "Error code: ${response.code()}"
                Log.e(TAG, errorMsg)
                Result.failure(RuntimeException(errorMsg))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción en readOneByName(): ${e.message}", e)
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
        activity: Activity
    ) {
        api.update(activity.id, activity.toRemote())
    }

    private fun Activity.toRemote(): ActivityRemote {
        val millis = this.createdAt.seconds * 1000L + this.createdAt.nanoseconds / 1_000_000
        return ActivityRemote(
            id = this.id,
            activityTypeId = this.activityTypeId,
            createdAt = millis,
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
    fun ActivityRemote.toExternal(): Activity {
        val timestamp = Timestamp(Date(this.createdAt))
        return Activity(
            id = this.id,
            activityTypeId = this.activityTypeId,
            createdAt = timestamp,
            description = this.description,
            imageRef = this.imageRef,
            latitude = this.latitude,
            longitude = this.longitude,
            name = this.name,
            ownerId = this.ownerId,
            rating = this.rating
        )
    }

    fun ActivityListItemRemote.toActivity(): Activity {
        val timestamp = Timestamp(Date(this.createdAt))
        return Activity(
            id = this.id,
            activityTypeId = this.activityTypeId,
            createdAt = timestamp,
            description = this.description,
            imageRef = this.imageRef,
            latitude = this.latitude,
            longitude = this.longitude,
            name = this.name,
            ownerId = this.ownerId,
            rating = this.rating
        )
    }

    private fun JsonElement.toFirebaseTimestamp(): Timestamp {
        return if (this.isJsonPrimitive && this.asJsonPrimitive.isNumber) {
            val millis = this.asLong
            Timestamp(Date(millis))
        } else if (this.isJsonObject) {
            val obj: JsonObject = this.asJsonObject
            // Support structures like {"seconds": 123, "nanoseconds": 0} or Firestore export
            val seconds = when {
                obj.has("seconds") -> obj.get("seconds").asLong
                obj.has("_seconds") -> obj.get("_seconds").asLong
                else -> 0L
            }
            val nanos = when {
                obj.has("nanoseconds") -> obj.get("nanoseconds").asInt
                obj.has("_nanoseconds") -> obj.get("_nanoseconds").asInt
                else -> 0
            }
            // Convert seconds/nanos to Date millis
            val millis = seconds * 1000 + (nanos / 1_000_000)
            Timestamp(Date(millis))
        } else {
            // Fallback to now if unexpected
            Timestamp.now()
        }
    }

}