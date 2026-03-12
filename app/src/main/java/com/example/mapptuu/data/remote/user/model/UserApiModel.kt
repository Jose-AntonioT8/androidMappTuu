package com.example.mapptuu.data.remote.user.model

data class UsersListRemote(
    val items: List<UsersListItemRemote>
)

data class UsersListItemRemote(
    val id: String,
    val createdAt: Long,
    val email: String,
    val name: String,
)

data class UsersRemote(
    val id: String,
    val createdAt: Long,
    val email: String,
    val name: String,
    val photoUri: String?,
)