package com.example.epaylater.ui.home.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.epaylater.base.BaseActivity
import com.example.epaylater.model.BalanceResponse
import com.example.epaylater.repository.BalanceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BalanceViewModel
@Inject constructor(private val balanceRepo: BalanceRepository): ViewModel(){

    private var disposable: CompositeDisposable? = null
    var balanceModel = MutableLiveData<BalanceResponse>()
    private val repoLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        disposable = CompositeDisposable()
      //  fetchTransaction()
    }

    fun getData(): LiveData<BalanceResponse> {
        return balanceModel
    }


    fun getError(): LiveData<Boolean> {
        return repoLoadError
    }


    fun getLoading(): LiveData<Boolean> {
        return loading
    }


    /**
     * fetchTransaction() fetch livedata of transaction list
     * use rx java and rx android for handing api in background thread
     */
    fun fetchTransaction() {
        loading.setValue(true)
        if(BaseActivity.isInternetConnected) {
            disposable!!.add(
                balanceRepo.getCurrentBalance().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                        DisposableSingleObserver<BalanceResponse>() {
                        override fun onSuccess(value: BalanceResponse) {
                            repoLoadError.setValue(false)
                            balanceModel.setValue(value)
                            loading.setValue(false)
                        }

                        override fun onError(e: Throwable) {
                            repoLoadError.setValue(true)
                            loading.setValue(false)
                        }
                    })
            )
        }
    }


    /**
     * when viewmodel task has been done then it clear the rx component from memory
     */
    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }
}