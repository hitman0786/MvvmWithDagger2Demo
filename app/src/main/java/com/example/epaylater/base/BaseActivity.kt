package com.example.epaylater.base

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.TextView
import com.example.epaylater.EpayLaterApplication
import com.example.epaylater.R
import com.example.epaylater.utlis.ConnectivityReceiver

/**
 * Base class for all activity classes
 * wen can add some activity specific code here
 */
open class BaseActivity: AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    var connectivityReceiver: ConnectivityReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Register internet connectivity receiver here
         */
        connectivityReceiver = ConnectivityReceiver()

        registerReceiver(connectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )


    }

    companion object {
        var isInternetConnected: Boolean = true
    }

    override fun onResume() {
        super.onResume()

        // register connection status listener
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()

        // unregister connection status listener
        ConnectivityReceiver.connectivityReceiverListener = null
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isInternetConnected = isConnected
        if(!isConnected) {
            noInternetDialog()
        }
    }

   //Popup for internet
    private fun noInternetDialog() {
        try {
            val dialog = Dialog(this@BaseActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.no_internet_dialog)

            val settingsTV = dialog.findViewById<TextView>(R.id.settingsTV)

            settingsTV.setOnClickListener {

                //open main setting page
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }

            val retryTV = dialog.findViewById<TextView>(R.id.retryTV)

            retryTV.setOnClickListener {

                if (isInternetConnected) {
                    dialog.dismiss()
                }

            }

            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Unregister receiver
        unregisterReceiver(connectivityReceiver)
    }
}