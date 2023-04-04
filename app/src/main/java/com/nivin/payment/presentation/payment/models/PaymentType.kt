package com.nivin.payment.presentation.payment.models

internal enum class PaymentType(val packageName:String? = null) {
    G_PAY("com.google.android.apps.nbu.paisa.user"),
    PHONE_PAY("com.phonepe.app"),
    PAY_TM("net.one97.paytm"),
    CREDIT,DEBIT,COD
}