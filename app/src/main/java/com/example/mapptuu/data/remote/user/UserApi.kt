package com.example.mapptuu.data.remote.user

import com.example.mapptuu.data.remote.user.model.UsersListRemote
import com.example.mapptuu.data.remote.user.model.UsersRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("/api/users")
    suspend fun readAll(): Response<UsersListRemote>
    @GET("/api/users/{id}")
    suspend fun readOne(@Path("id") id: String?): Response<UsersRemote>
    @GET("/api/users?name={name}")
    suspend fun readOneByName(@Query("name") name: String): Response<List<UsersRemote>>


    @DELETE("/api/users/{id}")
    suspend fun delete(@Path("id") id: String)

    @POST("/api/users")
    suspend fun insert(@Body users: UsersRemote): Response<UsersRemote>

    @PATCH("/api/users/{id}")
    suspend fun update(@Path("id") id: String, @Body users: UsersRemote): Response<UsersRemote>

}