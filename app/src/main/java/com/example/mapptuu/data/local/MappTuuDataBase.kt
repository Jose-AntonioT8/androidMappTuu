package com.example.mapptuu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mapptuu.data.local.activity.ActivityDao
import com.example.mapptuu.data.local.activity.ActivityEntity
import com.example.mapptuu.data.local.activityTypes.ActivityTypesDao
import com.example.mapptuu.data.local.activityTypes.ActivityTypesEntity
import com.example.mapptuu.data.local.plans.PlansDao
import com.example.mapptuu.data.local.plans.PlansEntity
import com.example.mapptuu.data.local.users.UsersDao
import com.example.mapptuu.data.local.users.UsersEntity

@Database(
    entities = [UsersEntity::class, ActivityEntity::class, ActivityTypesEntity::class, PlansEntity::class],
    version = 6,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class MappTuuDataBase: RoomDatabase() {

    abstract fun getUserDao(): UsersDao
    abstract fun getActivityDao(): ActivityDao
    abstract fun getActivityTypesDao(): ActivityTypesDao
    abstract fun getPlansDao(): PlansDao

}