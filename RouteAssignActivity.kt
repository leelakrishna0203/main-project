package com.example.bustract.Admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bustract.Adapters.FrontUi
import com.example.bustract.databinding.ActivityRouteAssignBinding

class RouteAssignActivity : AppCompatActivity() {
    private val bind by lazy { ActivityRouteAssignBinding.inflate(layoutInflater) }
    private val model by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        model.getdata()

        bind.cycle.let {
            model.myobserver().observe(this) { the ->
                if (the != null) {
                    it.adapter = FrontUi(context = this, array = the)
                    it.layoutManager = LinearLayoutManager(this)
                }
            }

        }

    }
}