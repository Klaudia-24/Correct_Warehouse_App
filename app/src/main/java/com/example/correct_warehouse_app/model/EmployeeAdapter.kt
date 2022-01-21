package com.example.correct_warehouse_app.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.correct_warehouse_app.R
import com.google.android.material.textview.MaterialTextView
import java.util.*

class EmployeeAdapter(private var employeeList: List<Employee>):
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>()  {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(employeeView: View, listener: onItemClickListener): RecyclerView.ViewHolder(employeeView){

        val employeeName: MaterialTextView = employeeView.findViewById(R.id.empEmployeeName)
        val employeeSurname: MaterialTextView = employeeView.findViewById(R.id.empEmployeeSurname)
        val salary: MaterialTextView = employeeView.findViewById(R.id.empEmployeeSalary)
        val address: MaterialTextView = employeeView.findViewById(R.id.empEmployeeAddress)

        init {
            employeeView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_list, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.employeeName.text = employeeList[position].name
        holder.employeeSurname.text = employeeList[position].surname
        holder.salary.text = employeeList[position].salary.toString() + " " + java.util.Currency.getInstance("GBP").getSymbol(
            Locale.ENGLISH
        )
        holder.address.text = employeeList[position].address
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }
}