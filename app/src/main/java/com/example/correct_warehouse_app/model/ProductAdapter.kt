package com.example.correct_warehouse_app.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.correct_warehouse_app.R
import com.google.android.material.textview.MaterialTextView
import java.util.*

class ProductAdapter(private var productList: List<Product>):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(productView: View, listener: onItemClickListener): RecyclerView.ViewHolder(productView){
        val productName: MaterialTextView = productView.findViewById(R.id.productName)
        val productSize: MaterialTextView = productView.findViewById(R.id.productSize)
        val productAmount: MaterialTextView = productView.findViewById(R.id.productAmount)
        val productPrice: MaterialTextView = productView.findViewById(R.id.productPrice)

        init {
            productView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = productList[position].name
        holder.productSize.text = productList[position].sizeofproduct.toString()
        holder.productAmount.text = productList[position].amount.toString()
        holder.productPrice.text = productList[position].price.toString() + " " + java.util.Currency.getInstance("GBP").getSymbol(
            Locale.ENGLISH
        )
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}