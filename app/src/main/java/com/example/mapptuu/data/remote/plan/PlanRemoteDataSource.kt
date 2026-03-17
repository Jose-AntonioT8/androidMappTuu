package com.example.mapptuu.data.remote.plan


import com.example.mapptuu.data.PlansDataSource
import com.example.mapptuu.data.model.Plans
import com.example.mapptuu.data.remote.plan.model.PlanUpsertRemote
import com.example.mapptuu.data.remote.plan.model.PlansRemote
import com.example.mapptuu.data.remote.plan.model.PlansListItemRemote
import com.google.firebase.Timestamp
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.util.Date
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import android.util.Log

const val TAG_PLAN = "PlanRemoteDataSource"

class PlanRemoteDataSource @Inject constructor(
    private val api: PlanApi,
    private val scope: CoroutineScope

): PlansDataSource {
    override suspend fun addAll(plansList: List<Plans>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Plans>>> {
        return flow {
            emit(Result.success(listOf<Plans>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(): Result<List<Plans>> {
        try {
            Log.d(TAG_PLAN, "Iniciando readAll()")
            val response = api.readAll()
            Log.d(TAG_PLAN, "Response code: ${response.code()}, isSuccessful: ${response.isSuccessful}")
            return if (response.isSuccessful) {
                val body = response.body()!!
                Log.d(TAG_PLAN, "Body obtenido, items: ${body.size}")
                val finalList = body.map { it.toPlans() }
                Log.d(TAG_PLAN, "readAll() completado, final list size: ${finalList.size}")
                Result.success(finalList)
            } else {
                val errorMsg = "Error code: ${response.code()}"
                Log.e(TAG_PLAN, errorMsg)
                Result.failure(RuntimeException(errorMsg))
            }
        } catch (e: Exception) {
            Log.e(TAG_PLAN, "Excepción en readAll(): ${e.message}", e)
            return Result.failure(e)
        }
    }

    override suspend fun readOne(id: String): Result<Plans> {
        try {
            val response = api.readOne(id)
            return response.body().let {
                Result.success(it!!.toExternal())
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun readOneByName(name: String): Result<List<Plans>> {
        try {
            val response = api.readOneByName(name)
            return if (response.isSuccessful) {
                val body = response.body()!!
                val finalList = body.map { it.toPlans() }
                Result.success(finalList)
            } else {
                val errorMsg = "Error code: ${response.code()}"
                Result.failure(RuntimeException(errorMsg))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(plans: Plans): Plans {
        val response = api.insert(plans.toUpsertRemote())
        if (!response.isSuccessful) {
            throw RuntimeException("Error al crear plan: ${response.code()}")
        }
        val body = response.body() ?: throw RuntimeException("Respuesta vacía al crear plan")
        return body.toExternal()
    }

    override suspend fun delete(id: String) {
        api.delete(id)
    }

    override suspend fun update( plans: Plans) {
        api.update(plans.id, plans.toUpsertRemote())
    }

    private fun Plans.toUpsertRemote(): PlanUpsertRemote {
        val millis = this.createdAt.seconds * 1000L + this.createdAt.nanoseconds / 1_000_000
        return PlanUpsertRemote(
            name = this.name,
            description = this.description,
            activitiesIds = this.activitiesIds,
            createdAt = JsonPrimitive(millis),
            imgRef = this.imgRef,
            ownerId = this.ownerId,
            rating = this.rating,
            visibility = this.visibility
        )
    }

    private fun Result<Plans>.toPlans(): Plans {
        return Plans(
            id = this.getOrNull()!!.id,
            name = this.getOrNull()!!.name,
            description = this.getOrNull()!!.description,
            activitiesIds = this.getOrNull()!!.activitiesIds,
            createdAt = this.getOrNull()!!.createdAt,
            imgRef = this.getOrNull()!!.imgRef,
            ownerId = this.getOrNull()!!.ownerId,
            rating = this.getOrNull()!!.rating,
            visibility = this.getOrNull()!!.visibility
        )
    }

    fun PlansRemote.toExternal(): Plans {
        val timestamp = this.createdAt.toFirebaseTimestamp()
        return Plans(
            id = this.id,
            name = this.name,
            description = this.description,
            activitiesIds = this.activitiesIds,
            createdAt = timestamp,
            imgRef = this.imgRef,
            ownerId = this.ownerId,
            rating = this.rating,
            visibility = this.visibility
        )
    }

    private fun PlansListItemRemote.toPlans(): Plans {
        val timestamp = Timestamp(Date(this.createdAt))
        return Plans(
            id = this.id,
            name = this.name,
            description = this.description,
            activitiesIds = this.activitiesIds,
            createdAt = timestamp,
            imgRef = this.imgRef,
            ownerId = this.ownerId,
            rating = this.rating,
            visibility = this.visibility
        )
    }

    private fun JsonElement.toFirebaseTimestamp(): Timestamp {
        return if (this.isJsonPrimitive && this.asJsonPrimitive.isNumber) {
            Timestamp(Date(this.asLong))
        } else if (this.isJsonObject) {
            val obj: JsonObject = this.asJsonObject
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
            val millis = seconds * 1000 + (nanos / 1_000_000)
            Timestamp(Date(millis))
        } else {
            Timestamp.now()
        }
    }
}