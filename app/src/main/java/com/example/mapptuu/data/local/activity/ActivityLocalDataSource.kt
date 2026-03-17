package com.example.mapptuu.data.local.activity

import com.example.mapptuu.data.ActivityDataSource
import com.example.mapptuu.data.model.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityLocalDataSource @Inject constructor(
    private val scope: CoroutineScope,
    private val activityDao: ActivityDao
): ActivityDataSource {
    override suspend fun addAll(activityList: List<Activity>) {
        activityList.forEach {
            activities->
            val entity = activities.toEntity()
            withContext(Dispatchers.IO) {
                activityDao.insert(entity)
            }
        }
    }

    override fun observe(): Flow<Result<List<Activity>>> {
        val databaseFlow = activityDao.observeAll()
        return databaseFlow.map {
            entities ->
            Result.success(entities.toModel())
        }
    }

    override suspend fun readAll(): Result<List<Activity>> {
        val result = Result.success(activityDao.getAll().toModel())
        return result
    }

    override suspend fun readOne(id: String): Result<Activity> {
        val entity = activityDao.readActivityById(id)
        return if(entity==null){
            Result.failure(ActivityNotFoundException())
        }
        else
            Result.success(entity.toModel())    }

    override suspend fun readOneByName(name: String): Result<List<Activity>> {
        val entities = activityDao.readActivityByName(name)
        return if (entities.isEmpty()) {
            Result.failure(ActivityNotFoundException())
        } else {
            val modelList = entities.map { it!!.toModel()  }
            Result.success(modelList)
        }
    }



    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(activity: Activity): Activity {
        val entity = activity.toEntity()
        activityDao.insert(entity)
        return activity
    }



    override suspend fun delete(id: String) {
        activityDao.delete(id)

    }

    override suspend fun update(
        activity: Activity
    ) {
        activityDao.update(activity.toEntity())

    }

}