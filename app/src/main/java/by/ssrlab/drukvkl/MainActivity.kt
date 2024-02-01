package by.ssrlab.drukvkl

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.drukvkl.client.FireClient
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

        loadData()
        setUpBottomNav()
        addGraphListener()
    }

    private fun loadData() {
        FireClient().apply {
            getCities("en") {
                mainVM.setCities(it)
            }

            getPoints("en") {
                mainVM.setPoints(it)
            }
        }
    }

    private fun setUpBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNav = binding.mainBottomNav
        bottomNav.setupWithNavController(navController)
    }

    private fun addGraphListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.map) hideHeader()
            else showHeader()
        }
    }

    private fun hideHeader() {
        mainVM.hideView(binding.mainHeader)
    }

    private fun showHeader() {
        mainVM.showView(binding.mainHeader)
    }

    fun hideBack() {
        mainVM.hideView(binding.mainBack)
    }

    fun showBack(navController: NavController) {
        mainVM.showView(binding.mainBack) {
            navController.popBackStack()
        }
    }

    fun getVM() = mainVM
}