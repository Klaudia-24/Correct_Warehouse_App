package com.example.correct_warehouse_app.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import java.util.*


class NavigationActivity : AppCompatActivity() {

    private var timeHandler: Handler? = null
    private var runnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val loginIntent = Intent(this, LoginActivity::class.java)

        timeHandler =  Handler(Looper.getMainLooper())
        runnable =  object: Runnable {
            override fun run() {
                Toast.makeText(
                    this@NavigationActivity,
                    "Log out NavigationActivity",
                    android.widget.Toast.LENGTH_SHORT).show()

                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(loginIntent)
                finish()
            }
        }
        startTimeHandler()

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

    override fun onUserInteraction() {
        super.onUserInteraction()
        resetTimeHandler()
    }

    //restart countdown
    fun resetTimeHandler() {
        runnable?.let { timeHandler?.removeCallbacks(it) }
        runnable?.let { timeHandler?.postDelayed(it, 20*1000) } //for 7 second

    }

    // start countdown
    fun startTimeHandler() {
        runnable?.let { timeHandler?.postDelayed(it, 20*1000) } //for 7 second
    }

    fun stopTimeHandler() {
        runnable?.let { timeHandler?.removeCallbacks(it) }
    }

    override fun onBackPressed() {
    }

    override fun onPause() {
        stopTimeHandler()
        Log.d("TEST", "nav onPauseActivity change")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        startTimeHandler()
        Log.d("TEST", "nav onResume_restartActivity")
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimeHandler()
        Log.d("TEST", "nav onDestroyActivity change")
    }
}