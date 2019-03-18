package com.example.epaylater.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.epaylater.EpayLaterApplication
import com.example.epaylater.R
import com.example.epaylater.base.BaseActivity
import com.example.epaylater.di.factory.ViewModelFactory
import com.example.epaylater.model.Error
import com.example.epaylater.model.TokenModel
import com.example.epaylater.ui.home.MainActivity
import com.example.epaylater.utlis.Constants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity: BaseActivity() {

    /**
     * inject viewModelFactory and get the instance of viewmodel
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mViewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_splash)


        mViewModel.getData()
        observeUser()
        observeProgress()
        observeError()
    }

    //check for any errors
    private fun observeError() {
        mViewModel.getErrors().observe(this, Observer {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    //show progress
    private fun observeProgress() {
        mViewModel.getProgress().observe(this, Observer {
            progressBar.visibility = it.getVisibility()
        })
    }


    //get updated token from data source
    private fun observeUser() {
        mViewModel.getTokenModel().observe(this, Observer {
            it?.let {
                when (it) {
                    is TokenModel -> {

                        Constants.TOKEN = it.token
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is Error -> mViewModel.getErrors().postValue(it.msg)
                }
            }
        })
    }

    private fun Boolean?.getVisibility(): Int = if (this != null && this) View.VISIBLE else View.GONE

}