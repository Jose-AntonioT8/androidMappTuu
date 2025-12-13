package com.example.mapptuu.data.model

import com.google.firebase.Timestamp

data class Plans(
 val id:Long,
 val activitiesIds:List<String>,
 val createdAt: Timestamp,
 val description:String,
 val imgRef:String,
 val name:String,
 val ownerId:String,
 val rating:Int,
 val visibility:Boolean
)