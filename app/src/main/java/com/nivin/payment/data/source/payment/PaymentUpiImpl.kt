package com.nivin.payment.data.source.payment

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.nivin.payment.common.showToast
import com.nivin.payment.data.model.request.PaymentUpiRequest
import com.nivin.payment.domain.source.payment.PaymentUpi


/**
 * Impl of [PaymentUpi]
 */
class PaymentUpiImpl : PaymentUpi {
    companion object{
        const val TAG = "RequestPayment"
        const val REQUEST_CODE = 1001
    }

    override fun requestPayment(activity: AppCompatActivity,paymentUpiRequest: PaymentUpiRequest) {
        val uri: Uri = Uri.Builder()
            .scheme("upi")
            .authority("pay")
            .appendQueryParameter("pa", paymentUpiRequest.virtualId) // virtual ID
            .appendQueryParameter("pn", paymentUpiRequest.merchantName) // name
            .appendQueryParameter("tn", paymentUpiRequest.note) // any note about payment
            .appendQueryParameter("am", paymentUpiRequest.amount) // amount
            .appendQueryParameter("cu", paymentUpiRequest.currencyCode) // currency
            .build()

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = uri
        intent.setPackage(paymentUpiRequest.upiAppPackageName)
        if (null != intent.resolveActivity(activity.packageManager)) {
            activity.startActivityForResult(intent, REQUEST_CODE)
        } else {
            activity.showToast("Cannot find the app, please choose another payment method")
        }
    }


}