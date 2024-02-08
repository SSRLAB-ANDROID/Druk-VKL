package by.ssrlab.drukvkl.rv.pager

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.ssrlab.drukvkl.databinding.TabExhibitBinding
import by.ssrlab.drukvkl.helpers.PARCELABLE_URI
import coil.load
import coil.transform.RoundedCornersTransformation

class TabExhibitFragment(): Fragment() {

    private lateinit var binding: TabExhibitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = TabExhibitBinding.inflate(layoutInflater)

        val imageAddress = arguments?.getString(PARCELABLE_URI)
        if (!imageAddress.isNullOrEmpty()) {
            binding.tabImage.load(Uri.parse(imageAddress)) {
                crossfade(true)
                transformations(RoundedCornersTransformation(20f))
            }
        }

        return binding.root
    }
}