package com.example.epaylater.ui.spend

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.epaylater.EpayLaterApplication
import com.example.epaylater.R
import com.example.epaylater.base.BaseActivity
import com.example.epaylater.di.factory.ViewModelFactory
import com.example.epaylater.model.SpendModel
import com.example.epaylater.utlis.DateConverter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_spend.*
import javax.inject.Inject

class SpendActivity: BaseActivity(){

    /**
     * inject viewModelFactory and get the instance of viewmodel
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mViewModel: SpendViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[SpendViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_spend)

       //Set current time in textview with given format
        dateTV.text = DateConverter.getDateFormatForspend(System.currentTimeMillis())

        //Call spend API
        spentBT.setOnClickListener {

            val descp = descriptionET.text.toString()
            val amount = amountET.text.toString()

            if (descp != "" && amount!= ""){

                val spend = SpendModel(
                    date = dateTV.text.toString(),
                    description = descp,
                    amount = amount,
                    currency = currencyTV.text.toString()
                )

                mViewModel.getData(spend)
                observeSpend()
                observeProgress()
                observeError()
            }

        }

        //Back to previous screen
        backIV.setOnClickListener {

            onBackPressed()
        }
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

    private fun observeSpend() {
        mViewModel.getSpendModel().observe(this, Observer {
            it?.let {

            }
        })
    }

    private fun Boolean?.getVisibility(): Int = if (this != null && this) View.VISIBLE else View.GONE
}