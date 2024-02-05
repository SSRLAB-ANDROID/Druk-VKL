package by.ssrlab.drukvkl.vm

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.ssrlab.drukvkl.MainActivity
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.client.FireClient
import by.ssrlab.drukvkl.databinding.DialogLanguageBinding
import by.ssrlab.drukvkl.db.City
import by.ssrlab.drukvkl.db.Place
import by.ssrlab.drukvkl.db.Point
import by.ssrlab.drukvkl.helpers.LANGUAGE

@Suppress("StaticFieldLeak")
class MainVM(
    private val activity: MainActivity,
    private val sharedPreferences: SharedPreferences,
    private val language: String
): ViewModel() {

    private val fireClient = FireClient(activity)

    class Factory(
        private val activity: MainActivity,
        private val sharedPreferences: SharedPreferences,
        private val language: String
    ): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainVM(activity, sharedPreferences, language) as T
        }
    }

    private fun changeLanguage(language: String, onSuccess: () -> Unit) {
        with (sharedPreferences.edit()) {
            putString(LANGUAGE, language)
            apply()
        }

        onSuccess()
    }

    /** Data loading */
    fun loadCitiesAndPoints() {
        fireClient.apply {
            getCities(language) {
                citiesList = it
                areCitiesLoaded = true
            }

            getPoints(language) {
                pointsList = it
                arePointsLoaded = true
            }
        }
    }

    fun getPlaces(city: City, onSuccess: () -> Unit) {
        fireClient.getPlaces(language, city.id.toInt()) { list ->
            currentCity = city
            currentPlaces = list
            onSuccess()
        }
    }

    fun getImageAddress(path: String, id: Int, onSuccess: (Uri) -> Unit) {
        fireClient.getImageAddress(path, id) {
            onSuccess(it)
        }
    }

    fun loadImagesList(onSuccess: (ArrayList<Uri>) -> Unit) {
        if (currentPlace.id.toInt() == 165 || currentPlace.id.toInt() == 171) {
            fireClient.getImagesAddresses("places", currentPlace.cityId.toInt(), currentPlace.id.toInt()) {
                onSuccess(it)
            }
        } else {
            fireClient.getImagesAddresses("places", currentPlace.cityId.toInt()) {
                onSuccess(it)
            }
        }
    }

    fun getAudioAddress(path: String, cityId: Int, placeId: Int, onSuccess: (Uri) -> Unit) {
        fireClient.getAudioAddress(path, cityId, placeId, language) {
            onSuccess(it)
        }
    }

    /** UI */
    private fun changeViewVisibility(view: View, toShow: Boolean = false, action: (() -> Unit)? = null) {
        val animEnter = AnimationUtils.loadAnimation(activity, R.anim.anim_alpha_enter)
        val animExit = AnimationUtils.loadAnimation(activity, R.anim.anim_alpha_exit)

        view.apply {
            if (toShow) {
                startAnimation(animEnter)
                visibility = View.VISIBLE

                if (action != null) setOnClickListener { action() }
            } else {
                startAnimation(animExit)
                visibility = View.GONE
            }
        }
    }

    fun showView(view: View, action: (() -> Unit)? = null) {
        if (view.visibility == View.GONE)
            changeViewVisibility(view, true) {
                if (action != null) action()
            }
    }

    fun hideView(view: View) {
        if (view.visibility == View.VISIBLE)
            changeViewVisibility(view)
    }

    fun showLanguageDialog() {
        val dialog = Dialog(activity)
        val dialogBinding = DialogLanguageBinding.inflate(LayoutInflater.from(activity))
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
        }

        dialogBinding.dialogLangRadioGroup.apply {
            check(
                when (language) {
                    "en" -> R.id.dialog_language_en
                    "be" -> R.id.dialog_language_be
                    else -> R.id.dialog_language_en
                }
            )
            
            setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.dialog_language_en -> {
                        changeLanguage("en") {
                            restartActivity()
                        }
                    }
                    R.id.dialog_language_be -> {
                        changeLanguage("be") {
                            restartActivity()
                        }
                    }
                }
            }
        }

        val width = activity.resources.displayMetrics.widthPixels
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.apply {
            copyFrom(dialog.window?.attributes)
            this.width = width - (width / 10)
            dialog.window?.attributes = layoutParams
        }

        dialog.show()
    }

    @Suppress("DEPRECATION")
    private fun restartActivity() {
        activity.apply {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
            overridePendingTransition(0, 0)
        }
    }

    /** Common cities */
    private var citiesList = ArrayList<City>()
    private var areCitiesLoaded = false
    fun getCities() = citiesList
    fun areCitiesLoaded() = areCitiesLoaded

    /** Common points */
    private var pointsList = ArrayList<Point>()
    private var arePointsLoaded = false
    fun getPoints() = pointsList
    fun arePointsLoaded() = arePointsLoaded

    /** Current city */
    private var currentCity = City()
    fun getCity() = currentCity

    /** Current places */
    private var currentPlaces = ArrayList<Place>()
    fun getPlaces() = currentPlaces

    /** Current place */
    private var currentPlace = Place()
    fun setPlace(place: Place) {
        currentPlace = place
    }
    fun getPlace() = currentPlace
}