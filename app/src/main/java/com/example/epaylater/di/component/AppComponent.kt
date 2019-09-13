package com.example.epaylater.di.component

import android.app.Application
import com.example.epaylater.EpayLaterApplication
import com.example.epaylater.di.ActivityModule
import com.example.epaylater.di.FragmentModule
import com.example.epaylater.di.NetworkModule
import com.example.epaylater.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@SuppressWarnings("unchecked")
@Component(modules = [NetworkModule::class,
    ViewModelModule::class,
    ActivityModule::class,
    FragmentModule::class,
    AndroidSupportInjectionModule::class])
@Singleton
interface AppComponent {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


    fun inject(appController: EpayLaterApplication)
}