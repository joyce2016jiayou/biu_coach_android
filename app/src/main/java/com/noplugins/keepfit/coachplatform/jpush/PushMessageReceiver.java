package com.noplugins.keepfit.coachplatform.jpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.*;
import cn.jpush.android.service.JPushMessageReceiver;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.base.MyApplication;
import com.noplugins.keepfit.coachplatform.util.MessageEvent;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class PushMessageReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";

    /**
     * 收到自定义消息回调
     * 支持的版本
     * 开始支持的版本：3.3.0
     * 说明 如果需要在旧版本的Receiver接收cn.jpush.android.intent.MESSAGE_RECEIVED广播
     * 可以不重写此方法，或者重写此方法且调用super.onMessage
     * 如果重写此方法，没有调用super，则不会发送广播到旧版本Receiver
     *
     * @param context
     * @param customMessage
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG, "[onMessage] " + customMessage);
        processCustomMessage(context, customMessage);

    }

    /**
     * 点击通知回调
     * 支持的版本
     * 开始支持的版本：3.3.0
     * 说明 如果需要在旧版本的Receiver接收cn.jpush.android.intent.NOTIFICATION_OPENED广播
     * 可以不重写此方法，或者重写此方法且调用super.onNotifyMessageOpened
     * 如果重写此方法，没有调用super，则不会发送广播到旧版本Receiver
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        //{"msg":"今天你吃了吗","orderItemNum":"2222","orderNum":"11111","title":"无插件","type":"checkMessage","userNum":"CUS19081685290145"}
        Log.e(TAG, "[onNotifyMessageOpened] " + message.notificationExtras);
        Log.e("极光返回的json", message.notificationExtras);
        JSONObject jsonObject = null;
        String type_id = "";
        try {
            jsonObject = new JSONObject(message.notificationExtras);
            type_id = jsonObject.getString("type");
            Log.e("极光返回的页面跳转的ID:", type_id);

            switch (type_id) {
                case "LogMessage"://跳转到消息第一项
                    MyApplication.destoryActivity("KeepFitActivity");//首先得销毁

                    Intent i = new Intent(context, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("jpush_enter", "jpush_enter1");
                    i.putExtras(bundle);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                    break;
                case "OrderMessage":
                    MyApplication.destoryActivity("KeepFitActivity");//首先得销毁

                    Intent i2 = new Intent(context, MainActivity.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("jpush_enter", "jpush_enter2");
                    i2.putExtras(bundle2);
                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i2);
                    break;
                case "PlaceMessage":
                    MyApplication.destoryActivity("KeepFitActivity");//首先得销毁

                    Intent i3 = new Intent(context, MainActivity.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("jpush_enter", "jpush_enter3");
                    i3.putExtras(bundle3);
                    i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i3);
                    break;
                case "checkMessage"://跳转到check in界面
//                    Intent i4 = new Intent(context, CheckCodeActivity.class);
//                    Bundle bundle4 = new Bundle();
//                    String order_id = jsonObject.getString("orderNum");
//                    bundle4.putString("order_number", order_id);
//                    i4.putExtras(bundle4);
//                    i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    context.startActivity(i4);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     * 通知的MultiAction回调
     * 支持的版本
     * 开始支持的版本：3.3.2
     * 说明 如果需要在旧版本的Receiver接收cn.jpush.android.intent.NOTIFICATION_CLICK_ACTION广播
     * 可以不重写此方法，或者重写此方法且调用super.onMultiActionClicked
     * 如果重写此方法，没有调用super，则不会发送广播到旧版本Receiver
     *
     * @param context
     * @param intent
     */
    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);
        Log.e(TAG, "nActionExtra" + nActionExtra);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            Log.d(TAG, "ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        if (nActionExtra.equals("my_extra1")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
        } else if (nActionExtra.equals("my_extra2")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
        } else if (nActionExtra.equals("my_extra3")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
        } else {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
        }
    }

    /**
     * 接受到的推送调用
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageArrived] " + message.notificationContent);
        JSONObject jsonObject = null;
        String type_id = "";
        try {
            jsonObject = new JSONObject(message.notificationExtras);
            type_id = jsonObject.getString("type");
        } catch (Throwable throwable) {

        }
        if (type_id.equals("1")) {
            //在这里设置订单的弹窗
            MessageEvent messageEvent = new MessageEvent("show_checkin_pop");
            EventBus.getDefault().postSticky(messageEvent);
        }


    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG, "[onCommandResult] " + cmdMessage);
    }

    /**
     * tag 增删查改的操作会在此方法中回调结果
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    /**
     * 查询某个 tag 与当前用户的绑定状态的操作会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    /**
     * alias 相关的操作会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    /**
     * 设置手机号码会在此方法中回调结果。
     *
     * @param context
     * @param jPushMessage
     */
    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, CustomMessage customMessage) {
//        if (MainActivity.isForeground) {
//            String message = customMessage.message;
//            String extras = customMessage.extra;
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//        }
    }
}
