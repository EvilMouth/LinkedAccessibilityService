package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

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
        LinkedASPlugin.log("onAccessibilityEvent event === " + event.toString());
        if (situations == null) {
            LinkedASPlugin.log("situations == null");
            return;
        }
        LinkedASPlugin.log("current situations === " + toString(situations));
        for (Situation situation : situations) {
            if (situation instanceof TestSituation) {
                situation.match(this, event);
                situation.execute(this, event);
                break;
            } else if (matchType(event.getEventType(), situation.eventTypes())
                    && situation.match(this, event)) {
                LinkedASPlugin.log(String.format("situation: %s match", situation.getClass().getSimpleName()));
                LinkedASPlugin.Predicate predicate = LinkedASPlugin.getBeforeExecutePredicate();
                if (predicate != null && !predicate.test(this, event, situation)) {
                    LinkedASPlugin.log("beforeExecutePredicate test false");
                    continue;
                }
                LinkedASPlugin.log(String.format("start execute %s", situation.getClass().getSimpleName()));
                if (situation.execute(this, event)) {
                    situations = situation.nextSituations();
                    LinkedASPlugin.log(String.format("execute %s success", situation.getClass().getSimpleName()));
                    break;
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

    @NonNull
    abstract public Situation[] firstSituations();

    @CallSuper
    public void reset() {
        situations = firstSituations();
    }

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
