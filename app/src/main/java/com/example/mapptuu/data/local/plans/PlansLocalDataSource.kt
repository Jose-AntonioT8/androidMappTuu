package com.example.mapptuu.data.local.plans

import com.example.mapptuu.data.PlansDataSource
import com.example.mapptuu.data.model.Plans

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.forEach

class PlansLocalDataSource @Inject constructor(
    private val scope : CoroutineScope,
    private val plansDao : PlansDao
): PlansDataSource {
    override suspend fun addAll(plansList: List<Plans>) {
        plansList.forEach { plans ->
            val entity = plans.toEntity()
            withContext(Dispatchers.IO) {
                plansDao.insert(entity)
            }
        }
    }

    override fun observe(): Flow<Result<List<Plans>>> {
        val databaseFlow = plansDao.observeAll()
        return databaseFlow.map { entities ->
            Result.success(entities.toModel())
        }
    }

    override suspend fun readAll(): Result<List<Plans>> {
        val result = Result.success(plansDao.getAll().toModel())
        return result
    }

    override suspend fun readOne(id: String): Result<Plans> {
        val entity = plansDao.readPlanById(id)
        return if (entity == null) {
            Result.failure(PlansNotFoundException())
        } else
            Result.success(entity.toModel())
    }

    override suspend fun readOneByName(name: String): Result<List<Plans>> {
        TODO("Not yet implemented")
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(plans: Plans) {
        val entity = plans.toEntity()
        plansDao.insert(entity)
    }


    override suspend fun delete(id: String) {
        plansDao.delete(id)

    }

    override suspend fun update( plans: Plans) {
        plansDao.update(plans.toEntity())
    }
}

