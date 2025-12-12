package com.example.mapptuu.data.remote.plan


import com.example.mapptuu.data.PlansDataSource
import com.example.mapptuu.data.model.Plans
import com.example.mapptuu.data.remote.plan.model.PlansRemote
import com.example.mapptuu.data.remote.plan.model.PlansListItemRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import kotlin.text.insert

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
            val response = api.readAll()
            val finalList = mutableListOf<Plans>()
            return if (response.isSuccessful) {
                val body = response.body()!!
                for (result in body.items) {
                    val remotePlans = readOne(id = result.id)
                    remotePlans.let {
                        finalList.add(remotePlans.toPlans())
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

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(plans: Plans) {
        api.insert(plans.toRemote())
    }

    override suspend fun delete(id: String) {
        api.delete(id)
    }

    override suspend fun update(id: String, plans: Plans) {
        api.update(id, plans.toRemote())
    }

    private fun Plans.toRemote(): PlansRemote {
        return PlansRemote(
            id = this.id,
            name = this.name,
            description = this.description,
            activitiesIds = this.activitiesIds,
            createdAt = this.createdAt,
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

    fun PlansRemote.toExternal():Plans {
        return Plans(
            id = this.id,
            name = this.name,
            description = this.description,
            activitiesIds = this.activitiesIds,
            createdAt = this.createdAt,
            imgRef = this.imgRef,
            ownerId = this.ownerId,
            rating = this.rating,
            visibility = this.visibility
        )
    }

    private fun PlansListItemRemote.toPlans(): Plans {
        return Plans(
            id = this.id,
            name = this.name,
            description = this.description,
            activitiesIds = this.activitiesIds,
            createdAt = this.createdAt,
            imgRef = this.imgRef,
            ownerId = this.ownerId,
            rating = this.rating,
            visibility = this.visibility
        )
    }
}