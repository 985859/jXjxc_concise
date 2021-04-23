package com.yiande.jxjxc.activity;

import android.content.Context;
import android.view.KeyEvent;

import androidx.collection.ArrayMap;

import com.gyf.immersionbar.OnKeyboardListener;
import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityRichTextBinding;
import com.yiande.jxjxc.presenter.RichTextPresenter;

public class RichTextActivity extends BaseActivity<RichTextPresenter, ActivityRichTextBinding> {

    public static void start(Context context, String title, String content, boolean isEdit) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("isEdit", isEdit);
        SkipUtil.skipActivity(context, RichTextActivity.class, map, 0);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_rich_text;
    }

    @Override
    protected RichTextPresenter getPresenter() {
        return new RichTextPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {

        mImmersionBar.titleBar(mBinding.richDetailTop).init();
        mImmersionBar.titleBar(mBinding.richDetailTop).keyboardEnable(true).setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {

            }
        }).init();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        mPresenter.setResultData();
        return super.onKeyDown(keyCode, event);
    }
}
