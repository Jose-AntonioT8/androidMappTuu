package com.example.mapptuu.data.remote.plan.model

import com.google.gson.JsonElement

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
    val createdAt: JsonElement,
    val description: String,
    val imgRef: String,
    val name: String,
    val ownerId: String,
    val rating: Float,
    val visibility: Boolean
)

/**
 * Body para crear/actualizar sin enviar `id`.
 * El `id` debe venir del backend (docRef.id) y no del cliente.
 */
data class PlanUpsertRemote(
    val activitiesIds: List<String>?,
    val createdAt: JsonElement,
    val description: String,
    val imgRef: String,
    val name: String,
    val ownerId: String,
    val rating: Float,
    val visibility: Boolean
)