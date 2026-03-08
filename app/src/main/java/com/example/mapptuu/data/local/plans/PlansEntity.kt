package com.example.mapptuu.data.local.plans


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mapptuu.data.model.Plans
import com.google.firebase.Timestamp

@Entity("plans")
data class PlansEntity(
    @PrimaryKey
    val id:String,
    val activityIds:List<String>,
    val createdAt:Timestamp,
    val description:String,
    val imgRef:String,
    val name:String,
    val ownerId:String,
    val rating:Float,
    val visibility:Boolean
)

fun Plans.toEntity(): PlansEntity {
    return PlansEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        activityIds = this.activityIds,
        createdAt = this.createdAt,
        imgRef = this.imgRef,
        ownerId = this.ownerId,
        rating = this.rating,
        visibility = this.visibility
    )
}

fun PlansEntity.toModel(): Plans {
    return Plans(
        id = this.id,
        name = this.name,
        description = this.description,
        activityIds = this.activityIds,
        createdAt = this.createdAt,
        imgRef = this.imgRef,
        ownerId = this.ownerId,
        rating = this.rating,
        visibility = this.visibility
    )
}
fun List<PlansEntity>.toModel(): List<Plans> {
    return this.map(PlansEntity::toModel)
}