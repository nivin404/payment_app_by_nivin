package com.nivin.payment.presentation.payment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nivin.nivinecommerceapp.R
import com.nivin.nivinecommerceapp.databinding.ActivityPaymentBinding
import com.nivin.nivinecommerceapp.databinding.ItemPaymentOptionBinding
import com.nivin.nivinecommerceapp.databinding.ItemPaymentTitleBinding
import com.nivin.payment.common.helpers.logger.LifeCycleLogger
import com.nivin.payment.common.helpers.logger.LifeCycleLoggerImpl
import com.nivin.payment.domain.source.payment.PaymentRazorPay
import com.nivin.payment.data.source.payment.PaymentRazorPayImpl
import com.nivin.payment.domain.source.payment.PaymentUpi
import com.nivin.payment.data.source.payment.PaymentUpiImpl
import com.nivin.payment.common.showErrorToast
import com.nivin.payment.common.showToast
import com.nivin.payment.common.ui.MyRecyclerAdapter
import com.nivin.payment.presentation.payment.models.PaymentItem
import com.nivin.payment.presentation.payment.models.PaymentOption
import com.nivin.payment.presentation.payment.models.Title
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity(), LifeCycleLogger by LifeCycleLoggerImpl() ,
    PaymentUpi by PaymentUpiImpl(),
    PaymentRazorPay by PaymentRazorPayImpl(),PaymentResultWithDataListener{
    private val binding: ActivityPaymentBinding by lazy {
        return@lazy ActivityPaymentBinding.inflate(layoutInflater)
    }
    private val viewModel: PaymentViewModel by viewModels()
    private val paymentMethodsList: MutableList<PaymentItem> = getPaymentMethods()
    private val myRecyclerAdapter: MyRecyclerAdapter<PaymentItem> by lazy {
        MyRecyclerAdapter(binding.recyclerView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        registerLifeCycleEvents(this)
        binding.viewModel = viewModel
        viewModel.getPaymentForwardFlow().onEach {
           requestPayment(this,it)
        }.launchIn(lifecycleScope)

        viewModel.getErrorFlow().onEach {
            showErrorToast(it)
        }.launchIn(lifecycleScope)

        myRecyclerAdapter.setOnCreateViewHolder {
            return@setOnCreateViewHolder if (it is PaymentOption) R.layout.item_payment_option
            else R.layout.item_payment_title
        }
        myRecyclerAdapter.setOnBindViewHolder { binding, dataModel, position ->
            when (binding) {
                is ItemPaymentTitleBinding -> {
                    binding.tvGroupName.text = (dataModel as Title).name
                    binding.root.isClickable = false
                }
                is ItemPaymentOptionBinding -> {
                    binding.radio.isClickable = true
                    binding.tvName.text = (dataModel as PaymentOption).name
                    binding.img.setImageDrawable(getDrawable(dataModel.imgResId))
                    binding.radio.isChecked = dataModel.isSelected
                }
            }
        }
        myRecyclerAdapter.setOnItemClickChange { previousItem, selectedItem ->
            if(previousItem is PaymentOption){
                previousItem.isSelected = false
            }
            if(selectedItem is PaymentOption){
                selectedItem.isSelected = true
            }
        }
        myRecyclerAdapter.notifyDataSetChanged(paymentMethodsList)
        binding.btPay.setOnClickListener {
            val position:Int? = myRecyclerAdapter.selectedItemPosition
            if(position == null){
                showToast("Please select any of the provided payment methods")
                return@setOnClickListener
            }
            viewModel.requestForwardPayment(paymentMethodsList[position] as PaymentOption)
        }
    }

    private fun updateError(error: Exception?) {
        showErrorToast(error)
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) =
        viewModel.requestPaymentSuccess(p0,p1)

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) =
        viewModel.requestUpdateError(p0,p1,p2)

}