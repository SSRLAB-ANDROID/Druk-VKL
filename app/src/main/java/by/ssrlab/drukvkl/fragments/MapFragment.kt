package by.ssrlab.drukvkl.fragments

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.databinding.FragmentMapBinding
import by.ssrlab.drukvkl.fragments.base.BaseFragment
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
}