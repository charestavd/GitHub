package com.example.github.Search.Detail.Fragment

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.github.R
import com.example.submission3.fragment.FollowersFragment
import com.example.submission3.fragment.FollowingFragment


class SectionPagerAdapter (private val mCtx : Context, fm : FragmentManager, data : Bundle)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private  var fragmentBundle : Bundle
    init {
        fragmentBundle = data
    }

    @StringRes
    private  val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)


    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when (position){
            0-> fragment = FollowersFragment()
            1-> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return  fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLES[position])
    }
}