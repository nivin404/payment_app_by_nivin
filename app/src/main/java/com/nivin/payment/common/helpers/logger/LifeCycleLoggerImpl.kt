package com.nivin.payment.common.helpers.logger

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleEventObserver

private val TAG = LifeCycleLoggerImpl::class.simpleName

/**
 * Implementation of LifecycleLogger using LifeCycleObserver
 */
class LifeCycleLoggerImpl : LifeCycleLogger {
    private var activityName: String? = null
    private val lifecycleObserver :LifecycleEventObserver by lazy {
        LifecycleEventObserver { source, event ->
            Log.d(TAG, "$activityName is on $event now")
        }
    }

    /**
     * Attaches the activity with the lifecycleObserver
     */
    override fun registerLifeCycleEvents(activity: AppCompatActivity) {
        activityName = activity.localClassName
        activity.lifecycle.addObserver(lifecycleObserver)
    }
}