package com.example.mapptuu.data.repository.plan

import com.example.mapptuu.data.PlansDataSource
import com.example.mapptuu.data.model.ActivityTypes
import com.example.mapptuu.data.model.Plans
import com.example.mapptuu.di.LocalDataSource
import com.example.mapptuu.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: PlansDataSource,
    @LocalDataSource private val localDataSource: PlansDataSource,
    private val scope: CoroutineScope
): PlanRepository {

    init {
        scope.launch {
            refresh()
        }
    }

    override suspend fun readAll(): Result<List<Plans>> {
        return remoteDataSource.readAll()
    }

    override suspend fun readOne(id: String): Result<Plans> {
        return localDataSource.readOne(id)
    }

    override suspend fun readdOneByName(name: String): Result<List<Plans>> {
        return localDataSource.readOneByName(name)
    }

    override fun observe(): Flow<Result<List<Plans>>> {
        return localDataSource.observe()
    }

    override suspend fun delete(id:String) {
        remoteDataSource.delete(id)
        localDataSource.delete(id)
    }

    override suspend fun refresh() {
        android.util.Log.d("ActivityRepository", "refresh() iniciado")
        val resultRemoteActivityTypes = remoteDataSource.readAll()
        if (resultRemoteActivityTypes.isSuccess) {
            localDataSource.addAll(resultRemoteActivityTypes.getOrNull()!!)
            android.util.Log.d("ActivityRepository", "refresh() correcto")
        }
        else{
            android.util.Log.d("ActivityRepository", "refresh() mal")
        }
    }

    override suspend fun insert(plans: Plans) {
        remoteDataSource.insert(plans)
        localDataSource.insert(plans)
    }

    override suspend fun update(plans: Plans) {
        remoteDataSource.update(plans)
        localDataSource.update(plans)
    }


}
