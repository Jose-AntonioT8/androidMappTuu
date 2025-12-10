package com.example.mapptuu.di


import com.example.mapptuu.data.ActivityDataSource
import com.example.mapptuu.data.ActivityTypesDataSource
import com.example.mapptuu.data.local.activity.ActivityLocalDataSource
import com.example.mapptuu.data.local.activityTypes.ActivityTypesLocalDataSource
import com.example.mapptuu.data.remote.activity.ActivityRemoteDataSource
import com.example.mapptuu.data.remote.activity.model.ActivityRemote
import com.example.mapptuu.data.repository.activity.ActivityRepository
import com.example.mapptuu.data.repository.activity.ActivityRepositoryImpl
import com.example.mapptuu.data.repository.activityType.ActivityTypeRepository
import com.example.mapptuu.data.repository.activityType.ActivityTypeRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindsRemoteActivityDataSource(ds: ActivityRemoteDataSource): ActivityDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalActivityDataSource(ds: ActivityLocalDataSource): ActivityDataSource

    @Binds
    @Singleton
    abstract  fun bindActivityRepository(repository: ActivityRepositoryImpl): ActivityRepository
    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindsRemoteActivityTypesDataSource(ds: ActivityTypeRemoteDataSource): ActivityTypesDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalActivityTypesDataSource(ds: ActivityTypesLocalDataSource): ActivityTypesDataSource

    @Binds
    @Singleton
    abstract fun bindActivityTypesRepository(repository: ActivityTypeRepositoryImpl): ActivityTypeRepository

    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindsRemotePlanetDataSource(ds: PlanetRemoteDataSource): PlanetDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalPlanetDataSource(ds: PlanetLocalDataSource): PlanetDataSource

    @Binds
    @Singleton
    abstract fun bindPlanetRepository(repository: PlanetRepositoryImpl): PlanetRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource