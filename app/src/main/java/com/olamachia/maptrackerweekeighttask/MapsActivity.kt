package com.olamachia.maptrackerweekeighttask

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import com.olamachia.maptrackerweekeighttask.Model.location
import com.olamachia.maptrackerweekeighttask.databinding.ActivityMapsBinding


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var fusedLocClient: FusedLocationProviderClient? = null

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationRequest: LocationRequest
//    private lateinit var fusedLocationClient: FusedLocationProviderClient



    // use it to request location updates and get the latest location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPartnerlocation()
        setupLocClient()
    
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    // use it to request location updates and get the latest location
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        getCurrentLocation()


    }
    private fun setupLocClient() {
        fusedLocClient =
            LocationServices.getFusedLocationProviderClient(this)
    }

    // prompt the user to grant/deny access
    private fun requestLocPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), //permission in the manifest
            REQUEST_LOCATION)
    }

    companion object {
        private const val REQUEST_LOCATION = 1 //request code to identify specific permission request
        private const val TAG = "MapsActivity" // for debugging

    }
    private fun getCurrentLocation() {
        // Check if the ACCESS_FINE_LOCATION permission was granted before requesting a location
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {

            // call requestLocPermissions() if permission isn't granted
            requestLocPermissions()
        } else {

            locationRequest = LocationRequest.create()
            locationRequest.apply {
                locationRequest.interval = 3
                locationRequest.fastestInterval = 2
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                fusedLocClient?.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.myLooper()!!
                )
            }
        }
    }
        private var locationCallback = object : LocationCallback(){

            override fun onLocationResult(p0: LocationResult) {

                val location = p0.lastLocation
                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
                val ref: DatabaseReference = database.getReference("Users")

                val latLng = LatLng(location.latitude, location.longitude)

                val myFirebaseData = location("updatedLocation.latitude, updatedLocation.longitude")
                map.clear()
                map.addMarker(MarkerOptions().position(latLng).title("You are currently here!"))!!
                    ?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.lanre))

                val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                map.moveCamera(update)

                ref.child("Ahmed").setValue(location("Ahmed", location.latitude, location.longitude))
                // if location is null , log an error message
                Log.e(TAG, "No location found")

            }
        }

//            fusedLocClient?.lastLocation?.addOnCompleteListener {
//                // lastLocation is a task running in the background
//                val location = it.result //obtain location
//                //reference to the database
//                val database: FirebaseDatabase = FirebaseDatabase.getInstance()
//                val ref: DatabaseReference = database.getReference("Users")
//                if (location != null) {
//
//                    val latLng = LatLng(location.latitude, location.longitude)
//                    // create a marker at the exact location
//                    map.addMarker(
//                        MarkerOptions().position(latLng)
//                            .title("You are currently here!")
//                    )
//                        ?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.lanre))
//                    // create an object that will specify how the camera will be updated
//                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
//
//                    map.moveCamera(update)
//                    //Save the location data to the database
//
//                    ref.child(
//                        "Ahmed"
//                    ).setValue(location("Ahmed", location.latitude, location.longitude))
//                } else {
//                    // if location is null , log an error message
//                    Log.e(TAG, "No location found")
//                }





    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //check if the request code matches the REQUEST_LOCATION
        if (requestCode == REQUEST_LOCATION)
        {
            //check if grantResults contains PERMISSION_GRANTED.If it does, call getCurrentLocation()
            if (grantResults.size == 1 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                //if it doesn`t log an error message
                Log.e(TAG, "Location permission has been denied")
            }
        }
    }

    fun getPartnerlocation(){
        //reference to the database
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val ref: DatabaseReference = database.getReference("Users")

        ref.addValueEventListener(
            object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val model = snapshot.child("Shak").getValue(location::class.java)
                    val latLng = LatLng(model?.latitude!!, model.longitude!!)
                    // create a marker at the exact location
                    map.addMarker(
                        MarkerOptions().position(latLng)
                            .title("Shak is currently here!")
                    )
                        ?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.beauty))
                    // create an object that will specify how the camera will be updated

                }

                override fun onCancelled(error: DatabaseError) {
                   Toast.makeText(applicationContext, "could not read from database", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

}

