package com.example.correct_warehouse_app.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.correct_warehouse_app.R
import com.example.correct_warehouse_app.model.CurrentUser
import com.example.correct_warehouse_app.view.fragment.AccountantNavFragment
import com.example.correct_warehouse_app.view.fragment.AdministratorNavFragment
import com.example.correct_warehouse_app.view.fragment.SalesRepNavFragment
import com.example.correct_warehouse_app.view.fragment.WarehousemanNavFragment


class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val currUser = intent.getSerializableExtra("EXTRA_CURRENT_USER") as CurrentUser

        val navFragment: Fragment? = when (currUser.roleid) {
            "admin" -> AdministratorNavFragment()
            "warMan" -> WarehousemanNavFragment()
            "salRep" -> SalesRepNavFragment()
            "acc" -> AccountantNavFragment()
            else -> null
        }

        if (navFragment != null) {
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val bundle = Bundle()
            bundle.putSerializable("EXTRA_CURRENT_USER", currUser)
            navFragment.arguments = bundle
            fragmentTransaction.add(R.id.fragmentContainer, navFragment).commit()
        }
    }

    override fun onBackPressed() {
    }

}