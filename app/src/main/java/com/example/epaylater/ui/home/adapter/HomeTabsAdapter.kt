package com.example.epaylater.ui.home.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.epaylater.ui.home.fragments.CurrentBalanceFragment
import com.example.epaylater.ui.home.fragments.TransactionListFragment

class HomeTabsAdapter(fm: FragmentManager, val mNumOfTabs: Int): FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return mNumOfTabs
    }

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return CurrentBalanceFragment()
            }
            1 -> {
                return TransactionListFragment()
            }
            else -> return null
        }
    }
}