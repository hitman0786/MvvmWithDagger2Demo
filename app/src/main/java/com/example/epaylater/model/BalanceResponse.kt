package com.example.epaylater.model

import com.google.gson.annotations.SerializedName

/**
 * Current balance model class
 */

sealed class BalanceResponse
data class BalanceModel(@SerializedName("balance") val balance:String,
                        @SerializedName("currency") val currency:String) : BalanceResponse()
data class BalanceError(val msg: String) : BalanceResponse()