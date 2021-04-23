package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.mylibrary.api.utils.PointLengthFilter;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.mylibrary.api.widget.GangedView;
import com.retrofithttp.api.LoadingDialog;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.ClassifyActivity;
import com.yiande.jxjxc.activity.ImgDetaicActivity;
import com.yiande.jxjxc.adapter.PicImageAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.KeyBean;
import com.yiande.jxjxc.bean.OClassBean;
import com.yiande.jxjxc.bean.ProductBean;
import com.yiande.jxjxc.databinding.ActivityProductBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.AddBrandUnitPop;
import com.yiande.jxjxc.popwindow.GangedSelectPop;
import com.yiande.jxjxc.popwindow.WheelSelectPop;
import com.yiande.jxjxc.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/17 17:39
 */
public class ProductPresenter extends BasePresenter<ActivityProductBinding> {
    private PicImageAdapter imageAdapter;
    private int proID;
    private int max = 5;
    private int userLevel = 0;
    private ProductBean productBean;
    private boolean isAdd = true;
    private GangedSelectPop GangedSelectPop;
    private WheelSelectPop<OClassBean> selectBrandPop;
    private WheelSelectPop<OClassBean> selectUnitPop;
    private AddBrandUnitPop addTextPop;
    private List<OClassBean> calssData = new ArrayList<>();
    private EventConsume<Integer> onClick = (type) -> {
        switch (type) {
            case 6:
                upPic();
                break;
            default:
                break;
        }
    };

    public ProductPresenter(RxAppCompatActivity mContext, ActivityProductBinding binding) {
        super(mContext, binding);
        mBinding.setOnClick(onClick);
        productBean = new ProductBean();
        Intent intent = getIntent();
        if (intent != null) {
            isAdd = intent.getBooleanExtra("isAdd", true);
            mBinding.setIsAdd(isAdd);
            if (isAdd) {
                productBean.setProduct_IsOK(1);
                setProductBean(productBean);
            } else {
                mBinding.productTop.setTitle("修改产品");
                productBean = (ProductBean) intent.getSerializableExtra("productBean");
                if (productBean != null) {
                    setProductBean(productBean);
                } else {
                    proID = intent.getIntExtra("proID", -1);
                    getData();
                }
            }
        }
        initLevel();

    }

    private void initLevel() {
        KeyBean keyBean = Util.getKeyBean(Util.USERLEVEL);
        mBinding.setIsKg(Util.isShowEntry(Util.KG));
        if (keyBean == null) {
            userLevel = 0;
        } else {
            userLevel = keyBean.getValue().getLevel();
        }
        mBinding.setLevel(userLevel);

        KeyBean bean = Util.getKeyBean(Util.PRODUCTLEVEL);
        if (bean == null) {
            getGangedSelectPop().setMaxLevel(0);
        } else {
            getGangedSelectPop().setMaxLevel(bean.getValue().getLevel() - 1);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        imageAdapter = new PicImageAdapter(null);
        mBinding.productAddPic.PicRec.setAdapter(imageAdapter);
        mBinding.productAddPic.PicRec.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        getClassData(3, 0);
        PointLengthFilter filter = new PointLengthFilter();
        filter.setPointerLength(3);
        mBinding.productKg.getEditText().setFilters(new InputFilter[]{filter});
    }

    @Override
    protected void setListener() {
        super.setListener();

        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                List<String> stringList = new ArrayList<>();
                for (LocalMedia media : imageAdapter.getData()) {
                    if ("url".equals(media.getMimeType())) {
                        stringList.add(media.getPath());
                    } else {
                        String path = Util.getPath(media);

                        stringList.add(path);
                    }
                }
                ImgDetaicActivity.start(mContext, position, stringList);
            }
        });

        mBinding.producutState.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer integer) {
                if (integer == 0) {
                    setState(1);
                } else {
                    setState(0);
                }

            }
        });
        mBinding.producutClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGangedSelectPop().showPopupWindow(mBinding.productTop, Gravity.BOTTOM);
               getGangedSelectPop().setselectID(productBean.getClassID());
            }
        });
        mBinding.producutClass.setAddOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassifyActivity.start(mContext, calssData, 0);
            }
        });

        mBinding.producutBrand.setAddOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBrandUnit(2);//添加品牌
            }
        });
        mBinding.producutBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBrandselectPop().showPopupWindow(mBinding.productTop, Gravity.BOTTOM);
            }
        });
        mBinding.producutUnit.setAddOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBrandUnit(3);//添加单位
            }
        });

        mBinding.producutUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getUnitselectPop().showPopupWindow(mBinding.productTop, Gravity.BOTTOM);
            }
        });

        mBinding.productAddPic.PicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPic();
            }
        });
    }

    public void setState(int state) {
        productBean.setProduct_IsOK(state);
    }

    public void setProductBean(ProductBean productBean) {
        this.productBean = productBean;
        mBinding.setData(productBean);
        getUnitselectPop().setID(productBean.getProduct_Unit_ID());
        getBrandselectPop().setID(productBean.getProduct_Brand_ID());
        mBinding.producutClass.setText(productBean.getClassName());
        if (productBean.getProduct_IsOK() == 1) {
            mBinding.producutState.setType(0);
        } else {
            mBinding.producutState.setType(1);
        }
        if (StringUtil.isNotEmpty(productBean.getProduct_Pic())) {
            List<String> picList = StringUtil.slipStringToList(productBean.getProduct_Pic(), ",");
            List<LocalMedia> selectList = new ArrayList<>();
            for (String s : picList) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(productBean.getProduct_PicUrl() + s);
                localMedia.setCutPath(s);
                localMedia.setMimeType("url");
                selectList.add(localMedia);
            }
            imageAdapter.setList(selectList);

        }
    }


    private void addPic() {
        int count = imageAdapter.getData().size();
        if (count < max) {
            Util.openCamera(mContext, max - count, true, false);
        } else {
            ToastUtil.showShort( "最多上传 " + max + " 张图片");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST:
                        // 结果回调
                        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                        if (Util.isNotListEmpty(selectList)) {
                            imageAdapter.addData(selectList);
                        } else {
                            ToastUtil.showShort( "获取图片失败");
                        }

                        break;
                    default:
                        break;
                }
                break;
            case TypeEnum.REFRESH:
                switch (requestCode) {
                    case 0:
                        int flag = 0;
                        if (data != null) {
                            flag = data.getIntExtra("flag", 0);
                        }
                        getClassData(flag, 1);
                        break;
                    default:
                        break;
                }

                break;

            default:
                break;
        }

    }


    private GangedView.OnSelcetLinsenter onselectLinsenter = (ids, names) -> {
        productBean.setClassID(ids);
        productBean.setCLassName(names);
    };

    private GangedSelectPop getGangedSelectPop() {
        if (GangedSelectPop == null) {
            GangedSelectPop = new GangedSelectPop(mContext);
            GangedSelectPop.setShowTextView(mBinding.producutClass.getTextView());
            getGangedSelectPop().setOnSelectListener(onselectLinsenter);
            getClassData(1, 0);
        }
        return GangedSelectPop;
    }


    private EventSendData<OClassBean, Boolean> brandselectConsume = (bean, isReset) -> {
        if (isReset) {
            productBean.setProduct_Brand_ID(0);
            productBean.setProduct_Brand_Name("");
        } else {
            if (bean != null) {
                productBean.setProduct_Brand_ID(bean.getOClass_ID());
                productBean.setProduct_Brand_Name(bean.getOClass_Name());
            }
        }
    };

    private WheelSelectPop<OClassBean> getBrandselectPop() {
        if (selectBrandPop == null) {
            selectBrandPop = new WheelSelectPop(mContext);
            getClassData(2, 0);
            selectBrandPop.setTitle("选择产品品牌");
            selectBrandPop.setEventConsume(brandselectConsume);
            selectBrandPop.setShowTextView(mBinding.producutBrand.getTextView());
        }
        return selectBrandPop;
    }

    private EventSendData<OClassBean, Boolean> unitselectConsume = (bean, isReset) -> {
        if (isReset) {
            productBean.setProduct_Unit_ID(0);
            productBean.setProduct_Unit_Name("");
        } else {
            if (bean != null) {
                productBean.setProduct_Unit_ID(bean.getOClass_ID());
                productBean.setProduct_Unit_Name(bean.getOClass_Name());
            }
        }
    };

    private WheelSelectPop<OClassBean> getUnitselectPop() {
        if (selectUnitPop == null) {
            selectUnitPop = new WheelSelectPop(mContext);
            selectUnitPop.setTitle("选择产品单位");
            getClassData(3, 0);
            selectUnitPop.setEventConsume(unitselectConsume);
            selectUnitPop.setShowTextView(mBinding.producutUnit.getTextView());
        }
        return selectUnitPop;
    }

    int flage = 2;
    private EventConsume<JsonBean<Object>> addBrandUnitConsume = (bean) -> {
        if (bean.code == 1) {
            getClassData(flage, 0);
        }
    };

    private void addBrandUnit(int flag) {
        this.flage = flag;
        if (addTextPop == null) {
            addTextPop = new AddBrandUnitPop(mContext);
            addTextPop.setMaxLength(10);
            addTextPop.setOnConfrimLsenter(addBrandUnitConsume);
        }
        addTextPop.setAdd(true);
        addTextPop.setEdit("");
        addTextPop.setFlag(flag);// 1 产品分类  2 品牌   3单位
        switch (flag) {
            case 2:
                addTextPop.setTitle("添加品牌");
                addTextPop.setHint("请输入品牌名称");
                break;
            case 3:
                addTextPop.setTitle("添加单位");
                addTextPop.setHint("请输入单位名称");
                break;
            default:
                break;
        }
        addTextPop.showPopupWindow(mBinding.productTop, Gravity.BOTTOM);
    }


    /**
     * @param flag 产品分类 1,品牌2,单位3
     * @param type 分类选择框是否重置 0 不重置 1重置
     * @return
     * @author hukui
     * @time 2020/12/3
     * @Description
     */
    private void getClassData(int flag, int type) {
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
                                    calssData = bean.data;
                                    if (type == 0) {
                                        getGangedSelectPop().setData(bean.data, false);
                                        if (Util.isNotListEmpty(productBean.getClassID())) {
                                            getGangedSelectPop().setselectID(productBean.getClassID());
                                        }
                                    } else {
                                        getGangedSelectPop().setData(bean.data);
                                    }
                                    break;
                                case 2:
                                    getBrandselectPop().setData(bean.data);
                                    break;
                                case 3:
                                    getUnitselectPop().setData(bean.data);

                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });

    }


    private void getData() {
        RetrofitHttp.getRequest(HttpApi.class)
                .getProduct(proID)
                .compose(RxThreadUtil.observableToMain())
                .compose(mContext.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<ProductBean>>(mContext) {
                    @Override
                    public void onSuccess(JsonBean<ProductBean> bean) {
                        if (bean.code == 1) {
                            if (bean.data != null) {
                                productBean = bean.data;
                                setProductBean(productBean);
                            }
                        }
                    }
                });
    }


    private String pic = "";

    private void upPic() {
        if (productBean != null) {
            pic = "";
            if (StringUtil.isEmpty(productBean.getProduct_Title())) {
                ToastUtil.showShort( mContext.getString(R.string.product_hint_name));
                return;
            }
            if (StringUtil.isEmpty(productBean.getProduct_Model())) {
                ToastUtil.showShort( mContext.getString(R.string.prodct_hint_size));
                return;
            }

            if (StringUtil.isEmpty(productBean.getProduct_BuyPrice())) {
                ToastUtil.showShort( mContext.getString(R.string.product_purchasePrice_hint));
                return;
            }

            if (StringUtil.isEmpty(productBean.getProduct_Price())) {
                ToastUtil.showShort( mBinding.productPrice.getHint());
                return;
            }

            if (Util.isNotListEmpty(imageAdapter.getData())) {
                List<File> fileList = new ArrayList<>();
                LoadingDialog dialog = new LoadingDialog(mContext);
                dialog.show();
                for (LocalMedia media : imageAdapter.getData()) {
                    if ("url".equals(media.getMimeType())) {
                        pic += media.getCutPath() + ',';
                    } else {
                        String path = Util.getPath(media);
                        File file = new File(path);
                        fileList.add(file);
                    }
                }
                productBean.setProduct_Pic(pic);
                if (fileList.size() > 0) {
                    mBinding.producutBT.setEnabled(false);
                    RetrofitHttp.getRequest(HttpApi.class)
                            .uploadImage(Util.filesToMultipartBody(fileList), 0)
                            .compose(RxThreadUtil.observableToMain())
                            .compose(mContext.bindToLifecycle())
                            .subscribe(new HttpObserver<JsonBean<String>>(mContext) {
                                @Override
                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                                    super.onSubscribe(d);
                                    dialog.dismiss();
                                }

                                @Override
                                public void onSuccess(JsonBean<String> bean) {
                                    if (bean.code == 1) {
                                        if (StringUtil.isNotEmpty(bean.data)) {
                                            pic += bean.data;
                                            productBean.setProduct_Pic(pic);
                                            postData();
                                        }
                                    }

                                }

                                @Override
                                public void onFinsh() {
                                    super.onFinsh();
                                    mBinding.producutBT.setEnabled(true);
                                }
                            });

                } else {
                    postData();
                }

            } else {
                postData();
            }

        }


    }

    private void postData() {

        if (productBean != null) {
            mBinding.producutBT.setEnabled(false);
            HttpObserver observer = new HttpObserver<JsonBean<Object>>(mContext) {
                @Override
                public void onSuccess(JsonBean<Object> bean) {
                    if (bean.code == 1) {
                        mContext.setResult(TypeEnum.REFRESH);
                    }
                }

                @Override
                public void msgOnClick(boolean click, int type) {
                    super.msgOnClick(click, type);
                    if (type == 6) {
                        Util.getAppFucntion(mContext, (code) -> {
                            if (code == 1) {
                                initLevel();
                                mContext.setResult(TypeEnum.INIT);
                            }
                        });
                    } else {
                        mContext.finish();
                    }

                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    super.onError(e);
                }

                @Override
                public void onFinsh() {
                    super.onFinsh();
                    mBinding.producutBT.setEnabled(true);
                }

            };
            if (isAdd) {
                RetrofitHttp.getRequest(HttpApi.class)
                        .productAdd(productBean)
                        .compose(RxThreadUtil.observableToMain())
                        .compose(mContext.bindToLifecycle())
                        .subscribe(observer);
            } else {
                RetrofitHttp.getRequest(HttpApi.class)
                        .productModify(productBean)
                        .compose(RxThreadUtil.observableToMain())
                        .compose(mContext.bindToLifecycle())
                        .subscribe(observer);
            }

        }

    }


}
