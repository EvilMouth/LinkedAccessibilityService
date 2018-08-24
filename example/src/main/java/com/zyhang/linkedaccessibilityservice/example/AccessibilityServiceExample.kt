package com.zyhang.linkedaccessibilityservice.example

import android.content.Intent
import android.net.Uri
import com.zyhang.linkedaccessibilityservice.LinkedAccessibilityService
import com.zyhang.linkedaccessibilityservice.Situation
import com.zyhang.linkedaccessibilityservice.example.situations.NotificationSituation

/**
 * Created by zyhang on 2018/8/22.10:50
 */

class AccessibilityServiceExample : LinkedAccessibilityService() {
    override fun firstSituations(): Array<Situation> {
        return arrayOf(NotificationSituation())
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("zyhang://linkedaccessibilityservice/"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
}
