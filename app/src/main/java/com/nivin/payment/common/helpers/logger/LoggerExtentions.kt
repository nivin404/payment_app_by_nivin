package com.nivin.payment.common.helpers.logger

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.nivin.nivinecommerceapp.BuildConfig

/**
 * Logs the message to the console, Also shows it as a toast if it is on debug mode
 */
fun Context.logErrorWithToast(message:String, tag:String){
    if(BuildConfig.DEBUG){
        Toast.makeText(this,message,Toast.LENGTH_LONG)
    }
    Log.e(tag,message)
}
