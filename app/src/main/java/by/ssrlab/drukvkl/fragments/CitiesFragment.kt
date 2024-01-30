package by.ssrlab.drukvkl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.databinding.FragmentCitiesListBinding
import by.ssrlab.drukvkl.db.City
import by.ssrlab.drukvkl.fragments.base.BaseFragment
import by.ssrlab.drukvkl.helpers.CITY_TITLE
import by.ssrlab.drukvkl.rv.CitiesListAdapter

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

        initAdapter()
        mainActivity.hideBack()
    }

    private fun initAdapter() {
        val list = ArrayList<City>()

        for (i in 1..7)
            list.add(City(i, i, "City $i"))

        citiesAdapter = CitiesListAdapter(list) {
            navigateNext(it)
        }

        binding.citiesRv.apply {
            layoutManager = LinearLayoutManager(mainActivity)
            adapter = citiesAdapter
        }
    }

    private fun navigateNext(title: String) {
        val bundle = bundleOf(Pair(CITY_TITLE, title))
        findNavController().navigate(R.id.action_citiesFragment_to_placesFragment, bundle)
    }
}