package by.ssrlab.drukvkl.fragments.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import by.ssrlab.drukvkl.MainActivity

open class BaseFragment: Fragment() {

    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity
    }

    override fun setEnterTransition(transition: Any?) {
        super.setEnterTransition(transition)
    }
}