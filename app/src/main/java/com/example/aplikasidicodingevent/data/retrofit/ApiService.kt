package com.example.aplikasidicodingevent.data.retrofit

import com.example.aplikasidicodingevent.data.response.ListEventsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int
    ): Call<ListEventsResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String): Call<ListEventsResponse>
}

