package com.example.epaylater.model

import com.google.gson.annotations.SerializedName

/**
 * Current balance model class
 */

data class BalanceResponse(@SerializedName("balance") val balance:String,
                        @SerializedName("currency") val currency:String){


    override fun equals(obj: Any?): Boolean {
        return balance == (obj as BalanceResponse).balance
    }
}