package com.example.autorespondercompose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class IncomingCallReceiver : BroadcastReceiver() {


    private var hasMessageBeenSent = false

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val callState = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val callerNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (callState == TelephonyManager.EXTRA_STATE_RINGING && callerNumber != null) {
                val preferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                val storedPhoneNumber = preferences.getString("phoneNumber", "")
                val storedMessage = preferences.getString("message", "")

                if (callerNumber == storedPhoneNumber && !hasMessageBeenSent) {
                    Log.d("IncomingCallReceiver", "Matching number detected: $callerNumber")
                    sendAutoReplySMS(context, callerNumber, storedMessage ?: "")
                    hasMessageBeenSent = true
                }
            } else if (callState == TelephonyManager.EXTRA_STATE_IDLE) {
                hasMessageBeenSent = false
            }
        }
    }

    private fun sendAutoReplySMS(context: Context, recipientNumber: String, replyMessage: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(recipientNumber, null, replyMessage, null, null)
            Log.d("IncomingCallReceiver", "Auto-reply sent: $replyMessage")
        } catch (e: Exception) {
            Log.e("IncomingCallReceiver", "Failed to send SMS: ${e.message}")
        }
    }
}
