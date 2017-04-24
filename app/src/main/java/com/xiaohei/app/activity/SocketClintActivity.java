package com.xiaohei.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xiaohei.app.Constant;
import com.xiaohei.app.R;
import com.xiaohei.app.base.BaseActivity;
import com.xiaohei.app.utils.SocketClint;

/**
 * Created by spc on 2017/4/24.
 */

public class SocketClintActivity extends BaseActivity implements View.OnClickListener {

    private SocketClint mClint;

    private EditText mEdServerPort, mEdServerAddress, mEdContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clint);
        initView();
        mClint = new SocketClint(mListener);

    }

    private void initView() {
        mEdServerPort = (EditText) findViewById(R.id.mEdServerPort);
        mEdServerAddress = (EditText) findViewById(R.id.mEdServerAddress);
        mEdContent = (EditText) findViewById(R.id.mEdContent);
        findViewById(R.id.mBtnSend).setOnClickListener(this);
        findViewById(R.id.mBtnConnect).setOnClickListener(this);
        mEdServerAddress.setText(Constant.SOCKETSERVER_ADDRESS);
        mEdServerPort.setText(Constant.SOCKETSERVER_PORT);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mBtnConnect) {
            mClint.connectServer(mEdServerAddress.getText().toString(), Integer.parseInt(mEdServerPort.getText().toString()));
        } else if (v.getId() == R.id.mBtnSend) {
            mClint.sendMessage(mEdContent.getText().toString());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClint.close();
    }


    //socket 客户端的回掉
    private SocketClint.ClintListener mListener = new SocketClint.ClintListener() {
        @Override
        public void connectServerSuccess() {

        }

        @Override
        public void haveMessageFromServer(String res) {

        }
    };

}
