package com.example.epaylater.model

import com.google.gson.annotations.SerializedName

/**
 * Login api model class
 */
sealed class LoginResponse
data class TokenModel(@SerializedName("token") val token:String) : LoginResponse()
data class Error(val msg: String) : LoginResponse()
