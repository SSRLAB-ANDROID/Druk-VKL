package by.ssrlab.drukvkl.rv.pager

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabExhibitAdapter(
    fragment: FragmentActivity,
    private val list: ArrayList<Uri>
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return TabExhibitFragment(list[position])
    }
}