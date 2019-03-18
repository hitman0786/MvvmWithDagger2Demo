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
import com.example.epaylater.model.BalanceResponse
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
        transactionViewModel()
    }

    private fun transactionViewModel() {

        mViewModel.getData().observe(activity!!, object: Observer<BalanceResponse>{
            override fun onChanged(list:BalanceResponse?) {
                if(list != null) {
                    currentblinfoTV.visibility = View.VISIBLE
                    currentblTV.visibility = View.VISIBLE
                    currentblTV.text = String.format("%s %s", list.balance, list.currency)
                }
            }
        })

        mViewModel.getError().observe(activity!!, object: Observer<Boolean>{
            override fun onChanged(isError: Boolean?) {

                if (isError != null) {
                    if(isError) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(activity!!, "Found Some Error!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        mViewModel.getLoading().observe(activity!!, object: Observer<Boolean>{
            override fun onChanged(isLoading: Boolean?) {

                if(isLoading != null){

                    if(isLoading) {
                        progressBar.visibility = View.VISIBLE
                        currentblinfoTV.visibility = View.GONE
                        currentblTV.visibility = View.GONE
                    }else{
                        progressBar.visibility = View.GONE
                        currentblinfoTV.visibility = View.VISIBLE
                        currentblTV.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

}