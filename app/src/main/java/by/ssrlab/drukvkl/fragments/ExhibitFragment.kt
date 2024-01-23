package by.ssrlab.drukvkl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.ssrlab.drukvkl.databinding.FragmentExhibitionBinding
import by.ssrlab.drukvkl.fragments.base.BaseFragment

class ExhibitFragment: BaseFragment() {

    private lateinit var binding: FragmentExhibitionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExhibitionBinding.inflate(layoutInflater)

        return binding.root
    }

}