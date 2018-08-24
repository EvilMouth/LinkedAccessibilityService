package com.zyhang.linkedaccessibilityservice.example.situations

import android.accessibilityservice.AccessibilityService
import android.app.Notification
import android.view.accessibility.AccessibilityEvent
import com.zyhang.linkedaccessibilityservice.Situation

/**
 * Created by zyhang on 2018/8/22.15:54
 * 通知栏通知
 * 打开通知
 */

class NotificationSituation : Situation {
    override fun eventTypes(): Int {
        return AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
    }

    override fun match(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        if ("android.app.Notification" == accessibilityEvent.className.toString()) {
            val list = accessibilityEvent.text
            println("NotificationSituation list === $list")
            list.forEach {
                if (it.contains("[微信红包]")) {
                    return true
                }
            }
        }
        return false
    }

    override fun execute(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        try {
            val parcelableData = accessibilityEvent.parcelableData
            val pendingIntent = (parcelableData as Notification).contentIntent
            pendingIntent.send()
            return true
        } catch (e: Exception) {
            // do nothing
        }
        return false
    }

    override fun nextSituations(): Array<Situation> {
        return arrayOf(ChatSituation())
    }
}
