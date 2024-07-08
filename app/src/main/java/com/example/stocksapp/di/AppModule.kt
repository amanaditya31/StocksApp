package com.example.stocksapp.di

import com.example.stocksapp.network.StocksApi
import com.example.stocksapp.utils.Constants.API_KEY
import com.example.stocksapp.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent ::class)
object AppModule {

    private val moshi= Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

    val logging= HttpLoggingInterceptor()

    val httpClient= OkHttpClient.Builder().apply {
//        addInterceptor(
//            Interceptor{
//                    chain ->
//                val builder=chain.request()
//                    .newBuilder()
//                builder.header("apikey", API_KEY)
//                return@Interceptor chain.proceed(builder.build())
//            }
//        )
        logging.level= HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()

    private val retrofit= Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

//    val retrofitService: StocksApi by lazy{
//        retrofit.create(StocksApi:: class.java)
//    }
    @Singleton
    @Provides
    fun providesStocksApi(): StocksApi {
        return retrofit.create(StocksApi::class.java)
    }
//    @Singleton
//    @Provides
//    fun providesStocksApi(): Lazy<StocksApi> {
//        return lazy {
//            retrofit.create(StocksApi::class.java)
//        }
//    }

}