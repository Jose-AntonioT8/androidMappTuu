package com.example.mapptuu.data.remote.activity

import com.example.mapptuu.data.remote.activity.model.ActivityListRemote
import com.example.mapptuu.data.remote.activity.model.ActivityRemote
import com.example.mapptuu.data.remote.activity.model.ActivityUpsertRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivityApi {
    @GET("/api/activities")
    suspend fun readAll(): Response<ActivityListRemote>
    @GET("/api/activities/{id}")
    suspend fun readOne(@Path("id") id: String): Response<ActivityRemote>
    @GET("/api/activities")
    suspend fun readOneByName(@Query("name") name: String): Response<ActivityListRemote>


    @DELETE("/api/activities/{id}")
    suspend fun delete(@Path("id") id: String)

    @POST("/api/activities")
    suspend fun insert(@Body activity: ActivityUpsertRemote): Response<ActivityRemote>

    @PATCH("/api/activities/{id}")
    suspend fun update(@Path("id") id: String, @Body activity: ActivityUpsertRemote): Response<ActivityRemote>

}