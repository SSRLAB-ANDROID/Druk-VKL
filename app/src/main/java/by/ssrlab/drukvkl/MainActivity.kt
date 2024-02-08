package by.ssrlab.drukvkl

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.ssrlab.drukvkl.client.FireClient
import by.ssrlab.drukvkl.databinding.ActivityMainBinding
import by.ssrlab.drukvkl.helpers.LANGUAGE
import by.ssrlab.drukvkl.helpers.SHARED_PREFERENCES
import by.ssrlab.drukvkl.vm.MainVM
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainVM: MainVM by viewModels {
        MainVM.Factory(this@MainActivity, sharedPreferences, language)
    }

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navController: NavController

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var language: String

    private fun Context.setAppLocale(): Context {
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)
        language = sharedPreferences.getString(LANGUAGE, "en") ?: "en"

        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return createConfigurationContext(config)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ContextWrapper(newBase?.setAppLocale()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainLanguage.setOnClickListener { mainVM.showLanguageDialog() }
    }

    override fun onStart() {
        super.onStart()

        mainVM.setFireClient(FireClient(this@MainActivity))
        mainVM.loadCitiesAndPoints()

        setUpBottomNav()
        addGraphListener()
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