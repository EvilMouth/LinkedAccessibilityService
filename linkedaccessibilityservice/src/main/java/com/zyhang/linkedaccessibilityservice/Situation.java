package com.zyhang.linkedaccessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;

/**
 * Created by zyhang on 2018/8/22.10:53
 * @see LinkedAccessibilityService#onAccessibilityEvent(AccessibilityEvent)
 */

public interface Situation {

    /**
     * The event types an {@link AccessibilityService} is interested in.
     * <p>
     * <strong>Can be dynamically set at runtime.</strong>
     * </p>
     *
     * @return The event types an {@link AccessibilityService} is interested in.
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_CLICKED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_LONG_CLICKED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_FOCUSED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_SELECTED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_TEXT_CHANGED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_WINDOW_STATE_CHANGED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_NOTIFICATION_STATE_CHANGED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_TOUCH_EXPLORATION_GESTURE_START
     * @see android.view.accessibility.AccessibilityEvent#TYPE_TOUCH_EXPLORATION_GESTURE_END
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_HOVER_ENTER
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_HOVER_EXIT
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_SCROLLED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_TEXT_SELECTION_CHANGED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_WINDOW_CONTENT_CHANGED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_TOUCH_INTERACTION_START
     * @see android.view.accessibility.AccessibilityEvent#TYPE_TOUCH_INTERACTION_END
     * @see android.view.accessibility.AccessibilityEvent#TYPE_ANNOUNCEMENT
     * @see android.view.accessibility.AccessibilityEvent#TYPE_GESTURE_DETECTION_START
     * @see android.view.accessibility.AccessibilityEvent#TYPE_GESTURE_DETECTION_END
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_ACCESSIBILITY_FOCUSED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED
     * @see android.view.accessibility.AccessibilityEvent#TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY
     * @see android.view.accessibility.AccessibilityEvent#TYPE_WINDOWS_CHANGED
     */
    int eventTypes();

    /**
     * match the situation {@link com.zyhang.linkedaccessibilityservice.Situation}
     *
     * @param accessibilityService the accessibilityService
     * @param accessibilityEvent   current accessibilityEvent
     * @return True if the situation match
     */
    boolean match(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent);

    /**
     * execute the action when situation match {@link com.zyhang.linkedaccessibilityservice.Situation}
     *
     * @param accessibilityService the accessibilityService
     * @param accessibilityEvent   current accessibilityEvent
     * @return True if execute success
     */
    boolean execute(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent);

    /**
     * Pointer to next situations {@link com.zyhang.linkedaccessibilityservice.Situation}
     *
     * @return next situations
     */
    Situation[] nextSituations();
}
