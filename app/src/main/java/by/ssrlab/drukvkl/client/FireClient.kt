package by.ssrlab.drukvkl.client

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import by.ssrlab.drukvkl.MainActivity
import by.ssrlab.drukvkl.db.City
import by.ssrlab.drukvkl.db.Place
import by.ssrlab.drukvkl.db.Point
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

class FireClient(private val activity: MainActivity? = null) {

    private val fireStore = Firebase.firestore
    private val storage = FirebaseStorage.getInstance().reference

    fun getCities(language: String, onSuccess: (ArrayList<City>) -> Unit) {
        fireStore.collection("cities").document(language).collection("ids")
            .get()
            .addOnSuccessListener { result ->
                val list = arrayListOf<City>()

                for (document in result) {
                    val docId = document.id
                    val docData = document.data

                    if (docData["visible"] as Boolean) {
                        val city = City(
                            docId.toLong(),
                            docData["id_locale"] as Long,
                            docData["name"] as String
                        )

                        list.add(city)
                    }
                }

                onSuccess(list)
            }
    }

    fun getPlaces(language: String, cityId: Int, onSuccess: (ArrayList<Place>) -> Unit) {
        fireStore.collection("places").document(language).collection(cityId.toString())
            .get()
            .addOnSuccessListener { result ->
                val list = arrayListOf<Place>()

                for (document in result) {
                    val docId = document.id
                    val docData = document.data

                    if (docData["visible"] as Boolean) {
                        val place = Place(
                            docId.toLong(),
                            cityId.toLong(),
                            docData["name"] as String,
                            docData["text"] as String
                        )

                        list.add(place)
                    }
                }

                onSuccess(list)
            }
    }

    fun getPoints(language: String, onSuccess: (ArrayList<Point>) -> Unit) {
        fireStore.collection("points_$language")
            .get()
            .addOnSuccessListener { result ->
                val list = arrayListOf<Point>()

                for (document in result) {
                    val docId = document.id
                    val docData = document.data

                    val point = Point(
                        docId.toLong(),
                        docData["name"] as String,
                        docData["lat"] as Double,
                        docData["lng"] as Double
                    )

                    list.add(point)
                }

                onSuccess(list)
            }
    }

    fun getImageAddress(path: String, imageId: Int, onSuccess: (Uri) -> Unit) {
        storage.child("$path/$imageId.png").downloadUrl.addOnSuccessListener {
            onSuccess(it)
        }
    }

    fun getImagesAddresses(path: String, cityId: Int, onSuccess: (ArrayList<Uri>) -> Unit) {
        storage.child("$path/$cityId/images/").listAll().addOnSuccessListener { listResult ->
            val list = ArrayList<Uri>()
            val isDataLoaded = MutableLiveData<Boolean>()
            for (i in listResult.items) i.downloadUrl.addOnSuccessListener { uri ->
                list.add(uri)

                isDataLoaded.value = list.size == listResult.items.size
            }

            isDataLoaded.observe(activity!!) {
                if (it) onSuccess(list)
            }
        }
    }

    fun getImagesAddresses(path: String, cityId: Int, placeId: Int, onSuccess: (ArrayList<Uri>) -> Unit) {
        storage.child("$path/$cityId/images/$placeId/").listAll().addOnSuccessListener { listResult ->
            val list = ArrayList<Uri>()
            val isDataLoaded = MutableLiveData<Boolean>()
            for (i in listResult.items) i.downloadUrl.addOnSuccessListener { uri ->
                list.add(uri)

                isDataLoaded.value = list.size == listResult.items.size
            }

            isDataLoaded.observe(activity!!) {
                if (it) onSuccess(list)
            }
        }
    }

    fun getAudioAddress(path: String, cityId: Int, placeId: Int, onSuccess: (Uri) -> Unit) {
        storage.child("$path/$cityId/audio/$placeId.mp3").downloadUrl.addOnSuccessListener { uri ->
            onSuccess(uri)
        }
    }

    fun getAudioAddress(path: String, cityId: Int, placeId: Int, audioId: Int, onSuccess: (Uri) -> Unit) {
        storage.child("$path/$cityId/audio/$placeId/$audioId.mp3").downloadUrl.addOnSuccessListener { uri ->
            onSuccess(uri)
        }
    }
}