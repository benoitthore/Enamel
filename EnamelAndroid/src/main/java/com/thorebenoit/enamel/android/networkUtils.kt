package com.thorebenoit.enamel.android

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.thorebenoit.enamel.kotlin.core.data.ignoreUnknownObjectMapper
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.File

inline val jacksonFactory: JacksonConverterFactory get() = JacksonConverterFactory.create(ignoreUnknownObjectMapper())
inline val coroutineCallAdapterFactory get() = CoroutineCallAdapterFactory()

interface CacheHandler {
    val hasNetwork: () -> Boolean
    val cacheSize: Long
    val cacheDir: File
    val cacheTime: Int
}

inline fun <reified S> createService(baseUrl: String, cacheHandler: CacheHandler? = null, log: Boolean = false): S {
    val builder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jacksonFactory)
        .addCallAdapterFactory(coroutineCallAdapterFactory)

    builder.client(createOkHttpClient(cacheHandler, log))

    val retrofit = builder.build()

    return retrofit.create(S::class.java)
}

fun createOkHttpClient(cacheHandler: CacheHandler?, log: Boolean): OkHttpClient =
    with(
        OkHttpClient
            .Builder()
    ) {

        if (log) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        cacheHandler?.apply {

            cache(Cache(cacheDir, cacheSize))
            addInterceptor { chain ->

                var request = chain.request()


                request = if (hasNetwork())
                    request.newBuilder().header("Cache-Control", "public, max-age=$cacheTime").build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=${60 * 60 * 24 * 7}"
                    ).build()


                val response = chain.proceed(request)

                response
            }
        }

        return@with build()
    }
