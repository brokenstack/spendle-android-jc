package pw.alk.spendle.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import pw.alk.spendle.models.AuthRequest
import pw.alk.spendle.models.AuthResponse
import pw.alk.spendle.models.GenericResponse
import pw.alk.spendle.models.TransactionBody
import pw.alk.spendle.models.WeeklyTotalResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


private const val BASE_URL = "https://spendle.deno.dev"

var gson: Gson = GsonBuilder()
    .setLenient()
    .create()

object RetrofitBuilder {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @POST("user/register")
    suspend fun registerUser(@Body request: AuthRequest): Response<AuthResponse>

    @POST("user/login")
    suspend fun loginUser(@Body request: AuthRequest): Response<AuthResponse>

    @GET("reports/weekly/total")
    suspend fun getWeeklyTotal(@Header("Authorization") token: String): Response<WeeklyTotalResponse>

    @POST("transaction/new")
    suspend fun newTransaction(
        @Body request: TransactionBody,
        @Header("Authorization") token: String
    ): Response<GenericResponse>
}