package com.zyhang.easyaccessibilityservice;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;

/**
 * Created by zyhang on 2018/8/22.15:25
 */

public class EasyASUtils {

    public static void toAccessibilitySetting(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
