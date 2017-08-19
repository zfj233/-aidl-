package com.example.zfj_.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * 服务端
 */
public class IRemoteService extends Service {

    /**
     * 当客户端绑定到该服务时执行
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IAddAidl.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d("TAG","收到了远程请求，输入的参数是"+num1+""+num2);
            return num1 + num2;
        }
    };
}
