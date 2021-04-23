package com.yiande.jxjxc.activity;

import android.content.Context;

import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityHtml5Binding;
import com.yiande.jxjxc.presenter.Html5Presenter;


/**
 * Created by myuser on 2017/9/15.
 */

public class Html5Activity extends BaseActivity<Html5Presenter, ActivityHtml5Binding> {

    public static void skip(Context context, String title, String content, boolean isUrl) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("isUrl", isUrl);
        SkipUtil.skipActivity(context, Html5Activity.class, map);
    }
    @Override
    protected int setLayoutId() {
        return R.layout.activity_html5;
    }

    @Override
    protected Html5Presenter getPresenter() {
        return new Html5Presenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.htmlTop).init();
    }

}
