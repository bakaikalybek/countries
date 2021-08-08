package kg.bakai.countries.network

import kg.bakai.countries.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("countries?")
    fun getCountries(
        @Query("limit") limit: Int
    ): Call<Response>
}