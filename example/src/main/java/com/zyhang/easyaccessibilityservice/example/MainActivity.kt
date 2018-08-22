package com.zyhang.easyaccessibilityservice.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zyhang.easyaccessibilityservice.EasyASPlugin
import com.zyhang.easyaccessibilityservice.EasyASUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EasyASPlugin.setLogEnable(true)
        EasyASPlugin.setBeforeExecutePredicate { _, _, situation ->
            println("situation === ${situation::class.java.simpleName}")
            true
        }
    }

    fun toSetting(view: View) {
        EasyASUtils.toAccessibilitySetting(this)
    }

    fun toWeChat(view: View) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("weixin://"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

}
