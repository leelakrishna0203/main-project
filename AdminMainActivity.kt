package com.example.bustract.Admin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.bustract.LoginActivity
import com.example.bustract.User.ModuleActivity
import com.example.bustract.databinding.ActivityAdminMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AdminMainActivity : AppCompatActivity() {
    private val bind by lazy {
        ActivityAdminMainBinding.inflate(layoutInflater)
    }

    private val model by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        model.getdata()
        val reallaunch =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode != RESULT_OK) {
                    model.getdata()
                }
            }


        // Add Drivers
        bind.search.setOnClickListener {
            startActivity(Intent(this@AdminMainActivity, AddTypeActivity::class.java).apply {
                putExtra("key","Driver")
            })

        }
        bind.logout2.setOnClickListener {
            startActivity(Intent(this@AdminMainActivity, AddTypeActivity::class.java).apply {
                putExtra("key","Deport")
            })

        }
        // Add Routes
        bind.track.setOnClickListener {
            reallaunch.launch(Intent(this@AdminMainActivity, AddRoute::class.java))

        }

        bind.logout23.setOnClickListener {
            MaterialAlertDialogBuilder(this).apply {
                setCancelable(false)
                setMessage("Do you want to Logout ?? \n Press 'Yes' to Logout or Press 'No' to cancel")
                setPositiveButton("Yes") { c, _ ->
                    c.dismiss()
                    getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
                    finishAffinity()
                    startActivity(Intent(this@AdminMainActivity, ModuleActivity::class.java))
                }
                setNegativeButton("No") { p, _ ->
                    p.dismiss()
                }
                show()
            }


        }
        // Assign routes
        bind.history.setOnClickListener {
            startActivity(Intent(this@AdminMainActivity, RouteAssignActivity::class.java))

        }




    }


}