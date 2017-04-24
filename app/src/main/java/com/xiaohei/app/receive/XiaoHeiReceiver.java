package com.xiaohei.app.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiaohei.app.Constant;

/**
 * Created by spc on 2017/4/24.
 * 小黑的广播，来了电话，短信，这里 获取就行
 * 最好动态去注册广播
 */

public class XiaoHeiReceiver extends BroadcastReceiver {
    public static String LOG_TAG = "upperSofter";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null)
            return;
        String phone = intent.getStringExtra(Constant.EXTRA_PHONE_PHONE_NUMBER);
        String name = intent.getStringExtra(Constant.EXTRA_PHONE_INCOME_NAME);

        if (intent.getStringExtra(Constant.EXTRA_PHONE_MESSAGE_CONTENT) != null) {
            //来短信了
            String msg = intent.getStringExtra(Constant.EXTRA_PHONE_MESSAGE_CONTENT);
            Log.e(LOG_TAG, "来短信了  " + name + "    " + phone + "  " + msg);
        } else {
            //来电话了
            Log.e(LOG_TAG, "来电话了~！！！" + name + "    " + phone);

        }
    }
}
