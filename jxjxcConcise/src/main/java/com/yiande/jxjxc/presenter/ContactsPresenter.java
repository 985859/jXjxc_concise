package com.yiande.jxjxc.presenter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.ContactsAdapter;
import com.yiande.jxjxc.azlist.AZTitleDecoration;
import com.yiande.jxjxc.azlist.AZWaveSideBarView;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.ContactsBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.UserBean;
import com.yiande.jxjxc.databinding.ActivityContactsBinding;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2021/4/22 15:11
 */
public class ContactsPresenter extends BasePresenter<ActivityContactsBinding> {
    private int userType;
    private ContactsAdapter contactsAdapter;
    private List<ContactsBean> contactsBeanList;

    private boolean isAll = false;//当前是否是全选状态

    public ContactsPresenter(RxAppCompatActivity mContext, ActivityContactsBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            userType = intent.getIntExtra("type", 1);
        }
        contactsBeanList = getAllContacts(mContext);
        Comparator comparator = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(contactsBeanList, new Comparator<ContactsBean>() {
            public int compare(ContactsBean bean1, ContactsBean bean2) {
                if (bean1.getKey().equals("#")) {
                    return 1;
                } else {
                    return comparator.compare(bean1.getKey(), bean2.getKey());
                }
            }
        });
        getSelectData(null);
    }

    @Override
    protected void initData() {
        super.initData();
        mBinding.contactsTop.getMySearchView().getEdittext().setFocusable(true);
        mBinding.contactsTop.getMySearchView().getEdittext().setFocusableInTouchMode(true);

        contactsAdapter = new ContactsAdapter(mContext, null);
        mBinding.contactsListRec.setAdapter(contactsAdapter);
        mBinding.contactsListRec.setLayoutManager(new LinearLayoutManager(mContext));
        AZTitleDecoration.TitleAttributes attributes = new AZTitleDecoration.TitleAttributes(mContext);
        attributes.setFloatTextColor(mContext.getResources().getColor(R.color.blue));
        attributes.setFloatBackgroundColor(mContext.getResources().getColor(R.color.white));
        attributes.setTextPadding(14);
        attributes.setDrawFloat(false);
        mBinding.contactsListRec.addItemDecoration(new AZTitleDecoration(attributes));


    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.contactsListBarRec.setOnLetterChangeListener(new AZWaveSideBarView.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = contactsAdapter.getSortLettersFirstPosition(letter);
                if (position != -1) {
                    if (mBinding.contactsListRec.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager manager = (LinearLayoutManager) mBinding.contactsListRec.getLayoutManager();
                        manager.scrollToPositionWithOffset(position, 0);
                    } else {
                        mBinding.contactsListRec.getLayoutManager().scrollToPosition(position);
                    }
                }
            }
        });
        mBinding.contactsTop.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAll) {
                    setAll(false);
                    mBinding.contactsTop.setRightText("全选");
                } else {
                    setAll(true);
                    mBinding.contactsTop.setRightText("取消");

                }
            }
        });
        mBinding.contactsTop.getMySearchView().getEdittext().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSelectData(s);
            }
        });

        mBinding.contactsListBT.setOnClickListener(v -> {
            List<UserBean> list = getUserList();
            if (Util.isListEmpty(list)) {
                ToastUtil.showShort("请选择需要导入的内容");
                return;
            }
            mBinding.contactsListBT.setEnabled(false);
            RetrofitHttp.getRequest(HttpApi.class)
                    .userBatchAdd(list)
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                        @Override
                        public void onSuccess(JsonBean<Object> bean) {
                            if (bean.code == 1) {
                                mContext.setResult(TypeEnum.REFRESH);

                            }
                        }

                        @Override
                        public void msgOnClick(boolean click, int isMsg) {
                            super.msgOnClick(click, isMsg);
                            mContext.finish();
                        }

                        @Override
                        public void onFinsh() {
                            super.onFinsh();
                            mBinding.contactsListBT.setEnabled(true);
                        }
                    });

        });

    }

    public void setAll(boolean all) {
        isAll = all;
        if (all) {
            if (contactsAdapter.getItemCount() > 0) {
                for (ContactsBean bean : contactsAdapter.getDataList()) {
                    for (UserBean userBean : bean.getUserList()) {
                        userBean.setSelect(all);
                    }
                }
            }
        } else {
            if (contactsBeanList != null && contactsBeanList.size() > 0) {
                for (ContactsBean bean : contactsAdapter.getDataList()) {
                    for (UserBean userBean : bean.getUserList()) {
                        userBean.setSelect(all);
                    }
                }
            }
        }
        contactsAdapter.notifyDataSetChanged();
    }

    private void getSelectData(Editable str) {
        if (str == null || str.length() == 0) {
            contactsAdapter.setDataList(contactsBeanList);
            return;
        } else {
            if (Util.isNotListEmpty(contactsBeanList)) {
                List<ContactsBean> beanList = new ArrayList<>();
                for (int i = 0; i < contactsBeanList.size(); i++) {
                    ContactsBean keyBean = new ContactsBean();
                    keyBean.setUserList(contactsBeanList.get(i).getUserList());
                    keyBean.setKey(contactsBeanList.get(i).getKey());
                    List<UserBean> userBeanList = new ArrayList<>();
                    if (keyBean != null && Util.isNotListEmpty(keyBean.getUserList())) {
                        for (int k = 0; k < keyBean.getUserList().size(); k++) {
                            UserBean bean = keyBean.getUserList().get(k);
                            if (matchString(bean.getUser_ComName(), str.toString()) || matchString(bean.getUser_Mob(), str.toString())) {
                                userBeanList.add(bean);
                            }
                        }
                    }
                    if (userBeanList.size() > 0) {
                        keyBean.setUserList(userBeanList);
                        beanList.add(keyBean);
                    }
                }
                contactsAdapter.setDataList(beanList);
            }
        }

    }

    //方法2、通过正则表达式
    private boolean matchString(String parent, String child) {
        int count = 0;
        Pattern p = Pattern.compile(child);
        Matcher m = p.matcher(parent);
        while (m.find()) {
            count++;
        }
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    private int flag = 0;

    public ArrayList<ContactsBean> getAllContacts(Context context) {
        ArrayList<ContactsBean> contactsBeanList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            //新建一个联系人实例
            UserBean userBean = new UserBean();
            userBean.setFlag(flag);
            flag += 1;
            //获取联系人ID
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            userBean.setUser_ComName(name);
            String s = name.substring(0, 1);
            String key = Util.getSpells(s);
            if (StringUtil.isEmpty(key)) {
                key = "#";
            }
            //获取联系人电话号码
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            while (phoneCursor.moveToNext()) {
                //格式化手机号码
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                phone = phone.replace("(", "");
                phone = phone.replace(")", "");
                userBean.setUser_Mob(phone);
            }
            userBean.setUser_Type(userType);
            addUser(key, userBean, contactsBeanList);
            //记得要把cursor给close掉
            phoneCursor.close();
        }
        cursor.close();
        return contactsBeanList;
    }

    private void addUser(String key, UserBean userBean, ArrayList<ContactsBean> contactsBeanList) {
        if (contactsBeanList.size() == 0) {
            contactsBeanList.add(getContactsBean(key, userBean));
        } else {
            for (ContactsBean contactsBean : contactsBeanList) {
                if (key.equals(contactsBean.getKey())) {
                    contactsBean.getUserList().add(userBean);
                    return;
                }
            }
            contactsBeanList.add(getContactsBean(key, userBean));
        }
    }

    private ContactsBean getContactsBean(String key, UserBean userBean) {
        ContactsBean contactsBean = new ContactsBean();
        contactsBean.setKey(key);
        List<UserBean> userBeans = new ArrayList<>();
        userBeans.add(userBean);
        contactsBean.setUserList(userBeans);
        return contactsBean;
    }


    private List<UserBean> getUserList() {
        List<UserBean> beanList = null;
        if (contactsBeanList != null && contactsBeanList.size() > 0) {
            beanList = new ArrayList<>();
            for (ContactsBean bean : contactsBeanList) {
                for (UserBean userBean : bean.getUserList()) {
                    if (userBean.isSelect()) {
                        beanList.add(userBean);
                    }
                }
            }
        }
        return beanList;
    }

}
