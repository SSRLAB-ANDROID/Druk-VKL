package by.ssrlab.drukvkl.rv.pager

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.drukvkl.databinding.TabExhibitBinding
import coil.load
import coil.transform.RoundedCornersTransformation

class TabExhibitFragment(private val imageAddress: Uri): Fragment() {

    private lateinit var binding: TabExhibitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = TabExhibitBinding.inflate(layoutInflater)

        binding.tabImage.load(imageAddress) {
            crossfade(true)
            transformations(RoundedCornersTransformation(20f))
        }

        return binding.root
    }
}