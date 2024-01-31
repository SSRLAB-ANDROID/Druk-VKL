package by.ssrlab.drukvkl.rv.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabExhibitAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return TabExhibitFragment()
    }
}