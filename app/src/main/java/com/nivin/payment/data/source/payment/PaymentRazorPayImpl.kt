package com.nivin.payment.data.source.payment

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nivin.payment.data.model.response.RazorPayKeysResponse
import com.nivin.payment.domain.source.payment.PaymentRazorPay
import com.razorpay.Checkout
import org.json.JSONObject

/**
 * Impl of [PaymentRazorPay]
 */
class PaymentRazorPayImpl : PaymentRazorPay {
    override fun requestPayment(paymentDetailsResponse: RazorPayKeysResponse , activity:AppCompatActivity) {
        Checkout.preload(activity.applicationContext)
        val checkout = Checkout()
        checkout.setKeyID(paymentDetailsResponse.apiKey)
        try {
            val options = JSONObject()
            options.put("name", paymentDetailsResponse.companyName)
            options.put("description", paymentDetailsResponse.description)
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency", paymentDetailsResponse.currency);
            options.put("order_id", paymentDetailsResponse.orderId);
            options.put("amount", paymentDetailsResponse.amount)//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email", paymentDetailsResponse.userPreEmail)
            prefill.put("contact", paymentDetailsResponse.userPrePhone)

            options.put("prefill", prefill)
            checkout.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}