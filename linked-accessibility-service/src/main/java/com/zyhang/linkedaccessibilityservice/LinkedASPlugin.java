package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by zyhang on 2018/8/22.14:14
 */

public final class LinkedASPlugin {

    @Nullable
    private static volatile LinkedAccessibilityService linkedAccessibilityService;
    @Nullable
    private static volatile LogCallback logCallback;
    @Nullable
    private static volatile Predicate beforeExecutePredicate;

    public static void setLinkedAccessibilityService(@NonNull LinkedAccessibilityService service) {
        LinkedASPlugin.linkedAccessibilityService = service;
    }

    @Nullable
    public static LinkedAccessibilityService getLinkedAccessibilityService() {
        return LinkedASPlugin.linkedAccessibilityService;
    }

    @Nullable
    public static LogCallback getLogCallback() {
        return logCallback;
    }

    public static void setLogCallback(@NonNull LogCallback logCallback) {
        LinkedASPlugin.logCallback = logCallback;
    }

    @Nullable
    public static Predicate getBeforeExecutePredicate() {
        return LinkedASPlugin.beforeExecutePredicate;
    }

    public static void setBeforeExecutePredicate(@NonNull Predicate predicate) {
        LinkedASPlugin.beforeExecutePredicate = predicate;
    }

    static void log(String msg) {
        LogCallback logCallback = LinkedASPlugin.logCallback;
        if (logCallback != null) {
            logCallback.print(msg);
        }
    }

    public interface LogCallback {
        /**
         * custom your log
         *
         * @param msg log
         */
        void print(String msg);
    }

    public interface Predicate {
        /**
         * Test the situation before execute and return a boolean.
         *
         * @param accessibilityService the accessibilityService
         * @param accessibilityEvent   current accessibilityEvent
         * @param situation            current situation
         * @return False will pass this situation
         * @see LinkedAccessibilityService#onAccessibilityEvent(AccessibilityEvent)
         */
        boolean test(AccessibilityService accessibilityService, AccessibilityEvent accessibilityEvent, Situation situation);
    }
}
