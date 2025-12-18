package com.example.mapptuu.data

import com.example.mapptuu.data.model.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityDataSource {
    suspend fun addAll(activityList: List<Activity>)
    fun observe(): Flow<Result<List<Activity>>>
    suspend fun readAll(): Result<List<Activity>>
    suspend fun readOne(id: String): Result<Activity>
    suspend fun readOneByName(name: String): Result<List<Activity>>
    suspend fun isError()
    suspend fun insert(activity: Activity)

    suspend fun delete(id:String)

    suspend fun update(activity: Activity)

}