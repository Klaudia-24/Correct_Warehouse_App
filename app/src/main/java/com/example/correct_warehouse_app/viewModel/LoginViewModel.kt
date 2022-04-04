package com.example.correct_warehouse_app.viewModel

import androidx.lifecycle.ViewModel
import com.example.correct_warehouse_app.model.*
import com.example.correct_warehouse_app.utils.HashString
import com.example.correct_warehouse_app.utils.RetrofitInstance
import com.example.correct_warehouse_app.utils.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {

    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)

    fun signInUser(login: String, password: String, onResult: (Boolean)->Unit){

        val hashedPassword = HashString.hash(password)
        val signInRequest = SignInRequest(login, hashedPassword)

        retrofitService.isSignInSuccessful(signInRequest).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun getCurrentUser(login: String, onResult: (CurrentUser?)->Unit){

        retrofitService.getCurrentUser(login).enqueue(object : Callback<CurrentUser?> {
            override fun onResponse(call: Call<CurrentUser?>, response: Response<CurrentUser?>) {

                onResult(response.body())
            }
            override fun onFailure(call: Call<CurrentUser?>, t: Throwable) {

                onResult(null)
            }
        })
    }

}