package by.ssrlab.drukvkl.vm

import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.ssrlab.drukvkl.MainActivity
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.db.City

@Suppress("StaticFieldLeak")
class MainVM(private val activity: MainActivity): ViewModel() {

    class Factory(private val activity: MainActivity): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainVM(activity) as T
        }
    }
    
    private var citiesList = ArrayList<City>()
    var isCitiesLoaded = false

    fun changeViewVisibility(view: View, toShow: Boolean = false, action: (() -> Unit)? = null) {
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

    fun setCities(list: ArrayList<City>) {
        citiesList = list
        isCitiesLoaded = true
    }

    fun getCities() = citiesList
}