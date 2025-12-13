package com.example.mapptuu.data.model

import com.google.firebase.Timestamp


data class Users(
    val id:String,
    val createdAt: Timestamp,
    val email:String,
    val name:String,
)

