package com.example.correct_warehouse_app.model

data class Reservation(
    val reservationid: Int,
    val employeeid: Int,
    val employeename: String,
    val employeesurname: String,
    val resdate: String,
    val name: String,
    val sizeofproduct: Int,
    val price: Float,
    val amount: Int
)