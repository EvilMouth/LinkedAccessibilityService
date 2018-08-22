package com.zyhang.easyaccessibilityservice.example.situations

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.zyhang.easyaccessibilityservice.Situation

/**
 * Created by zyhang on 2018/8/22.15:54
 */

class OpenRedPackageSituation : Situation {
    override fun eventTypes(): Int {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    }

    override fun match(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        return "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI" == accessibilityEvent.className.toString()
    }

    override fun execute(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        accessibilityService.rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cb1").forEach {
            return it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
        return false
    }

    override fun nextSituations(): Array<Situation> {
        return arrayOf(CloseRedPackageSituation())
    }
}
