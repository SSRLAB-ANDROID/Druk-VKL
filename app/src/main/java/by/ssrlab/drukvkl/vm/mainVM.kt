package by.ssrlab.drukvkl.vm

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.db.City

@Suppress("StaticFieldLeak")
class MainVM(private val context: Context): ViewModel() {

    class Factory(private val context: Context): ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainVM(context) as T
        }
    }
    
    private val citiesList = MutableLiveData<City>()

    fun changeViewVisibility(view: View, toShow: Boolean = false, action: (() -> Unit)? = null) {
        val animEnter = AnimationUtils.loadAnimation(context, R.anim.anim_alpha_enter)
        val animExit = AnimationUtils.loadAnimation(context, R.anim.anim_alpha_exit)

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
}