package com.zyhang.linkedaccessibilityservice.print;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;

/**
 * Created by zyhang on 2019/4/12.10:08
 */
public interface NodeInfoPrinter {
    /**
     * call from background
     */
    void print(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent);
}
