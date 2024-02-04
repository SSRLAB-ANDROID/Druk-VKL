package by.ssrlab.drukvkl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.databinding.FragmentCitiesListBinding
import by.ssrlab.drukvkl.db.Place
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

        mainVM.setPlace(Place())
        mainActivity.hideBack()

        scope.launch {
            while (!mainVM.areCitiesLoaded()) {
                delay(500)
            }

            mainActivity.runOnUiThread {
                initAdapter()
            }
        }
    }

    private fun initAdapter() {
        val list = mainVM.getCities()

        citiesAdapter = CitiesListAdapter(mainActivity, list) {
            mainVM.getPlaces(it) {
                findNavController().navigate(R.id.action_citiesFragment_to_placesFragment)
            }
        }

        binding.citiesRv.apply {
            layoutManager = LinearLayoutManager(mainActivity)
            adapter = citiesAdapter
        }
    }
}