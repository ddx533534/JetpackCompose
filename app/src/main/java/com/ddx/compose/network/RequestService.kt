package com.ddx.compose.network

import com.ddx.compose.model.Term
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RequestService {
    @GET(value = "/user/getTerms")
    suspend fun getTerms(): Call<List<Term>>;


    @POST(value = "/user/login")
    suspend fun login(
        @Query("user_name") username: String,
        @Query("password") password: String
    ): Call<LoginResult>

    @POST(value = "/user/register")
    suspend fun register(
        @Query("user_name") username: String,
        @Query("first_password") firstPassword: String,
        @Query("second_password") secondPassword: String,
    ): Call<RegisterResult>
}

val NETWORK_URL = ""
