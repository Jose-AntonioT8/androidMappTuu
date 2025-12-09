package com.example.mapptuu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mapptuu.data.local.activity.ActivityEntity

@Database(
    entities = [User::class, ActivityEntity::class, ActivityType::class, Plans::class],
    version = 1,
    exportSchema = false)
abstract class MappTuuDataBase: RoomDatabase() {


}