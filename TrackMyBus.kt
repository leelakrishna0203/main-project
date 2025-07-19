package com.example.bustract.User

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bustract.Adapters.ViewDoc
import com.example.bustract.User.Core.SeatViewModel
import com.example.bustract.databinding.ActivityTrackMyBusBinding

class TrackMyBus : AppCompatActivity() {
    private val model by lazy {
        ViewModelProvider(this)[SeatViewModel::class.java]
    }
    private val bind by lazy {
        ActivityTrackMyBusBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        model.startData("c")
        bind.searchView2.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { model.startData(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { model.startData(it) }
                    return true
                }
            })
        model.viewdata().observe(this) {
            if (it != null) {
                bind.cycle5.apply {
                    adapter = ViewDoc(this@TrackMyBus, it)
                    layoutManager = LinearLayoutManager(this@TrackMyBus)
                }
            }
        }

    }
}