package example.money;

import android.accessibilityservice.AccessibilityService;
import android.app.KeyguardManager;
import android.os.PowerManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/4/14.
 */

public class MyAccessibilityService extends AccessibilityService {

    private boolean flag = true;
    private int mHour;
    private int mMinuts;

    //锁屏、唤醒相关
    private KeyguardManager  km;
    private KeyguardManager.KeyguardLock kl;
    private PowerManager pm;
    private PowerManager.WakeLock wl;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
/*        int eventType = event.getEventType(); // 事件类型
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED: // 通知栏事件
                Log.i(TAG, "TYPE_NOTIFICATION_STATE_CHANGED");
                if (PhoneController.isLockScreen(this)) { // 锁屏
                    PhoneController.wakeAndUnlockScreen(this);   // 唤醒点亮屏幕
                }
                break;
            default:
                break;

        }*/

        long time=System.currentTimeMillis();
        Calendar mCalendar=Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        mHour=mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinuts=mCalendar.get(Calendar.MINUTE);

        Log.e("time",mHour+":"+mMinuts);

        if (flag){
            Log.i(TAG, "-------------------------------------------------------------");
            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
            clickLuckyMoney(rootNode); // 点击红包
        }else {
            Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++++++");
        }

//        printEventLog(event);
    }

    @Override
    public void onInterrupt() {
    }

    private void printEventLog(AccessibilityEvent event) {
        Log.i(TAG, "-------------------------------------------------------------");
        int eventType = event.getEventType(); //事件类型
        Log.i(TAG, "PackageName:" + event.getPackageName() + ""); // 响应事件的包名
        Log.i(TAG, "Source Class:" + event.getClassName() + ""); // 事件源的类名
        Log.i(TAG, "Description:" + event.getContentDescription()+ ""); // 事件源描述
        Log.i(TAG, "Event Type(int):" + eventType + "");

        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                Log.i(TAG, "event type:TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                Log.i(TAG, "event type:TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED://View获取到焦点
                Log.i(TAG, "event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                Log.i(TAG, "event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                Log.i(TAG, "event type:TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                Log.i(TAG, "event type:TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.i(TAG, "event type:TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                Log.i(TAG, "event type:TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                Log.i(TAG, "event type:TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                Log.i(TAG, "event type:TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
            default:
                Log.i(TAG, "no listen event");
        }

        for (CharSequence txt : event.getText()) {
            Log.i(TAG, "text:" + txt);
        }

        Log.i(TAG, "-------------------------------------------------------------");
    }

    /**
     * 搜索并点击红包
     */
    private void clickLuckyMoney(AccessibilityNodeInfo rootNode) {
        if(rootNode != null) {
            int count = rootNode.getChildCount();
            for (int i = count - 1; i >= 0; i--) {  // 倒序查找最新的红包
                AccessibilityNodeInfo node = rootNode.getChild(i);
                if (node == null)
                    continue;
                int count1 = node.getChildCount();
                if (count1 != 0){
                    for (int j = 0; j < count1; j++) {
                        AccessibilityNodeInfo node1 = node.getChild(j);
                        int count2 = node1.getChildCount();
                        if (count2 != 0){
                            for (int k = 0; k < count2; k++) {
                                AccessibilityNodeInfo node2 = node1.getChild(k);
                                CharSequence text2 = node2.getText();
                                if (text2 != null && text2.toString().equals("内勤签到")) {
                                    Log.e("bbb","----------");
                                    AccessibilityNodeInfo parent2 = node2.getParent();
                                    while (parent2 != null) {
                                        if (parent2.isClickable()) {
//                                            parent2.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                            flag = false;
                                            break;
                                        }
                                        parent2 = parent2.getParent();
                                    }
                                }
                            }
                        }
                    }
                }
//                clickLuckyMoney(node);
            }
        }
    }

}
