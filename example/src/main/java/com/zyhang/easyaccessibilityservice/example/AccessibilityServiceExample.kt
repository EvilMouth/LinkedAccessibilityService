package com.zyhang.easyaccessibilityservice.example

import android.content.Intent
import android.net.Uri
import com.zyhang.easyaccessibilityservice.EasyAccessibilityService
import com.zyhang.easyaccessibilityservice.Situation
import com.zyhang.easyaccessibilityservice.example.situations.MainSituation

/**
 * Created by zyhang on 2018/8/22.10:50
 */

class AccessibilityServiceExample : EasyAccessibilityService() {
    override fun firstSituations(): Array<Situation> {
        return arrayOf(MainSituation())
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("zyhang://easyaccessibilityservice/"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }
}
