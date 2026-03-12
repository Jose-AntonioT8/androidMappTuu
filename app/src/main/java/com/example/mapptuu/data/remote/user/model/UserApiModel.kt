package com.example.mapptuu.data.remote.user.model

import com.example.mapptuu.data.remote.IsoOrMillisLongAdapter
import com.google.gson.annotations.JsonAdapter

data class UsersListRemote(
    val items: List<UsersListItemRemote>
)

data class UsersListItemRemote(
    val id: String,
    @JsonAdapter(IsoOrMillisLongAdapter::class) val createdAt: Long,
    val email: String,
    val name: String,
)
data class UsersRemote(
    val id: String,
    @JsonAdapter(IsoOrMillisLongAdapter::class) val createdAt: Long,
    val email: String,
    val name: String,
    val photoUri: String?,
)