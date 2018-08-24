package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.accessibility.AccessibilityEvent;

import java.util.Arrays;

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
        int eventType = event.getEventType();

        if (situations == null) {
            LinkedASPlugin.log("situations == null");
            return;
        }
        LinkedASPlugin.log("current situations === " + Arrays.toString(situations));
        for (Situation situation : situations) {
            if (matchType(eventType, situation.eventTypes())
                    &&
                    situation.match(this, event)) {
                LinkedASPlugin.log(String.format("situation: %s match", situation.getClass().getSimpleName()));
                LinkedASPlugin.Predicate predicate = LinkedASPlugin.getBeforeExecutePredicate();
                if (predicate != null && !predicate.test(this, event, situation)) {
                    LinkedASPlugin.log("beforeExecutePredicate test false");
                    return;
                }
                LinkedASPlugin.log(String.format("start execute %s", situation.getClass().getSimpleName()));
                if (situation.execute(this, event)) {
                    situations = situation.nextSituations();
                    LinkedASPlugin.log(String.format("execute %s success", situation.getClass().getSimpleName()));
                }
                break;
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
