package com.example.epaylater.repository

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.epaylater.base.BaseActivity
import com.example.epaylater.model.SpendModel
import com.example.epaylater.remote.ApiService
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SpendRepository
@Inject constructor(private val apiService: ApiService){

    private val mCompositeDisposable = CompositeDisposable()

    private val progress = MutableLiveData<Boolean>()

    fun getProgress() = progress

    fun getCompositeDisposable() = mCompositeDisposable


    /**
     * getAuthToken() return livedata of login response
     * use rx java and rx android for handing api in background thread
     */
    fun makeTransaction(jsonObject: SpendModel): MutableLiveData<JsonObject> {

        val data = MutableLiveData<JsonObject>()
        if(BaseActivity.isInternetConnected) {
            apiService.transaction(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress.postValue(true) }
                .subscribeBy(
                    onNext = {
                        progress.postValue(false)
                        Log.e("DATA", it.toString())

                    },
                    onError = {
                        progress.postValue(false)
                        Log.e("NODATA", it.toString())
                    }
                ).addTo(mCompositeDisposable)
        }
        return data
    }
}