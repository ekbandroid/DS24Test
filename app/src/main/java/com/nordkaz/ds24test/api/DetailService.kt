package com.nordkaz.ds24test.api

import android.util.Log
import com.nordkaz.ds24test.model.Detail
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val TAG = "DetailService"


fun getDetails(
        service: DetailService,
        id: Int,
        onSuccess: (data: List<Detail>) -> Unit,
        onError: (error: String) -> Unit) {
    Log.d(TAG, "query: id: $id")


    service.getDetail(id)
            .enqueue(
            object : Callback<DetailResponse> {
                override fun onFailure(call: Call<DetailResponse>?, t: Throwable) {
                    Log.d(TAG, "fail to get data")
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(
                        call: Call<DetailResponse>?,
                        response: Response<DetailResponse>
                ) {
                    Log.d(TAG, "got a response $response")
                    if (response.isSuccessful) {
                        val repos = response.body()?.items ?: emptyList()
                        onSuccess(repos)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
    )
}

interface DetailService {

    @GET("test/detail.php")
    fun getDetail(
                    @Query("id") id: Int): Call<DetailResponse>

    companion object {
        private const val BASE_URL = "http://ds24.ru/"

        fun create(): DetailService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DetailService::class.java)
        }
    }
}