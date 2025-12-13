package com.example.mapptuu.data.local

import androidx.room.TypeConverter
import com.google.firebase.Timestamp

class Converters {
    @TypeConverter
    fun fromTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.seconds
    }

    @TypeConverter
    fun toTimestamp(value: Long?): Timestamp? {
        return value?.let { Timestamp(it, 0) }
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        return data?.split(",")?.map { it } ?: emptyList()
    }
}
