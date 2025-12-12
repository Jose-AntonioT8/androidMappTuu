package com.example.mapptuu.data.local.activity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.example.mapptuu.data.model.Activity

@Entity("activity")
data class ActivityEntity(
    @PrimaryKey
    val id:String,
    val activityTypeId:String,
    val createdAt: Timestamp,
    val description:String,
    val imageRef:String,
    val latitude:String,
    val longitude:String,
    val name:String,
    val ownerId:String,
    val rating:Int
)

fun Activity.toEntity(): ActivityEntity{
    return ActivityEntity(
        id = this.id,
        activityTypeId = this.activityTypeId,
        createdAt = this.createdAt,
        description = this.description,
        imageRef = this.imageRef,
        latitude = this.latitude,
        longitude = this.longitude,
        name = this.name,
        ownerId = this.ownerId,
        rating = this.rating
    )
}
fun ActivityEntity.toModel(): Activity{
    return Activity(
        id = this.id,
        activityTypeId = this.activityTypeId,
        createdAt = this.createdAt,
        description = this.description,
        imageRef = this.imageRef,
        latitude = this.latitude,
        longitude = this.longitude,
        name = this.name,
        ownerId = this.ownerId,
        rating = this.rating
    )
}

fun List<ActivityEntity>.toModel(): List<Activity>{
    return this.map(ActivityEntity::toModel)
}