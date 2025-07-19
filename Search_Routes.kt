package com.example.bustract.User

import android.Manifest
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bustract.Adapters.ForSearch
import com.example.bustract.Driver.UpdateRouteActivity
import com.example.bustract.Interactions.Searchable
import com.example.bustract.Responses.Model.Search
import com.example.bustract.User.Core.MainViewModel
import com.example.bustract.databinding.ActivitySearchRoutesBinding
import com.example.bustract.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Search_Routes : AppCompatActivity(), Searchable {
    private val bind by lazy { ActivitySearchRoutesBinding.inflate(layoutInflater) }
    private val model by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    var currentLatLng = ""
    lateinit var fused: FusedLocationProviderClient

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        fused = LocationServices.getFusedLocationProviderClient(this)
        fused.lastLocation.addOnSuccessListener {
            if (it != null) {
                currentLatLng = "${it.latitude},${it.longitude}"
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        Geocoder(this).getFromLocation(it.latitude, it.latitude, 1) {

                            model.getdata(it[0].locality)
                        }

                    } else {
                        val city = Geocoder(this).getFromLocation(it.latitude, it.latitude, 1)
                            ?.get(0)?.locality
                        city?.let { it1 ->
                            model.getdata(it1)

                        }

                    }
                } catch (e: Exception) {
                    model.getdata("Hyderabad")
                }




                bind.searchView.setQuery("", true)
                model.update().observe(this) { data ->
                    if (data != null) {
                        model.calculate(it)
                        bind.cycle3.let { the ->
                            the.adapter = ForSearch(this, data, this@Search_Routes)
                            the.layoutManager = LinearLayoutManager(this)
                        }

                    }
                }
            }
            bind.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { model.getdata2(it) }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { model.getdata2(it) }
                    return true
                }
            })
        }.addOnFailureListener {
            showToast(it.message)
        }


    }

    override fun Forintent(search: Search) {
       /* if (search.distance != null) {*/
            Intent(this, TrackMyBus::class.java).apply {
                putExtra("routedetails", search)
                putExtra("latlon", currentLatLng)
                putExtra("select_date", intent.getStringExtra("select_date"))
                startActivity(this)
            }
        /*} else {
            showToast("distance is not get in")
        }*/

    }
}