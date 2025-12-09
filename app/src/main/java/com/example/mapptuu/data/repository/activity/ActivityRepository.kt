package com.example.mapptuu.data.repository.activity

import com.example.mapptuu.data.model.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun readAll(): Result<List<Activity>>
    suspend fun readOne(id:String): Result<Activity>


    fun observe(): Flow<Result<List<Activity>>>
    suspend fun delete(id:String)

    suspend fun refresh()

    suspend fun insert(activity: Activity)
}