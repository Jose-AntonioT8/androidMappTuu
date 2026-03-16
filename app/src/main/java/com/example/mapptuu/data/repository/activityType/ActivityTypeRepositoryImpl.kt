package com.example.mapptuu.data.repository.activityType

import com.example.mapptuu.data.ActivityTypesDataSource
import com.example.mapptuu.data.model.ActivityTypes
import com.example.mapptuu.di.LocalDataSource
import com.example.mapptuu.di.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityTypeRepositoryImpl  @Inject constructor(
    @RemoteDataSource private val remoteDataSource: ActivityTypesDataSource,
    @LocalDataSource private val localDataSource: ActivityTypesDataSource
): ActivityTypeRepository {
    override suspend fun readAll(): Result<List<ActivityTypes>> {
        return remoteDataSource.readAll()
    }

    override suspend fun readOne(id: String): Result<ActivityTypes> {
        val localResult = localDataSource.readOne(id)
        if (localResult.isSuccess) return localResult

        // Si el local esta vacio, refresca y lo intenta de nuevo la busqueda por id
        refresh()
        val refreshedLocalResult = localDataSource.readOne(id)
        if (refreshedLocalResult.isSuccess) return refreshedLocalResult

        // hace el fetch al remoto y lo carga localmente
        val remoteResult = remoteDataSource.readOne(id)
        if (remoteResult.isSuccess) {
            localDataSource.insert(remoteResult.getOrNull()!!)
        }
        return remoteResult
    }

    override fun observe(): Flow<Result<List<ActivityTypes>>> {
        return localDataSource.observe()
    }

    override suspend fun delete(id: String) {
        localDataSource.delete(id)
    }

    override suspend fun refresh() {
        val resultRemoteActivityTypes = remoteDataSource.readAll()
        if (resultRemoteActivityTypes.isSuccess) {
            localDataSource.addAll(resultRemoteActivityTypes.getOrNull()!!)
        }
    }

    override suspend fun insert(activityTypes: ActivityTypes) {
        localDataSource.insert(activityTypes)
    }
}
