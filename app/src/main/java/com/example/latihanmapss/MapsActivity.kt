package com.example.latihanmapss

import android.Manifest.*
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest
import android.location.Address
import android.location.Geocoder
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val permission = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val permissionCode = 1100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        cariTempat.setOnQueryTextFocusChangeListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: query: String?): Boolean {
                val cari = p0.toString()
                val address : List<Address> = emptyList()
                val geocoder = Geocoder(this@MapsActivity)
                address = geocoder.getFromLocationName(cari, 1)
                if (address!=null)
                val hasilCari = address.get(0)
                val latLng = LatLng(hasilCari.latitude,hasilCari.longitude)

                mMap.addMarker(MarkerOptions().position(latLng).title(cari))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18.0f))

                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        verifyPermission()

    }

    private fun verifyPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    permission[0]
                ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    permission[1]
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val uty = LatLng(-7.747033, 110.355398)
                val zoomSize = 16.0f
                mMap.addMarker(MarkerOptions().position(uty).title("Universitas Teknologi Yogyakarta"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(uty, zoomSize))
                mMap.isMyLocationEnabled = true
            } else {
                ActivityCompat.requestPermissions(this, permission, permissionCode)

            }
        } else {
            val uty = LatLng(-7.747033, 110.355398)
            val zoomSize = 16.0f
            mMap.addMarker(MarkerOptions().position(uty).title("Universitas Teknologi Yogyakarta"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(uty, zoomSize))
            mMap.isMyLocationEnabled = true
        }

    }
}

