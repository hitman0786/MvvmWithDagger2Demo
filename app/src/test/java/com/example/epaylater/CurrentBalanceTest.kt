package com.example.epaylater

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.example.epaylater.model.TransactionResponse
import com.example.epaylater.repository.TransactionsRepository
import com.example.epaylater.ui.home.viewmodel.TransactionViewModel
import io.reactivex.Observable
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class CurrentBalanceTest {

    @Mock
    private lateinit var mockRepository: TransactionsRepository

    private lateinit var mViewModel: TransactionViewModel

    @Mock
    private lateinit var transactons: Observer<List<TransactionResponse>>

    // Test rule for making the RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        mViewModel = TransactionViewModel(mockRepository)
    }

    @Test
    fun showDataFromCurrentBalanceApi(){

        //Mockito.`when`(mockRepository.getTransactionsList()).thenReturn(Single.just(transactons.value))

       // assertEquals(true, mainActivityViewModel.getLoading())
        //assertEquals(getTransactions(), mainActivityViewModel.getTransactions())

        // make the github api to return mock data
        Mockito.`when`(mockRepository.getTransactionsList()).thenReturn(Single.just(getTransactions()))
         // observe on the MutableLiveData with an observer
        mViewModel.getTransactions().observeForever(transactons)

        // assert that the name matches
        assert(mViewModel.getTransactions().value!![0].amount == "35.25")

       // Mockito.verify(mainActivityViewModel.transactons).value = transactons
    }

    fun getTransactions(): List<TransactionResponse> {
        val data = TransactionResponse("123",
            "2016-12-11T12:23:34Z",
            "A bag of spanners","35.25","GBP")
        val datalist:List<TransactionResponse> = listOf(data)

        return datalist
    }
}