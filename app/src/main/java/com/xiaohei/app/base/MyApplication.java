package com.xiaohei.app.base;

import android.app.Application;
import android.app.Service;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.xiaohei.app.listener.PhoneCallListener;

/**
 * Created by spc on 2017/4/24.
 */

public class MyApplication extends Application {
    private static MyApplication mInst;
    public TelephonyManager tm;

    @Override
    public void onCreate() {
        super.onCreate();
        mInst = this;
        tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(PhoneCallListener.getInstance(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    public static MyApplication getInst() {
        return mInst;
    }
}
