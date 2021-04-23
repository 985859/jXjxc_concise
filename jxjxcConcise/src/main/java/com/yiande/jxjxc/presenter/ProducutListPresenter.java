package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.reflect.TypeToken;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.managelayout.TopLinearLayoutManager;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.json.JsonUtil;
import com.mylibrary.api.widget.GangedView;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.ImgDetaicActivity;
import com.yiande.jxjxc.activity.ProductActivity;
import com.yiande.jxjxc.activity.SelectActivity;
import com.yiande.jxjxc.adapter.ProductAdapter;
import com.yiande.jxjxc.adapter.SpinnerAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.KeyBean;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.bean.OrderProAddBean;
import com.yiande.jxjxc.bean.ProductBean;
import com.yiande.jxjxc.bean.ProductListBean;
import com.yiande.jxjxc.databinding.ActivityProductListBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.GangedSelectPop;
import com.yiande.jxjxc.popwindow.ItemPop;
import com.yiande.jxjxc.popwindow.ProductSelectPop;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/18 18:40
 */
public class ProducutListPresenter extends BasePresenter<ActivityProductListBinding> {
    private GangedSelectPop GangedSelectPop;
    private ProductSelectPop ProductSelectPop;
    private SpinnerAdapter<OClassBean> brandAdapter;
    private SpinnerAdapter<OClassBean> stateAdapter;
    private SpinnerAdapter<OClassBean> inventoryStateAdapter;
    private ProductAdapter productAdapter;
    private ItemPop itemPop;
    private String classID;

    private String keywords = "";
    private String inventoryNum = "";
    private int brandID;
    private int page = 1;
    private int isOk = 2;
    private int level = 0;//客户级别
    private int userID = 0;//客户ID
    private int userLevel = 0;
    private int selectPosition = -1;
    private int warning = 0;
    private int type;//0 普通 1 入库  2 出库 3 库存预警

    public ProducutListPresenter(RxAppCompatActivity mContext, ActivityProductListBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            keywords = intent.getStringExtra("keywords");
            type = intent.getIntExtra("type", 0);
            userID = intent.getIntExtra("userID", 0);
            level = intent.getIntExtra("level", 0);
            productAdapter.setType(type);
            mBinding.setType(type);
            if (StringUtil.isNotEmpty(keywords)) {
                mBinding.productListTop.setTitle(keywords);
                mBinding.productListTop.setRightText("");
            }
            switch (type) {
                case 1:
                case 2:
                    isOk = 1;
                    mBinding.productListTop.setRightText("");
                    break;
                case 3:
                    mBinding.productListTop.setTitle("库存预警");
                    mBinding.productListTop.setRightText("");
                    warning = 1;
                    break;
                default:
                    break;
            }

        }

        mBinding.productListRefresh.autoRefresh();
        initLevel();
    }

    @Override
    protected void initData() {
        super.initData();
        brandAdapter = new SpinnerAdapter(null);
        mBinding.productListBrand.setAdapter(brandAdapter);
        getClassData(2);
        productAdapter = new ProductAdapter(null);
        mBinding.productListRec.setAdapter(productAdapter);
        mBinding.productListRec.setLayoutManager(new TopLinearLayoutManager(mContext));

        List<OClassBean> beanList = new ArrayList<>();
        OClassBean bean = new OClassBean();
        bean.setOClass_Name("全部");
        bean.setOClass_ID(2);
        beanList.add(bean);
        bean = new OClassBean();
        bean.setOClass_Name("启用");
        bean.setOClass_ID(1);
        beanList.add(bean);
        bean = new OClassBean();
        bean.setOClass_Name("禁用");
        bean.setOClass_ID(0);
        beanList.add(bean);

        stateAdapter = new SpinnerAdapter<>(null);
        stateAdapter.setList(beanList);
        mBinding.productListState.setAdapter(stateAdapter);


        List<OClassBean> list = new ArrayList<>();
        bean = new OClassBean();
        bean.setOClass_Name("全部");
        bean.setOClass_ID(-2);
        list.add(bean);
        bean = new OClassBean();
        bean.setOClass_Name("库存大于0");
        bean.setOClass_ID(1);
        list.add(bean);
        bean = new OClassBean();
        bean.setOClass_Name("库存等于0");
        bean.setOClass_ID(0);
        list.add(bean);
        bean = new OClassBean();
        bean.setOClass_Name("库存小于0");
        bean.setOClass_ID(-1);
        list.add(bean);

        inventoryStateAdapter = new SpinnerAdapter<>(null);
        inventoryStateAdapter.setList(list);
        mBinding.productListInventoryState.setAdapter(inventoryStateAdapter);


    }

    private void initLevel() {
        if (type == 2) {
            productAdapter.setLevel(level);
        } else {
            KeyBean keyBean = Util.getKeyBean(Util.USERLEVEL);
            if (keyBean == null) {
                userLevel = 0;
            } else {
                userLevel = keyBean.getValue().getLevel();
            }
            productAdapter.setLevel(userLevel);
        }
        productAdapter.setIsKg(Util.isShowEntry(Util.KG));
        KeyBean bean = Util.getKeyBean(Util.PRODUCTLEVEL);
        if (bean == null) {
            getGangedSelectPop().setMaxLevel(0);
        } else {
            getGangedSelectPop().setMaxLevel(bean.getValue().getLevel() - 1);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.productListTop.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.start(mContext, null, 0, true);
            }
        });
        mBinding.productListTop.setMySearchViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectActivity.start(mContext, 3, mBinding.productListTop.getSelectHint());
            }
        });
        mBinding.productListBrand.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                brandID = brandAdapter.getData().get(position).getId();
                brandAdapter.setSelectIndex(position);
                onRefreshData();

            }
        });

        mBinding.productListClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGangedSelectPop().showPopupWindow(mBinding.productListTop, Gravity.BOTTOM);
            }
        });
        mBinding.productListRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page += 1;
                getDate();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                onRefreshData();
            }
        });

        productAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (type == 0 || type == 3) {
                    ProductDetailActivity.start(mContext, productAdapter.getItem(position).getProduct_ID());
                }

            }
        });
        productAdapter.addChildClickViewIds(R.id.itmProduct_StockType, R.id.itmProduct_Pic, R.id.itmProduct_More);
        productAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.itmProduct_StockType:
                        getProductSelectPop().setData(productAdapter.getItem(position));
                        getProductSelectPop().showPopupWindow(mBinding.productListTop, Gravity.BOTTOM);
                        break;
                    case R.id.itmProduct_More:
                        selectPosition = position;
                        getItemPop().setTitle(productAdapter.getItem(position).getProduct_Title());
                        getItemPop().showPopupWindow(mBinding.productListTop, Gravity.BOTTOM);

                        break;

                    case R.id.itmProduct_Pic:
                        String picUrl = productAdapter.getItem(position).getProduct_PicUrl();
                        List<String> picList = StringUtil.slipStringToList(productAdapter.getItem(position).getProduct_Pic(), ",");
                        List<String> picUrls = new ArrayList<>();
                        for (String str : picList) {
                            picUrls.add(picUrl + str);
                        }
                        ImgDetaicActivity.start(mContext, 0, picUrls);
                        break;
                    default:
                        break;
                }
            }
        });
        mBinding.productListState.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                stateAdapter.setSelectIndex(position);
                isOk = stateAdapter.getItem(position).getOClass_ID();
                onRefreshData();
            }
        });
        mBinding.productListInventoryState.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                inventoryStateAdapter.setSelectIndex(position);
                if (position == 0) {
                    inventoryNum = "";
                } else {
                    inventoryNum = String.valueOf(inventoryStateAdapter.getItem(position).getOClass_ID());
                }

                onRefreshData();
            }
        });
    }

    private void onRefreshData() {
        page = 1;
        getDate();
    }

    EventConsume<Integer> itemClickListener = new EventConsume<Integer>() {
        @Override
        public void accept(Integer integer) {
            switch (integer) {
                case 0:
                    ProductActivity.start(mContext, null, productAdapter.getItem(selectPosition).getProduct_ID(), false);
                    break;
                case 1:

                    DialogUtils.showDialog(mContext, "是否删除  " + productAdapter.getItem(selectPosition).getProduct_Title(), new MyDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                delearPro(productAdapter.getItem(selectPosition));

                            }
                        }
                    });
                    break;
                default:
                    break;
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

    private GangedView.OnSelcetLinsenter selectListener = (ids, names) -> {
        if (Util.isNotListEmpty(ids)) {
            mBinding.productListClass.setRightTextColor(mContext.getResources().getColor(R.color.blue));
            mBinding.productListClass.setRightIcon(R.drawable.down_blue);
            if (ids.size() > 0) {
                classID = ids.get(ids.size() - 1);
            }
        } else {
            mBinding.productListClass.setRightTextColor(mContext.getResources().getColor(R.color.textColor));
            mBinding.productListClass.setRightIcon(R.drawable.down_up);
            classID = "";

        }
        onRefreshData();

    };


    private GangedSelectPop getGangedSelectPop() {
        if (GangedSelectPop == null) {
            GangedSelectPop = new GangedSelectPop(mContext);
            GangedSelectPop.setShowTextView(mBinding.productListClass.getRightView(), "产品分类");
            getGangedSelectPop().setOnSelectListener(selectListener);
            getClassData(1);
        }
        return GangedSelectPop;
    }


    @Override
    protected void finish() {
        super.finish();
        Intent intent = new Intent();
        intent.putExtra("listData", (Serializable) proAddBeanList);
        mContext.setResult(TypeEnum.REFRESH, intent);
    }

    private List<OrderProAddBean> proAddBeanList = new ArrayList<>();
    private EventConsume<OrderProAddBean> proAddBeanEventConsume = (bean) -> {
        proAddBeanList.add(JsonUtil.fromJson(bean, new TypeToken<OrderProAddBean>() {
        }.getType()));
        DialogUtils.showDialog(mContext, "是否继续添加产品", "继续添加", "返回", new MyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (!confirm) {
                    mContext.finish();
                }
            }
        }, false);

    };

    private ProductSelectPop getProductSelectPop() {
        if (ProductSelectPop == null) {
            ProductSelectPop = new ProductSelectPop(mContext, type);
            if (type == 1) {
                ProductSelectPop.setLevel(0);
            } else {
                ProductSelectPop.setLevel(level);
            }
            ProductSelectPop.setSupID(userID);
            ProductSelectPop.setProAddBeanEventConsume(proAddBeanEventConsume);
        }
        return ProductSelectPop;
    }

    private void getClassData(int flag) {
        RetrofitHttp.getRequest(HttpApi.class)
                .getOClassList(flag)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<OClassBean>>>(mContext, false) {
                    @Override
                    public void onSuccess(JsonBean<List<OClassBean>> bean) {
                        if (bean.code == 1) {
                            switch (flag) {
                                case 1:
                                    getGangedSelectPop().setData(bean.data);
                                    break;
                                case 2:
                                    if (Util.isNotListEmpty(bean.data)) {
                                        OClassBean bean1 = new OClassBean();
                                        bean1.setOClass_ID(0);
                                        bean1.setOClass_Name("全部");
                                        bean.data.add(0, bean1);
                                    }
                                    brandAdapter.setList(bean.data);
                                    brandAdapter.setEmptyView(Util.getEmptyView(mContext, "暂无品牌"));
                                    break;
                                default:
                                    break;
                            }

                        }
                    }
                });

    }


    private void getDate() {
        ArrayMap<String, Object> map = new ArrayMap<>();
        map.put("p.page", page);
        map.put("p.brand", brandID);
        map.put("p.isOK", isOk);
        map.put("p.warning", warning);
        if (StringUtil.isNotEmpty(inventoryNum))
            map.put("p.inventoryNum", inventoryNum);
        if (StringUtil.isNotEmpty(classID))
            map.put("p.cls", classID);
        if (StringUtil.isNotEmpty(keywords))
            map.put("p.text", keywords);

        RetrofitHttp.getRequest(HttpApi.class)
                .getProductList(map)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<ProductListBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<ProductListBean> bean) {
                        if (page == 1) {
                            mBinding.setData(bean.data);
                        }
                        JsonBean<List<ProductBean>> jsonBean = new JsonBean<>();
                        jsonBean.data = bean.data.getData();
                        jsonBean.msg = bean.msg;
                        jsonBean.count = bean.count;
                        jsonBean.ismsg = bean.ismsg;
                        jsonBean.code = bean.code;
                        Util.setRceclerData(jsonBean, page, mBinding.productListRefresh, mBinding.productListRec, productAdapter);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        super.onError(e);
                    }


                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case TypeEnum.REFRESH:
                onRefreshData();
                break;
            case TypeEnum.INIT:
                initLevel();
                onRefreshData();
                break;
            default:
                break;
        }
    }

    private void delearPro(ProductBean productBean) {
        RetrofitHttp.getRequest(HttpApi.class)
                .productDel(productBean.getProduct_ID())
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<Object>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<Object> bean) {
                        if (bean.code == 1) {
                            productAdapter.remove(productBean);
                        }
                    }
                });
    }
}
