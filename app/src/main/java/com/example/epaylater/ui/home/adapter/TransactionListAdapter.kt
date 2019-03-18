package com.example.epaylater.ui.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.epaylater.R
import com.example.epaylater.model.TransactionResponse
import com.example.epaylater.utlis.DateConverter
import kotlinx.android.synthetic.main.row_item_transaction.view.*

class TransactionListAdapter: RecyclerView.Adapter<TransactionListAdapter.TransactionItem>(){

    var transList: ArrayList<TransactionResponse>? = null
    var context: Context? = null

    override fun onBindViewHolder(holder: TransactionItem, position: Int) {

        /**
         * set data to view holder views for each row
         */
        if (transList!!.isNotEmpty()) {

            val data = transList!![position]
            holder.transIdTV.text = String.format("%s %s", "Transaction ID : ", data.id)
            holder.descriptionTV.text = String.format("%s %s", "Description : ", data.description)
            holder.amountTV.text = String.format("%s %s %s", "Transaction Amount : ", data.amount, data.currency)
            holder.dateTV.text = String.format("%s", DateConverter.getDate(data.date))
        }
    }

    /**
     * inflate row item layout
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItem {
        this.context = parent.context
        return TransactionItem(LayoutInflater.from(parent.context).inflate(R.layout.row_item_transaction, parent, false))
    }

    /**
     * set recyclerview list size
     */
    override fun getItemCount(): Int {
        return if (transList != null) transList!!.size else 0
    }


    /**
     * get updated list of transactions
     */
    fun setTransactionItems(itemList: List<TransactionResponse>) {
        this.transList = itemList.toCollection(ArrayList())
        notifyDataSetChanged()
    }

    /**
     * TransactionItem class is a viewholder class
     * use for creating view objects
     */
    inner class TransactionItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transIdTV = itemView.transIdTV
        val descriptionTV = itemView.descriptionTV
        val amountTV = itemView.amountTV
        val dateTV = itemView.dateTV
    }
}