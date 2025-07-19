package com.example.bustract.User

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bustract.LoginActivity
import com.example.bustract.databinding.ActivityModuleBinding

class ModuleActivity : AppCompatActivity() {
    private val bind by lazy { ActivityModuleBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        bind.passenger.setOnClickListener {
            startActivity(Intent(this@ModuleActivity, UserMainActivity::class.java))
        }
        bind.Employee.setOnClickListener {
            startActivity(Intent(this@ModuleActivity, LoginActivity::class.java))
        }

    }
}