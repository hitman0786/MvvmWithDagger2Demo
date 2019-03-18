package com.example.epaylater.ui.splash


import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.epaylater.model.LoginResponse
import com.example.epaylater.repository.LoginRepository
import javax.inject.Inject

class LoginViewModel
@Inject constructor(private val loginRepo: LoginRepository): ViewModel() {

    private var tokenModel = MutableLiveData<LoginResponse>()
    private val error = MutableLiveData<String>()

    //show loading progress
    fun getProgress() = loginRepo.getProgress()

    //get token
    fun getTokenModel(): MutableLiveData<LoginResponse> = tokenModel

    fun getErrors() = error

    fun getData() {
        tokenModel.value?.let {
            return
        }
        tokenModel = loginRepo.getAuthToken()
    }

    /**
     * when viewmodel task has been done then it clear the rx component from memory
     */
    override fun onCleared() {
        super.onCleared()
        Log.d("DISPOSE", "----- disposed -------")
        loginRepo.getCompositeDisposable().clear()
        loginRepo.getCompositeDisposable().dispose()
    }
}