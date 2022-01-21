package com.example.correct_warehouse_app.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.correct_warehouse_app.R
import com.example.correct_warehouse_app.model.CurrentUser
import com.example.correct_warehouse_app.view.activity.DisplayDataActivity
import com.example.correct_warehouse_app.view.activity.ProfileActivity
import kotlinx.android.synthetic.main.sales_representative_nav_fragment.*

class SalesRepNavFragment: Fragment() {

    private var currUser: CurrentUser? = null

    companion object{
        fun newInstance() = SalesRepNavFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        currUser = bundle!!.get("EXTRA_CURRENT_USER") as CurrentUser

        return inflater.inflate(R.layout.sales_representative_nav_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myProfileButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, ProfileActivity::class.java).also {
                    it.putExtra("EXTRA_CURRENT_USER", currUser)
                }
                it.startActivity(intent)
            }
        }

        productsButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, DisplayDataActivity::class.java).also {
                    it.putExtra("EXTRA_CURRENT_USER", currUser)
                    it.putExtra("EXTRA_LIST_TYPE", "Products")
                }
                it.startActivity(intent)
            }
        }

        reservationsButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, DisplayDataActivity::class.java).also {
                    it.putExtra("EXTRA_CURRENT_USER", currUser)
                    it.putExtra("EXTRA_LIST_TYPE", "Reservations")
                }
                it.startActivity(intent)
            }
        }
    }
}