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

    fun getLowStockProductsData(onResult: (Boolean)->Unit){

        retrofitService.getLowStockProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>){

                lowStockProductList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun addNewProductData(newProduct: Product, onResult: (Boolean)->Unit){

        retrofitService.addNewProduct(newProduct).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyProduct(product: Product, onResult: (Boolean)->Unit){

        retrofitService.modifyProduct(product).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun deleteProduct(id: Int, onResult: (Boolean)->Unit){

        retrofitService.deleteProduct(id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    // RESERVATIONS

    fun getReservationsData(onResult: (Boolean)->Unit){

        retrofitService.getAllReservations().enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>){

                reservationList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                onResult(false)
            }
        })
    }

    fun addNewReservationData(newReservation: NewReservation, onResult: (Boolean)->Unit){

        retrofitService.addNewReservation(newReservation).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyReservation(reservation: Reservation, onResult: (Boolean)->Unit){

        retrofitService.modifyReservation(reservation).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun deleteReservation(id: Int, onResult: (Boolean)->Unit){

        retrofitService.deleteReservation(id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    // EMPLOYEE

    fun getEmployeeData(onResult: (Boolean)->Unit){

        retrofitService.getAllEmployees().enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>){

                employeeList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun addNewEmployeeData(newEmployee: Employee, onResult: (Boolean)->Unit){

        retrofitService.addNewEmployee(newEmployee).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyEmployee(employee: Employee, onResult: (Boolean)->Unit){

        retrofitService.modifyEmployee(employee).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun deleteEmployee(id: Int, onResult: (Boolean)->Unit){

        retrofitService.deleteEmployee(id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun getEmployeeDataAdmin(onResult: (Boolean)->Unit){

        retrofitService.getAllEmployeesAdmin().enqueue(object : Callback<List<EmployeeAdminData>> {
            override fun onResponse(call: Call<List<EmployeeAdminData>>, response: Response<List<EmployeeAdminData>>){

                employeeAdminList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<EmployeeAdminData>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun getNewEmployeeDataAdmin(onResult: (Boolean)->Unit){

        retrofitService.getAllNewEmployeesAdmin().enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>){

                newEmployeeList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun getBlockedEmployeeDataAdmin(onResult: (Boolean)->Unit){

        retrofitService.getAllBlockedEmployeesAdmin().enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>){

                blockedEmployeeList.value = response.body()
                onResult(true)
            }
            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun addNewEmployeeDataAdmin(newEmployeeAdmin: EmployeeAdminData, onResult: (Boolean)->Unit){

        retrofitService.addNewEmployeeAdmin(newEmployeeAdmin).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyEmployeeAdmin(employee: EmployeeAdminData, onResult: (Boolean)->Unit){

        retrofitService.modifyEmployeeAdmin(employee).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun addNewEmployeeAdminLogin(employeeLoginDataRequest: EmployeeLoginDataRequest, onResult: (Boolean)->Unit){

        retrofitService.addEmployeeAdminLogin(employeeLoginDataRequest).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun modifyEmployeeAdminLogin(employeeLoginDataRequest: EmployeeLoginDataRequest, onResult: (Boolean)->Unit){

        retrofitService.modifyEmployeeAdminLogin(employeeLoginDataRequest).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun deleteEmployeeAdmin(id: Int, onResult: (Boolean)->Unit){

        retrofitService.deleteEmployeeAdmin(id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }

    fun unblockEmployeeAdmin(id: Int, onResult: (Boolean)->Unit){

        retrofitService.unblockEmployeeAdmin(id).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>){

                onResult(response.body().toBoolean())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {

                onResult(false)
            }
        })
    }
}