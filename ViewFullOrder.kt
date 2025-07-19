package com.example.bustract.User

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.bustract.Responses.Model.History
import com.example.bustract.databinding.ActivityViewFullOrderBinding
import com.example.bustract.spannaAble

class ViewFullOrder : AppCompatActivity() {
    private val bind by lazy {
        ActivityViewFullOrderBinding.inflate(layoutInflater)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("data", History::class.java)
        } else {
            intent.getParcelableExtra("data")
        }?.apply {
            bind.details4.text = spannaAble(
                string = "<big><b>$vehiclenumber</b></big><br>" +
                        "<b>From :</b>$fromplace<br>" +
                        "<b>To :</b>$toplace<br>" +
                        "<b>Journey Starts :</b>$starttime<br>" +
                        "<b>Journey Ends :</b>$endtime<br>" +
                        "<b>Days to Travel :</b>$days<br>" +
                        "<b>Total Amount :</b>₹ $total /-<br>" +
                        "<b>Seats : </b> <br> $seats"
            )
            bind.image.load(profile)
            bind.navigate.setOnClickListener {
                Intent(this@ViewFullOrder, ViewTheLocation::class.java).apply {
                    putExtra("latlon", latlon)
                    startActivity(this)
                }
            }
        }
    }
}