package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyhang.linkedaccessibilityservice.print.DefaultNodeInfoPrinter;
import com.zyhang.linkedaccessibilityservice.print.NodeInfoPrinter;

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
    @Nullable
    private static volatile HandlerThread logHandlerThread;
    @Nullable
    private static volatile Handler logHandler;
    /**
     * intercept situation before match callback
     */
    @Nullable
    private static volatile Predicate beforeExecutePredicate;
    /**
     * if set
     * all event will print tree of nodeInfo
     *
     * @see DefaultNodeInfoPrinter
     */
    private static volatile boolean globalNodeInfoPrintable = false;
    @Nullable
    private static volatile NodeInfoPrinter nodeInfoPrinter = new DefaultNodeInfoPrinter();

    public static void setLinkedAccessibilityService(@Nullable LinkedAccessibilityService service) {
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

    public static void setLogCallback(@Nullable LogCallback logCallback) {
        LinkedASPlugin.logCallback = logCallback;
        if (logCallback != null && LinkedASPlugin.logHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread("logHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            LogCallback logCallback = LinkedASPlugin.logCallback;
                            if (logCallback != null) {
                                logCallback.print(msg.obj.toString());
                            }
                            break;
                        case 2:
                            NodeInfoPrinter nodeInfoPrinter = LinkedASPlugin.nodeInfoPrinter;
                            AccessibilityService accessibilityService = LinkedASPlugin.linkedAccessibilityService;
                            if (nodeInfoPrinter != null && accessibilityService != null) {
                                nodeInfoPrinter.print(accessibilityService, ((AccessibilityEvent) msg.obj));
                            }
                            break;
                    }
                }
            };
            LinkedASPlugin.logHandlerThread = handlerThread;
            LinkedASPlugin.logHandler = handler;
        }
    }

    @Nullable
    public static HandlerThread getLogHandlerThread() {
        return LinkedASPlugin.logHandlerThread;
    }

    public static void setLogHandlerThread(@Nullable HandlerThread logHandlerThread) {
        LinkedASPlugin.logHandlerThread = logHandlerThread;
    }

    @Nullable
    public static Handler getLogHandler() {
        return LinkedASPlugin.logHandler;
    }

    public static void setLogHandler(@Nullable Handler logHandler) {
        LinkedASPlugin.logHandler = logHandler;
    }

    @Nullable
    public static Predicate getBeforeExecutePredicate() {
        return LinkedASPlugin.beforeExecutePredicate;
    }

    public static void setBeforeExecutePredicate(@Nullable Predicate predicate) {
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
        if (LinkedASPlugin.logCallback == null)
            return;
        Handler logHandler = LinkedASPlugin.logHandler;
        if (logHandler != null) {
            Message message = Message.obtain();
            message.what = 1;
            message.obj = msg;
            logHandler.sendMessage(message);
        }
    }

    public static void printNodeInfo(@NonNull AccessibilityEvent accessibilityEvent) {
        if (LinkedASPlugin.logCallback == null || LinkedASPlugin.nodeInfoPrinter == null)
            return;
        Handler logHandler = LinkedASPlugin.logHandler;
        if (logHandler != null) {
            Message message = Message.obtain();
            message.what = 2;
            message.obj = accessibilityEvent;
            logHandler.sendMessage(message);
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
