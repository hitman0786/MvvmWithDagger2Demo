package com.example.epaylater.di

import com.example.epaylater.ui.home.fragments.CurrentBalanceFragment
import com.example.epaylater.ui.home.fragments.TransactionListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun currentBalanceFragment(): CurrentBalanceFragment
    @ContributesAndroidInjector
    abstract fun transactionListFragment(): TransactionListFragment
}