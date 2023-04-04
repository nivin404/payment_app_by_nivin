package com.nivin.payment.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.nivin.payment.common.errors.UnknownException
import kotlin.reflect.KClass

/**
 * opens the next activity, with flags CLEAR_TOP & SINGLE_TOP
 * if [closeCurrent] is true the currentActivity will finish
 */
fun Context.openNextPage(classType:KClass<out Any>,closeCurrent:Boolean = false){
    val intent = Intent(this,classType.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    startActivity(intent)
    if(closeCurrent && this is Activity)finish()
}

/**
 * shows the toast
 */
fun Context.showToast(message:String){
    Toast.makeText(this, message,Toast.LENGTH_LONG).show()
}

/**
 * shows toast for exceptions
 */
fun Context.showErrorToast(exception:Exception?){
    val message: String = exception?.message?:"Something went wrong!"
    showToast(message)
}

/**
 * provides UnknownException if the provided exception is null
 */
fun Exception?.checkUnknown(): Exception{
    return this ?: UnknownException()
}
