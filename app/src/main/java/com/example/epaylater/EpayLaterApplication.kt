package com.example.epaylater

import android.app.Application
import dagger.android.HasActivityInjector
import android.app.Activity
import com.example.epaylater.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject



/**
 * Base Application class for this application
 * All initialization goes here related to complete app
 */
class EpayLaterApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

}