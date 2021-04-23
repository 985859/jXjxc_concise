package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mylibrary.api.utils.SystemUtil;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.AZAdapter;
import com.yiande.jxjxc.adapter.AZItmAdapter;
import com.yiande.jxjxc.azlist.AZItemEntity;
import com.yiande.jxjxc.azlist.AZTitleDecoration;
import com.yiande.jxjxc.azlist.AZWaveSideBarView;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.SupplierBean;
import com.yiande.jxjxc.bean.UserKeyBean;
import com.yiande.jxjxc.databinding.ActivityAZListBinding;
import com.yiande.jxjxc.myInterface.EventTransform;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/10 15:18
 */
public class AZPresenter extends BasePresenter<ActivityAZListBinding> {

    private AZAdapter azAdapter;
    List<UserKeyBean> userKeyBeanList;
    AZItmAdapter itmAdapter;
    private int type;//1 客户 2 经销商
    private int state;//1 出库 2 入库 3 新增欠款 4 新增赊账 5 新增售后

    public AZPresenter(RxAppCompatActivity mContext, ActivityAZListBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 1);
            state = intent.getIntExtra("state", 0);
        }
        itmAdapter.setState(state);
        azAdapter.setState(state);
        getData();
    }


    @Override
    protected void initData() {
        super.initData();
        mBinding.AZListTop.getMySearchView().getEdittext().setFocusable(true);
        mBinding.AZListTop.getMySearchView().getEdittext().setFocusableInTouchMode(true);
        azAdapter = new AZAdapter(mContext, new ArrayList<>());
        mBinding.azListRec.setAdapter(azAdapter);
        mBinding.azListRec.setLayoutManager(new LinearLayoutManager(mContext));
        AZTitleDecoration.TitleAttributes attributes = new AZTitleDecoration.TitleAttributes(mContext);
        attributes.setFloatTextColor(mContext.getResources().getColor(R.color.blue));
        attributes.setFloatBackgroundColor(mContext.getResources().getColor(R.color.white));
        attributes.setTextPadding(14);
        mBinding.azListRec.addItemDecoration(new AZTitleDecoration(attributes));
        itmAdapter = new AZItmAdapter();
        mBinding.AZListSelectRec.setAdapter(itmAdapter);
        mBinding.AZListSelectRec.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.AZListSelectRec.setMinimumHeight(SystemUtil.getScreenHeight(mContext) / 3);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.azListBarRec.setOnLetterChangeListener(new AZWaveSideBarView.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int position = azAdapter.getSortLettersFirstPosition(letter);
                if (position != -1) {
                    if (mBinding.azListRec.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager manager = (LinearLayoutManager) mBinding.azListRec.getLayoutManager();
                        manager.scrollToPositionWithOffset(position, 0);
                    } else {
                        mBinding.azListRec.getLayoutManager().scrollToPosition(position);
                    }
                }
            }
        });
        mBinding.AZListTop.getMySearchView().getEdittext().addTextChangedListener(new TextWatcher() {
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
    }

    private void getSelectData(Editable str) {
        if (str == null || str.length() == 0) {
            mBinding.setShowRec(false);
        } else {
            if (Util.isNotListEmpty(userKeyBeanList)) {
                List<SupplierBean> supplierBeanList = new ArrayList<>();
                for (int i = 0; i < userKeyBeanList.size(); i++) {
                    UserKeyBean keyBean = userKeyBeanList.get(i);
                    if (keyBean != null && Util.isNotListEmpty(keyBean.getUser())) {
                        for (int k = 0; k < keyBean.getUser().size(); k++) {
                            SupplierBean bean = keyBean.getUser().get(k);
                            if (matchString(bean.getComName(), str.toString())) {
                                supplierBeanList.add(bean);
                            }
                        }
                    }
                }
                if (supplierBeanList.size() > 0) {
                    mBinding.setShowRec(true);
                    itmAdapter.setList(supplierBeanList);
                } else {
                    mBinding.setShowRec(false);
                }

            } else {
                mBinding.setShowRec(false);
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

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getChoiceUser(type)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<UserKeyBean>>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<List<UserKeyBean>> bean) {
                        if (bean.code == 1) {
                            userKeyBeanList = bean.data;
                            azAdapter.setDataList(bean.data);
                        }
                    }
                });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (TypeEnum.REFRESH == resultCode) {
            mContext.setResult(resultCode);
            mContext.finish();
        }
    }
}
