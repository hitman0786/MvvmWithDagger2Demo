package com.example.epaylater.ui.home.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epaylater.EpayLaterApplication
import com.example.epaylater.R
import com.example.epaylater.di.factory.ViewModelFactory
import com.example.epaylater.model.TransactionResponse
import com.example.epaylater.ui.home.adapter.TransactionListAdapter
import com.example.epaylater.ui.home.viewmodel.TransactionViewModel
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_transaction_list.*
import javax.inject.Inject


class TransactionListFragment: Fragment() {

    /**
     * inject viewModelFactory and get the instance of viewmodel
     */
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mViewModel: TransactionViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)[TransactionViewModel::class.java]
    }

    var transactionAdapter: TransactionListAdapter? = null

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    //inflate layout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //add adapter to recyclerview
        transactionListRV.layoutManager = LinearLayoutManager(activity!!)
        transactionAdapter = TransactionListAdapter()
        transactionListRV.adapter = transactionAdapter

        //perform viewmodel operations
        transactionViewModel()
    }

    private fun transactionViewModel() {

        mViewModel.getTransactions().observe(activity!!, object: Observer<List<TransactionResponse>>{
            override fun onChanged(list: List<TransactionResponse>?) {

                if(list != null) transactionAdapter!!.setTransactionItems(list)
            }
        })

        mViewModel.getError().observe(activity!!, object: Observer<Boolean>{
            override fun onChanged(isError: Boolean?) {

                if (isError != null) {
                    if(isError) {
                        nodatafoundTV.setVisibility(View.VISIBLE);
                        progressBar.visibility = View.GONE
                        transactionListRV.visibility = View.GONE
                    }else {
                        nodatafoundTV.setVisibility(View.GONE);
                        nodatafoundTV.setText(null);
                    }
                }
            }
        })

        mViewModel.getLoading().observe(activity!!, object: Observer<Boolean>{
            override fun onChanged(isLoading: Boolean?) {

                if(isLoading != null){

                if(isLoading) {
                    progressBar.visibility = View.VISIBLE
                    nodatafoundTV.visibility = View.GONE
                    transactionListRV.visibility = View.GONE
                }else{
                    progressBar.visibility = View.GONE
                    nodatafoundTV.visibility = View.GONE
                    transactionListRV.visibility = View.VISIBLE
                }
                }
            }
        })
    }
}