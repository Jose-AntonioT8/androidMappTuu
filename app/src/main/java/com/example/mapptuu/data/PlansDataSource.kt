package com.example.mapptuu.data


import com.example.mapptuu.data.model.Plans
import kotlinx.coroutines.flow.Flow

interface PlansDataSource {
    suspend fun addAll(plansList: List<Plans>)
    fun observe(): Flow<Result<List<Plans>>>
    suspend fun readAll(): Result<List<Plans>>
    suspend fun readOne(id: String): Result<Plans>
    suspend fun isError()
    suspend fun insert(plans: Plans)

    suspend fun delete(id:String)
}