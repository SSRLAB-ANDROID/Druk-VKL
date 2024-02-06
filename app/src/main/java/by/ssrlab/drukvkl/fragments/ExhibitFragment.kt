package by.ssrlab.drukvkl.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.ssrlab.drukvkl.databinding.FragmentExhibitBinding
import by.ssrlab.drukvkl.db.Place
import by.ssrlab.drukvkl.fragments.base.BaseFragment
import by.ssrlab.drukvkl.helpers.AppMediaPlayer
import by.ssrlab.drukvkl.rv.pager.TabExhibitAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ExhibitFragment: BaseFragment() {

    private lateinit var binding: FragmentExhibitBinding
    private lateinit var tabAdapter: TabExhibitAdapter

    private lateinit var place: Place

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExhibitBinding.inflate(layoutInflater)

        setUpData()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        mainActivity.showBack(findNavController())
    }

    override fun onStop() {
        super.onStop()

        AppMediaPlayer.pause(binding.exhibitPlay)
    }

    private fun setUpData() {
        place = mainVM.getPlace()
        binding.apply {
            exhibitTitle.text = place.name
            exhibitBody.text = place.text
        }

        mainVM.loadImagesList {
            setUpTabs(it)
        }

        mainVM.getAudioAddress("places", place.cityId.toInt(), place.id.toInt()) {
            setUpAudio(it)
        }
    }

    private fun setUpTabs(imagesUriList: ArrayList<Uri>) {
        tabAdapter = TabExhibitAdapter(mainActivity, imagesUriList)
        binding.exhibitPager.adapter = tabAdapter

        //tab, position
        TabLayoutMediator(binding.exhibitTab, binding.exhibitPager) { _, _ -> }.attach()
    }

    private fun setUpAudio(uri: Uri) {
        AppMediaPlayer.initPlayer(mainActivity, uri)
        binding.exhibitPlay.apply {
            setOnClickListener {
                AppMediaPlayer.play(binding.exhibitPlay)
            }

            visibility = View.VISIBLE
        }
    }
}