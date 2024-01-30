package by.ssrlab.drukvkl.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.ssrlab.drukvkl.databinding.FragmentExhibitBinding
import by.ssrlab.drukvkl.fragments.base.BaseFragment
import by.ssrlab.drukvkl.rv.pager.TabExhibitAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ExhibitFragment: BaseFragment() {

    private lateinit var binding: FragmentExhibitBinding
    private lateinit var tabAdapter: TabExhibitAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExhibitBinding.inflate(layoutInflater)

        binding.exhibitTitle.text = getTitle()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        setUpTabs()
        mainActivity.showBack(findNavController())
    }

    private fun setUpTabs() {
        tabAdapter = TabExhibitAdapter(mainActivity)
        binding.exhibitPager.adapter = tabAdapter

        //tab, position
        TabLayoutMediator(binding.exhibitTab, binding.exhibitPager) { _, _ -> }.attach()
    }
}