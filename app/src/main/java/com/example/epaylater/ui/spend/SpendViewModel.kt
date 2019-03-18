package com.example.epaylater.ui.spend

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.epaylater.model.SpendModel
import com.example.epaylater.repository.SpendRepository
import com.google.gson.JsonObject
import javax.inject.Inject

class SpendViewModel
@Inject constructor(private val spendRepo: SpendRepository): ViewModel(){

    private var spendModel = MutableLiveData<JsonObject>()
    private val error = MutableLiveData<String>()

    //Show progress of API
    fun getProgress() = spendRepo.getProgress()

    //get balance
    fun getSpendModel(): MutableLiveData<JsonObject> = spendModel

    //find error
    fun getErrors() = error

    fun getData(jsonObject: SpendModel) {
        spendModel.value?.let {
            return
        }
        spendModel = spendRepo.makeTransaction(jsonObject)
    }

    /**
     * when viewmodel task has been done then it clear the rx component from memory
     */
    override fun onCleared() {
        super.onCleared()
        Log.d("DISPOSE", "----- disposed -------")
        spendRepo.getCompositeDisposable().clear()
        spendRepo.getCompositeDisposable().dispose()
    }
}