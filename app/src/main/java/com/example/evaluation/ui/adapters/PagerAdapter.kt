package com.example.evaluation.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.example.evaluation.ui.activities.BaseActivity

class PagerAdapter(private val myContext: Context, fm: FragmentManager, private var searchQuery : String) :
    FragmentStatePagerAdapter(fm) {

    fun updateSearchQuery(query : String){
        searchQuery = query

    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> (myContext as BaseActivity).navigationController?.launchPhotosFragment(searchQuery)!!
            1 -> (myContext as BaseActivity).navigationController?.launchVideosFragment(searchQuery)!!
            else -> (myContext as BaseActivity).navigationController?.launchFavoritesFragment()!!
        }
    }

    override fun getCount(): Int {
       return 3
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }


}