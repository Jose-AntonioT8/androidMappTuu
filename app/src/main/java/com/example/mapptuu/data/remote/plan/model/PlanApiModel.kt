package com.example.mapptuu.data.remote.plan.model

// Typealias para que PlanListRemote sea directamente un array
typealias PlanListRemote = List<PlansListItemRemote>

data class PlansListItemRemote(
    val id: String,
    val activitiesIds: List<String>,
    val createdAt: Long,
    val description: String,
    val imgRef: String,
    val name: String,
    val ownerId: String,
    val rating: Float,
    val visibility: Boolean
)

data class PlansRemote(
    val id: String,
    val activitiesIds: List<String>?,
    val createdAt: Long,
    val description: String,
    val imgRef: String,
    val name: String,
    val ownerId: String,
    val rating: Float,
    val visibility: Boolean
)