package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.adapter.ZoomPageAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.databinding.ActivityImgDetailBinding;

import java.util.ArrayList;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/7 8:40
 */
public class ImgDetailPresenter extends BasePresenter<ActivityImgDetailBinding> {
    ZoomPageAdapter zoomPageAdapter;
    String ID = "";
    ArrayList<String> list = new ArrayList<>();
    public ImgDetailPresenter(RxAppCompatActivity mContext, ActivityImgDetailBinding binding) {
        super(mContext, binding);
        Intent intent = mContext.getIntent();
        if (intent != null) {
            int code = intent.getIntExtra("code", 0);
            list = intent.getStringArrayListExtra("list");
            if (list != null && list.size() > 0) {
                zoomPageAdapter = new ZoomPageAdapter(mContext, list);
                mBinding.imgDetailViewpager.setAdapter(zoomPageAdapter);
                mBinding.imgDetailViewpager.setCurrentItem(code, false);
                mBinding.imgDetailNumber.setText(code + 1 + "/" + list.size());
            }
        }
    }



    @Override
    protected void setListener() {
        super.setListener();
        mBinding.imgDetailFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });
        mBinding.imgDetailViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mBinding.imgDetailNumber.setText(position + 1 + "/" + list.size());
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

 
}
