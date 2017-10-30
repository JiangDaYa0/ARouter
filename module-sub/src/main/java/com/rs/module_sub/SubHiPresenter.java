package com.rs.module_sub;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rs.data.presenter.Presenter;

/**
 * Created by jiangdylan on 2017/7/26.
 */
@Route(path = "/sub/hi")
public class SubHiPresenter implements Presenter {
    Context context;
    @Override
    public void hi(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}
