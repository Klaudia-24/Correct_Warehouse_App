package com.example.correct_warehouse_app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.correct_warehouse_app.model.*
import com.example.correct_warehouse_app.utils.RetrofitInstance
import com.example.correct_warehouse_app.utils.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisplayDataViewModel: ViewModel() {

    private var productList = MutableLiveData<List<Product>>()
    private var lowStockProductList = MutableLiveData<List<Product>>()
    private var reservationList = MutableLiveData<List<Reservation>>()
    private var employeeList = MutableLiveData<List<Employee>>()
    private var employeeAdminList = MutableLiveData<List<EmployeeAdminData>>()
    private var newEmployeeList = MutableLiveData<List<Employee>>()
    private var blockedEmployeeList = MutableLiveData<List<Employee>>()
    var roleList = MutableLiveData<List<Role>>()

    val retrofitService = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)

    fun getProductList(): MutableLiveData<List<Product>> {
        return productList
    }

    fun getLowStockProductList(): MutableLiveData<List<Product>> {
        return lowStockProductList
    }

    fun getReservationList(): MutableLiveData<List<Reservation>> {
        return reservationList
    }

    fun getEmployeeList(): MutableLiveData<List<Employee>> {
        return employeeList
    }

    fun getEmployeeAdminList(): MutableLiveData<List<EmployeeAdminData>> {
        return employeeAdminList
    }

    fun getNewEmployeeList(): MutableLiveData<List<Employee>> {
        return newEmployeeList
    }

    fun getBlockedEmployeeList(): MutableLiveData<List<Employee>> {
        return blockedEmployeeList
    }

    fun getProductsData(userKey: String, onResult: (Boolean)->Unit){

        retrofitService.getAllProducts(userKey).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>){

                productList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun getLowStockProductsData(userKey: String, onResult: (Boolean)->Unit){

        retrofitService.getLowStockProducts(userKey).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>){

                lowStockProductList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun addNewProductData(userKey: String, newProduct: Product, onResult: (Boolean)->Unit){

        retrofitService.addNewProduct(userKey, newProduct).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyProduct(userKey: String, product: Product, onResult: (Boolean)->Unit){

        retrofitService.modifyProduct(userKey, product).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun deleteProduct(userKey: String, id: Int, onResult: (Boolean)->Unit){

        retrofitService.deleteProduct(userKey, id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    // RESERVATIONS

    fun getReservationsData(userKey: String, onResult: (Boolean)->Unit){

        retrofitService.getAllReservations(userKey).enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>){

                reservationList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                onResult(false)
            }
        })
    }

    fun addNewReservationData(userKey: String, newReservation: NewReservation, onResult: (Boolean)->Unit){

        retrofitService.addNewReservation(userKey, newReservation).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun deleteReservation(userKey: String, id: Int, onResult: (Boolean)->Unit){

        retrofitService.deleteReservation(userKey, id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    // EMPLOYEE

    fun getEmployeeData(userKey: String, onResult: (Boolean)->Unit){

        retrofitService.getAllEmployees(userKey).enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>){

                employeeList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun addNewEmployeeData(userKey: String, newEmployee: Employee, onResult: (Boolean)->Unit){

        retrofitService.addNewEmployee(userKey, newEmployee).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyEmployee(userKey: String, employee: Employee, onResult: (Boolean)->Unit){

        retrofitService.modifyEmployee(userKey, employee).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun deleteEmployee(userKey: String, id: Int, onResult: (Boolean)->Unit){

        retrofitService.deleteEmployee(userKey, id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun getEmployeeDataAdmin(userKey: String, onResult: (Boolean)->Unit){

        retrofitService.getAllEmployeesAdmin(userKey).enqueue(object : Callback<List<EmployeeAdminData>> {
            override fun onResponse(call: Call<List<EmployeeAdminData>>, response: Response<List<EmployeeAdminData>>){

                employeeAdminList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<EmployeeAdminData>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun getNewEmployeeDataAdmin(userKey: String, onResult: (Boolean)->Unit){

        retrofitService.getAllNewEmployeesAdmin(userKey).enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>){

                newEmployeeList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun getBlockedEmployeeDataAdmin(userKey: String, onResult: (Boolean)->Unit){

        retrofitService.getAllBlockedEmployeesAdmin(userKey).enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>){

                blockedEmployeeList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyEmployeeAdmin(userKey: String, employee: EmployeeAdminData, onResult: (Boolean)->Unit){

        retrofitService.modifyEmployeeAdmin(userKey, employee).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun addNewEmployeeAdminLogin(userKey: String, employeeLoginDataRequest: EmployeeLoginDataRequest, onResult: (Boolean)->Unit){

        retrofitService.addEmployeeAdminLogin(userKey, employeeLoginDataRequest).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyEmployeeAdminLogin(userKey: String, employeeLoginDataRequest: EmployeeLoginDataRequest, onResult: (Boolean)->Unit){

        retrofitService.modifyEmployeeAdminLogin(userKey, employeeLoginDataRequest).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun deleteEmployeeAdmin(userKey: String, id: Int, onResult: (Boolean)->Unit){

        retrofitService.deleteEmployeeAdmin(userKey, id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun unblockEmployeeAdmin(userKey: String, id: Int, onResult: (Boolean)->Unit){

        retrofitService.unblockEmployeeAdmin(userKey, id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }
}