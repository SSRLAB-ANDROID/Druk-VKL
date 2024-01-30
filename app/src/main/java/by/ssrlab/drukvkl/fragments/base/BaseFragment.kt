package by.ssrlab.drukvkl.fragments.base

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.ssrlab.drukvkl.MainActivity
import by.ssrlab.drukvkl.helpers.CITY_TITLE

open class BaseFragment: Fragment() {

    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity
    }

    fun navigateNext(title: String, address: Int) {
        val bundle = bundleOf(Pair(CITY_TITLE, title))
        findNavController().navigate(address, bundle)
    }

    fun getTitle(): String = arguments?.getString(CITY_TITLE) ?: ""
}