package com.example.correct_warehouse_app.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.correct_warehouse_app.R
import com.google.android.material.textview.MaterialTextView
import java.util.*

class ReservationAdapter(private var reservationList: List<Reservation>):
    RecyclerView.Adapter<ReservationAdapter.ViewHolder>()  {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListner){
        mListner = listener
    }

    inner class ViewHolder(reservationView: View, listener: onItemClickListner): RecyclerView.ViewHolder(reservationView){
        val productName: MaterialTextView = reservationView.findViewById(R.id.resProductName)
        val productSize: MaterialTextView = reservationView.findViewById(R.id.resProductSize)
        val productAmount: MaterialTextView = reservationView.findViewById(R.id.resProductAmount)
        val productPrice: MaterialTextView = reservationView.findViewById(R.id.resProductTotal)
        val reservationDate: MaterialTextView = reservationView.findViewById(R.id.resDate)
        val employeeName: MaterialTextView = reservationView.findViewById(R.id.resEmployeeName)
        val employeeSurname: MaterialTextView = reservationView.findViewById(R.id.resEmployeeSurname)

        init {
            reservationView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reservation_list, parent, false)
        return ViewHolder(view, mListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = reservationList[position].name
        holder.productSize.text = reservationList[position].sizeofproduct.toString()
        holder.productAmount.text = reservationList[position].amount.toString()
        holder.productPrice.text = reservationList[position].price.toString() + " " + java.util.Currency.getInstance("GBP").getSymbol(
            Locale.ENGLISH
        )

        holder.reservationDate.text = reservationList[position].resdate
        holder.employeeName.text = reservationList[position].employeename
        holder.employeeSurname.text = reservationList[position].employeesurname
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }
}