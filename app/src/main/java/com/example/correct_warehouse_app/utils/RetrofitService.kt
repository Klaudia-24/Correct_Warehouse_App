package com.example.correct_warehouse_app.utils

import com.example.correct_warehouse_app.model.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("signIn")//
    fun isSignInSuccessful(@Body signInRequest: SignInRequest): Call<String>

    @POST("currentUser")//
    fun getCurrentUser(@Body login: String): Call<CurrentUser>

    @POST("verifyUser")//
    fun verifyCurrentUser(@Body userKey: String): Call<String>

    // PRODUCTS
    @POST("products/data")//
    fun getAllProducts(@Body userKey: String): Call<List<Product>>

    @GET("products/lowStock")//
    fun getLowStockProducts(): Call<List<Product>>

    @POST("products")//
    fun addNewProduct(@Body product: Product): Call<String>

    @PUT("products")//
    fun modifyProduct(@Body product: Product): Call<String>

    @DELETE("products/{id}")//
    fun deleteProduct(@Path("id") id: Int): Call<String>

    // RESERVATIONS
    @GET("reservations")//
    fun getAllReservations(): Call<List<Reservation>>

    @POST("reservations")//
    fun addNewReservation(@Body newReservation: NewReservation): Call<String>

    @PUT("reservations")
    fun modifyReservation(@Body reservation: Reservation): Call<String>

    @DELETE("reservations/{id}")
    fun deleteReservation(@Path("id") id: Int): Call<String>

    // EMPLOYEES
    @GET("employees")//
    fun getAllEmployees(): Call<List<Employee>>

    @POST("employees")//
    fun addNewEmployee(@Body employee: Employee): Call<String>

    @PUT("employees")//
    fun modifyEmployee(@Body employee: Employee): Call<String>

    @DELETE("employees/{id}")
    fun deleteEmployee(@Path("id") id: Int): Call<String>

    // EMPLOYEES ADMIN
    @GET("admin/employees")//
    fun getAllEmployeesAdmin(): Call<List<EmployeeAdminData>>

    @GET("admin/employees/new")//
    fun getAllNewEmployeesAdmin(): Call<List<Employee>>

    @GET("admin/employees/blocked")//
    fun getAllBlockedEmployeesAdmin(): Call<List<Employee>>

    @POST("admin/employees")
    fun addNewEmployeeAdmin(@Body employeeAdminData: EmployeeAdminData): Call<String>

    @POST("admin/employees/login")
    fun addEmployeeAdminLogin(@Body employeeLoginDataRequest: EmployeeLoginDataRequest): Call<String>

    @PUT("admin/employees")
    fun modifyEmployeeAdmin(@Body employeeAdminData: EmployeeAdminData): Call<String>

    @PUT("admin/employees/login")
    fun modifyEmployeeAdminLogin(@Body employeeLoginDataRequest: EmployeeLoginDataRequest): Call<String>

    @DELETE("admin/employees/{id}")
    fun deleteEmployeeAdmin(@Path("id") id: Int): Call<String>

    @DELETE("admin/employees/unblock/{id}")
    fun unblockEmployeeAdmin(@Path("id") id: Int): Call<String>

    // ROLE
    @GET("roles")
    fun getAllRoles(): Call<List<Role>>
}