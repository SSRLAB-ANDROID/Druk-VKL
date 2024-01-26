package by.ssrlab.drukvkl

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.drukvkl.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        binding.mainHeader.apply {
            startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_alpha_hide))
            visibility = View.GONE
        }
    }

    private fun showHeader() {
        binding.mainHeader.apply {
            startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim_alpha_enter))
            visibility = View.VISIBLE
        }
    }
}