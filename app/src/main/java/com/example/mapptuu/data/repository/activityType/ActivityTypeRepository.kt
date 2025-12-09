package com.example.mapptuu.data.repository.activityType

import com.example.mapptuu.data.model.ActivityTypes
import kotlinx.coroutines.flow.Flow

interface ActivityTypeRepository {
    suspend fun readAll(): Result<List<ActivityTypes>>
    suspend fun readOne(id:String): Result<ActivityTypes>


    fun observe(): Flow<Result<List<ActivityTypes>>>
    suspend fun delete(id:String)

    suspend fun refresh()

    suspend fun insert(activityTypes: ActivityTypes)
}