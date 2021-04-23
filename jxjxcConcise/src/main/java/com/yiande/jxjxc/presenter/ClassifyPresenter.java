package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;

import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.adapter.ClassfityAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.KeyBean;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.bean.OClassBody;
import com.yiande.jxjxc.databinding.ActivityClassfityBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.OnSelectListener;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.AddClassifyPop;
import com.yiande.jxjxc.popwindow.ItemPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/16 17:02
 */
public class ClassifyPresenter extends BasePresenter<ActivityClassfityBinding> {
    private ClassfityAdapter classfityAdapter;
    private AddClassifyPop addClassPop;
    private OClassBean oClassBean;
    private List<OClassBean> classBeanList;
    private ItemPop itemPop;
    private Map<Integer, Integer> selectIndex;//记录 要查找的位置索引 每次调用前记得清空索引
    List<OClassBean> data = new ArrayList<>();

    public ClassifyPresenter(RxAppCompatActivity mContext, ActivityClassfityBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        initMaxLevel();
        if (intent != null) {
            data = (List<OClassBean>) intent.getSerializableExtra("data");
            if (Util.isNotListEmpty(data)) {
                classfityAdapter.setList(data);
            } else {
                mBinding.classfityRefresh.autoRefresh();
            }
        }

    }


    @Override
    protected void initData() {
        super.initData();
        mBinding.classfityRefresh.setEnableLoadMore(false);
        classfityAdapter = new ClassfityAdapter(null);
        mBinding.classfityRec.setAdapter(classfityAdapter);
        mBinding.classfityRec.setLayoutManager(new TopLinearLayoutManager(mContext));

    }

    private void initMaxLevel() {
        KeyBean keyBean = Util.getKeyBean(Util.PRODUCTLEVEL);
        if (classfityAdapter != null) {
            if (keyBean == null) {
                classfityAdapter.setMaxLevel(1);
            } else {
                classfityAdapter.setMaxLevel(keyBean.getValue().getLevel());
            }
        }

    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.classfityTop.setRightViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClass(0, 0, "", classfityAdapter.getData(), true);
            }
        });

        mBinding.classfityRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getData();
            }
        });
        classfityAdapter.setAddListener(new OnSelectListener<OClassBean>() {
            @Override
            public void onSelect(OClassBean bean, List<OClassBean> data, boolean b) {
                addClass(bean.getOClass_ID(), 0, "", data, true);
            }
        });

        classfityAdapter.setMoreListener((map) -> {
            selectIndex = map;
            if (map != null) {
                int level = map.size();
                if (level > 0) {
                    oClassBean = null;
                    classBeanList = classfityAdapter.getData();
                    for (int i = 1; i <= level; i++) {
                        int position = map.get(i);
                        if (position < classBeanList.size()) {
                            oClassBean = classBeanList.get(position);
                            if (i != level) {
                                classBeanList = oClassBean.getOClass_Child();
                            }
                        }
                    }
                    if (oClassBean != null) {
                        getItemPop().showPopupWindow(mBinding.classfityTop, Gravity.BOTTOM);
                        getItemPop().setTitle(oClassBean.getName());
                    }
                }
            }

        });
    }

    boolean isFirst = true;

    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getOClassList(1)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<OClassBean>>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<List<OClassBean>> bean) {
                        if (bean.code == 1) {
                            classfityAdapter.setList(bean.data);
                            if (isFirst) {
                                isFirst = false;
                                classfityAdapter.setEmptyView(Util.getEmptyView(mContext));
                            }
                        }

                    }

                    @Override
                    public void onFinsh() {
                        super.onFinsh();
                        mBinding.classfityRefresh.finishRefresh();
                    }
                });
    }


    private EventSendData<OClassBody, Boolean> sendDataInterface = (bean, data) -> {
        postData(bean, data);
    };

    private void addClass(int fatherID, int id, String edit, List<OClassBean> data, boolean isAdd) {
        addClassPop = getAddClassPop();

        addClassPop.setFatherID(fatherID);
        addClassPop.setID(id);
        addClassPop.setAdd(isAdd);
        //要在setID   setAdd之后
        addClassPop.setData(data);
        addClassPop.setEdit(edit);
        addClassPop.showKeyboard();
        addClassPop.setOnAddSucceedListener(sendDataInterface);
        addClassPop.showPopupWindow(mBinding.classfityTop, Gravity.BOTTOM);
    }


    private AddClassifyPop getAddClassPop() {
        if (addClassPop == null) {
            addClassPop = new AddClassifyPop(mContext);
            addClassPop.setMaxLength(10);
        }
        return addClassPop;
    }

    private void postData(OClassBody body, boolean isAdd) {
        HttpObserver observer = new HttpObserver<JsonBean<Object>>(mContext) {
            @Override
            public void onSuccess(JsonBean<Object> bean) {
                if (bean.code == 1) {
                    getData();
                    getAddClassPop().dismiss();
                    int falg = 1;
                    if (isAdd) {
                        falg = 0;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("flag", falg);
                    mContext.setResult(TypeEnum.REFRESH, intent);
                }

            }

            @Override
            public void msgOnClick(boolean click, int type) {
                super.msgOnClick(click, type);
                if (type == 6) {
                    Util.getAppFucntion(mContext, (code) -> {
                        if (code == 1) {
                            initMaxLevel();

                        }
                    });
                }
            }
        };
        if (isAdd) {
            RetrofitHttp.getRequest(HttpApi.class)
                    .oClassAdd(body)
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);
        } else {
            RetrofitHttp.getRequest(HttpApi.class)
                    .oClassModify(body)
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);
        }

    }


    private void delearClass(OClassBean classBean) {
        RetrofitHttp.getRequest(HttpApi.class)
                .oClassDel(classBean.getOClass_ID(), 1)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<Object> bean) {
                        if (bean.code == 1) {
                            mContext.setResult(TypeEnum.REFRESH);
                            delearBean();

                        }
                    }
                });
    }


    private void delearBean() {
        if (selectIndex != null) {
            int level = selectIndex.size();
            if (level > 0) {
                OClassBean oClassBean = null;
                List<OClassBean> classBeanList = classfityAdapter.getData();
                int index = 0;
                for (int i = 1; i <= level; i++) {
                    if (i == 1) {
                        index = selectIndex.get(i);
                    }
                    int position = selectIndex.get(i);
                    if (position < classBeanList.size()) {
                        oClassBean = classBeanList.get(position);
                        if (i != level) {
                            classBeanList = oClassBean.getOClass_Child();
                        } else {//查找到最后一级 删除数据更新UI
                            if (level == 1) {
                                classfityAdapter.remove(oClassBean);
                            } else {
                                classBeanList.remove(oClassBean);
                                classfityAdapter.notifyItemChanged(index);
                            }
                        }
                    }
                }

            }
        }
    }

    EventConsume<Integer> itemClickListener = new EventConsume<Integer>() {
        @Override
        public void accept(Integer integer) {
            if (oClassBean != null) {
                switch (integer) {
                    case 0:

                        addClass(oClassBean.getOClass_FatherID(), oClassBean.getOClass_ID(), oClassBean.getOClass_Name(), classBeanList, false);

                        break;
                    case 1:

                        DialogUtils.showDialog(mContext, "是否删除  " + oClassBean.getOClass_Name(), new MyDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {

                                    delearClass(oClassBean);
                                }
                            }
                        });

                        break;
                    default:
                        break;
                }

            }

        }
    };

    private ItemPop getItemPop() {
        if (itemPop == null) {
            itemPop = new ItemPop(mContext);
            itemPop.setOnItemClickListener(itemClickListener);
        }
        return itemPop;
    }
}
