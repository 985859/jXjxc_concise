package com.yiande.jxjxc.activity;

import android.content.Context;


import androidx.collection.ArrayMap;

import com.mylibrary.api.utils.SkipUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.databinding.ActivityContactsBinding;
import com.yiande.jxjxc.presenter.ContactsPresenter;

public class ContactsActivity extends BaseActivity<ContactsPresenter, ActivityContactsBinding> {

    public static void start(Context context, int type) {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("type", type);
        SkipUtil.skipActivity(context, ContactsActivity.class, map, 0, true);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected ContactsPresenter getPresenter() {
        return new ContactsPresenter(mContext, mBinding);
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar.titleBar(mBinding.contactsTop).init();
    }
}