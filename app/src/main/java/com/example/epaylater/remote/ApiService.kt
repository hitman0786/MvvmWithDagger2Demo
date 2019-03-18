package com.example.epaylater.remote

import com.example.epaylater.model.SpendModel
import com.example.epaylater.model.TransactionResponse
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

/**
 * Retrofit2 api service interface
 * define api end points
 */

interface ApiService {

    @POST("login")
    fun getAuthToken(@Body locationPost:JsonObject): Observable<JsonObject>

    @GET("balance")
    fun getCurrentBalance(): Observable<JsonObject>

    @GET("transactions")
    fun getTransactionsList(): Single<List<TransactionResponse>>

    @POST("spend")
    fun transaction(@Body spend: SpendModel): Observable<JsonObject>

}