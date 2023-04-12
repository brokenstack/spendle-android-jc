package pw.alk.spendle.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import pw.alk.spendle.models.RegisterRequest
import pw.alk.spendle.models.RegisterResponse
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.lang.reflect.Type


private const val BASE_URL = "https://spendle.deno.dev"

var gson: Gson = GsonBuilder()
    .setLenient()
    .create()

class SameTypeErrorConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter { body ->
            if (body.contentLength() == 0L) {
                null
            } else {
                delegate.convert(body)
            }
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val delegate = retrofit.nextRequestBodyConverter<Any>(
            this,
            type,
            parameterAnnotations,
            methodAnnotations
        )
        return delegate as Converter<Any, RequestBody>
    }
}

object RetrofitBuilder {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(SameTypeErrorConverterFactory())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @POST("user/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}