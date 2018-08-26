package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by zyhang on 2018/8/22.14:14
 */

public final class LinkedASPlugin {

    private static final String TAG = "LinkedAS";
    @Nullable
    private static volatile LinkedAccessibilityService linkedAccessibilityService;
    private static volatile boolean logEnable;
    @Nullable
    private static volatile Predicate beforeExecutePredicate;

    public static void setLinkedAccessibilityService(@NonNull LinkedAccessibilityService service) {
        LinkedASPlugin.linkedAccessibilityService = service;
    }

    @Nullable
    public static LinkedAccessibilityService getLinkedAccessibilityService() {
        return LinkedASPlugin.linkedAccessibilityService;
    }

    public static void enableLogger(boolean enable) {
        LinkedASPlugin.logEnable = enable;
    }

    @Nullable
    public static Predicate getBeforeExecutePredicate() {
        return LinkedASPlugin.beforeExecutePredicate;
    }

    public static void setBeforeExecutePredicate(@NonNull Predicate predicate) {
        LinkedASPlugin.beforeExecutePredicate = predicate;
    }

    static void log(String msg) {
        if (LinkedASPlugin.logEnable) {
            Log.i(LinkedASPlugin.TAG, msg);
        }
    }

    public interface Predicate {
        /**
         * Test the given input value and return a boolean.
         *
         * @param accessibilityService the accessibilityService
         * @param accessibilityEvent   current accessibilityEvent
         * @param situation            current situation
         * @return the boolean result
         */
        boolean test(AccessibilityService accessibilityService, AccessibilityEvent accessibilityEvent, Situation situation);
    }
}
