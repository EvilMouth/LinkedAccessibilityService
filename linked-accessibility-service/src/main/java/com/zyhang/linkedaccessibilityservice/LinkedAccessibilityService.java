package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.zyhang.linkedaccessibilityservice.print.NodeInfoPrintable;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

/**
 * Created by zyhang on 2018/8/22.10:46
 */

abstract public class LinkedAccessibilityService extends AccessibilityService {

    private Situation[] situations;

    @CallSuper
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        LinkedASPlugin.setLinkedAccessibilityService(this);
        LinkedASPlugin.log("onServiceConnected");
        reset();
    }

    @CallSuper
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        LinkedASPlugin.setCurrentEventSource(event.getSource());
        LinkedASPlugin.log("onAccessibilityEvent event === " + event.toString());
        if (situations == null) {
            LinkedASPlugin.log("situations == null");
            return;
        }
        LinkedASPlugin.log("current situations === " + toString(situations));
        // log node info if global log enable
        if (LinkedASPlugin.isGlobalNodeInfoPrintable()) {
            LinkedASPlugin.printNodeInfo(this, event);
        }
        for (Situation situation : situations) {
            // log node info if situation instanceof NodeInfoPrintable
            if (!LinkedASPlugin.isGlobalNodeInfoPrintable() || situation instanceof NodeInfoPrintable) {
                LinkedASPlugin.printNodeInfo(this, event);
            }
            // check the type correct
            if (matchType(event.getEventType(), situation.eventTypes())) {
                // check the situation is match
                if (situation.match(this, event)) {
                    String situationName = situation.getClass().getSimpleName();
                    LinkedASPlugin.log(String.format("situation: %s match", situationName));
                    // you can plugin in to intercept executor
                    LinkedASPlugin.Predicate predicate = LinkedASPlugin.getBeforeExecutePredicate();
                    if (predicate != null && !predicate.test(this, event, situation)) {
                        LinkedASPlugin.log("beforeExecutePredicate test false");
                        continue;
                    }
                    // execute the matched situation
                    LinkedASPlugin.log(String.format("executing %s", situationName));
                    if (situation.execute(this, event)) {
                        // make next situations
                        situations = situation.nextSituations();
                        LinkedASPlugin.log(String.format("execute %s success", situationName));
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LinkedASPlugin.log("onUnbind intent === " + intent.toString());
        return super.onUnbind(intent);
    }

    @CallSuper
    @Override
    public void onInterrupt() {
        LinkedASPlugin.log("onInterrupt");
    }

    /**
     * @return the init situations
     */
    @NonNull
    abstract public Situation[] firstSituations();

    /**
     * reset state
     */
    @CallSuper
    public void reset() {
        situations = firstSituations();
    }

    /**
     * log situations
     *
     * @param situations situation array
     * @return log
     */
    private static String toString(Situation[] situations) {
        if (situations == null)
            return "null";

        int iMax = situations.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(situations[i].getClass().getSimpleName());
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }

    /**
     * check is match
     *
     * @param eventType  target
     * @param eventTypes flags
     * @return True if match
     */
    private static boolean matchType(int eventType, int eventTypes) {
        while (eventTypes != 0) {
            final int eventTypeBit = (1 << Integer.numberOfTrailingZeros(eventTypes));
            if (eventType == eventTypeBit) {
                return true;
            }
            eventTypes &= ~eventTypeBit;
        }
        return false;
    }
}
