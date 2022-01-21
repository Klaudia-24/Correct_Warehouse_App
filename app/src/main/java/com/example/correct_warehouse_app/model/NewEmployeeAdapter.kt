package com.example.correct_warehouse_app.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.correct_warehouse_app.R
import com.google.android.material.textview.MaterialTextView

class NewEmployeeAdapter(private var newEmployeeList: List<Employee>):
    RecyclerView.Adapter<NewEmployeeAdapter.ViewHolder>()  {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(newEmployeeAdminView: View, listener: onItemClickListener): RecyclerView.ViewHolder(newEmployeeAdminView){

        val employeeName: MaterialTextView = newEmployeeAdminView.findViewById(R.id.empNewEmployeeSurname)
        val employeeSurname: MaterialTextView = newEmployeeAdminView.findViewById(R.id.empNewEmployeeName)

        init {
            newEmployeeAdminView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.new_employee_list, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.employeeName.text = newEmployeeList[position].name
        holder.employeeSurname.text = newEmployeeList[position].surname

    }

    override fun getItemCount(): Int {
        return newEmployeeList.size
    }
}