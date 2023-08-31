package com.ddx.compose.network

import com.ddx.compose.model.Term
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Objects

interface RequestService {
    @GET(value = "/user/getTerms")
    fun getTerms(): Call<List<Term>>;


    @POST(value = "/user/login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<LoginResult>

    @POST(value = "/user/register")
    fun register(ad
        @Query("user_name") username: String,
        @Query("first_password") firstPassword: String,
        @Query("second_password") secondPassword: String,
    ): Call<RegisterResult>

    @POST(value = "/main")
    fun main(
        @Query("user_name") username: String,
        @Query("password") password: String
    ): Call<MainResult>
}

val NETWORK_URL = "http://11.8.192.168:8080"
