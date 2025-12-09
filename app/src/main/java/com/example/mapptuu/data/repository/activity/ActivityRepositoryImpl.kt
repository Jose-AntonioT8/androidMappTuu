package com.example.mapptuu.data.repository.activity

import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


class ActivityRepositoryImpl  @Inject constructor(
    @RemoteDataSource private val remoteDataSource: ActivityRepository,
    @LocalDataSource private val localDataSource: ActivityRepository,
    private val scope: CoroutineScope
): ActivityRepository {
}