package com.zyhang.linkedaccessibilityservice.example.situations

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.zyhang.linkedaccessibilityservice.LinkedASUtils
import com.zyhang.linkedaccessibilityservice.Situation

/**
 * Created by zyhang on 2018/8/22.15:54
 * 会话页
 * 打开红包
 */

class ChatSituation : Situation {
    override fun eventTypes(): Int {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    }

    override fun match(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        return "com.tencent.mm.ui.LauncherUI" == accessibilityEvent.className.toString()
                &&
                LinkedASUtils.isTextExist(accessibilityService.rootInActiveWindow, "领取红包")
    }

    override fun execute(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        return LinkedASUtils.findAndClickByText(accessibilityService.rootInActiveWindow, "领取红包", true)
    }

    override fun nextSituations(): Array<Situation> {
        return arrayOf(RedPackageFreshSituation(), RedPackageStaleSituation())
    }
}
