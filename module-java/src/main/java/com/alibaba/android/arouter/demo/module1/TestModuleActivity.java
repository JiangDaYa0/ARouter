package com.alibaba.android.arouter.demo.module1;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rs.data.presenter.IHi;
import com.rs.data.presenter.Presenter;

@Route(path = "/module/1")
public class TestModuleActivity extends Activity {
    @Autowired(name = "/sub/hi")
    Presenter presenter;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IHi iHi = IHi.Stub.asInterface(service);
            try {
                iHi.hi("hello moto");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_module);
        ARouter.getInstance().inject(this);
        Intent intent = new Intent();
        intent.setClassName(this, "com.rs.module_sub.SubHiService");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
//        presenter.hi("from the bundle");


    } 
}
