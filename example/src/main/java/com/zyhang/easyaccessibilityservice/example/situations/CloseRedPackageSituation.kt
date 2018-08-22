package com.zyhang.easyaccessibilityservice.example.situations

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.zyhang.easyaccessibilityservice.Situation

/**
 * Created by zyhang on 2018/8/22.15:54
 */

class CloseRedPackageSituation : Situation {
    override fun eventTypes(): Int {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    }

    override fun match(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        return "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI" == accessibilityEvent.className.toString()
    }

    override fun execute(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        // 回到桌面
        return accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
                &&
                accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
                &&
                accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME)
    }

    override fun nextSituations(): Array<Situation> {
        return arrayOf(NotificationSituation())
    }
}
