package com.zyhang.linkedaccessibilityservice.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zyhang.linkedaccessibilityservice.LinkedASPlugin
import com.zyhang.linkedaccessibilityservice.LinkedASUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LinkedASPlugin.enableLogger(true)
        LinkedASPlugin.setBeforeExecutePredicate { _, _, situation ->
            println("situation === ${situation::class.java.simpleName}")
            true
        }
    }

    fun resetSituation(view: View) {
        LinkedASPlugin.getLinkedAccessibilityService()?.reset()
    }

    fun toSetting(view: View) {
        LinkedASUtils.toAccessibilitySetting(this)
    }

    fun toWeChat(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("weixin://"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

}
