package com.example.epaylater.ui.home


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import com.example.epaylater.EpayLaterApplication
import com.example.epaylater.R
import com.example.epaylater.base.BaseActivity
import com.example.epaylater.ui.home.adapter.HomeTabsAdapter
import com.example.epaylater.ui.spend.SpendActivity
import dagger.android.AndroidInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector



/**
 * This is home for this app
 * It has viewpager and tabs to connect with two fragments
 * Fragments are using for showing current balance and Transactions
 */
class MainActivity: BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)

        //Setup toolbar
        setSupportActionBar(toolbar)

        //Setup Tabs
        tabLayout.addTab(tabLayout.newTab().setText("Current Balance"))
        tabLayout.addTab(tabLayout.newTab().setText("Transactions"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL


        //Add adapter to viewpager for showing tabs data
        val tabsAdapter = HomeTabsAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = tabsAdapter

        //Setup tabs with viewpager
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


        makeTransactionBT.setOnClickListener {

            val intent = Intent(this, SpendActivity::class.java)
            startActivity(intent)
        }

    }


    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }


}