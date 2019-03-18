package com.example.epaylater.di

import android.util.Log
import com.example.epaylater.remote.ApiService
import com.example.epaylater.utlis.Constants
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */

    companion object {

    }
    @Provides
    @Reusable
    internal fun providePostApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        //Create a new Interceptor.
        val headerAuthorizationInterceptor = Interceptor { chain ->
            var request: okhttp3.Request = chain.request()
            if(Constants.TOKEN != "token") {
                val headers = request.headers().newBuilder().add("Authorization", "Bearer ${Constants.TOKEN}").build()
                request = request.newBuilder().headers(headers).build()
                Log.e("test", Constants.TOKEN)
            }


            chain.proceed(request)
        }


        return OkHttpClient()
                .newBuilder()
                .addInterceptor(headerAuthorizationInterceptor)
                .addNetworkInterceptor(logging)
                .connectTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */

    @Provides
    @Reusable
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .client(provideOkHttpClient())
                .baseUrl("https://interviewer-api.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

}