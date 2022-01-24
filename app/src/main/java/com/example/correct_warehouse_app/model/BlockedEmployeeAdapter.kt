package com.example.correct_warehouse_app.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.correct_warehouse_app.R
import com.google.android.material.textview.MaterialTextView

class BlockedEmployeeAdapter(private var blockedEmployeeList: List<Employee>):
    RecyclerView.Adapter<BlockedEmployeeAdapter.ViewHolder>()  {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(blockedEmployeeAdminView: View, listener: onItemClickListener):
        RecyclerView.ViewHolder(blockedEmployeeAdminView){

        val employeeName: MaterialTextView = blockedEmployeeAdminView.findViewById(R.id.empBlockedEmployeeSurname)
        val employeeSurname: MaterialTextView = blockedEmployeeAdminView.findViewById(R.id.empBlockedEmployeeName)

        init {
            blockedEmployeeAdminView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blocked_employee_list, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.employeeName.text = blockedEmployeeList[position].name
        holder.employeeSurname.text = blockedEmployeeList[position].surname

    }

    override fun getItemCount(): Int {
        return blockedEmployeeList.size
    }
}