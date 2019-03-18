package com.example.epaylater.di

import com.example.epaylater.base.BaseActivity
import com.example.epaylater.ui.home.MainActivity
import com.example.epaylater.ui.home.fragments.CurrentBalanceFragment
import com.example.epaylater.ui.home.fragments.TransactionListFragment
import com.example.epaylater.ui.spend.SpendActivity
import com.example.epaylater.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
    @ContributesAndroidInjector
    abstract fun spendActivity(): SpendActivity

}