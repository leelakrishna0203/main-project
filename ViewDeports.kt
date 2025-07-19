package com.example.bustract.Admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bustract.Adapters.DepotAdapter
import com.example.bustract.Adapters.DriverView
import com.example.bustract.BusDepot.ScheduledBuses
import com.example.bustract.Comfun
import com.example.bustract.Interactions.Selector
import com.example.bustract.Responses.Model.Route
import com.example.bustract.Responses.Model.User
import com.example.bustract.databinding.ActivityViewDriversBinding

class ViewDeports : AppCompatActivity(), Selector {
    private val bind by lazy {
        ActivityViewDriversBinding.inflate(layoutInflater)
    }
    val cf by lazy {
        Comfun(this)
    }
    private val viewmodel by lazy {
        ViewModelProvider(this)[MyViewModel::class.java]
    }
    private lateinit var route: Route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        viewmodel.getdeports()
        viewmodel.observedrvier().observe(this@ViewDeports) {
            if (it != null) {
                bind.cycle2.apply {
                    layoutManager = LinearLayoutManager(this@ViewDeports)
                    adapter = DepotAdapter(this@ViewDeports, it, this@ViewDeports)
                }
            }


            bind.searchview.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { it1 -> viewmodel.contains(it1) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    newText?.let { it1 -> viewmodel.contains(it1) }
                    return true
                }
            })


        }


    }

    override fun state(user: User) {
        Intent(this, ScheduledBuses::class.java).apply {
            putExtra("id", user.id)
            startActivity(this)
        }
    }
}
