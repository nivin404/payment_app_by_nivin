package com.nivin.payment.presentation.payment

import com.nivin.nivinecommerceapp.R
import com.nivin.payment.presentation.payment.models.PaymentItem
import com.nivin.payment.presentation.payment.models.PaymentOption
import com.nivin.payment.presentation.payment.models.PaymentType
import com.nivin.payment.presentation.payment.models.Title

/**
 * Gets the list of all the supported payment methods and group titles
 */
internal fun getPaymentMethods(): MutableList<PaymentItem> {
    val list:MutableList<PaymentItem> = mutableListOf()
    list.add(Title("UPI"))
    list.add(PaymentOption("Google pay",PaymentType.G_PAY, R.drawable.ic_gpay))
    list.add(PaymentOption("Phone pay",PaymentType.PHONE_PAY,R.drawable.ic_phone_pay))
    list.add(PaymentOption("Paytm",PaymentType.PAY_TM,R.drawable.ic_paytm))
    list.add(Title("Cards"))
    list.add(PaymentOption("Debit card",PaymentType.DEBIT,R.drawable.ic_debit))
    list.add(PaymentOption("Credit card",PaymentType.CREDIT,R.drawable.ic_credit_card))
    return list
}