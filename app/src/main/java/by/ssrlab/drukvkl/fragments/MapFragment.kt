package by.ssrlab.drukvkl.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.databinding.FragmentMapBinding
import by.ssrlab.drukvkl.fragments.base.BaseFragment
import by.ssrlab.drukvkl.helpers.CENTER_MAP
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MapFragment: BaseFragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(layoutInflater)

        binding.apply {
            mapLocationHolder.isEnabled = false
            mapLocationIc.isEnabled = false
        }

        initMap()

        return binding.root
    }

    private fun initMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        moveCamera()
        showMyLocation()

        scope.launch {
            while (!mainVM.arePointsLoaded()) {
                delay(500)
            }

            mainActivity.runOnUiThread {
                setPoints()
            }
        }
    }

    private fun setPoints() {
        for (i in mainVM.getPoints()) {
            val point = LatLng(i.lat, i.lng)
            map.addMarker(
                MarkerOptions()
                    .position(point)
                    .title(i.name)
                    .icon(bitMapDescriptor())
            )
        }
    }

    private fun bitMapDescriptor(): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(mainActivity, R.drawable.ic_point)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun moveCamera() {
        if (mainVM.getPlace().id == 0L) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTER_MAP, 4f))
        } else {
            val place = mainVM.getPlace()
            val point = mainVM.getPoints().find { it.id == place.id }

            val placePoint = if (point != null) {
                LatLng(point.lat, point.lng)
            } else {
                CENTER_MAP
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(placePoint, 16f))
        }
    }

    private fun showMyLocation() {
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            getPermissions()
        } else {
            map.isMyLocationEnabled = true
        }
    }

    private fun getPermissions() {
        ActivityCompat.requestPermissions(
            mainActivity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PackageManager.PERMISSION_GRANTED)
    }
}