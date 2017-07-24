package com.rs.data.presenter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rs.data.R;


@Route(path = "/data/activity")
public class SubActivity extends AppCompatActivity {

    @Autowired(name = "name")
    String n;
    @Autowired(name = "age")
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        ARouter.getInstance().inject(this);
        Toast.makeText(SubActivity.this, n + " age: " + a, Toast.LENGTH_SHORT).show();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TestParcelable testParcelable = new TestParcelable("jack", 666);
//                TestObj testObj = new TestObj("Rose", 777);
                ARouter.getInstance().build("/test/activity1")
                        .withString("name", "老王")
                        .withInt("age", 18)
                        .withBoolean("boy", true)
                        .withLong("high", 180)
                        .withString("url", "https://a.b.c")
                        .navigation();
            }
        });
    }

    private void initializeInjector() {

    }
}
