package com.example.epaylater.repository


import com.example.epaylater.model.BalanceResponse
import com.example.epaylater.remote.ApiService
import io.reactivex.Single
import javax.inject.Inject

class BalanceRepository
@Inject constructor(private val apiService: ApiService){

    fun getCurrentBalance(): Single<BalanceResponse> {
        return apiService.getCurrentBalance()
    }

}