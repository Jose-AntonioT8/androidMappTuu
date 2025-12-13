package com.example.mapptuu.data.remote.plan

import com.example.mapptuu.data.remote.plan.model.PlanListRemote
import com.example.mapptuu.data.remote.plan.model.PlansRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlanApi {
    @GET("/api/plans")
    suspend fun readAll(): Response<PlanListRemote>
    @GET("/api/plans/{id}")
    suspend fun readOne(@Path("id") id: Long): Response<PlansRemote>
    @GET("/api/plans?name={name}")
    suspend fun readOneByName(@Query("name") name: String): Response<PlanListRemote>


    @DELETE("/api/plans/{id}")
    suspend fun delete(@Path("id") id: Long)

    @POST("/api/plans")
    suspend fun insert(@Body plans: PlansRemote): Response<PlansRemote>

    @PATCH("/api/plans/{id}")
    suspend fun update(@Path("id") id: Long, @Body plans: PlansRemote): Response<PlansRemote>

}