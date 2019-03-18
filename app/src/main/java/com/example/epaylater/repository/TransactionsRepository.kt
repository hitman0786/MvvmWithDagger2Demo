package com.example.epaylater.repository


import com.example.epaylater.model.TransactionResponse
import com.example.epaylater.remote.ApiService
import javax.inject.Inject
import io.reactivex.Single

class TransactionsRepository
@Inject constructor(private val apiService: ApiService) {


    /**
     * getTransactionsList() return livedata of transaction list response
     * use Single operator for that
     */

    fun getTransactionsList(): Single<List<TransactionResponse>> {
        return apiService.getTransactionsList()
    }

}