package com.example.correct_warehouse_app.model

import java.io.Serializable

data class CurrentUser(
    val employeeid: Int,
    val name: String,
    val surname: String,
    val salary: Float,
    val address: String,
    val login: String,
    val email: String,
    val roleid: String,
    val rolename: String,
    val userkey: String
) : Serializable