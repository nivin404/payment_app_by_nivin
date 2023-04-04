package com.nivin.payment.common.helpers.logger

import androidx.appcompat.app.AppCompatActivity

/**
 * Logs the Lifecycle events
 */
interface LifeCycleLogger {
    /**
     * attaches the activity with the event logger
     */
    fun registerLifeCycleEvents(activity: AppCompatActivity)
}