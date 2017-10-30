package com.alibaba.android.arouter.demo.testservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Created by jiangdylan on 2017/7/21.
 */
@Route(path = "/service/hello2")
public class HelloTowImpl implements HelloService {
    Context mContext;
    @Override
    public void sayHello(String name) {
        Toast.makeText(mContext, "Hello " + name, Toast.LENGTH_SHORT).show();
//        Toast.makeText(mContext, "Hello2 " + name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
