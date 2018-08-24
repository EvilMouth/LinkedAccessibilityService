package com.zyhang.linkedaccessibilityservice.example.situations

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.zyhang.linkedaccessibilityservice.LinkedASUtils
import com.zyhang.linkedaccessibilityservice.Situation

/**
 * Created by zyhang on 2018/8/22.15:54
 * 可以打开的红包
 * 打开
 */

class RedPackageFreshSituation : Situation {
    override fun eventTypes(): Int {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    }

    override fun match(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI" == accessibilityEvent.className.toString()) {
            val list = LinkedASUtils.findAllButton(accessibilityService.rootInActiveWindow)
            if (list.size == 1) {
                return true
            }
        }
        return false
    }

    override fun execute(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        val list = LinkedASUtils.findAllButton(accessibilityService.rootInActiveWindow)
        return list[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    override fun nextSituations(): Array<Situation> {
        return arrayOf(RedPackageDetailSituation())
    }
}
