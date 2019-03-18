package com.example.epaylater.di

import android.arch.lifecycle.ViewModel
import dagger.multibindings.IntoMap
import dagger.Binds
import android.arch.lifecycle.ViewModelProvider
import com.example.epaylater.di.factory.ViewModelFactory
import com.example.epaylater.ui.home.viewmodel.BalanceViewModel
import com.example.epaylater.ui.home.viewmodel.TransactionViewModel
import com.example.epaylater.ui.spend.SpendViewModel
import com.example.epaylater.ui.splash.LoginViewModel
import dagger.Module


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun postLoginViewModel(viewModel: LoginViewModel): ViewModel

    //Add more ViewModels here

    @Binds
    @IntoMap
    @ViewModelKey(BalanceViewModel::class)
    internal abstract fun postBalanceViewModel(viewModel: BalanceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    internal abstract fun postTransactionViewModel(viewModel: TransactionViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SpendViewModel::class)
    internal abstract fun postSpendViewModel(viewModel: SpendViewModel): ViewModel
}