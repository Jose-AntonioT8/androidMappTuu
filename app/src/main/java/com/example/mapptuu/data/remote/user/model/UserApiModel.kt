package com.example.mapptuu.data.remote.user.model


import java.sql.Timestamp

data class UsersListRemote(
    val items:List<UsersListItemRemote>
)

data class UsersListItemRemote(
    val id:String,
    val createdAt:Timestamp,
    val email:String,
    val name:String,
)
data class UsersRemote(
    val id:String,
    val createdAt:Timestamp,
    val email:String,
    val name:String,
)