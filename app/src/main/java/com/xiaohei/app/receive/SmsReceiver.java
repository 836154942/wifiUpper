package com.xiaohei.app.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.xiaohei.app.Constant;
import com.xiaohei.app.base.MyApplication;
import com.xiaohei.app.utils.CommUtils;

/**
 * Created by spc on 2017/4/24.
 * 手机来消息，的广播，可以在这获取 信息。然后封装 发送到自己的广播
 */

public class SmsReceiver extends BroadcastReceiver {
    public static String LOG_TAG = "upperSofter";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                String phoneNumber = msg.getOriginatingAddress();//电话
                String msgContent = msg.getDisplayMessageBody();//消息

                Intent in = new Intent(Constant.ACTION_PHONE_INCONME);
                in.putExtra(Constant.EXTRA_PHONE_PHONE_NUMBER, phoneNumber);
                in.putExtra(Constant.EXTRA_PHONE_MESSAGE_CONTENT, msgContent);
                in.putExtra(Constant.EXTRA_PHONE_INCOME_NAME, CommUtils.getContactName(phoneNumber));
                MyApplication.getInst().sendBroadcast(in);
            }
        }
    }

}

