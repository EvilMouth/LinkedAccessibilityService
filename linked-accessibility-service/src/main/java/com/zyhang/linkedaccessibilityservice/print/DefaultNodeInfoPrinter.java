package com.zyhang.linkedaccessibilityservice.print;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.zyhang.linkedaccessibilityservice.LinkedASPlugin;

import androidx.annotation.NonNull;

/**
 * Created by zyhang on 2019/4/12.10:09
 */
public class DefaultNodeInfoPrinter implements NodeInfoPrinter {

    private int floor;

    @Override
    public void print(@NonNull AccessibilityService accessibilityService, @NonNull AccessibilityEvent accessibilityEvent) {
        floor = 0;
        LinkedASPlugin.log("################################################################################################################################");
        traverse(accessibilityService.getRootInActiveWindow());
        LinkedASPlugin.log("################################################################################################################################");
    }

    private void traverse(AccessibilityNodeInfo rootNodeInfo) {
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

    private static void logNodeInfo(int floor, AccessibilityNodeInfo nodeInfo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < floor; i++) {
            if (i == floor - 1) {
                sb.append("└── ");
                break;
            }
            sb.append("    ");
        }
        LinkedASPlugin.log(String.format("%s%s; text=%s; desc=%s", sb.toString(), nodeInfo.getClassName(), nodeInfo.getText(), nodeInfo.getContentDescription()));
    }
}
