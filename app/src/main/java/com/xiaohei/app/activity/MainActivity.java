package com.xiaohei.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xiaohei.app.R;
import com.xiaohei.app.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.mBtnClint).setOnClickListener(this);
        findViewById(R.id.mBtnServer).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mBtnClint:
                startActivity(new Intent(this, SocketClintActivity.class));
                break;
            case R.id.mBtnServer:
                startActivity(new Intent(this, SocketServerActivity.class));
                break;
        }
    }
}
