package com.example.epaylater.repository

import android.arch.lifecycle.MutableLiveData
import com.example.epaylater.base.BaseActivity
import com.example.epaylater.model.Error
import com.example.epaylater.model.LoginResponse
import com.example.epaylater.model.TokenModel
import com.example.epaylater.remote.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.google.gson.JsonObject


class LoginRepository
@Inject constructor(private val apiService: ApiService) {

    private val mCompositeDisposable = CompositeDisposable()

    private val progress = MutableLiveData<Boolean>()

    fun getProgress() = progress

    fun getCompositeDisposable() = mCompositeDisposable

    /**
     * getAuthToken() return livedata of login response
     * use rx java and rx android for handing api in background thread
     */
    fun getAuthToken(): MutableLiveData<LoginResponse> {
        val data = MutableLiveData<LoginResponse>()
        val jsonObject = JsonObject()
        if(BaseActivity.isInternetConnected) {
            apiService.getAuthToken(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { progress.postValue(true) }
                .subscribeBy(
                    onNext = {
                        progress.postValue(false)
                        data.value = TokenModel(it["token"].asString)
                    },
                    onError = {
                        progress.postValue(false)
                        data.value = Error("Token not found")
                    }
                ).addTo(mCompositeDisposable)
        }
        return data
    }

}