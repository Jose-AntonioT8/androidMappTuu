package com.example.mapptuu.data.remote.activity.model

import com.google.gson.JsonElement

// Typealias para que ActivityListRemote sea directamente un array
typealias ActivityListRemote = List<ActivityListItemRemote>

data class ActivityListItemRemote(
    val id: String,
    val activityTypeId: String,
    val createdAt: Long,
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
    val createdAt: JsonElement,
    val description: String,
    val imageRef: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val ownerId: String,
    val rating: Float
)

/**
 * Body para crear/actualizar sin enviar `id`.
 * El `id` debe venir del backend (docRef.id) y no del cliente.
 */
data class ActivityUpsertRemote(
    val activityTypeId: String,
    val createdAt: JsonElement,
    val description: String,
    val imageRef: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val ownerId: String,
    val rating: Float
)