package com.example.epaylater.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.epaylater.model.BalanceResponse
import com.example.epaylater.repository.BalanceRepository
import javax.inject.Inject

class BalanceViewModel
@Inject constructor(private val balanceRepo: BalanceRepository): ViewModel(){

    private var balanceModel = MutableLiveData<BalanceResponse>()
    private val error = MutableLiveData<String>()

    //Show progress of API
    fun getProgress() = balanceRepo.getProgress()

    //get balance
    fun getBalanceModel(): MutableLiveData<BalanceResponse> = balanceModel

    //find error
    fun getErrors() = error

    fun getData() {
        balanceModel.value?.let {
            return
        }
        balanceModel = balanceRepo.getCurrentBalance()
    }

    /**
     * when viewmodel task has been done then it clear the rx component from memory
     */
    override fun onCleared() {
        super.onCleared()
        Log.d("DISPOSE", "----- disposed -------")
        balanceRepo.getCompositeDisposable().clear()
        balanceRepo.getCompositeDisposable().dispose()
    }
}