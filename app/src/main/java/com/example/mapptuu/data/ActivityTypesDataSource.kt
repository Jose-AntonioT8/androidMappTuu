package com.example.mapptuu.data

import com.example.mapptuu.data.model.ActivityTypes
import kotlinx.coroutines.flow.Flow

interface ActivityTypesDataSource {
    suspend fun addAll(activityTypesList: List<ActivityTypes>)
    fun observe(): Flow<Result<List<ActivityTypes>>>
    suspend fun readAll(): Result<List<ActivityTypes>>
    suspend fun readOne(id: Long): Result<ActivityTypes>
    suspend fun isError()
    suspend fun insert(activityTypes: ActivityTypes)

    suspend fun delete(id:Long)
    suspend fun update(activityType: ActivityTypes)

}