package com.alibaba.android.arouter.demo.module1;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rs.data.presenter.Presenter;

@Route(path = "/module/1")
public class TestModuleActivity extends Activity {
    @Autowired
    Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_module);
        ARouter.getInstance().inject(this);
        presenter.hi("from the bundle");
    }
}
