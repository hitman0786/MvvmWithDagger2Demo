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
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@RunWith(MockitoJUnitRunner::class)
class CurrentBalanceTest {


    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    lateinit var apiService: ApiService
    lateinit var repository: BalanceRepository

    @Mock
    lateinit var observer: Observer<BalanceResponse>

    lateinit var balanceViewmodel: BalanceViewModel

    lateinit var liveData: MutableLiveData<BalanceResponse>


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        repository = BalanceRepository(apiService)
        balanceViewmodel = BalanceViewModel(repository)


    }

    @Test
    fun showDataFromCurrentBalanceApi(){

        val response = BalanceResponse("123","GBP")

        val observer = mock<Observer<BalanceResponse>>()
        balanceViewmodel.getData().observeForever(observer)
        //when
        liveData.value = response
        //than
        verify(observer).onChanged(listOf(BalanceResponse(response.balance,response.currency)))

       /* Mockito.`when`(repository.getCurrentBalance()).thenReturn(Single.just(response))
        balanceViewmodel.balanceModel.observeForever(observer)
        val listdata = balanceViewmodel.getData()
        assert(listdata.value!!.balance == response.balance)*/
    }

}