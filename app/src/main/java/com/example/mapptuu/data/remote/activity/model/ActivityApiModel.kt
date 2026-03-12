package com.example.mapptuu.data.remote.activity.model

import com.example.mapptuu.data.remote.IsoOrMillisLongAdapter
import com.google.gson.annotations.JsonAdapter

// Typealias para que ActivityListRemote sea directamente un array
typealias ActivityListRemote = List<ActivityListItemRemote>

data class ActivityListItemRemote(
    val id: String,
    val activityTypeId: String,
    @JsonAdapter(IsoOrMillisLongAdapter::class) val createdAt: Long,
    val description: String,
    val imageRef: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val ownerId: String,
    val rating: Float
)
data class ActivityRemote(
    val id: String,
    val activityTypeId: String,
    @JsonAdapter(IsoOrMillisLongAdapter::class) val createdAt: Long,
    val description: String,
    val imageRef: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val ownerId: String,
    val rating: Float
)