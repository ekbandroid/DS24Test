package com.nordkaz.ds24test.api

import android.util.Log
import com.nordkaz.ds24test.model.Quote
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

private const val TAG = "QuotesService"


fun searchQuotes(
        service: QuotesService,
        page: Int,
        itemsPerPage: Int,
        onSuccess: (repos: List<Quote>) -> Unit,
        onError: (error: String) -> Unit) {
    Log.d(TAG, "page: $page, itemsPerPage: $itemsPerPage")

    service.searchRepos(page, itemsPerPage)
            .enqueue(
            object : Callback<QuoteSearchResponse> {
                override fun onFailure(call: Call<QuoteSearchResponse>?, t: Throwable) {
                    Log.d(TAG, "fail to get data")
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(
                        call: Call<QuoteSearchResponse>?,
                        response: Response<QuoteSearchResponse>
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

interface QuotesService {

    @GET("test/list.php")
    fun searchRepos(@Query("offset") page: Int,
                    @Query("limit") itemsPerPage: Int): Call<QuoteSearchResponse>

    companion object {
        private const val BASE_URL = "http://ds24.ru/"

        fun create(): QuotesService {
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
                    .create(QuotesService::class.java)
        }
    }
}