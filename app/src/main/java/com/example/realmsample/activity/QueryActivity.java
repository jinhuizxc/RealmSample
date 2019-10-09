package com.example.realmsample.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.realmsample.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查、改
 */
public class QueryActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_query;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolbar,"改、查");
    }

    @OnClick({R.id.btn_query, R.id.btn_condition_query,R.id.btn_other_query})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                ActivityUtils.startActivity(AllDogActivity.class);
                break;
            case R.id.btn_condition_query:
                ActivityUtils.startActivity(ConditionQueryActivity.class);
                break;
            case R.id.btn_other_query:
                ActivityUtils.startActivity(OtherQueryActivity.class);
                break;
            default:
                break;
        }
    }

}
