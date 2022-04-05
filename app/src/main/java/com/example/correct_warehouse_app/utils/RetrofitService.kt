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
    fun getLowStockProducts(@Header ("userKey") userKey: String, ): Call<List<Product>>

//    @POST("products")//
//    fun addNewProduct(@Body product: Product): Call<String>

    @POST("products")
    fun addNewProduct(
        @Header ("userKey") userKey: String,
        @Body product: Product
    ): Call<String>

    @PUT("products")//
    fun modifyProduct(
        @Header ("userKey") userKey: String,
        @Body product: Product
        ): Call<String>

    @DELETE("products/{id}")//
    fun deleteProduct(@Header ("userKey") userKey: String, @Path("id") id: Int): Call<String>

    // RESERVATIONS
    @GET("reservations")//
    fun getAllReservations(@Header ("userKey") userKey: String): Call<List<Reservation>>

    @POST("reservations")//
    fun addNewReservation(@Header ("userKey") userKey: String, @Body newReservation: NewReservation): Call<String>

    @PUT("reservations")
    fun modifyReservation(@Header ("userKey") userKey: String, @Body reservation: Reservation): Call<String>

    @DELETE("reservations/{id}")
    fun deleteReservation(@Header ("userKey") userKey: String, @Path("id") id: Int): Call<String>

    // EMPLOYEES
    @GET("employees")//
    fun getAllEmployees(@Header ("userKey") userKey: String): Call<List<Employee>>

    @POST("employees")//
    fun addNewEmployee(@Header ("userKey") userKey: String, @Body employee: Employee): Call<String>

    @PUT("employees")//
    fun modifyEmployee(@Header ("userKey") userKey: String, @Body employee: Employee): Call<String>

    @DELETE("employees/{id}")
    fun deleteEmployee(@Header ("userKey") userKey: String, @Path("id") id: Int): Call<String>

    // EMPLOYEES ADMIN
    @GET("admin/employees")//
    fun getAllEmployeesAdmin(@Header ("userKey") userKey: String): Call<List<EmployeeAdminData>>

    @GET("admin/employees/new")//
    fun getAllNewEmployeesAdmin(@Header ("userKey") userKey: String): Call<List<Employee>>

    @GET("admin/employees/blocked")//
    fun getAllBlockedEmployeesAdmin(@Header ("userKey") userKey: String): Call<List<Employee>>

    @POST("admin/employees")
    fun addNewEmployeeAdmin(@Header ("userKey") userKey: String, @Body employeeAdminData: EmployeeAdminData): Call<String>

    @POST("admin/employees/login")
    fun addEmployeeAdminLogin(@Header ("userKey") userKey: String, @Body employeeLoginDataRequest: EmployeeLoginDataRequest): Call<String>

    @PUT("admin/employees")
    fun modifyEmployeeAdmin(@Header ("userKey") userKey: String, @Body employeeAdminData: EmployeeAdminData): Call<String>

    @PUT("admin/employees/login")
    fun modifyEmployeeAdminLogin(@Header ("userKey") userKey: String, @Body employeeLoginDataRequest: EmployeeLoginDataRequest): Call<String>

    @DELETE("admin/employees/{id}")
    fun deleteEmployeeAdmin(@Header ("userKey") userKey: String, @Path("id") id: Int): Call<String>

    @DELETE("admin/employees/unblock/{id}")
    fun unblockEmployeeAdmin(@Header ("userKey") userKey: String, @Path("id") id: Int): Call<String>

    // ROLE
    @GET("roles")
    fun getAllRoles(@Header ("userKey") userKey: String): Call<List<Role>>
}