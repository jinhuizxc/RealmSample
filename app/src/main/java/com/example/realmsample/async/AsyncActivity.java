package com.example.realmsample.async;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.realmsample.R;
import com.example.realmsample.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class AsyncActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_async;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolbar,"异步操作");
    }

    @OnClick({R.id.btn_add_delete,R.id.btn_update_query})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_add_delete:
                ActivityUtils.startActivity(AddDeleteActivity.class);
                break;
            case R.id.btn_update_query:
                ActivityUtils.startActivity(AsyncQueryActivity.class);
                break;
            default:
                break;

        }

    }
}
