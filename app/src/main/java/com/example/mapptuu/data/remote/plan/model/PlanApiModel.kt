package com.example.mapptuu.data.remote.plan.model

import java.sql.Timestamp

data class PlanListRemote(
    val items:List<PlansListItemRemote>
)

data class PlansListItemRemote(
    val id:String,
    val activitiesIds:List<String>,
    val createdAt:Timestamp,
    val description:String,
    val imgRef:String,
    val name:String,
    val ownerId:String,
    val rating:Int,
    val visibility:Boolean
)
data class PlansRemote(
    val id:String,
    val activitiesIds:List<String>,
    val createdAt:Timestamp,
    val description:String,
    val imgRef:String,
    val name:String,
    val ownerId:String,
    val rating:Int,
    val visibility:Boolean
)