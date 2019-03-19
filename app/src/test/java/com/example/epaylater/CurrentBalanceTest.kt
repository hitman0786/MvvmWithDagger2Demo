package com.example.epaylater

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.example.epaylater.model.BalanceResponse
import com.example.epaylater.model.TransactionResponse
import com.example.epaylater.remote.ApiService
import com.example.epaylater.repository.BalanceRepository
import com.example.epaylater.repository.TransactionsRepository
import com.example.epaylater.ui.home.viewmodel.BalanceViewModel
import com.example.epaylater.ui.home.viewmodel.TransactionViewModel
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import org.json.JSONException
import org.json.JSONObject
import org.junit.*
import org.mockito.Mockito.*


@RunWith(MockitoJUnitRunner::class)
class CurrentBalanceTest {


    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()


    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var balanceResponseObserver: Observer<BalanceResponse>

    lateinit var repository: BalanceRepository

    lateinit var balanceViewmodel: BalanceViewModel

    lateinit var liveData: MutableLiveData<BalanceResponse>


    @Before
    fun setup(){

        MockitoAnnotations.initMocks(this)
        repository = BalanceRepository(apiService)
        balanceViewmodel = BalanceViewModel(repository)

        liveData = MutableLiveData()
        val paydata = Single.just(BalanceResponse("123", "GBP"))

        `when`(repository.getCurrentBalance()).thenReturn(paydata)

       // balanceResponseObserver = mock(Observer::class.java) as Observer<BalanceResponse>

        balanceViewmodel.getData().observeForever(balanceResponseObserver)
    }

    @Test
    fun showDataFromCurrentBalanceApi(){

       val data = BalanceResponse("290", "GBPO")
        liveData.value = data

        verify(balanceResponseObserver).onChanged(data)
    }

}