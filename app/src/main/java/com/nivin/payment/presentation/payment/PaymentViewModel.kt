package com.nivin.payment.presentation.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nivin.payment.common.checkUnknown
import com.nivin.payment.common.errors.EmptyDataException
import com.nivin.payment.common.util.Response
import com.nivin.payment.data.model.request.PaymentUpiRequest
import com.nivin.payment.data.model.response.PaymentDetailsResponse
import com.nivin.payment.domain.usecase.payment.GetPaymentDetailsUseCase
import com.nivin.payment.presentation.payment.models.PaymentOption
import com.razorpay.Checkout
import com.razorpay.PaymentData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PaymentViewModel @Inject constructor(
    private val getPaymentDetailsUseCase: GetPaymentDetailsUseCase
): ViewModel() {
    // references
    private var paymentDetailsResponse:PaymentDetailsResponse? = null

    // use to bind with the views
    var paymentAmount:String = "$300"

    // listen to get all the exceptions
    private val _errorFlow: MutableSharedFlow<Exception> = MutableSharedFlow()
    internal fun getErrorFlow(): SharedFlow<Exception> = _errorFlow

    // listen to forward the payment option
    private val _paymentForwardFlow: MutableSharedFlow<PaymentUpiRequest> = MutableSharedFlow()
    internal fun getPaymentForwardFlow(): SharedFlow<PaymentUpiRequest> = _paymentForwardFlow


    init {
        getPaymentDetails()
    }

    /**
     * requests to continue the payment with the provided payment option
     */
    fun requestForwardPayment(paymentOption: PaymentOption?) {
        if (paymentOption == null) {
            updateError(Exception("Please select any of the provided Payment methods to continue"))
            return
        }
        viewModelScope.launch {
            checkAndRequestPayment(paymentOption)
        }
    }

    /**
     * gets the payment details
     */
    private fun getPaymentDetails(){
        getPaymentDetailsUseCase().onEach{
            when (it) {
                is Response.Error -> updateError(it.error.checkUnknown())
                is Response.Loading -> Unit
                is Response.Success -> {
                    if (it.data == null) updateError(EmptyDataException(PaymentDetailsResponse::class))
                    else paymentDetailsResponse = it.data
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Checks the payment details is loaded and continue with payment or emits exception
     */
    private suspend fun checkAndRequestPayment(data: PaymentOption) {
        if(paymentDetailsResponse == null){
            _errorFlow.emit(Exception("Payment details not loaded yet, Please wait or try again later"))
        }else {
            checkForSupportedPaymentAndForward(data, paymentDetailsResponse!!)
        }
    }


    /**
     * Checks whether the provided payment is currently supported and forwards payment
     * else emits exception
     */
    private suspend fun checkForSupportedPaymentAndForward(
        paymentOption: PaymentOption,
        paymentDetails: PaymentDetailsResponse
    ) {
        val packageName: String? = paymentOption.paymentType.packageName
        if (packageName != null) {
            // payment through UPI apps
            val paymentUpiRequest = PaymentUpiRequest(
                paymentDetails.virtualId,
                paymentDetails.merchantName,
                paymentDetails.amount,
                paymentOption.paymentType.packageName,
                paymentDetails.note,
                paymentDetails.currencyCode)
            _paymentForwardFlow.emit(paymentUpiRequest)
        }else {
            // payment through  other methods, credit or debit
            _errorFlow.emit(Exception("This Payment method is not supported for now!"))
        }
    }

    /**
     * Emits the exceptions
     */
    private fun updateError(exception: Exception) {
        viewModelScope.launch {
            _errorFlow.emit(exception)
        }
    }


    /**
     * Payment is updates the server and updates the UI
     */
    fun requestPaymentSuccess(p0: String?, p1: PaymentData?) {
        //todo
    }

    /**
     * Payment is cancelled finds the reason and updates the server or the UI
     */
    fun requestUpdateError(errorCode: Int, p1: String?, p2: PaymentData?) {
        when (errorCode) {
            Checkout.PAYMENT_CANCELED -> Unit
            else -> {
                updateError(Exception("Payment Error, Code: $errorCode"))
            }
        }
    }
}