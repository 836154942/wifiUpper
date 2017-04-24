package com.xiaohei.app.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by spc on 2017/4/24.
 * socket通信的客户端工具类
 */

public class SocketClint {
    public static String LOG_TAG = "upperSofter";

    //handler的what码
    public static final int WHAT_CONNECT_SERVER = 0x01;//clint连接上了服务器
    public static final int WHAT_HAVEMESSAGE = 0x12;//clint接收到服务器的消息

    private Socket mClintSocket = null;
    private BufferedReader br = null;
    private PrintWriter pw = null;

    private ClintListener listener;

    public SocketClint(ClintListener listener) {
        this.listener = listener;
    }

    public interface ClintListener {
        void connectServerSuccess();//链接上了服务器

        void haveMessageFromServer(String res);//收到服务器发来的数据
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_CONNECT_SERVER:
                    Log.e(LOG_TAG, "服务器链接上了………………");
                    CommUtils.showToast("连接上了服务器");
                    listener.connectServerSuccess();
                    break;
                case WHAT_HAVEMESSAGE:
                    String res = (String) msg.obj;
                    Log.e(LOG_TAG, "收到消息………………" + res);
                    CommUtils.showToast(res);
                    listener.haveMessageFromServer(res);
                    break;
            }
        }
    };

    //链接服务器
    public void connectServer(final String ipAddress, final int port) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    mClintSocket = new Socket(ipAddress, port);
                    mHandler.sendEmptyMessage(WHAT_CONNECT_SERVER);
                    br = new BufferedReader(new InputStreamReader(
                            mClintSocket.getInputStream()));
                    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            mClintSocket.getOutputStream())));
                    while (true) {
                        String str = br.readLine();
                        if (str != null) {
                            Message message = mHandler.obtainMessage();
                            message.what = WHAT_HAVEMESSAGE;
                            message.obj = str;
                            mHandler.sendMessage(message);
                        } else {
                            throw new Exception("close");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close();
                }
            }
        }.start();
    }

    //发送消息给服务器
    public void sendMessage(final String string) {
        new Thread() {
            @Override
            public void run() {
                pw.println(string);
                pw.flush();
            }
        }.start();
    }

    public void close() {
        Log.e(LOG_TAG, "断开与服务器的连接了");
        try {
            br.close();
            pw.close();
            mClintSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
