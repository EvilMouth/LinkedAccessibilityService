package com.zyhang.linkedaccessibilityservice.example.situations

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.zyhang.linkedaccessibilityservice.Situation

/**
 * Created by zyhang on 2018/8/22.15:54
 */

class RedPackageDetailSituation : Situation {
    override fun eventTypes(): Int {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    }

    override fun match(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        return "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI" == accessibilityEvent.className.toString()
    }

    override fun execute(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        return accessibilityService.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }

    override fun nextSituations(): Array<Situation> {
        return arrayOf(ConversationToReceiveSituation())
    }
}
