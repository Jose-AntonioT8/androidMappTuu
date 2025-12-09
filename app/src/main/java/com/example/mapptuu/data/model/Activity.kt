package com.example.mapptuu.data.model
import java.sql.Timestamp

data class Activity(
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