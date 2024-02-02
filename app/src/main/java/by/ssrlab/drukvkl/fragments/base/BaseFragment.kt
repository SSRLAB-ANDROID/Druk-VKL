package by.ssrlab.drukvkl.fragments.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import by.ssrlab.drukvkl.MainActivity
import by.ssrlab.drukvkl.vm.MainVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseFragment: Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var mainVM: MainVM

    private val job = Job()
    val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity
        mainVM = mainActivity.getVM()
    }
}