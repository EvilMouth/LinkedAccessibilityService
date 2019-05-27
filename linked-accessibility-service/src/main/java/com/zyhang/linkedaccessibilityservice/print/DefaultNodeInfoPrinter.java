package com.zyhang.linkedaccessibilityservice.print;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import com.zyhang.linkedaccessibilityservice.LinkedASPlugin;

/**
 * Created by zyhang on 2019/4/12.10:09
 */
@SuppressWarnings("WeakerAccess")
public class DefaultNodeInfoPrinter implements NodeInfoPrinter {

    private static final int MAXLENGTH = 2000;

    protected int floor;
    protected StringBuilder msgSb = new StringBuilder();

    @Override
    public void print(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent) {
        floor = 0;
        msgSb.delete(0, msgSb.length());

        msgSb.append("################################################################################################################################\n");
        traverse(accessibilityService.getRootInActiveWindow());
        msgSb.append("################################################################################################################################");
        log(msgSb.toString());
    }

    protected void traverse(AccessibilityNodeInfo rootNodeInfo) {
        if (rootNodeInfo == null)
            return;
        logNodeInfo(floor, rootNodeInfo);
        for (int i = 0, max = rootNodeInfo.getChildCount(); i < max; i++) {
            AccessibilityNodeInfo nodeInfo = rootNodeInfo.getChild(i);
            if (nodeInfo == null)
                continue;
            floor += 1;
            traverse(nodeInfo);
            floor -= 1;
        }
    }

    protected void logNodeInfo(int floor, AccessibilityNodeInfo nodeInfo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < floor; i++) {
            if (i == floor - 1) {
                sb.append("└── ");
                break;
            }
            sb.append("    ");
        }
        msgSb.append(String.format("%s%s\n", sb.toString(), getLogInfo(nodeInfo)));
    }

    protected String getLogInfo(AccessibilityNodeInfo nodeInfo) {
        Rect bounds = new Rect();
        nodeInfo.getBoundsInScreen(bounds);
        return String.format("%s; text=%s; desc=%s bounds=%s", nodeInfo.getClassName(), nodeInfo.getText(), nodeInfo.getContentDescription(), bounds.toShortString());
    }

    public static void log(String msg) {
        int msgLength = msg.length();
        int start = 0;
        int end = MAXLENGTH;
        for (; ; ) {
            if (msgLength > end) {
                LinkedASPlugin.log(msg.substring(start, end));
                start = end;
                end = end + MAXLENGTH;
            } else {
                LinkedASPlugin.log(msg.substring(start, msgLength));
                break;
            }
        }
    }
}
