package com.example.epaylater.ui.home.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.epaylater.EpayLaterApplication
import com.example.epaylater.R
import com.example.epaylater.di.factory.ViewModelFactory
import com.example.epaylater.model.BalanceError
import com.example.epaylater.model.BalanceModel
import com.example.epaylater.ui.home.viewmodel.BalanceViewModel
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_current_balance.*
import javax.inject.Inject



class CurrentBalanceFragment: Fragment() {

    /**
     * inject viewModelFactory and get the instance of viewmodel
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mViewModel: BalanceViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelFactory)[BalanceViewModel::class.java]
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    //inflate layout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_current_balance, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //perform viewmodel operations
        mViewModel.getData()
        observeBalance()
        observeProgress()
        observeError()
    }

    //Observe error
    private fun observeError() {
        mViewModel.getErrors().observe(this, Observer {
            it?.let {
                Toast.makeText(activity!!, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    //Show progress
    private fun observeProgress() {
        mViewModel.getProgress().observe(this, Observer {
            progressBar.visibility = it.getVisibility()
        })
    }

    //Get balance updated data and update UI
    private fun observeBalance() {
        mViewModel.getBalanceModel().observe(this, Observer {
            it?.let {
                when (it) {
                    is BalanceModel -> {
                        mViewModel.getBalanceModel().postValue(it)
                         currentblinfoTV.visibility = View.VISIBLE
                         currentblTV.visibility = View.VISIBLE
                         currentblTV.text = String.format("%s %s", it.balance, it.currency)

                    }
                    is BalanceError -> {
                        mViewModel.getErrors().postValue(it.msg)
                        currentblinfoTV.visibility = View.GONE
                        currentblTV.visibility = View.VISIBLE

                         currentblTV.text = it.msg

                    }
                }
            }
        })
    }

    private fun Boolean?.getVisibility(): Int = if (this != null && this) View.VISIBLE else View.GONE
}