package com.example.jetpack


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api{

    @GET("/users")
    fun getPosts():Call<List<Post>>

    @GET("/users/{id}")
    fun getPost(@Path("id") id:Int):Call<Post>

    @POST("/users")
    fun addPost(@Body post: Post):Call<Post>

    @DELETE("/users/{id}")
    fun delete(@Path("id") id: Int):Call<Void>
}