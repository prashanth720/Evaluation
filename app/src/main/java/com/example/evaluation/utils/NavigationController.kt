package com.example.evaluation.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.evaluation.R
import com.example.evaluation.network.models.Photo
import com.example.evaluation.ui.fragments.*

class NavigationController(var context: Context, private var fragmentManager : FragmentManager) {


    fun launchHomeFragment(){
        FragmentHelper.replace(fragmentManager, R.id.fragment_container, HomeFragment())
    }

    fun launchPhotosFragment(searchQuery : String) : Fragment{
        return PhotosFragment.newInstance(searchQuery)
    }

    fun launchVideosFragment(searchQuery : String) : Fragment {
        return VideosFragment.newInstance(searchQuery)
    }

    fun launchFavoritesFragment() : Fragment {
       return FavoritesFragment();
    }

    fun launchDetailsFragment(item : Photo){
        FragmentHelper.addToBackStackAndAddWithAnimation(
            fragmentManager, R.id.fragment_container,
            DetailsFragment.newInstance(item), R.anim.enter_from_left, R.anim.exit_to_right
        )
    }
}