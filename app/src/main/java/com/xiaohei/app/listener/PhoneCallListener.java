package com.xiaohei.app.listener;

import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.xiaohei.app.Constant;
import com.xiaohei.app.base.MyApplication;
import com.xiaohei.app.utils.CommUtils;

/**
 * Created by spc on 2017/4/24.
 * 电话监听
 */

public class PhoneCallListener {
    public static String LOG_TAG = "upperSofter";
    private static PhoneStateListener listener;

    public static synchronized PhoneStateListener getInstance() {

        if (listener == null) {
            synchronized (PhoneCallListener.class) {
                if (listener == null) {
                    creteListener();
                }
            }
        }
        return listener;
    }

    private static synchronized void creteListener() {
        listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.e(LOG_TAG, "挂断");
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        Log.e(LOG_TAG, "接听");
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        String name = CommUtils.getContactName(incomingNumber);
                        Intent intent = new Intent(Constant.ACTION_PHONE_INCONME);
                        intent.putExtra(Constant.EXTRA_PHONE_PHONE_NUMBER, incomingNumber);
                        intent.putExtra(Constant.EXTRA_PHONE_INCOME_NAME, name);
                        MyApplication.getInst().sendBroadcast(intent);
                        break;
                }
            }
        };
    }

}
