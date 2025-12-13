package com.example.mapptuu.data.local.activityTypes

import com.example.mapptuu.data.ActivityTypesDataSource
import com.example.mapptuu.data.model.ActivityTypes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivityTypesLocalDataSource @Inject constructor(
    private val scope: CoroutineScope,
    private val activityTypesDao: ActivityTypesDao
): ActivityTypesDataSource {
    override suspend fun addAll(activityTypesList: List<ActivityTypes>) {
        activityTypesList.forEach {
                activityTypes->
            val entity = activityTypes.toEntity()
            withContext(Dispatchers.IO) {
                activityTypesDao.insert(entity)
            }
        }
    }

    override fun observe(): Flow<Result<List<ActivityTypes>>> {
        val databaseFlow = activityTypesDao.observeAll()
        return databaseFlow.map {
                entities ->
            Result.success(entities.toModel())
        }
    }

    override suspend fun readAll(): Result<List<ActivityTypes>> {
        val result = Result.success(activityTypesDao.getAll().toModel())
        return result
    }

    override suspend fun readOne(id: Long): Result<ActivityTypes> {
        val entity = activityTypesDao.readActivityTypesById(id)
        return if(entity==null){
            Result.failure(ActivityTypesNotFoundException())
        }
        else
            Result.success(entity.toModel())    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(activityTypes: ActivityTypes) {
        val entity = activityTypes.toEntity()
        activityTypesDao.insert(entity)
    }



    override suspend fun delete(id: Long) {
        activityTypesDao.delete(id)

    }

    override suspend fun update(
        activityType: ActivityTypes
    ) {
        activityTypesDao.update( activityType.toEntity())


    }

}