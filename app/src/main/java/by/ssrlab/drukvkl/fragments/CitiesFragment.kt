package by.ssrlab.drukvkl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.databinding.FragmentCitiesListBinding
import by.ssrlab.drukvkl.db.City
import by.ssrlab.drukvkl.fragments.base.BaseFragment
import by.ssrlab.drukvkl.rv.CitiesListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CitiesFragment: BaseFragment() {

    private lateinit var binding: FragmentCitiesListBinding
    private lateinit var citiesAdapter: CitiesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCitiesListBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        mainActivity.hideBack()

        scope.launch {
            while (!mainVM.isCitiesLoaded) {
                delay(500)
            }

            mainActivity.runOnUiThread {
                initAdapter()
            }
        }
    }

    private fun initAdapter() {
        val list = mainVM.getCities()

        citiesAdapter = CitiesListAdapter(list) {
            navigateNext(it, R.id.action_citiesFragment_to_placesFragment)
        }

        binding.citiesRv.apply {
            layoutManager = LinearLayoutManager(mainActivity)
            adapter = citiesAdapter
        }
    }
}