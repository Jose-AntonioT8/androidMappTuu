package com.example.mapptuu.di

import android.content.Context
import androidx.room.Room
import com.example.mapptuu.data.local.MappTuuDataBase
import com.example.mapptuu.data.local.activity.ActivityDao
import com.example.mapptuu.data.local.activityTypes.ActivityTypesDao
import com.example.mapptuu.data.local.plans.PlansDao
import com.example.mapptuu.data.local.users.UsersDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): MappTuuDataBase {

        val database = Room.databaseBuilder(context = applicationContext,
            MappTuuDataBase::class.java,
            name = "MappTuu-db")
            .fallbackToDestructiveMigration(true)
            .build()
        return database
    }

    @Provides
    fun provideActivityDao(
        database: MappTuuDataBase
    ): ActivityDao {
        return database.getActivityDao()
    }
    @Provides
    fun provideActivityTypesDao(
        database: MappTuuDataBase
    ): ActivityTypesDao {
        return database.getActivityTypesDao()
    }

    @Provides
    fun providePlanDao(
        database: MappTuuDataBase
    ): PlansDao {
        return database.getPlansDao()
    }


    @Provides
    fun provideUsersDao(
        database: MappTuuDataBase
    ): UsersDao {
        return database.getUserDao()
    }
}