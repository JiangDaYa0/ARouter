package com.rs.module_sub;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rs.data.presenter.IHi;

/**
 * Created by jiangdylan on 2017/7/26.
 */
@Route(path = "/aidl/service/hi")
public class SubHiService extends Service {

    private Binder binder = new IHi.Stub() {

        @Override
        public void hi(String text) throws RemoteException {
            Toast.makeText(SubHiService.this, text, Toast.LENGTH_SHORT).show();
            Log.d("tag", "this from sub module say: " + text);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
