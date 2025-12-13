package com.example.mapptuu.data.remote.activityType

import com.example.mapptuu.data.remote.activityType.model.ActivityTypesRemote
import com.example.mapptuu.data.remote.activityType.model.ActivityTypesListRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivityTypeApi  {
    @GET("/api/activityTypes")
    suspend fun readAll(): Response<ActivityTypesListRemote>
    @GET("/api/activityTypes/{id}")
    suspend fun readOne(@Path("id") id: Long): Response<ActivityTypesRemote>
    @GET("/api/activityTypes?name={name}")
    suspend fun readOneByName(@Query("name") name: String): Response<List<ActivityTypesRemote>>


    @DELETE("/api/activityTypes/{id}")
    suspend fun delete(@Path("id") id: Long)

    @POST("/api/activityTypes")
    suspend fun insert(@Body activityTypes: ActivityTypesRemote): Response<ActivityTypesRemote>

    @PATCH("/api/activityTypes/{id}")
    suspend fun update(@Path("id") id: Long, @Body activityTypes: ActivityTypesRemote): Response<ActivityTypesRemote>

}