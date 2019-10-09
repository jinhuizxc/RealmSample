package com.example.realmsample.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.realmsample.R;
import com.example.realmsample.adapter.holder.BaseViewHolder;
import com.example.realmsample.bean.Dog;
import com.example.realmsample.utils.RealmHelper;

import java.util.List;

public class DogAdapter extends BaseAdapter<Dog> {

    private RealmHelper mRealmHelper;

    public DogAdapter(Context mContext, List<Dog> mDatas, int mLayoutId) {
        super(mContext, mDatas, mLayoutId);
        mRealmHelper = new RealmHelper(mContext);
    }

    @Override
    protected void convert(Context mContext, BaseViewHolder holder, final Dog dog) {

        holder.setText(R.id.tv_name, dog.getName())
                .setText(R.id.tv_id, dog.getId());

        final ImageView iv = holder.getView(R.id.iv_like);

        if (mRealmHelper.isDogExist(dog.getId())) {
            iv.setSelected(true);
        } else {
            iv.setSelected(false);
        }

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iv.isSelected()) {
                    iv.setSelected(false);
                    mRealmHelper.deleteDog(dog.getId());
                } else {
                    iv.setSelected(true);
                    mRealmHelper.addDog(dog);
                }
            }
        });
    }
}
