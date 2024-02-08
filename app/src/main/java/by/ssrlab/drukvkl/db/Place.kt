package by.ssrlab.drukvkl.db

import android.net.Uri

data class Place(
    val id: Long = 0,
    val cityId: Long = 0,
    val name: String = "",
    val text: String = "",
    var images: ArrayList<Uri> = arrayListOf()
)
