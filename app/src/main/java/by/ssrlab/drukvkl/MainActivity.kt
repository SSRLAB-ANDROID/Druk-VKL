package by.ssrlab.drukvkl

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.drukvkl.databinding.ActivityMainBinding
import by.ssrlab.drukvkl.vm.MainVM
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainVM: MainVM by viewModels {
        MainVM.Factory(this@MainActivity)
    }

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        setUpBottomNav()
        addGraphListener()
    }

    private fun setUpBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNav = binding.mainBottomNav
        bottomNav.apply {
            inflateMenu(R.menu.main_bottom_nav_menu)
            setupWithNavController(navController)
        }
    }

    private fun setTransparentStatusBar(isTransparent: Boolean = false) {
        val window = window
        if (!isTransparent) {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    private fun addGraphListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.map) {
                hideHeader()
                setTransparentStatusBar(true)
            } else {
                showHeader()
                setTransparentStatusBar()
            }
        }
    }

    private fun hideHeader() {
        if (binding.mainHeader.visibility == View.VISIBLE)
            mainVM.changeViewVisibility(binding.mainHeader, true)
    }

    private fun showHeader() {
        if (binding.mainHeader.visibility == View.GONE)
            mainVM.changeViewVisibility(binding.mainHeader)
    }

    fun hideBack() {
        if (binding.mainBack.visibility == View.VISIBLE)
            mainVM.changeViewVisibility(binding.mainBack)
    }

    fun showBack(navController: NavController) {
        if (binding.mainBack.visibility == View.GONE) {
            mainVM.changeViewVisibility(binding.mainBack, true) {
                navController.popBackStack()
            }
        }
    }
}