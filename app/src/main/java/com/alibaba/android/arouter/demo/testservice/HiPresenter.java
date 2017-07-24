package com.alibaba.android.arouter.demo.testservice;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rs.data.presenter.Presenter;

/**
 * Created by jiangdylan on 2017/7/24.
 */
@Route(path = "/presenter/hi")
public class HiPresenter implements Presenter {
    private Context context;
    @Override
    public void hi(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}
