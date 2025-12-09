package com.example.mapptuu.data.local.activityTypes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mapptuu.data.model.ActivityTypes

@Entity("activityTypes")
data class ActivityTypesEntity(
    @PrimaryKey
    val id:String,
    val name:String,
    val color:String,
    val description:String
)

fun ActivityTypes.toEntity(): ActivityTypesEntity {
    return ActivityTypesEntity(
        id = this.id,
        name = this.name,
        color = this.color,
        description = this.description
    )
}

fun ActivityTypesEntity.toModel(): ActivityTypes {
    return ActivityTypes(
        id = this.id,
        name = this.name,
        color = this.color,
        description = this.description
    )
}
fun List<ActivityTypesEntity>.toModel(): List<ActivityTypes> {
    return this.map(ActivityTypesEntity::toModel)
}