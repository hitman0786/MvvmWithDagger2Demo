package com.example.epaylater.model

class SpendResponse {

}

data class SpendModel(
  val date: String,
  val description: String,
  val amount: String,
  val currency: String
)