package com.example.realmsample.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.realmsample.R;
import com.example.realmsample.bean.Dog;

import butterknife.BindView;
import io.realm.Realm;

/**
 * 其他查询
 */
public class OtherQueryActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar mToolbar;

    @BindView(R.id.tv_average_age)
    TextView tvAverage;//平均年龄
    @BindView(R.id.tv_sum_age)
    TextView tvSumAge;//总年龄
    @BindView(R.id.tv_max_id)
    TextView tvMaxId;

    private Realm mRealm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_query;
    }

    @Override
    protected void init() {
        super.init();
        setToolbar(mToolbar, "其他查询");

        mRealm = Realm.getDefaultInstance();

        getAverageAge();

        getSumAge();

        getMaxId();

    }

    /**
     * 查询平均年龄
     */
    private void getAverageAge() {
        double avgAge = mRealm.where(Dog.class).findAll().average("age");
        tvAverage.setText(avgAge + "岁");
    }

    /**
     * 查询总年龄
     */
    private void getSumAge() {
        Number sum = mRealm.where(Dog.class).findAll().sum("age");
        int sumAge = sum.intValue();
        tvSumAge.setText(sumAge + "岁");
    }

    /**
     * 查询最大年龄
     */
    private void getMaxId() {
        Number max = mRealm.where(Dog.class).findAll().max("age");
        int maxAge = max.intValue();
        tvMaxId.setText(maxAge + "岁");
    }


}
