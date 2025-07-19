package com.example.bustract.User

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.bustract.R
import com.example.bustract.databinding.ActivityUserMainBinding
import com.example.bustract.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import java.text.DecimalFormat

class ViewTheLocation : AppCompatActivity(), OnMapReadyCallback {

    private val bind by lazy {
        ActivityUserMainBinding.inflate(layoutInflater)
    }
    val decimal = DecimalFormat("##.#######")
    var check: Boolean = false
    private lateinit var fused: FusedLocationProviderClient
    var googleMap: GoogleMap? = null
    val ff = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        fused = LocationServices.getFusedLocationProviderClient(this)
        val maps = supportFragmentManager.findFragmentById(R.id.containers) as SupportMapFragment
        maps.getMapAsync(this)
        val reques =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it[ff[0]] == true && it[ff[1]] == true) {
                    currentlocation()
                }

            }

        if (ActivityCompat.checkSelfPermission(this, ff[0]) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, ff[1]) != PackageManager.PERMISSION_GRANTED
        ) {
            reques.launch(ff)
            check = true
        }


    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        if (!check) {
            currentlocation()
        }
    }

    @SuppressLint("MissingPermission")
    fun currentlocation() {

        if (googleMap != null) {
            googleMap.apply {
                fused.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        val latlon = LatLng(
                            decimal.format(it.latitude).toDouble(),
                            decimal.format(it.longitude).toDouble()
                        )
                        googleMap?.addMarker(
                            MarkerOptions().title("Current Location").position(latlon)
                        )
                        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon, 16f))

                        val buslatlon = intent.getStringExtra("latlon")?.split(",")
                        if (buslatlon?.size == 2) {
                            val secondlatlon =
                                LatLng(buslatlon[0].toDouble(), buslatlon[1].toDouble())
                            googleMap?.addMarker(
                                MarkerOptions().title("Bus Location").position(secondlatlon)
                            )
                            googleMap?.addPolygon(
                                PolygonOptions().add(secondlatlon, latlon).fillColor(Color.RED)
                                    .strokeColor(Color.RED)
                            )
                        } else {
                            showToast("Can't find the Location of the bus")
                        }

                    }
                }.addOnFailureListener {
                    showToast(it.message)
                }
            }
        }

    }

}