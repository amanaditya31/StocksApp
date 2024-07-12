package com.example.stocksapp.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.stocksapp.network.StocksApi
import com.example.stocksapp.network.StocksApiImage
import com.example.stocksapp.utils.Constants.BASE_URL
import com.example.stocksapp.utils.Constants.BASE_URL_IMAGE
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent ::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext // Provides the Application context for injection
    }
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                return true
            }
        }
        return false
    }
    class CacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(10, TimeUnit.DAYS)
                .build()
            return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

    class ForceCacheInterceptor(private val context: Context)  : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder: Request.Builder = chain.request().newBuilder()
            if (!isInternetAvailable(context)) {
                builder.cacheControl(CacheControl.FORCE_CACHE);
            }
            return chain.proceed(builder.build());
        }
    }
    @Provides
    @Singleton
    fun provideHttpClient(context: Context): OkHttpClient { // Inject Context
        val cacheSize = (5 * 1024 * 1024).toLong()
        val cache = Cache(context.cacheDir, cacheSize) // Use injected context

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(ForceCacheInterceptor(context)) // Pass context to the interceptor
            .addNetworkInterceptor(logging)
            .build()
    }

    // ... (your other classes: CacheInterceptor, ForceCacheInterceptor, Moshi instance)

    @Singleton
    @Provides
    fun providesStocksApi(okHttpClient: OkHttpClient): StocksApi {
        // inject Okhttp client instead of creating in here
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(StocksApi::class.java)
    }
    private val moshi= Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//
//    val logging= HttpLoggingInterceptor()
//
//    val httpClient= OkHttpClient.Builder()
//        .cache(Cache(File(applicationContext.cacheDir, "http-cache"), 10L * 1024L * 1024L)) // 10 MiB
//        .addNetworkInterceptor(CacheInterceptor())
//        .addInterceptor(ForceCacheInterceptor())
//        .apply {
//        logging.level= HttpLoggingInterceptor.Level.BODY
//        addNetworkInterceptor(logging)
//    }.build()
//
//    private val retrofit= Retrofit.Builder()
//        .addConverterFactory(MoshiConverterFactory.create(moshi))
//        .baseUrl(BASE_URL)
//        .client(httpClient)
//        .build()
//
//    @Singleton
//    @Provides
//    fun providesStocksApi(): StocksApi {
//        return retrofit.create(StocksApi::class.java)
//    }


    //For image loading
    private val moshi2= Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val logging2= HttpLoggingInterceptor()

    val httpClient2= OkHttpClient.Builder().apply {
        logging2.level= HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging2)
    }.build()

    private val retrofit2= Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi2))
        .baseUrl(BASE_URL_IMAGE)
        .client(httpClient2)
        .build()

    //    val retrofitService: StocksApi by lazy{
//        retrofit.create(StocksApi:: class.java)
//    }
    @Singleton
    @Provides
    fun providesStocksApiImage(): StocksApiImage {
        return retrofit2.create(StocksApiImage::class.java)
    }
}