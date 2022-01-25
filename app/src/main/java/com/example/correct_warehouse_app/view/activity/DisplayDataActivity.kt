package com.example.correct_warehouse_app.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.correct_warehouse_app.R
import com.example.correct_warehouse_app.model.*
import com.example.correct_warehouse_app.utils.HashString
import com.example.correct_warehouse_app.viewModel.DisplayDataViewModel
import kotlinx.android.synthetic.main.activity_display_data.*
import kotlinx.android.synthetic.main.activity_display_data.view.*
import kotlinx.android.synthetic.main.employee_admin_delete_dialog.view.*
import kotlinx.android.synthetic.main.employee_admin_dialog.view.*
import kotlinx.android.synthetic.main.employee_admin_login_dialog.view.*
import kotlinx.android.synthetic.main.employee_admin_login_dialog.view.dialogEmpAdminLoginWindowTitle
import kotlinx.android.synthetic.main.employee_admin_password_dialog.view.*
import kotlinx.android.synthetic.main.employee_delete_dialog.view.*
import kotlinx.android.synthetic.main.employee_delete_dialog.view.dialogEmpDeleteWindowTitle
import kotlinx.android.synthetic.main.employee_dialog.view.*
import kotlinx.android.synthetic.main.employee_unblock_dialog.view.*
import kotlinx.android.synthetic.main.new_reservation_dialog.view.*
import kotlinx.android.synthetic.main.product_delete_dialog.view.*
import kotlinx.android.synthetic.main.product_delete_dialog.view.confirmButton
import kotlinx.android.synthetic.main.product_dialog.view.*
import kotlinx.android.synthetic.main.product_dialog.view.cancelButton
import kotlinx.android.synthetic.main.product_dialog.view.dialogProdNameET
import kotlinx.android.synthetic.main.product_dialog.view.dialogWindowTitle
import kotlinx.android.synthetic.main.product_dialog.view.saveButton
import kotlinx.android.synthetic.main.product_list.view.*
import kotlinx.android.synthetic.main.reservation_delete_dialog.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class DisplayDataActivity : AppCompatActivity() {

    private lateinit var displayDataViewModel: DisplayDataViewModel
    private var selectedItem: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)

        val currUser = intent.getSerializableExtra("EXTRA_CURRENT_USER") as CurrentUser
        val listType = intent.getSerializableExtra("EXTRA_LIST_TYPE") as String

        dataTitle.setText(listType)

        backToNavButton.setOnClickListener {
            Intent(this, NavigationActivity::class.java).also {
                it.putExtra("EXTRA_CURRENT_USER", currUser)
                startActivity(it)
            }
        }

        val layoutParamsReserveButton: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        val layoutParamsAddButton: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        val layoutParamsModifyButton: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        val layoutParamsDeleteButton: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        val layoutParamsSetPasswordButton: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)

        when(listType){

            "Products" -> {
                passwordSetButton.isClickable = false
                passwordSetButton.isEnabled = false
                passwordSetButton.isVisible = false

                when (currUser.roleid) {
                    "admin" -> {

                        layoutParamsReserveButton.setMargins(550, 5, 50, 10)
                        layoutParamsAddButton.setMargins(350, 5, 50, 10)
                        layoutParamsModifyButton.setMargins(600, 30, 50, 10)
                        layoutParamsDeleteButton.setMargins(950, 30, 50, 10)
                    }
                    "warMan" -> {

                        reserveButton.isClickable = false
                        reserveButton.isEnabled = false
                        reserveButton.isVisible = false

                        addButton.isClickable = false
                        addButton.isEnabled = false
                        addButton.isVisible = false

                        deleteButton.isClickable = false
                        deleteButton.isEnabled = false
                        deleteButton.isVisible = false

                        layoutParamsModifyButton.setMargins(450, 30, 50, 10)
                        modifyButton.layoutParams = layoutParamsModifyButton
                    }
                    "salRep" -> {

                        modifyButton.isClickable = false
                        modifyButton.isEnabled = false
                        modifyButton.isVisible = false

                        addButton.isClickable = false
                        addButton.isEnabled = false
                        addButton.isVisible = false

                        deleteButton.isClickable = false
                        deleteButton.isEnabled = false
                        deleteButton.isVisible = false

                        layoutParamsReserveButton.setMargins(600, 30, 50, 10)
                        reserveButton.layoutParams = layoutParamsModifyButton

                    }
                    "acc" -> {

                        reserveButton.isClickable = false
                        reserveButton.isEnabled = false
                        reserveButton.isVisible = false

                        layoutParamsAddButton.setMargins(250, 5, 50, 10)
                        layoutParamsModifyButton.setMargins(600, 30, 50, 10)
                        layoutParamsDeleteButton.setMargins(950, 30, 50, 10)

                        addButton.layoutParams = layoutParamsAddButton
                        modifyButton.layoutParams = layoutParamsModifyButton
                        deleteButton.layoutParams = layoutParamsDeleteButton

                    }
                    else -> null
                }

                initRetrofitInstanceProducts(currUser.userkey)

                reserveButton.setOnClickListener {

                    if(selectedItem==null){
                        Toast.makeText(this@DisplayDataActivity, "No product selected", Toast.LENGTH_SHORT).show()
                    }else {

                        val reserveDialogWindow =
                            LayoutInflater.from(this).inflate(R.layout.new_reservation_dialog, null)
                        val mBuilder = AlertDialog.Builder(this).setView(reserveDialogWindow)
                        reserveDialogWindow.dialogNewResWindowTitle.text = "Reserve product"
                        val mAlertDialog = mBuilder.show()

                        var productsList: List<Product>? = null
                        displayDataViewModel.getProductList().observe(this, {
                            productsList = it
                        })

                        var selectedProductId: Int? = null

                        selectedItem?.let { it1 ->
                            reserveDialogWindow.dialogNewResProdNameET.setText(productsList?.get(it1)?.name.toString())
                            reserveDialogWindow.dialogNewResAmountET.setText(productsList?.get(it1)?.amount.toString())

                            selectedProductId = productsList?.get(it1)?.productid
                        }

                        val sdf = SimpleDateFormat("yyyy-MM-dd")
                        val currentDate = sdf.format(Date())
                        reserveDialogWindow.dialogNewResDateET.setText(currentDate.toString())

                        reserveDialogWindow.confirmButton.setOnClickListener {

                            var newReservation: NewReservation? = null

                            try {
                                newReservation = selectedProductId?.let { it1 ->
                                    NewReservation(
                                        currUser.employeeid,
                                        reserveDialogWindow.dialogNewResDateET.text.toString(),
                                        it1,
                                        reserveDialogWindow.dialogNewResAmountET.text.toString()
                                            .toInt()
                                    )
                                }
                            }catch(e: Exception){
                                Toast.makeText(this@DisplayDataActivity, "One field is empty", Toast.LENGTH_LONG).show()
                            }

                            if (newReservation != null) {
                                displayDataViewModel.addNewReservationData(newReservation){

                                    if(it){
                                        initRetrofitInstanceProducts(currUser.userkey)
                                    }
                                }
                                mAlertDialog.dismiss()
                            }
                        }

                        reserveDialogWindow.cancelButton.setOnClickListener {

                            mAlertDialog.dismiss()
                        }
                    }
                }

                addButton.setOnClickListener {

                    val addProductDialogWindow = LayoutInflater.from(this).inflate(R.layout.product_dialog, null)
                    val mBuilder = AlertDialog.Builder(this).setView(addProductDialogWindow)
                    addProductDialogWindow.dialogWindowTitle.text = "New product"
                    val mAlertDialog = mBuilder.show()

                    addProductDialogWindow.saveButton.setOnClickListener{

                        var newProduct: Product? = null
                        try {
                            newProduct = Product(0,
                                addProductDialogWindow.dialogProdNameET.text.toString(),
                                addProductDialogWindow.dialogProdSizeET.text.toString().toInt(),
                                addProductDialogWindow.dialogProdAmountET.text.toString().toInt(),
                                addProductDialogWindow.dialogProdPriceET.text.toString().toFloat())
                        }catch(e: Exception){
                            Toast.makeText(this@DisplayDataActivity, "Some of the fields are empty", Toast.LENGTH_LONG).show()
                        }


                        if(newProduct!=null) {
                            displayDataViewModel.addNewProductData(newProduct) {

                                if (it) {
                                    initRetrofitInstanceProducts(currUser.userkey)
                                }
                            }

                            mAlertDialog.dismiss()
                        }
                        else{
                            Log.d("TEST","new product")
                        }
                    }

                    addProductDialogWindow.cancelButton.setOnClickListener{

                        mAlertDialog.dismiss()
                    }
                }

                modifyButton.setOnClickListener {

                    if(selectedItem==null){
                        Toast.makeText(this@DisplayDataActivity, "No product selected", Toast.LENGTH_SHORT).show()
                    }else {

                        val modifyProductDialogWindow =
                            LayoutInflater.from(this).inflate(R.layout.product_dialog, null)
                        val mBuilder = AlertDialog.Builder(this).setView(modifyProductDialogWindow)
                        modifyProductDialogWindow.dialogWindowTitle.text = "Edit product"
                        val mAlertDialog = mBuilder.show()

                        var productsList: List<Product>? = null
                        displayDataViewModel.getProductList().observe(this, {
                            productsList = it
                        })

                        var selectedProductId: Int? = null

                        selectedItem?.let { it1 ->
                            modifyProductDialogWindow.dialogProdNameET.setText(productsList?.get(it1)?.name.toString())
                            modifyProductDialogWindow.dialogProdSizeET.setText(productsList?.get(it1)?.sizeofproduct.toString())
                            modifyProductDialogWindow.dialogProdAmountET.setText(productsList?.get(it1)?.amount.toString())
                            modifyProductDialogWindow.dialogProdPriceET.setText(productsList?.get(it1)?.price.toString())

                            selectedProductId = productsList?.get(it1)?.productid
                        }

                        modifyProductDialogWindow.saveButton.setOnClickListener {

                            var product: Product? = null

                            try {
                                product = selectedProductId?.let { it1 ->
                                    Product(
                                        it1,
                                        modifyProductDialogWindow.dialogProdNameET.text.toString(),
                                        modifyProductDialogWindow.dialogProdSizeET.text.toString().toInt(),
                                        modifyProductDialogWindow.dialogProdAmountET.text.toString().toInt(),
                                        modifyProductDialogWindow.dialogProdPriceET.text.toString().toFloat()
                                    )
                                }
                            }catch(e: Exception){
                                Toast.makeText(this@DisplayDataActivity, "Some of the fields are empty", Toast.LENGTH_LONG).show()
                            }

                            if (product != null) {
                                displayDataViewModel.modifyProduct(product){

                                    if(it){
                                        initRetrofitInstanceProducts(currUser.userkey)
                                    }
                                }
                                mAlertDialog.dismiss()
                            }
                        }

                        modifyProductDialogWindow.cancelButton.setOnClickListener {

                            mAlertDialog.dismiss()
                        }
                    }
                }

                deleteButton.setOnClickListener {

                    if(selectedItem==null){
                        Toast.makeText(this@DisplayDataActivity, "No product selected", Toast.LENGTH_SHORT).show()
                    }else {

                        val deleteProductDialogWindow = LayoutInflater.from(this).inflate(R.layout.product_delete_dialog, null)
                        val mBuilder = AlertDialog.Builder(this).setView(deleteProductDialogWindow)
                        deleteProductDialogWindow.dialogDeleteWindowTitle.text = "Delete product"
                        val mAlertDialog = mBuilder.show()

                        var productsList: List<Product>? = null
                        displayDataViewModel.getProductList().observe(this, {
                            productsList = it
                        })

                        var selectedProductId: Int? = null
                        var prodName: String? = null
                        var prodSize: String? = null
                        var prodAmount: String? = null
                        var prodPrice: String? = null

                        selectedItem?.let { it1 ->

                            prodName = productsList?.get(it1)?.name.toString()
                            prodSize = productsList?.get(it1)?.sizeofproduct.toString()
                            prodAmount = productsList?.get(it1)?.amount.toString()
                            prodPrice = productsList?.get(it1)?.price.toString()
                            selectedProductId = productsList?.get(it1)?.productid.toString().toInt()
                        }

                        deleteProductDialogWindow.dialogDeleteProdName.text = prodName
                        deleteProductDialogWindow.dialogDeleteProdSize.text = prodSize
                        deleteProductDialogWindow.dialogDeleteProdAmount.text = prodAmount
                        deleteProductDialogWindow.dialogDeleteProdPrice.text = prodPrice

                        deleteProductDialogWindow.confirmButton.setOnClickListener {

                            if(selectedProductId!=null) {
                                displayDataViewModel.deleteProduct(selectedProductId!!){
                                    if(it){
                                        initRetrofitInstanceProducts(currUser.userkey)
                                        selectedProductId = null
                                    }
                                }
                            }

                            mAlertDialog.dismiss()
                        }

                        deleteProductDialogWindow.cancelButton.setOnClickListener {

                            mAlertDialog.dismiss()
                        }
                    }
                }

                refreshButton.setOnClickListener {

                    dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_dark_8, null))
                    errorInfo.setText("")
                    initRetrofitInstanceProducts(currUser.userkey)
                }
            }
            "Employees" -> {

                when (currUser.roleid) {
                    "admin" -> {

                        reserveButton.isClickable = false
                        reserveButton.isEnabled = false
                        reserveButton.isVisible = false


                        layoutParamsSetPasswordButton.setMargins(250, 5, 50, 10)

                        initRetrofitInstanceEmployeesAdmin()

                        passwordSetButton.setOnClickListener {

                            if(selectedItem==null){
                                Toast.makeText(this@DisplayDataActivity, "No employee selected", Toast.LENGTH_SHORT).show()
                            }else {

                                val empPasswordAdminDialogWindow = LayoutInflater.from(this)
                                    .inflate(R.layout.employee_admin_password_dialog, null)
                                val mBuilder = AlertDialog.Builder(this).setView(empPasswordAdminDialogWindow)
                                empPasswordAdminDialogWindow.dialogEmpAdminPassWindowTitle.text = "Employee new password"
                                val mAlertDialog = mBuilder.show()

                                var employeesAdminList: List<EmployeeAdminData>? = null
                                displayDataViewModel.getEmployeeAdminList().observe(this, {
                                    employeesAdminList = it
                                })

                                var selectedEmployeeId: Int? = null
                                var selectedEmployeeLogin: String? = null
                                var selectedEmployeeEmail: String? = null
                                var selectedEmployeeRole: String? = null

                                selectedItem?.let { it1 ->

                                    selectedEmployeeId = employeesAdminList?.get(it1)?.employeeid
                                    selectedEmployeeLogin = employeesAdminList?.get(it1)?.login
                                    selectedEmployeeEmail = employeesAdminList?.get(it1)?.email
                                    selectedEmployeeRole = employeesAdminList?.get(it1)?.rolename
                                }

                                empPasswordAdminDialogWindow.confirmButton.setOnClickListener {

                                    var password1: String =
                                        empPasswordAdminDialogWindow.dialogEmpAdminLoginPassword1ET.text.toString()
                                    var password2: String =
                                        empPasswordAdminDialogWindow.dialogEmpAdminLoginPassword2ET.text.toString()

                                    if (password1.equals(password2, true)) {

                                        var employeeLoginDataRequest: EmployeeLoginDataRequest? =
                                            null

                                        if (!password1.isEmpty() and !password2.isEmpty()) {

                                            try {
                                                employeeLoginDataRequest =
                                                    selectedEmployeeId?.let { it1 ->
                                                        EmployeeLoginDataRequest(
                                                            it1,
                                                            "",
                                                            HashString.hash(
                                                                empPasswordAdminDialogWindow.dialogEmpAdminLoginPassword1ET.text.toString()
                                                            ),
                                                            "",
                                                            ""
                                                        )
                                                    }
                                            } catch (e: Exception) {
                                                Toast.makeText(
                                                    this@DisplayDataActivity,
                                                    "Some of the fields are empty",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }

                                            if (employeeLoginDataRequest != null) {
                                                displayDataViewModel.modifyEmployeeAdminLogin(
                                                    employeeLoginDataRequest
                                                ) {

                                                    if (it) {
                                                        initRetrofitInstanceEmployeesAdmin()
                                                    }
                                                }
                                                mAlertDialog.dismiss()
                                            }
                                        } else {
                                            Toast.makeText(
                                                this@DisplayDataActivity,
                                                "Some of the fields are empty",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }else{
                                        Toast.makeText(
                                            this@DisplayDataActivity,
                                            "Passwords are not equal",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                empPasswordAdminDialogWindow.cancelButton.setOnClickListener {

                                    mAlertDialog.dismiss()
                                }
                            }
                        }

                        addButton.setOnClickListener {

                            val addEmployeeDialogWindow = LayoutInflater.from(this).inflate(R.layout.employee_dialog, null)
                            val mBuilder = AlertDialog.Builder(this).setView(addEmployeeDialogWindow)
                            addEmployeeDialogWindow.dialogWindowTitle.text = "New employee"
                            val mAlertDialog = mBuilder.show()

                            addEmployeeDialogWindow.saveButton.setOnClickListener{

                                var newEmployee: Employee? = null

                                try {

                                    newEmployee = Employee(0,
                                        addEmployeeDialogWindow.dialogEmpNameET.text.toString(),
                                        addEmployeeDialogWindow.dialogEmpSurnameET.text.toString(),
                                        addEmployeeDialogWindow.dialogEmpSalaryET.text.toString().toFloat(),
                                        addEmployeeDialogWindow.dialogEmpAddressET.text.toString())

                                }catch(e: Exception){
                                    Toast.makeText(this@DisplayDataActivity, "Some of the fields are empty", Toast.LENGTH_LONG).show()
                                }

                                if(newEmployee != null) {

                                    displayDataViewModel.addNewEmployeeData(newEmployee) {

                                        if (it) {
                                            initRetrofitInstanceEmployeesAdmin()
                                        }
                                    }
                                    mAlertDialog.dismiss()
                                }
                            }

                            addEmployeeDialogWindow.cancelButton.setOnClickListener{

                                mAlertDialog.dismiss()
                            }
                        }

                        modifyButton.setOnClickListener {

                            if(selectedItem==null){
                                Toast.makeText(this@DisplayDataActivity, "No employee selected", Toast.LENGTH_SHORT).show()
                            }else {

                                val modifyEmployeeAdminDialogWindow =
                                    LayoutInflater.from(this).inflate(R.layout.employee_admin_dialog, null)
                                val mBuilder = AlertDialog.Builder(this).setView(modifyEmployeeAdminDialogWindow)
                                modifyEmployeeAdminDialogWindow.dialogEmpAdminWindowTitle.text = "Edit employee"
                                val mAlertDialog = mBuilder.show()

                                var employeesAdminList: List<EmployeeAdminData>? = null
                                displayDataViewModel.getEmployeeAdminList().observe(this, {
                                    employeesAdminList = it
                                })

                                var selectedEmployeeId: Int? = null

                                selectedItem?.let { it1 ->
                                    modifyEmployeeAdminDialogWindow.dialogEmpAdminNameET.setText(employeesAdminList?.get(it1)?.name.toString())
                                    modifyEmployeeAdminDialogWindow.dialogEmpAdminSurnameET.setText(employeesAdminList?.get(it1)?.surname.toString())
                                    modifyEmployeeAdminDialogWindow.dialogEmpAdminSalaryET.setText(employeesAdminList?.get(it1)?.salary.toString())
                                    modifyEmployeeAdminDialogWindow.dialogEmpAdminAddressET.setText(employeesAdminList?.get(it1)?.address.toString())
                                    modifyEmployeeAdminDialogWindow.dialogEmpAdminLoginET.setText(employeesAdminList?.get(it1)?.login.toString())
                                    modifyEmployeeAdminDialogWindow.dialogEmpAdminEmailET.setText(employeesAdminList?.get(it1)?.email.toString())
                                    modifyEmployeeAdminDialogWindow.dialogEmpAdminRoleET.setText(employeesAdminList?.get(it1)?.rolename.toString())

                                    selectedEmployeeId = employeesAdminList?.get(it1)?.employeeid
                                }

                                modifyEmployeeAdminDialogWindow.saveButton.setOnClickListener {

                                    var employee: EmployeeAdminData? = null

                                    try {
                                        employee = selectedEmployeeId?.let { it1 ->
                                            EmployeeAdminData(
                                                it1,
                                                modifyEmployeeAdminDialogWindow.dialogEmpAdminNameET.text.toString(),
                                                modifyEmployeeAdminDialogWindow.dialogEmpAdminSurnameET.text.toString(),
                                                modifyEmployeeAdminDialogWindow.dialogEmpAdminSalaryET.text.toString().toFloat(),
                                                modifyEmployeeAdminDialogWindow.dialogEmpAdminAddressET.text.toString(),
                                                modifyEmployeeAdminDialogWindow.dialogEmpAdminLoginET.text.toString(),
                                                modifyEmployeeAdminDialogWindow.dialogEmpAdminEmailET.text.toString(),
                                                modifyEmployeeAdminDialogWindow.dialogEmpAdminRoleET.text.toString()
                                            )
                                        }
                                    }catch(e: Exception){
                                        Toast.makeText(this@DisplayDataActivity, "Some of the fields are empty", Toast.LENGTH_LONG).show()
                                    }

                                    if (employee != null) {
                                        displayDataViewModel.modifyEmployeeAdmin(employee){

                                            if(it){
                                                initRetrofitInstanceEmployeesAdmin()
                                            }
                                        }
                                        mAlertDialog.dismiss()
                                    }
                                }

                                modifyEmployeeAdminDialogWindow.cancelButton.setOnClickListener {

                                    mAlertDialog.dismiss()
                                }
                            }
                        }

                        deleteButton.setOnClickListener {

                            if(selectedItem==null){
                                Toast.makeText(this@DisplayDataActivity, "No employee selected", Toast.LENGTH_SHORT).show()
                            }else {

                                val deleteEmployeeAdminDialogWindow = LayoutInflater.from(this).inflate(R.layout.employee_admin_delete_dialog, null)
                                val mBuilder = AlertDialog.Builder(this).setView(deleteEmployeeAdminDialogWindow)
                                deleteEmployeeAdminDialogWindow.dialogDeleteEmpAdminWindowTitle.text = "Delete employee"
                                val mAlertDialog = mBuilder.show()

                                var employeesAdminList: List<EmployeeAdminData>? = null
                                displayDataViewModel.getEmployeeAdminList().observe(this, {
                                    employeesAdminList = it
                                })

                                var selectedEmployeeId: Int? = null
                                var empName: String? = null
                                var empSurname: String? = null
                                var empSalary: Float? = null
                                var empAddress: String? = null
                                var empLogin: String? = null
                                var empEmail: String? = null
                                var empRole: String? = null

                                selectedItem?.let { it1 ->

                                    empName = employeesAdminList?.get(it1)?.name.toString()
                                    empSurname = employeesAdminList?.get(it1)?.surname.toString()
                                    empSalary = employeesAdminList?.get(it1)?.salary.toString().toFloat()
                                    empAddress = employeesAdminList?.get(it1)?.address.toString()
                                    empLogin = employeesAdminList?.get(it1)?.login.toString()
                                    empEmail = employeesAdminList?.get(it1)?.email.toString()
                                    empRole = employeesAdminList?.get(it1)?.rolename.toString()
                                    selectedEmployeeId = employeesAdminList?.get(it1)?.employeeid.toString().toInt()
                                }

                                deleteEmployeeAdminDialogWindow.dialogDeleteEmpAdminName.text = empName
                                deleteEmployeeAdminDialogWindow.dialogDeleteEmpAdminSurname.text = empSurname
                                deleteEmployeeAdminDialogWindow.dialogDeleteEmpAdminSalary.text = empSalary.toString()
                                deleteEmployeeAdminDialogWindow.dialogDeleteEmpAdminAddress.text = empAddress
                                deleteEmployeeAdminDialogWindow.dialogDeleteEmpAdminLogin.text = empLogin
                                deleteEmployeeAdminDialogWindow.dialogDeleteEmpAdminEmail.text = empEmail
                                deleteEmployeeAdminDialogWindow.dialogDeleteEmpAdminRole.text = empRole


                                deleteEmployeeAdminDialogWindow.confirmButton.setOnClickListener {

                                    if(selectedEmployeeId!=null) {
                                        displayDataViewModel.deleteEmployeeAdmin(selectedEmployeeId!!){
                                            if(it){
                                                initRetrofitInstanceEmployeesAdmin()
                                                selectedEmployeeId = null
                                            }
                                        }
                                    }

                                    mAlertDialog.dismiss()
                                }

                                deleteEmployeeAdminDialogWindow.cancelButton.setOnClickListener {

                                    mAlertDialog.dismiss()
                                }
                            }
                        }

                        refreshButton.setOnClickListener {

                            dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_dark_8, null))
                            errorInfo.setText("")
                            initRetrofitInstanceEmployeesAdmin()
                        }

                    }
                    "acc" -> {

                        layoutParamsAddButton.setMargins(250, 5, 50, 10)
                        layoutParamsModifyButton.setMargins(600, 30, 50, 10)
                        layoutParamsDeleteButton.setMargins(950, 30, 50, 10)

                        passwordSetButton.isClickable = false
                        passwordSetButton.isEnabled = false
                        passwordSetButton.isVisible = false
                        reserveButton.isClickable = false
                        reserveButton.isEnabled = false
                        reserveButton.isVisible = false
                        addButton.layoutParams = layoutParamsAddButton
                        modifyButton.layoutParams = layoutParamsModifyButton
                        deleteButton.layoutParams = layoutParamsDeleteButton

                        initRetrofitInstanceEmployees()

                        addButton.setOnClickListener {

                            val addEmployeeDialogWindow = LayoutInflater.from(this).inflate(R.layout.employee_dialog, null)
                            val mBuilder = AlertDialog.Builder(this).setView(addEmployeeDialogWindow)
                            addEmployeeDialogWindow.dialogWindowTitle.text = "New employee"
                            val mAlertDialog = mBuilder.show()

                            addEmployeeDialogWindow.saveButton.setOnClickListener{

                                var newEmployee: Employee? = null

                                try{
                                    newEmployee = Employee(
                                        0,
                                        addEmployeeDialogWindow.dialogEmpNameET.text.toString(),
                                        addEmployeeDialogWindow.dialogEmpSurnameET.text.toString(),
                                        addEmployeeDialogWindow.dialogEmpSalaryET.text.toString().toFloat(),
                                        addEmployeeDialogWindow.dialogEmpAddressET.text.toString())

                                }catch(e: Exception){
                                    Toast.makeText(this@DisplayDataActivity, "Some of the fields are empty", Toast.LENGTH_LONG).show()
                                }

                                if(newEmployee != null) {
                                    displayDataViewModel.addNewEmployeeData(newEmployee!!) {

                                        if (it) {
                                            initRetrofitInstanceEmployees()
                                        }
                                    }
                                    mAlertDialog.dismiss()
                                }
                            }

                            addEmployeeDialogWindow.cancelButton.setOnClickListener{

                                mAlertDialog.dismiss()
                            }
                        }

                        modifyButton.setOnClickListener {

                            if(selectedItem==null){
                                Toast.makeText(this@DisplayDataActivity, "No employee selected", Toast.LENGTH_SHORT).show()
                            }else {

                                val modifyEmployeeDialogWindow =
                                    LayoutInflater.from(this).inflate(R.layout.employee_dialog, null)
                                val mBuilder = AlertDialog.Builder(this).setView(modifyEmployeeDialogWindow)
                                modifyEmployeeDialogWindow.dialogWindowTitle.text = "Edit employee"
                                val mAlertDialog = mBuilder.show()

                                var employeesList: List<Employee>? = null
                                displayDataViewModel.getEmployeeList().observe(this, {
                                    employeesList = it
                                })

                                var selectedEmployeeId: Int? = null

                                selectedItem?.let { it1 ->
                                    modifyEmployeeDialogWindow.dialogEmpNameET.setText(employeesList?.get(it1)?.name.toString())
                                    modifyEmployeeDialogWindow.dialogEmpSurnameET.setText(employeesList?.get(it1)?.surname.toString())
                                    modifyEmployeeDialogWindow.dialogEmpSalaryET.setText(employeesList?.get(it1)?.salary.toString())
                                    modifyEmployeeDialogWindow.dialogEmpAddressET.setText(employeesList?.get(it1)?.address.toString())

                                    selectedEmployeeId = employeesList?.get(it1)?.employeeid
                                }

                                modifyEmployeeDialogWindow.saveButton.setOnClickListener {

                                    var employee: Employee? = null

                                    try {
                                        employee = selectedEmployeeId?.let { it1 ->
                                            Employee(
                                                it1,
                                                modifyEmployeeDialogWindow.dialogEmpNameET.text.toString(),
                                                modifyEmployeeDialogWindow.dialogEmpSurnameET.text.toString(),
                                                modifyEmployeeDialogWindow.dialogEmpSalaryET.text.toString().toFloat(),
                                                modifyEmployeeDialogWindow.dialogEmpAddressET.text.toString()
                                            )
                                        }
                                    }catch(e: Exception){
                                        Toast.makeText(this@DisplayDataActivity, "Some of the fields are empty", Toast.LENGTH_LONG).show()
                                    }


                                    if (employee != null) {
                                        displayDataViewModel.modifyEmployee(employee!!){

                                            if(it){
                                                initRetrofitInstanceEmployees()
                                            }
                                        }
                                        mAlertDialog.dismiss()
                                    }
                                }

                                modifyEmployeeDialogWindow.cancelButton.setOnClickListener {

                                    mAlertDialog.dismiss()
                                }
                            }
                        }

                        deleteButton.setOnClickListener {

                            if(selectedItem==null){
                                Toast.makeText(this@DisplayDataActivity, "No employee selected", Toast.LENGTH_SHORT).show()
                            }else {

                                val deleteEmployeeDialogWindow = LayoutInflater.from(this).inflate(R.layout.employee_delete_dialog, null)
                                val mBuilder = AlertDialog.Builder(this).setView(deleteEmployeeDialogWindow)
                                deleteEmployeeDialogWindow.dialogEmpDeleteWindowTitle.text = "Delete employee"
                                val mAlertDialog = mBuilder.show()

                                var employeesList: List<Employee>? = null
                                displayDataViewModel.getEmployeeList().observe(this, {
                                    employeesList = it
                                })

                                var selectedEmployeeId: Int? = null
                                var empName: String? = null
                                var empSurname: String? = null
                                var empSalary: Float? = null
                                var empAddress: String? = null

                                selectedItem?.let { it1 ->

                                    empName = employeesList?.get(it1)?.name.toString()
                                    empSurname = employeesList?.get(it1)?.surname.toString()
                                    empSalary = employeesList?.get(it1)?.salary.toString().toFloat()
                                    empAddress = employeesList?.get(it1)?.address.toString()
                                    selectedEmployeeId = employeesList?.get(it1)?.employeeid.toString().toInt()
                                }

                                deleteEmployeeDialogWindow.dialogDeleteEmpName.text = empName
                                deleteEmployeeDialogWindow.dialogDeleteEmpSurname.text = empSurname
                                deleteEmployeeDialogWindow.dialogDeleteEmpSalary.text = empSalary.toString()
                                deleteEmployeeDialogWindow.dialogDeleteEmpAddress.text = empAddress

                                deleteEmployeeDialogWindow.confirmButton.setOnClickListener {

                                    if(selectedEmployeeId!=null) {
                                        displayDataViewModel.deleteEmployee(selectedEmployeeId!!){
                                            if(it){
                                                initRetrofitInstanceEmployees()
                                                selectedEmployeeId = null
                                            }
                                        }
                                    }

                                    mAlertDialog.dismiss()
                                }

                                deleteEmployeeDialogWindow.cancelButton.setOnClickListener {

                                    mAlertDialog.dismiss()
                                }
                            }
                        }

                        refreshButton.setOnClickListener {

                            dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_dark_8, null))
                            errorInfo.setText("")
                            initRetrofitInstanceEmployees()
                        }
                    }
                    else -> null
                }
            }
            "Reservations" -> {

                layoutParamsDeleteButton.setMargins(440, 30, 50, 10)

                passwordSetButton.isClickable = false
                passwordSetButton.isEnabled = false
                passwordSetButton.isVisible = false
                reserveButton.isClickable = false
                reserveButton.isEnabled = false
                reserveButton.isVisible = false
                addButton.isClickable = false
                addButton.isEnabled = false
                addButton.isVisible = false
                modifyButton.isClickable = false
                modifyButton.isEnabled = false
                modifyButton.isVisible = false
                deleteButton.layoutParams = layoutParamsDeleteButton

                initRetrofitInstanceReservations()

                when (currUser.roleid) {
                    "admin" -> {

                        deleteButton.setOnClickListener {

                            if(selectedItem==null){
                                Toast.makeText(this@DisplayDataActivity, "No reservation selected", Toast.LENGTH_SHORT).show()
                            }else {

                                val deleteResDialogWindow = LayoutInflater.from(this).inflate(R.layout.reservation_delete_dialog, null)
                                val mBuilder = AlertDialog.Builder(this).setView(deleteResDialogWindow)
                                deleteResDialogWindow.dialogDeleteResWindowTitle.text = "Delete reservation"
                                val mAlertDialog = mBuilder.show()

                                var reservationsList: List<Reservation>? = null
                                displayDataViewModel.getReservationList().observe(this, {
                                    reservationsList = it
                                })

                                var selectedResId: Int? = null
                                var resProduct: String? = null
                                var resAmount: Int? = null
                                var resDate: String? = null
                                var resPrice: Float? = null
                                var resEmpName: String? = null
                                var resEmpSurname: String? = null

                                selectedItem?.let { it1 ->

                                    resProduct = reservationsList?.get(it1)?.name.toString()
                                    resAmount = reservationsList?.get(it1)?.amount.toString().toInt()
                                    resDate = reservationsList?.get(it1)?.resdate.toString()
                                    resPrice = reservationsList?.get(it1)?.price.toString().toFloat()
                                    resEmpName = reservationsList?.get(it1)?.employeename.toString()
                                    resEmpSurname = reservationsList?.get(it1)?.employeesurname.toString()
                                    selectedResId = reservationsList?.get(it1)?.reservationid.toString().toInt()
                                }

                                deleteResDialogWindow.dialogDeleteResProdNameET.text = resProduct
                                deleteResDialogWindow.dialogDeleteResAmountET.text = resAmount.toString()
                                deleteResDialogWindow.dialogDeleteResDateET.text = resDate
                                deleteResDialogWindow.dialogDeleteResPriceET.text = resPrice.toString()
                                deleteResDialogWindow.dialogDeleteResEmpNameET.text = resEmpName
                                deleteResDialogWindow.dialogDeleteResEmpSurnameET.text = resEmpSurname

                                deleteResDialogWindow.confirmButton.setOnClickListener {

                                    if(selectedResId!=null) {
                                        displayDataViewModel.deleteReservation(selectedResId!!){
                                            if(it){
                                                initRetrofitInstanceReservations()
                                                selectedResId = null
                                            }
                                        }
                                    }

                                    mAlertDialog.dismiss()
                                }

                                deleteResDialogWindow.cancelButton.setOnClickListener {

                                    mAlertDialog.dismiss()
                                }
                            }
                        }

                    }
                    "salRep" -> {

                    }
                    else -> null
                }


                refreshButton.setOnClickListener {

                    dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_dark_8, null))
                    errorInfo.setText("")
                    initRetrofitInstanceReservations()
                }

            }
            "Low stock" ->{

                passwordSetButton.isClickable = false
                passwordSetButton.isEnabled = false
                passwordSetButton.isVisible = false

                reserveButton.isClickable = false
                reserveButton.isEnabled = false
                reserveButton.isVisible = false

                addButton.isClickable = false
                addButton.isEnabled = false
                addButton.isVisible = false

                modifyButton.isClickable = false
                modifyButton.isEnabled = false
                modifyButton.isVisible = false

                deleteButton.isClickable = false
                deleteButton.isEnabled = false
                deleteButton.isVisible = false

                initRetrofitInstanceLowStockProducts()

                refreshButton.setOnClickListener {

                    dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_dark_8, null))
                    errorInfo.setText("")
                    initRetrofitInstanceLowStockProducts()
                }

            }
            "New employees" ->{

                layoutParamsAddButton.setMargins(420, 5, 50, 10)

                passwordSetButton.isClickable = false
                passwordSetButton.isEnabled = false
                passwordSetButton.isVisible = false

                reserveButton.isClickable = false
                reserveButton.isEnabled = false
                reserveButton.isVisible = false

                modifyButton.isClickable = false
                modifyButton.isEnabled = false
                modifyButton.isVisible = false

                deleteButton.isClickable = false
                deleteButton.isEnabled = false
                deleteButton.isVisible = false

                addButton.layoutParams = layoutParamsAddButton

                initRetrofitInstanceNewEmployees()

                addButton.setOnClickListener {

                    if(selectedItem==null){
                        Toast.makeText(this@DisplayDataActivity, "No employee selected", Toast.LENGTH_SHORT).show()
                    }else {

                        val empLoginAdminDialogWindow = LayoutInflater.from(this)
                            .inflate(R.layout.employee_admin_login_dialog, null)
                        val mBuilder = AlertDialog.Builder(this).setView(empLoginAdminDialogWindow)
                        empLoginAdminDialogWindow.dialogEmpAdminLoginWindowTitle.text = "Employee login data"
                        val mAlertDialog = mBuilder.show()

                        var employeesAdminList: List<Employee>? = null
                        displayDataViewModel.getNewEmployeeList().observe(this, {
                            employeesAdminList = it
                        })

                        var selectedEmployeeId: Int? = null

                        selectedItem?.let { it1 ->

                            selectedEmployeeId = employeesAdminList?.get(it1)?.employeeid
                        }

                        empLoginAdminDialogWindow.confirmButton.setOnClickListener {

                            var employeeLoginDataRequest: EmployeeLoginDataRequest? = null

                            try {
                                employeeLoginDataRequest = selectedEmployeeId?.let { it1 ->
                                    EmployeeLoginDataRequest(
                                        it1,
                                        empLoginAdminDialogWindow.dialogEmpAdminLoginLoginET.text.toString(),
                                        HashString.hash(empLoginAdminDialogWindow.dialogEmpAdminLoginPasswordET.text.toString()),
                                        empLoginAdminDialogWindow.dialogEmpAdminLoginEmailET.text.toString(),
                                        empLoginAdminDialogWindow.dialogEmpAdminLoginRoleET.text.toString()
                                    )
                                }
                            }catch(e: Exception){
                                Toast.makeText(this@DisplayDataActivity, "Some of the fields are empty", Toast.LENGTH_LONG).show()
                            }

                            if (employeeLoginDataRequest != null) {
                                displayDataViewModel.addNewEmployeeAdminLogin(employeeLoginDataRequest!!) {

                                    if (it) {
                                        initRetrofitInstanceNewEmployees()
                                    }
                                }
                                mAlertDialog.dismiss()
                            }
                        }

                        empLoginAdminDialogWindow.cancelButton.setOnClickListener {

                            mAlertDialog.dismiss()
                        }
                    }
                }

                refreshButton.setOnClickListener {

                    dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_dark_8, null))
                    errorInfo.setText("")
                    initRetrofitInstanceNewEmployees()
                }
            }
            "Blocked employees" ->{

                layoutParamsDeleteButton.setMargins(440, 10, 50, 10)

                passwordSetButton.isClickable = false
                passwordSetButton.isEnabled = false
                passwordSetButton.isVisible = false

                reserveButton.isClickable = false
                reserveButton.isEnabled = false
                reserveButton.isVisible = false

                modifyButton.isClickable = false
                modifyButton.isEnabled = false
                modifyButton.isVisible = false

                addButton.isClickable = false
                addButton.isEnabled = false
                addButton.isVisible = false

                deleteButton.layoutParams = layoutParamsDeleteButton

                initRetrofitInstanceBlockedEmployees()

                deleteButton.setOnClickListener {

                    if(selectedItem==null){
                        Toast.makeText(this@DisplayDataActivity, "No employee selected", Toast.LENGTH_SHORT).show()
                    }else {

                        val unblockEmployeeAdminDialogWindow = LayoutInflater.from(this).inflate(R.layout.employee_unblock_dialog, null)
                        val mBuilder = AlertDialog.Builder(this).setView(unblockEmployeeAdminDialogWindow)
                        unblockEmployeeAdminDialogWindow.dialogEmpUnblockWindowTitle.text = "Unblock employee"
                        val mAlertDialog = mBuilder.show()

                        var blockedEmployees: List<Employee>? = null
                        displayDataViewModel.getBlockedEmployeeList().observe(this, {
                            blockedEmployees = it
                        })

                        var selectedEmployeeId: Int? = null
                        var empName: String? = null
                        var empSurname: String? = null

                        selectedItem?.let { it1 ->

                            empName = blockedEmployees?.get(it1)?.name.toString()
                            empSurname = blockedEmployees?.get(it1)?.surname.toString()
                            selectedEmployeeId = blockedEmployees?.get(it1)?.employeeid.toString().toInt()
                        }

                        unblockEmployeeAdminDialogWindow.dialogUnblockEmpName.text = empName
                        unblockEmployeeAdminDialogWindow.dialogUnblockEmpSurname.text = empSurname

                        unblockEmployeeAdminDialogWindow.confirmButton.setOnClickListener {

                            if(selectedEmployeeId!=null) {
                                displayDataViewModel.unblockEmployeeAdmin(selectedEmployeeId!!){
                                    if(it){
                                        initRetrofitInstanceBlockedEmployees()
                                        selectedEmployeeId = null
                                    }
                                }
                            }

                            mAlertDialog.dismiss()
                        }

                        unblockEmployeeAdminDialogWindow.cancelButton.setOnClickListener {

                            mAlertDialog.dismiss()
                        }
                    }
                }

                refreshButton.setOnClickListener {

                    dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.blue_dark_8, null))
                    errorInfo.setText("")
                    initRetrofitInstanceBlockedEmployees()
                }

            }
        }
    }

    private fun initRetrofitInstanceProducts(userKey: String){
        displayDataViewModel = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        displayDataViewModel.getProductsData(userKey){
            if(!it){

                dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.error_color, null))
                errorInfo.setText("Server error")

                Toast.makeText(
                    this@DisplayDataActivity,
                    "Cannot connect to the server",
                    android.widget.Toast.LENGTH_LONG).show()
            }
        }
        displayDataViewModel.getProductList().observe(this,{
            initAdapterProducts(it)
        })
    }

    private fun initAdapterProducts(productsList: List<Product>){
        recViewDisplayData.layoutManager = LinearLayoutManager(this)
        val adapter = ProductAdapter(productsList)
        recViewDisplayData.adapter = adapter
        adapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                selectedItem = position
            }
        })
    }

    private fun initRetrofitInstanceReservations(){
        displayDataViewModel = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        displayDataViewModel.getReservationsData(){
            if(!it){

                dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.error_color, null))
                errorInfo.setText("Server error")

                Toast.makeText(
                    this@DisplayDataActivity,
                    "Cannot connect to the server",
                    android.widget.Toast.LENGTH_LONG).show()
            }
        }
        //observer
        displayDataViewModel.getReservationList().observe(this,{
            initAdapterReservations(it)
        })
    }

    private fun initAdapterReservations(reservationsList: List<Reservation>){
        recViewDisplayData.layoutManager = LinearLayoutManager(this)
        val adapter = ReservationAdapter(reservationsList)
        recViewDisplayData.adapter = adapter
        adapter.setOnItemClickListener(object : ReservationAdapter.onItemClickListner{
            override fun onItemClick(position: Int) {

                selectedItem = position
            }
        })
    }

    private fun initRetrofitInstanceEmployees(){
        displayDataViewModel = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        displayDataViewModel.getEmployeeData(){
            if(!it){

                dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.error_color, null))
                errorInfo.setText("Server error")

                Toast.makeText(
                    this@DisplayDataActivity,
                    "Cannot connect to the server",
                    android.widget.Toast.LENGTH_LONG).show()
            }
        }
        //observer
        displayDataViewModel.getEmployeeList().observe(this,{
            initAdapterEmployees(it)
        })
    }

    private fun initRetrofitInstanceNewEmployees(){
        displayDataViewModel = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        displayDataViewModel.getNewEmployeeDataAdmin(){
            if(!it){

                dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.error_color, null))
                errorInfo.setText("Server error")

                Toast.makeText(
                    this@DisplayDataActivity,
                    "Cannot connect to the server",
                    android.widget.Toast.LENGTH_LONG).show()
            }
        }
        //observer
        displayDataViewModel.getNewEmployeeList().observe(this,{
            initAdapterNewEmployees(it)
        })
    }

    private fun initRetrofitInstanceBlockedEmployees(){
        displayDataViewModel = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        displayDataViewModel.getBlockedEmployeeDataAdmin(){
            if(!it){

                dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.error_color, null))
                errorInfo.setText("Server error")

                Toast.makeText(
                    this@DisplayDataActivity,
                    "Cannot connect to the server",
                    android.widget.Toast.LENGTH_LONG).show()
            }
        }
        //observer
        displayDataViewModel.getBlockedEmployeeList().observe(this,{
            initAdapterBlockedEmployees(it)
        })
    }

    private fun initAdapterEmployees(employeesList: List<Employee>){
        recViewDisplayData.layoutManager = LinearLayoutManager(this)
        val adapter = EmployeeAdapter(employeesList)
        recViewDisplayData.adapter = adapter
        adapter.setOnItemClickListener(object : EmployeeAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                selectedItem = position
            }
        })
    }

    private fun initAdapterNewEmployees(employeesList: List<Employee>){
        recViewDisplayData.layoutManager = LinearLayoutManager(this)
        val adapter = NewEmployeeAdapter(employeesList)
        recViewDisplayData.adapter = adapter
        adapter.setOnItemClickListener(object : NewEmployeeAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                selectedItem = position
            }
        })
    }
    private fun initAdapterBlockedEmployees(employeesList: List<Employee>){
        recViewDisplayData.layoutManager = LinearLayoutManager(this)
        val adapter = BlockedEmployeeAdapter(employeesList)
        recViewDisplayData.adapter = adapter
        adapter.setOnItemClickListener(object : BlockedEmployeeAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                selectedItem = position
            }
        })
    }

    private fun initRetrofitInstanceEmployeesAdmin(){
        displayDataViewModel = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        displayDataViewModel.getEmployeeDataAdmin(){
            if(!it){

                dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.error_color, null))
                errorInfo.setText("Server error")

                Toast.makeText(
                    this@DisplayDataActivity,
                    "Cannot connect to the server",
                    android.widget.Toast.LENGTH_LONG).show()
            }
        }
        //observer
        displayDataViewModel.getEmployeeAdminList().observe(this,{
            initAdapterEmployeesAdmin(it)
        })
    }

    private fun initAdapterEmployeesAdmin(employeesAdminList: List<EmployeeAdminData>){
        recViewDisplayData.layoutManager = LinearLayoutManager(this)
        val adapter = EmployeeAdminAdapter(employeesAdminList)
        recViewDisplayData.adapter = adapter
        adapter.setOnItemClickListener(object : EmployeeAdminAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {

                selectedItem = position
            }
        })
    }

    private fun initRetrofitInstanceLowStockProducts(){
        displayDataViewModel = ViewModelProvider(this).get(DisplayDataViewModel::class.java)
        displayDataViewModel.getLowStockProductsData(){
            if(!it){

                dataTitle.setTextColor(ResourcesCompat.getColor(resources, R.color.error_color, null))
                errorInfo.setText("Server error")

                Toast.makeText(
                    this@DisplayDataActivity,
                    "Cannot connect to the server",
                    android.widget.Toast.LENGTH_LONG).show()
            }
        }
        //observer
        displayDataViewModel.getLowStockProductList().observe(this,{
            initAdapterProducts(it)
        })
    }

}