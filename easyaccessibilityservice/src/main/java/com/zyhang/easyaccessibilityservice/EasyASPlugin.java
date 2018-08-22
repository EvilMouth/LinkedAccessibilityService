package com.zyhang.easyaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by zyhang on 2018/8/22.14:14
 */

public final class EasyASPlugin {

    private static final String TAG = "EasyAS";
    private static volatile boolean logEnable;
    @Nullable
    private static volatile Predicate beforeExecutePredicate;

    public static void setLogEnable(boolean enable) {
        logEnable = enable;
    }

    @Nullable
    public static Predicate getBeforeExecutePredicate() {
        return beforeExecutePredicate;
    }

    public static void setBeforeExecutePredicate(@NonNull Predicate predicate) {
        beforeExecutePredicate = predicate;
    }

    static void log(String msg) {
        if (logEnable) {
            Log.i(TAG, msg);
        }
    }

    public interface Predicate {
        /**
         * Test the given input value and return a boolean.
         *
         * @return the boolean result
         */
        boolean test(AccessibilityService accessibilityService, AccessibilityEvent accessibilityEvent, Situation situation);
    }
}
