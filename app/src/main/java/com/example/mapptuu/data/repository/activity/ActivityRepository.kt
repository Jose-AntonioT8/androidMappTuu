package com.example.mapptuu.data.repository.activity

import com.example.mapptuu.data.model.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun readAll(): Result<List<Activity>>
    suspend fun readOne(id:Long): Result<Activity>

    suspend fun readdOneByName(name:String): Result<List<Activity>>

    fun observe(): Flow<Result<List<Activity>>>
    suspend fun delete(id:Long)

    suspend fun refresh()

    suspend fun insert(activity: Activity)
}