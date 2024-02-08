package by.ssrlab.drukvkl.rv.pager

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.ssrlab.drukvkl.helpers.PARCELABLE_URI

class TabExhibitAdapter(
    fragment: FragmentActivity,
    private val list: ArrayList<Uri>
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        val exhibitTab = TabExhibitFragment()
        val bundle = Bundle()
        bundle.putString(PARCELABLE_URI, list[position].toString())

        exhibitTab.arguments = bundle
        return exhibitTab
    }
}