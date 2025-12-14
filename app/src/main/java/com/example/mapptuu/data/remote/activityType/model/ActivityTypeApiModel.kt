package com.example.mapptuu.data.remote.activityType.model


data class ActivityTypesListRemote(
    val items:List<ActivityTypesListItemRemote>
)

data class ActivityTypesListItemRemote(
    val id:String,
    val color:String,
    val description:String,
    val name:String,
)
data class ActivityTypesRemote(
    val id:String,
    val color:String,
    val description:String,
    val name:String,
)