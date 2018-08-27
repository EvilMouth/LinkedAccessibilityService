package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.support.annotation.NonNull;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by zyhang on 2018/8/24.11:21
 * <p>
 * extends this class and enable logger to test the event
 */

abstract public class TestSituation implements Situation {
    @Override
    final public int eventTypes() {
        return AccessibilityEvent.TYPES_ALL_MASK;
    }

    @Override
    final public boolean match(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent) {
        testMatch(accessibilityService, accessibilityEvent);
        return false;
    }

    @Override
    final public boolean execute(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent) {
        testExecute(accessibilityService, accessibilityEvent);
        return false;
    }

    @Override
    final public Situation[] nextSituations() {
        return new Situation[0];
    }

    abstract public void testMatch(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent);

    abstract public void testExecute(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent);
}
