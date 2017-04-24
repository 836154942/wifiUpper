package com.xiaohei.app.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.Toast;

import com.xiaohei.app.base.MyApplication;

/**
 * Created by spc on 2017/4/24.
 */

public class CommUtils {
    public static String LOG_TAG = "upperSofter";

    //根据手机号好来获取联系人
    public static String getContactName(String number) {
        if (TextUtils.isEmpty(number)) {
            return null;
        }

        final ContentResolver resolver = MyApplication.getInst().getContentResolver();

        Uri lookupUri = null;
        String[] projection = new String[] { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.DISPLAY_NAME };
        Cursor cursor = null;
        try {
            lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            cursor =resolver.query(lookupUri, projection, null, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                lookupUri = Uri.withAppendedPath(android.provider.Contacts.Phones.CONTENT_FILTER_URL,
                        Uri.encode(number));
                cursor = resolver.query(lookupUri, projection, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String ret = null;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            ret = cursor.getString(1);
        }

        cursor.close();
        return ret;
    }



    public static Toast mToast;

    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInst(), msg, Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
