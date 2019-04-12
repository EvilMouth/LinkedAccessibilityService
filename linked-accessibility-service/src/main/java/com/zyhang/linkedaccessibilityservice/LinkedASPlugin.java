package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.zyhang.linkedaccessibilityservice.print.DefaultNodeInfoPrinter;
import com.zyhang.linkedaccessibilityservice.print.NodeInfoPrinter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by zyhang on 2018/8/22.14:14
 */

@SuppressWarnings("WeakerAccess")
public final class LinkedASPlugin {

    @Nullable
    private static volatile LinkedAccessibilityService linkedAccessibilityService;
    /**
     * kept the latest event source node info
     */
    @Nullable
    private static volatile AccessibilityNodeInfo currentEventSource;
    /**
     * custom your own log utils
     */
    @Nullable
    private static volatile LogCallback logCallback;
    /**
     * intercept situation before match callback
     */
    @Nullable
    private static volatile Predicate beforeExecutePredicate;
    /**
     * if set
     * all event will print tree of nodeInfo
     * @see DefaultNodeInfoPrinter
     */
    private static volatile boolean globalNodeInfoPrintable = false;
    @Nullable
    private static volatile NodeInfoPrinter nodeInfoPrinter = new DefaultNodeInfoPrinter();

    public static void setLinkedAccessibilityService(@NonNull LinkedAccessibilityService service) {
        LinkedASPlugin.linkedAccessibilityService = service;
    }

    @Nullable
    public static LinkedAccessibilityService getLinkedAccessibilityService() {
        return LinkedASPlugin.linkedAccessibilityService;
    }

    @Nullable
    public static AccessibilityNodeInfo getCurrentEventSource() {
        return LinkedASPlugin.currentEventSource;
    }

    public static void setCurrentEventSource(@Nullable AccessibilityNodeInfo currentEventSource) {
        LinkedASPlugin.currentEventSource = currentEventSource;
    }

    @Nullable
    public static LogCallback getLogCallback() {
        return LinkedASPlugin.logCallback;
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

    public static boolean isGlobalNodeInfoPrintable() {
        return LinkedASPlugin.globalNodeInfoPrintable;
    }

    public static void setGlobalNodeInfoPrintable(boolean globalNodeInfoPrintable) {
        LinkedASPlugin.globalNodeInfoPrintable = globalNodeInfoPrintable;
    }

    @Nullable
    public static NodeInfoPrinter getNodeInfoPrinter() {
        return LinkedASPlugin.nodeInfoPrinter;
    }

    public static void setNodeInfoPrinter(@Nullable NodeInfoPrinter nodeInfoPrinter) {
        LinkedASPlugin.nodeInfoPrinter = nodeInfoPrinter;
    }

    public static void log(String msg) {
        LogCallback logCallback = LinkedASPlugin.getLogCallback();
        if (logCallback != null) {
            logCallback.print(msg);
        }
    }

    public static void printNodeInfo(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent) {
        NodeInfoPrinter nodeInfoPrinter = LinkedASPlugin.getNodeInfoPrinter();
        if (nodeInfoPrinter != null) {
            nodeInfoPrinter.print(accessibilityService, accessibilityEvent);
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
