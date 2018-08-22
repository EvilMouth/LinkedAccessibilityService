package com.zyhang.easyaccessibilityservice.example.situations

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.zyhang.easyaccessibilityservice.EasyASUtils
import com.zyhang.easyaccessibilityservice.Situation

/**
 * Created by zyhang on 2018/8/22.15:54
 */

class ClickRedPackageMessageSituation : Situation {
    override fun eventTypes(): Int {
        return AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
    }

    override fun match(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        return "com.tencent.mm.ui.LauncherUI" == accessibilityEvent.className.toString()
                &&
                EasyASUtils.isTextExist(accessibilityService.rootInActiveWindow, "领取红包")
    }

    override fun execute(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        return EasyASUtils.findAndClickByText(accessibilityService.rootInActiveWindow, "领取红包", true)
    }

    override fun nextSituations(): Array<Situation> {
        return arrayOf(OpenRedPackageSituation())
    }
}
