package com.example.correct_warehouse_app.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.correct_warehouse_app.R
import com.example.correct_warehouse_app.model.CurrentUser
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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
}