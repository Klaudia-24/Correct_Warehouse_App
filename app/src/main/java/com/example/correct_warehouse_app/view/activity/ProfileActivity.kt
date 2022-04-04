package com.example.correct_warehouse_app.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.correct_warehouse_app.R
import com.example.correct_warehouse_app.model.CurrentUser
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private var timeoutHandler: Handler? = null
    private var interactionTimeoutRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val loginIntent = Intent(this, LoginActivity::class.java)

        timeoutHandler =  Handler(Looper.getMainLooper())
        interactionTimeoutRunnable =  object: Runnable {
            // Handle Timeout stuffs here
            override fun run() {
                Toast.makeText(
                    this@ProfileActivity,
                    "Log out ProfileActivity",
                    android.widget.Toast.LENGTH_SHORT).show()


                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(loginIntent)
                finish()
            }
        }

        //start countdown
        startHandler()

        val currUser = intent.getSerializableExtra("EXTRA_CURRENT_USER") as CurrentUser
        name.setText(currUser.name)
        surname.setText(currUser.surname)
        userLogin.setText(currUser.login)
        userEmail.setText(currUser.email)
        userAddress.setText(currUser.address)
        userSalary.setText(currUser.salary.toString() + " " + java.util.Currency.getInstance("GBP").getSymbol(
            Locale.ENGLISH
        ))
        userRole.setText(currUser.rolename)

        backToNavButton.setOnClickListener {
            Intent(this, NavigationActivity::class.java).also {
                it.putExtra("EXTRA_CURRENT_USER", currUser)
                startActivity(it)
            }
        }

        signOutButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        resetHandler()
    }

    //restart countdown
    fun resetHandler() {
        interactionTimeoutRunnable?.let { timeoutHandler?.removeCallbacks(it) }
        interactionTimeoutRunnable?.let { timeoutHandler?.postDelayed(it, 5*1000) } //for 5 second

    }

    // start countdown
    fun startHandler() {
        interactionTimeoutRunnable?.let { timeoutHandler?.postDelayed(it, 5*1000) } //for 5 second
    }

    fun stopHandler() {
        interactionTimeoutRunnable?.let { timeoutHandler?.removeCallbacks(it) }
    }

    override fun onPause() {
        stopHandler()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        startHandler()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopHandler()
    }
}