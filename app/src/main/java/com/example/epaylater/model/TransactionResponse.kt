package com.example.epaylater.model

/**
 * Transaction model class
 */
data class TransactionResponse(val id:String,
                            val date:String,
                            val description:String,
                            val amount:String,
                            val currency:String)