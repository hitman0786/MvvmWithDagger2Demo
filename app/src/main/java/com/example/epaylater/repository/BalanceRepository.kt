package com.example.epaylater.repository

import android.arch.lifecycle.MutableLiveData
import com.example.epaylater.base.BaseActivity
import com.example.epaylater.model.BalanceError
import com.example.epaylater.model.BalanceModel
import com.example.epaylater.model.BalanceResponse
import com.example.epaylater.remote.ApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BalanceRepository
@Inject constructor(private val apiService: ApiService){

    private val mCompositeDisposable = CompositeDisposable()

    private val progress = MutableLiveData<Boolean>()

    fun getProgress() = progress

    fun getCompositeDisposable() = mCompositeDisposable

    /**
     * getCurrentBalance() return livedata of balance response
     * use rx java and rx android for handing api in background thread
     */
    fun getCurrentBalance(): MutableLiveData<BalanceResponse>{

        val data = MutableLiveData<BalanceResponse>()

        if(BaseActivity.isInternetConnected) {
            apiService.getCurrentBalance()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress.postValue(true) }
                .subscribeBy(
                    onNext = {
                        progress.postValue(false)
                        data.value = BalanceModel(it["balance"].asString, it["currency"].asString)
                    },
                    onError = {
                        progress.postValue(false)
                        data.value = BalanceError("Found some error to get current balance")
                    }
                ).addTo(mCompositeDisposable)
        }
        return data
    }
}