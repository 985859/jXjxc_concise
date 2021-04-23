package com.yiande.jxjxc.presenter;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.mylibrary.api.interfaces.TypeValues;
import com.mylibrary.api.utils.DateUtils;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.LoadingDialog;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.AfterServiceProductActivity;
import com.yiande.jxjxc.activity.ImgDetaicActivity;
import com.yiande.jxjxc.activity.RichTextActivity;
import com.yiande.jxjxc.adapter.AfterServiceProAdapter;
import com.yiande.jxjxc.adapter.PicImageAdapter;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.AdminListBean;
import com.yiande.jxjxc.bean.AfterServiceBody;
import com.yiande.jxjxc.bean.AfterServiceProductBean;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.databinding.ActivityAfterServiceAddBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.EventSendData;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;
import com.yiande.jxjxc.popwindow.AfterServiceProductSelectPop;
import com.yiande.jxjxc.popwindow.WheelSelectPop;
import com.yiande.jxjxc.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

import static android.app.Activity.RESULT_OK;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/22 17:48
 */
public class AfterServiceAddPresenter extends BasePresenter<ActivityAfterServiceAddBinding> {
    private int userID;
    AfterServiceProductSelectPop ProductSelectPop;
    private String userComName;
    private AfterServiceBody serviceBody;
    private WheelSelectPop<AdminListBean> maintainEmployeePop;
    private String beginTIme;
    private String endTime;
    private String content = "";
    private TimePickerView pvTime;
    private Calendar selectedDate;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private String dateFormat = "yyyy-MM-dd";
    private Date startData;
    private Date endData;
    private int selectIndex;
    private int timeType;
    private int max = 5;
    AfterServiceProAdapter proAdapter;
    private PicImageAdapter imageAdapter;
    private EventConsume<Integer> onClik = (type) -> {
        switch (type) {
            case 0://添加售后产品
                AfterServiceProductActivity.start(mContext, userID);
                break;
            case 1://选择维修人员
                getMaintainEmployeePop().showPopupWindow(mBinding.afterServiceAddTop, Gravity.BOTTOM);
                break;
            case 2://选择维修开始时间
                startCalendar();
                break;
            case 3://悬着维修结束时间
                endCalendar();
                break;
            case 4://编辑售后详情
                RichTextActivity.start(mContext, "售后详情记录", content, true);
                break;
            case 5://提交数据
                upPic();
                break;
            default:
                break;
        }
    };

    public AfterServiceAddPresenter(RxAppCompatActivity mContext, ActivityAfterServiceAddBinding binding) {
        super(mContext, binding);
        getMaintainEmployeePop();
        Intent intent = getIntent();
        if (intent != null) {

            userID = intent.getIntExtra("userID", 0);
            userComName = intent.getStringExtra("userComName");
        }
        serviceBody = new AfterServiceBody();
        serviceBody.setUID(userID);
        serviceBody.setSID(-1);
        binding.setOnClick(onClik);
        binding.setData(serviceBody);
        binding.setComName(userComName);
        selectedDate = Calendar.getInstance();//系统当前时间
        startCalendar = Calendar.getInstance();
        startCalendar.set(DateUtils.getYear() - 1, 0, 1, 0, 0, 0);
        endCalendar = Calendar.getInstance();
        endCalendar.set(DateUtils.getYear() + 1, 11, 31, 0, 0, 0);
        String endTime = DateUtils.getTime(dateFormat);
        endData = DateUtils.formarStringToDate(endTime, dateFormat);

    }

    @Override
    protected void initData() {
        super.initData();

        imageAdapter = new PicImageAdapter(null);
        mBinding.afterServiceAddPic.PicRec.setAdapter(imageAdapter);
        mBinding.afterServiceAddPic.PicRec.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        proAdapter = new AfterServiceProAdapter();
        mBinding.afterServiceAddProductRec.setAdapter(proAdapter);
        mBinding.afterServiceAddProductRec.setLayoutManager(new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

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
        proAdapter.addChildClickViewIds(R.id.itmAfterServicePro_Ament, R.id.itmAfterServicePro_Delete);
        proAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                selectIndex = position;
                switch (view.getId()) {
                    case R.id.itmAfterServicePro_Ament:
                        getProductSelectPop().setData(proAdapter.getItem(position));
                        getProductSelectPop().showPopupWindow(mBinding.afterServiceAddTop, Gravity.BOTTOM);
                        break;
                    case R.id.itmAfterServicePro_Delete:
                        proAdapter.remove(proAdapter.getItem(position));
                        showProRec();
                        maintainProfit();
                        break;
                    default:
                        break;
                }
            }
        });
        mBinding.afterServiceAddPic.PicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPic();
            }
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                maintainProfit();
            }
        };
        mBinding.afterServiceAddOtherAmount.getEditText().addTextChangedListener(watcher);
        mBinding.afterServiceAddChargeFees.getEditText().addTextChangedListener(watcher);

    }

    private void addPic() {
        int count = imageAdapter.getData().size();
        if (count < max) {
            Util.openCamera(mContext, max - count, true, false);
        } else {
            ToastUtil.showShort("最多上传 " + max + " 张图片");
        }
    }

    private EventSendData<AdminListBean, Boolean> eventConsume = (bean, isReset) -> {
        if (isReset) {
            serviceBody.setSID(0);
        } else {
            if (bean != null) {
                serviceBody.setSID(bean.getAdmin_ID());
            }

        }

    };

    private WheelSelectPop<AdminListBean> getMaintainEmployeePop() {
        if (maintainEmployeePop == null) {
            maintainEmployeePop = new WheelSelectPop(mContext);
            maintainEmployeePop.setTitle("选择维修人员");
            maintainEmployeePop.getAdminData(mContext);
            maintainEmployeePop.setEventConsume(eventConsume);
            maintainEmployeePop.setShowTextView(mBinding.afterServiceAddMaintain.getTextView());
        }
        return maintainEmployeePop;
    }

    void startCalendar() {
        timeType = 1;
        initTimePicker("开始时间");
        pvTime.show();
    }


    void endCalendar() {
        timeType = 2;
        initTimePicker("结束时间");
        pvTime.show();
    }

    private void initTimePicker(String title) {//Dialog 模式下，在底部弹出
        if (timeType == 1) {
            if (startData != null) {
                selectedDate.clear();
                selectedDate.setTime(startData);
            }
        } else {
            selectedDate.clear();
            selectedDate.setTime(endData);
        }
        if (pvTime == null) {
            pvTime = Util.getTimePickerView(mContext, startCalendar, endCalendar, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    if (date != null) {
                        int i;
                        switch (timeType) {
                            case 1:
                                //大于、等于、小于分别返回1、0、-1
                                i = DateUtils.compareData(date, endData);
                                if (i == 1) {
                                    ToastUtil.showShort( "开始时间不能大于结束时间");
                                } else {
                                    beginTIme = DateUtils.formarDateToString(date, dateFormat);
                                    mBinding.afterServiceAddMaintainBegin.setText(beginTIme);
                                    startData = date;
                                    serviceBody.setBegin(beginTIme);
                                }
                                break;
                            case 2:
                                //大于、等于、小于分别返回1、0、-1
                                i = DateUtils.compareData(date, startData);
                                if (i == -1) {
                                       ToastUtil.showShort( "结束时间不能小于开始时间");
                                } else {
                                    endTime = DateUtils.formarDateToString(date, dateFormat);
                                    mBinding.afterServiceAddMaintainEnd.setText(endTime);
                                    endData = date;
                                    serviceBody.setEnd(endTime);
                                }
                                break;
                            case 3:
                                break;
                        }
                    }
                }
            });
        }
        pvTime.setDate(selectedDate);
        pvTime.setTitleText(title);
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
                if (data != null) {
                    List<AfterServiceProductBean> addBeanList = (List<AfterServiceProductBean>) data.getSerializableExtra("listData");
                    setProData(addBeanList);
                }

                break;
            case TypeValues.DATA:
                if (data != null) {
                    if (StringUtil.isNotEmpty(data.getStringExtra("content"))) {
                        content = data.getStringExtra("content");
                        serviceBody.setContent(content);
                        mBinding.afterServiceAddDetail.setText(StringUtil.removeHtmlTag(content));
                    } else {
                        mBinding.afterServiceAddDetail.setText("");
                    }
                }
                break;
            default:
                break;
        }

    }

    private void setProData(List<AfterServiceProductBean> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (Util.isNotListEmpty(proAdapter.getData())) {
            for (int i = 0; i < data.size(); i++) {
                AfterServiceProductBean bean = data.get(i);
                boolean isAdd = true;
                for (int k = 0; k < proAdapter.getData().size(); k++) {
                    AfterServiceProductBean bean1 = proAdapter.getData().get(k);
                    if (bean.getID() == bean1.getID()) {
                        proAdapter.setData(k, bean);
                        isAdd = false;
                        break;
                    }
                }
                if (isAdd) {
                    proAdapter.addData(bean);
                }
            }
        } else {
            proAdapter.addData(data);
        }
        maintainProfit();
        showProRec();
    }


    private void showProRec() {
        if (Util.isNotListEmpty(proAdapter.getData())) {
            mBinding.setShowRec(true);
        } else {
            mBinding.setShowRec(false);
        }

    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/23
     * @Description 维修利润
     */
    private void maintainProfit() {
        Double d = getMaintainMoney();//产品维修开销
        Double d1 = StringUtil.toDouble(mBinding.afterServiceAddOtherAmount.getText());//其余开销
        Double d2 = StringUtil.toDouble(mBinding.afterServiceAddChargeFees.getText());//收取费用
        Double amount = d + d1;
        Double amount2 = d2 - amount;
        mBinding.afterServiceAddMaintainMoney.setText(d.toString());
        mBinding.afterServiceAddMaintainAmount.setText(amount.toString());
        mBinding.afterServiceAddMaintainProfit.setText(amount2.toString());
    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/12/23
     * @Description 产品维修开销
     */
    private double getMaintainMoney() {
        double d = 0;
        if (Util.isNotListEmpty(proAdapter.getData())) {
            for (AfterServiceProductBean bean : proAdapter.getData()) {
                d += StringUtil.toDouble(bean.getMoney());
            }
        }
        return d;

    }

    private String pic = "";

    private void upPic() {
        if (serviceBody != null) {
            pic = "";

            if (Util.isListEmpty(proAdapter.getData())) {
                   ToastUtil.showShort( "请添加售后产品");
                return;
            }

            if (serviceBody.getSID() == -1) {
                   ToastUtil.showShort( mContext.getString(R.string.maintain_employee_hit));
                return;
            }

            serviceBody.setProduct(proAdapter.getData());
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

                if (fileList.size() > 0) {
                    mBinding.afterServiceAddBT.setEnabled(false);
                    RetrofitHttp.getRequest(HttpApi.class)
                            .uploadImage(Util.filesToMultipartBody(fileList), 1)
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
                                            serviceBody.setPic(pic);
                                            postData();
                                        }
                                    }

                                }

                                @Override
                                public void onFinsh() {
                                    super.onFinsh();
                                    mBinding.afterServiceAddBT.setEnabled(true);
                                }
                            });

                } else {
                    serviceBody.setPic(pic);
                    postData();
                }

            } else {
                postData();
            }

        }

    }

    private void postData() {
        if (serviceBody != null) {
            mBinding.afterServiceAddBT.setEnabled(false);
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
                    mContext.finish();
                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                    super.onError(e);
                }

                @Override
                public void onFinsh() {
                    super.onFinsh();
                    mBinding.afterServiceAddBT.setEnabled(true);
                }

            };
            RetrofitHttp.getRequest(HttpApi.class)
                    .serviceAdd(serviceBody)
                    .compose(RxThreadUtil.observableToMain())
                    .compose(mContext.bindToLifecycle())
                    .subscribe(observer);

        }

    }

    private EventConsume<AfterServiceProductBean> proAddBeanEventConsume = (bean) -> {
        proAdapter.setData(selectIndex, bean);
        maintainProfit();
    };


    private AfterServiceProductSelectPop getProductSelectPop() {
        if (ProductSelectPop == null) {
            ProductSelectPop = new AfterServiceProductSelectPop(mContext);
            ProductSelectPop.setTopView(mBinding.afterServiceAddTop);
            ProductSelectPop.setProAddBeanEventConsume(proAddBeanEventConsume);
        }
        return ProductSelectPop;
    }
}
