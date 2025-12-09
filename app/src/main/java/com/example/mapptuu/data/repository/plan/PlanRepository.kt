package com.example.mapptuu.data.repository.plan

import com.example.mapptuu.data.model.Plans
import kotlinx.coroutines.flow.Flow

interface PlanRepository {
    suspend fun readAll(): Result<List<Plans>>
    suspend fun readOne(id:String): Result<Plans>


    fun observe(): Flow<Result<List<Plans>>>
    suspend fun delete(id:String)

    suspend fun refresh()

    suspend fun insert(plans: Plans)
}