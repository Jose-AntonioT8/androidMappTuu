package com.example.mapptuu.data.model
import java.sql.Timestamp

data class Users(
    val id:String,
    val createdAt:Timestamp,
    val email:String,
    val name:String,
)

