package by.ssrlab.drukvkl.client

import by.ssrlab.drukvkl.db.City
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FireClient {

    private val fireStore = Firebase.firestore

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
}