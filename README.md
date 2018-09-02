# LinkedAccessibilityService

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)
[ ![Download](https://api.bintray.com/packages/zyhang/maven/LinkedAccessibilityService/images/download.svg) ](https://bintray.com/zyhang/maven/LinkedAccessibilityService/_latestVersion)

提供链式使用AccessibilityService

## Usage
``` kotlin
class AccessibilityServiceExample : LinkedAccessibilityService() {
    override fun firstSituations(): Array<Situation> {
        return arrayOf(NotificationSituation())
    }
}

class NotificationSituation : Situation {
    override fun eventTypes(): Int {
        return AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
    }

    override fun match(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        if ("android.app.Notification" == accessibilityEvent.className.toString()) {
            val list = accessibilityEvent.text
            println("NotificationSituation list === $list")
            list.forEach {
                if (it.contains("[微信红包]")) {
                    return true
                }
            }
        }
        return false
    }

    override fun execute(accessibilityService: AccessibilityService, accessibilityEvent: AccessibilityEvent): Boolean {
        try {
            val parcelableData = accessibilityEvent.parcelableData
            val pendingIntent = (parcelableData as Notification).contentIntent
            pendingIntent.send()
            return true
        } catch (e: Exception) {
            // do nothing
        }
        return false
    }

    override fun nextSituations(): Array<Situation> {
        return arrayOf(ConversationToReceiveSituation())
    }
}
```

## Installation
```groovy
implementation 'com.zyhang:linkedaccessibilityservice:<latest-version>'
```