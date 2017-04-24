package com.xiaohei.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.xiaohei.app.Constant;
import com.xiaohei.app.R;
import com.xiaohei.app.base.BaseActivity;
import com.xiaohei.app.utils.CommUtils;
import com.xiaohei.app.utils.SocketServer;


/**
 * Created by spc on 2017/4/24.
 */

public class SocketServerActivity extends BaseActivity implements View.OnClickListener {
    public SocketServer mServer;
    private EditText mEdContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        mEdContent = (EditText) findViewById(R.id.mEdContent);
        findViewById(R.id.mBtnSend).setOnClickListener(this);
        mServer = new SocketServer(listener);
        mServer.initServer(Integer.parseInt(Constant.SOCKETSERVER_PORT));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mServer.close();
    }

    private SocketServer.ServerListener listener = new SocketServer.ServerListener() {
        @Override
        public void devicesConnected() {
            Log.e(Constant.LOG_TAG, "有设备链接了");
        }

        @Override
        public void newMessageFromClint(String res) {
            CommUtils.showToast("服务器收到  " + res);
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mBtnSend) {
            mServer.sendMessage(mEdContent.getText().toString());
        }
    }
}
