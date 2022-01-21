package com.example.correct_warehouse_app.model

data class NewReservation(

    val employeeid: Int,
    val resdate: String,
    val productid: Int,
    val amount: Int
)