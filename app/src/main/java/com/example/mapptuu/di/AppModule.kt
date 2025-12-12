package com.example.mapptuu.di


import com.example.mapptuu.data.ActivityDataSource
import com.example.mapptuu.data.ActivityTypesDataSource
import com.example.mapptuu.data.PlansDataSource
import com.example.mapptuu.data.UsersDataSource
import com.example.mapptuu.data.local.activity.ActivityLocalDataSource
import com.example.mapptuu.data.local.activityTypes.ActivityTypesLocalDataSource
import com.example.mapptuu.data.local.plans.PlansLocalDataSource
import com.example.mapptuu.data.local.users.UsersLocalDataSource
import com.example.mapptuu.data.remote.activity.ActivityRemoteDataSource
import com.example.mapptuu.data.remote.activity.model.ActivityRemote
import com.example.mapptuu.data.remote.activityType.ActivityTypeRemoteDataSource
import com.example.mapptuu.data.remote.plan.PlanRemoteDataSource
import com.example.mapptuu.data.remote.user.UserRemoteDataSource
import com.example.mapptuu.data.repository.activity.ActivityRepository
import com.example.mapptuu.data.repository.activity.ActivityRepositoryImpl
import com.example.mapptuu.data.repository.activityType.ActivityTypeRepository
import com.example.mapptuu.data.repository.activityType.ActivityTypeRepositoryImpl
import com.example.mapptuu.data.repository.plan.PlanRepository
import com.example.mapptuu.data.repository.plan.PlanRepositoryImpl
import com.example.mapptuu.data.repository.user.UserRepository
import com.example.mapptuu.data.repository.user.UserRepositoryImpl
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
    abstract fun bindsRemotePlanDataSource(ds: PlanRemoteDataSource): PlansDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalPlanDataSource(ds: PlansLocalDataSource): PlansDataSource

    @Binds
    @Singleton
    abstract fun bindPlanRepository(repository: PlanRepositoryImpl): PlanRepository

    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindsRemoteUserDataSource(ds: UserRemoteDataSource): UsersDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalUserDataSource(ds: UsersLocalDataSource): UsersDataSource

    @Binds
    @Singleton
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource