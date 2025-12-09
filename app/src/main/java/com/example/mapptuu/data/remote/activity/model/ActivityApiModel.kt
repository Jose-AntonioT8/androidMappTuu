package com.example.mapptuu.data.remote.activity.model

import java.sql.Timestamp

data class ActivityListRemote(
    val items:List<ActivityListItemRemote>
)

data class ActivityListItemRemote(

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
data class ActivityRemote(
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