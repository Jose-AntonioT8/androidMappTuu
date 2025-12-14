package com.example.mapptuu.data.local.users



import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mapptuu.data.model.Users
import com.google.firebase.Timestamp

@Entity("users")
data class UsersEntity(
    @PrimaryKey
    val id:String,
    val createdAt:Timestamp,
    val email:String,
    val name:String,
)

fun Users.toEntity(): UsersEntity {
    return UsersEntity(
        id = this.id,
        name = this.name,
        email = this.email,
        createdAt = this.createdAt,
    )
}

fun UsersEntity.toModel(): Users {
    return Users(
        id = this.id,
        name = this.name,
        createdAt = this.createdAt,
        email = this.email,
    )
}
fun List<UsersEntity>.toModel(): List<Users> {
    return this.map(UsersEntity::toModel)
}