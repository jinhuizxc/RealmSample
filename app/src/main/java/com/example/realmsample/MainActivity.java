package com.example.realmsample;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.realmsample.async.AsyncActivity;
import com.example.realmsample.activity.BaseActivity;
import com.example.realmsample.activity.DogListActivity;
import com.example.realmsample.activity.QueryActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Realm realm = Realm.getDefaultInstance(); // opens "myrealm.realm"
 * try {
 * // ... Do something ...
 * } finally {
 * realm.close();
 * }
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar.setTitle("RealmSample");
        setSupportActionBar(mToolbar);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick({R.id.btn_add, R.id.btn_query, R.id.btn_async})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                ActivityUtils.startActivity(DogListActivity.class);
                break;
            case R.id.btn_query:
                ActivityUtils.startActivity(QueryActivity.class);
                break;
            case R.id.btn_async:
                ActivityUtils.startActivity(AsyncActivity.class);
                break;
            default:
                break;
        }
    }

}
