package com.example.bustract

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import coil.load
import com.example.bustract.Admin.AdminMainActivity
import com.example.bustract.BusDepot.BusStandActivity
import com.example.bustract.Driver.DriverActivity
import com.example.bustract.User.ModuleActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

  /*      requestWindowFeature(Window.FEATURE_NO_TITLE)
 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
     window?.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }*/

        setContentView(R.layout.activity_main)
val type=getSharedPreferences("user", MODE_PRIVATE).getString("type","")
    findViewById<ImageView>(R.id.image).apply {
        alpha = 0f
        animate().setDuration(1500).alpha(1f)
            .withEndAction {
                finish()
                when(type){
                    "admin"->{
                        startActivity(Intent(this@MainActivity, AdminMainActivity::class.java))
                    }
                    "Deport"->{
                        startActivity(Intent(this@MainActivity, BusStandActivity::class.java))
                    }
                    "Driver"->{
                        startActivity(Intent(this@MainActivity,DriverActivity::class.java))
                    }
                    else->{
                        startActivity(Intent(this@MainActivity, ModuleActivity::class.java))
                    }
                }

            }.withStartAction {
                load(R.drawable.unnamed) {
                    crossfade(1000)
                    crossfade(true)
                }
            }
    }

    }
    }
