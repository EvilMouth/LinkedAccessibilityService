package com.zyhang.easyaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

/**
 * Created by zyhang on 2018/8/22.10:53
 */

public interface Situation {

    int eventTypes();

    boolean match(AccessibilityService accessibilityService, AccessibilityEvent accessibilityEvent);

    void execute(AccessibilityService accessibilityService);

    Situation[] nextSituations();
}
