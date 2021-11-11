package es.borjavg.data.api.services

import es.borjavg.data.api.model.ThingData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ThingsService {

    @GET("popular")
    fun getPopularThings(): Call<List<ThingData>>

    @GET("users/{username}/likes")
    fun getUserLikes(@Path("username") username: String): Call<List<ThingData>>

}
