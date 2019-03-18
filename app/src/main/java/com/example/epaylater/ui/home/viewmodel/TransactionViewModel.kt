package com.example.epaylater.ui.home.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.epaylater.base.BaseActivity
import com.example.epaylater.model.TransactionResponse
import com.example.epaylater.repository.TransactionsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class TransactionViewModel
@Inject constructor(private val transRepo: TransactionsRepository): ViewModel(){

    private var disposable: CompositeDisposable? = null

    val transactons = MutableLiveData<List<TransactionResponse>>()
    private val repoLoadError = MutableLiveData<Boolean>()
     val loading = MutableLiveData<Boolean>()

    init {
        disposable = CompositeDisposable()
        fetchTransaction()
    }

    //get transaction list
    fun getTransactions(): LiveData<List<TransactionResponse>> {
        return transactons
    }

    //Check error
    fun getError(): LiveData<Boolean> {
        return repoLoadError
    }

    //show loading progress
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
                transRepo.getTransactionsList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object :
                        DisposableSingleObserver<List<TransactionResponse>>() {
                        override fun onSuccess(value: List<TransactionResponse>) {
                            repoLoadError.setValue(false)
                            transactons.setValue(value)
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