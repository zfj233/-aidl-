package com.example.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.zfj_.myapplication.IAddAidl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtNum1;
    private EditText mEtNum2;
    private EditText mEtRes;
    private Button mBtnAdd;
    IAddAidl iAddAidl;
    private ServiceConnection conn = new ServiceConnection() {
        // 绑定服务执行
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程的服务
            iAddAidl = IAddAidl.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iAddAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindService();
    }

    private void initView() {
        mEtNum1 = (EditText) findViewById(R.id.et_num1);
        mEtNum2 = (EditText) findViewById(R.id.et_num2);
        mEtRes = (EditText) findViewById(R.id.et_res);
        mBtnAdd = (Button) findViewById(R.id.btn_add);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int num1 = Integer.parseInt(mEtNum1.getText().toString());
        int num2 = Integer.parseInt(mEtNum2.getText().toString());
        try {
            //调用远程服务
            int res = iAddAidl.add(num1, num2);
            mEtRes.setText(res + "");
        } catch (RemoteException e) {
            e.printStackTrace();
            mEtRes.setText("出错了！");
        }
    }

    private void bindService() {
        //获取到服务端
        Intent intent = new Intent();
        //必须显式Intent使用Service
        intent.setComponent(new ComponentName("com.example.zfj_.myapplication",
                "com.example.zfj_.myapplication.IRemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
