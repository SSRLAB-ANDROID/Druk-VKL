package by.ssrlab.drukvkl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.ssrlab.drukvkl.R
import by.ssrlab.drukvkl.databinding.FragmentPlacesListBinding
import by.ssrlab.drukvkl.fragments.base.BaseFragment
import by.ssrlab.drukvkl.rv.PlacesListAdapter

class PlacesFragment: BaseFragment() {

    private lateinit var binding: FragmentPlacesListBinding
    private lateinit var placesAdapter: PlacesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPlacesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initAdapter()
        mainActivity.showBack(findNavController())
    }

    private fun initAdapter() {
        placesAdapter = PlacesListAdapter(mainActivity) {
            findNavController().navigate(R.id.action_placesFragment_to_exhibitFragment)
        }

        binding.placesRv.apply {
            layoutManager = LinearLayoutManager(mainActivity)
            adapter = placesAdapter
        }
    }
}