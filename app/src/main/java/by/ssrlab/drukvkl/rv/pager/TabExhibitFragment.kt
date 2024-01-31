package by.ssrlab.drukvkl.rv.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.drukvkl.databinding.TabExhibitBinding

class TabExhibitFragment: Fragment() {

    private lateinit var binding: TabExhibitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = TabExhibitBinding.inflate(layoutInflater)

        return binding.root
    }
}