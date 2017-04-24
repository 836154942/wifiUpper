package com.xiaohei.app.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.xiaohei.app.Constant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by spc on 2017/4/24.
 * socket通信的的服务器
 */

public class SocketServer {

    private static final int WHAT_CONNECT_CLINT = 0x21;//clint连接上了服务器
    private static final int WHAT_CLINT_MESSAGE = 0x22;//clint发来消息了

    ServerSocket mServerSocket = null;
    Socket socket = null;
    BufferedReader br = null;
    PrintWriter pw = null;
    ServerListener listener;


    public interface ServerListener {
        void devicesConnected();//客户端连接了

        void newMessageFromClint(String res);//客户端发来了消息
    }

    public SocketServer(ServerListener listener) {
        this.listener = listener;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_CONNECT_CLINT:
                    listener.devicesConnected();
                    break;
                case WHAT_CLINT_MESSAGE:
                    String content = (String) msg.obj;
                    listener.newMessageFromClint(content);
                    break;
            }

        }
    };

    //初始化server
    public void initServer(final int port) {

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    mServerSocket = new ServerSocket(port);
                    Log.e(Constant.LOG_TAG, "服务端 等待链接了");
                    socket = mServerSocket.accept();//阻塞，等待连接
                    listener.devicesConnected();
                    handler.sendEmptyMessage(WHAT_CONNECT_CLINT);
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        Message message = Message.obtain();
                        message.what = WHAT_CLINT_MESSAGE;
                        message.obj = temp;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    close();
                }
            }
        }.start();
    }

    public void sendMessage(String msg) {
        pw.println("服务器给你发的~" + msg);
        pw.flush();
    }

    //关闭连接
    public void close() {
        Log.e(Constant.LOG_TAG, "断开与客户端的连接了");
        try {
            br.close();
            pw.close();
            mServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
