package com.example.correct_warehouse_app.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.correct_warehouse_app.R
import com.google.android.material.textview.MaterialTextView
import java.util.*

class EmployeeAdminAdapter(private var employeeAdminList: List<EmployeeAdminData>):
    RecyclerView.Adapter<EmployeeAdminAdapter.ViewHolder>()  {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(employeeAdminView: View, listener: onItemClickListener): RecyclerView.ViewHolder(employeeAdminView){

        val employeeName: MaterialTextView = employeeAdminView.findViewById(R.id.empEmployeeName)
        val employeeSurname: MaterialTextView = employeeAdminView.findViewById(R.id.empEmployeeSurname)
        val salary: MaterialTextView = employeeAdminView.findViewById(R.id.empEmployeeSalary)
        val address: MaterialTextView = employeeAdminView.findViewById(R.id.empEmployeeAddress)
        val empLogin: MaterialTextView = employeeAdminView.findViewById(R.id.empEmployeeLogin)
        val email: MaterialTextView = employeeAdminView.findViewById(R.id.empEmployeeEmail)
        val roleName: MaterialTextView = employeeAdminView.findViewById(R.id.empEmployeeRole)

        init {
            employeeAdminView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_admin_list, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.employeeName.text = employeeAdminList[position].name
        holder.employeeSurname.text = employeeAdminList[position].surname
        holder.salary.text = employeeAdminList[position].salary.toString() + " " + java.util.Currency.getInstance("GBP").getSymbol(
            Locale.ENGLISH
        )
        holder.address.text = employeeAdminList[position].address
        holder.empLogin.text = employeeAdminList[position].login
        holder.email.text = employeeAdminList[position].email
        holder.roleName.text = employeeAdminList[position].rolename
    }

    override fun getItemCount(): Int {
        return employeeAdminList.size
    }
}