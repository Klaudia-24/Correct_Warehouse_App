package com.example.correct_warehouse_app.model

data class EmployeeLoginDataRequest(

    val employeeid: Int,
    val login: String,
    val password: String,
    val email: String,
    val rolename: String
)