package com.yiande.jxjxc.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.mylibrary.api.utils.SharedUtil;
import com.mylibrary.api.utils.SkipUtil;
import com.mylibrary.api.utils.SpannableUtil;
import com.mylibrary.api.utils.StringUtil;
import com.nanchen.compresshelper.CompressHelper;
import com.retrofithttp.api.RetrofitHttp;
import com.retrofithttp.api.util.RxThreadUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.App;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.activity.AZListActivity;
import com.yiande.jxjxc.activity.AdminListActivity;
import com.yiande.jxjxc.activity.AfterServiceListActivity;
import com.yiande.jxjxc.activity.ClassifyActivity;
import com.yiande.jxjxc.activity.DebtManageActivity;
import com.yiande.jxjxc.activity.LoginActivity;
import com.yiande.jxjxc.activity.ProductActivity;
import com.yiande.jxjxc.activity.ProductListActivity;
import com.yiande.jxjxc.activity.StatisticsActivity;
import com.yiande.jxjxc.activity.StockOrderListActivity;
import com.yiande.jxjxc.activity.UnitBrandActivity;
import com.yiande.jxjxc.activity.UserListActivity;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.base.HttpObserver;
import com.yiande.jxjxc.bean.JsonBean;
import com.yiande.jxjxc.bean.KeyBean;
import com.yiande.jxjxc.bean.LevelBean;
import com.yiande.jxjxc.bean.ProductBean;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.HttpApi;
import com.yiande.jxjxc.myInterface.TypeEnum;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/7/24 8:54
 */
public class Util {

    public static boolean isListEmpty(List list) {
        boolean empty = false;
        if (list == null || list.size() == 0)
            empty = true;
        return empty;
    }

    public static boolean isNotListEmpty(List list) {
        boolean empty = true;
        if (list == null || list.size() == 0)
            empty = false;
        return empty;
    }


    public static SpannableStringBuilder setMustTilte(String title) {
        if (StringUtil.isEmpty(title)) {
            title = "";
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(title);
        builder.append(" *");
        builder.setSpan(new ForegroundColorSpan(Color.parseColor("#fe2552")), builder.length() - 1, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;

    }


    /**
     * 功    能：选择视频
     * 参    数：
     * 返回值：无
     **/
    public static void openViedio(Activity activity) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .isCamera(true)
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(false)// 是否显示拍照按钮 true or false
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .isCompress(true)// 是否压缩 true or false
                .withAspectRatio(16, 9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(120)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(3)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(10)//视频秒数录制 默认60s int
                .forResult(TypeEnum.VIEDEO);//结果回调onActivityResult code
    }

    /**
     * 功    能：直接相机 录制
     * 参    数： 0 拍照 1 录制
     * 返回值：无
     **/
    public static void recordVideo(Activity activity) {

        PictureSelector.create((Activity) activity)
                .openCamera(PictureMimeType.ofVideo())
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(3)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(10)//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);

    }

    /**
     * 功    能：直接相机拍照
     * 参    数： 0 拍照 1 录制
     * 返回值：无
     **/
    public static void openCamera(Activity activity) {
        openCamera(activity, PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 功    能：直接相机拍照
     * 参    数： 0 拍照 1 录制
     * 返回值：无
     **/
    public static void openCamera(Activity activity, int requestCode) {
        PictureSelector.create((Activity) activity)
                .openCamera(PictureMimeType.ofImage())
                .isCompress(true)// 是否压缩 true or false
                .minimumCompressSize(300)// 小于300kb的图片不压缩
                .forResult(requestCode);
    }

    /**
     * 功    能：从相机或图库中获取照片
     * 参    数：pickMulti  图片的选择数量 showCamera  是否显示相机      cutOut 是否允许裁剪
     * 返回值：无
     **/
    public static void openCamera(final Activity context, int pickMulti, boolean showCamera, boolean cutOut) {
        openCamera(context, pickMulti, showCamera, cutOut, PictureConfig.CHOOSE_REQUEST);
    }

    public static void openCamera(final Activity context, int pickMulti, boolean showCamera, boolean cutOut, int requestCode) {
        int selectionMode;
        if (pickMulti > 1) {
            selectionMode = PictureConfig.MULTIPLE;
        } else {
            selectionMode = PictureConfig.SINGLE;
        }
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(context)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .maxSelectNum(pickMulti)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .isPreviewImage(false)
                .selectionMode(selectionMode)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .isCamera(showCamera)// 是否显示拍照按钮 true or false
                .isEnableCrop(cutOut)// 是否裁剪 true or false
                .cropImageWideHigh(180, 180)
                .isCompress(true)// 是否压缩 true or false
                .isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cutOutQuality(80)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(300)// 小于300kb的图片不压缩
                .forResult(requestCode);//结果回调onActivityResult code
    }


    public static String getPath(LocalMedia media) {
        String path = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            path = media.getAndroidQToPath();
        } else if (media.isCut() && media.isCompressed()) {
            path = media.getCompressPath();
        } else if (media.isCut()) {
            path = media.getCutPath();
        } else if (media.isCompressed()) {
            path = media.getCompressPath();
        } else {
            path = media.getPath();
        }
        return path;
    }

    public static File compressFile(Context context, String path) {
        if (StringUtil.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        return compressFile(context, file);
    }

    public static File compressFile(Context context, File oldFile) {
        if (oldFile == null) {
            return null;
        }
        long length = oldFile.length();
        //小于500kb 不压缩
        if (length < 500 * 1024) {
            return oldFile;
        }
        File newFile = new CompressHelper.Builder(context)
                .setMaxWidth(960)  // 默认最大宽度为720
                .setMaxHeight(960) // 默认最大高度为960
                .setQuality(80)    // 默认压缩质量为80
                .setFileName(oldFile.getName()) // 设置你需要修改的文件名
                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                .build()
                .compressToFile(oldFile);
        return newFile;
    }

    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/8/7
     * @Description 真正的路径，但你不能从android访问 它可能是空的
     */
    public static String getRealPath(LocalMedia media) {
        String path = "";
        if (StringUtil.isNotEmpty(media.getRealPath())) {
            path = media.getRealPath();
        } else {
            path = media.getPath();
        }
        return path;
    }


    public static MultipartBody.Part getMultipartPartFile(String picPath) {
        File mFile = new File(picPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", mFile.getName(), requestFile);
        return body;
    }

    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (isNotListEmpty(files)) {
            for (int i = 0; i < 10; i++) {
                File file = files.get(i);
                // 这里为了简单起见，没有判断file的类型
                RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data;charset=UTF-8"));
                builder.addFormDataPart("params" + i, file.getName(), requestBody);
            }
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    public static String readPhotoInfo(String path) {
        String info = "";
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            String shijain = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            String moshi = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
            String zhizaoshang = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
            String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);

            String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String timestamp = exifInterface.getAttribute(ExifInterface.TAG_GPS_TIMESTAMP);
            String processing_method = exifInterface.getAttribute(ExifInterface
                    .TAG_GPS_PROCESSING_METHOD);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append("时间 = " + shijain + "\n")
                    .append("型号 = " + moshi + "\n")
                    .append("制造商 = " + zhizaoshang + "\n")
                    .append("GPS时间戳 = " + timestamp + "\n")
                    .append("GPS定位类型 = " + processing_method + "\n")
                    .append("GPS经度 = " + latitude + "\n")
                    .append("GPS经度 = " + longitude + "\n");
            info = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static <T, BH extends BaseViewHolder> void setRceclerData(JsonBean<List<T>> data, int page, SmartRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter<T, BH> adapter) {
        setRceclerData(data, page, "", refreshLayout, recyclerView, adapter, 10);
    }

    public static <T, BH extends BaseViewHolder> void setRceclerData(JsonBean<List<T>> data, int page, String empteyText, SmartRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter<T, BH> adapter) {
        setRceclerData(data, page, empteyText, refreshLayout, recyclerView, adapter, 10);
    }

    public static <T, BH extends BaseViewHolder> void setRceclerData(JsonBean<List<T>> data, int page, String empteyText, SmartRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter<T, BH> adapter, int count) {
        if (data == null || recyclerView == null || refreshLayout == null || adapter == null) return;
        adapter.removeAllFooterView();
        if (page == 1) {
            refreshLayout.finishRefresh();
            refreshLayout.setEnableLoadMore(true);
        } else {
            refreshLayout.finishLoadMore();
        }
        if (data.code == 1) {
            if (page == 1) {
                if (data.count == 0) { //没有数据
                    adapter.getData().clear();
                    adapter.notifyDataSetChanged();
                    adapter.setEmptyView(getEmptyView(recyclerView.getContext(), empteyText, -1));
                } else if (data.count <= count) {//没有更多数据
                    adapter.setList(data.data);
                    recyclerView.smoothScrollToPosition(0);
                    refreshLayout.setEnableLoadMore(false);
                    adapter.addFooterView(getNoDataView(recyclerView.getContext()));
                } else if (data.count > count) {//有下一页数据
                    adapter.setList(data.data);
                    recyclerView.smoothScrollToPosition(0);
                }

            } else if (page > 1) {
                if (data.count <= count) {//没有数据
                    if (isNotListEmpty(data.data))
                        adapter.addData(data.data);
                    refreshLayout.setEnableLoadMore(false);
                    adapter.addFooterView(getNoDataView(recyclerView.getContext()));
                } else {//有下一页数据
                    if (isNotListEmpty(data.data))
                        adapter.addData(data.data);
                }

            }
        }

    }

    public static View getNoDataView(Context context) {
        View noDataView = LayoutInflater.from(context).inflate(R.layout.view_no_data, null, false);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int margin = (int) context.getResources().getDimension(R.dimen.padding1);
        params.setMargins(margin, margin, margin, margin);
        noDataView.setLayoutParams(params);
        return noDataView;
    }

    public static View getEmptyView(Context context) {
        return getEmptyView(context, "", -1, -1);
    }

    public static View getEmptyView(Context context, String text) {

        return getEmptyView(context, text, -1, -1);
    }

    public static View getEmptyView(Context context, String text, int id) {

        return getEmptyView(context, text, id, -1);
    }

    public static View getEmptyView(Context context, String empteyText, int id, int textColor) {
        if (StringUtil.isEmpty(empteyText)) {
            empteyText = "暂无内容";
        }
        View noDataView = LayoutInflater.from(context).inflate(R.layout.view_empty, null, false);
        ImageView IMG = noDataView.findViewById(R.id.itm_emptyIMG);
        TextView tv = noDataView.findViewById(R.id.itm_emptyTV);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int margin = (int) context.getResources().getDimension(R.dimen.padding1);
        params.setMargins(margin, margin, margin, margin);
        noDataView.setLayoutParams(params);
        if (id != -1) {
            IMG.setBackgroundResource(id);
        }
        tv.setText(empteyText);
        if (textColor != -1) {
            tv.setTextColor(textColor);
        }
        return noDataView;
    }


    public static int getRolePicID(int roleID) {
        int picID = 0;
        switch (roleID) {
            case 1:
                picID = R.drawable.menu_1_1;
                break;
            case 2:
                picID = R.drawable.menu_1_2;
                break;
            case 3:
                picID = R.drawable.menu_1_3;
                break;
            case 4:
                picID = R.drawable.menu_1_4;
                break;
            case 101:
                picID = R.drawable.menu_1_1;
                break;
            case 102:
                picID = R.drawable.menu_1_2;
                break;
            case 103:
                picID = R.drawable.menu_1_3;
                break;
            case 104:
                picID = R.drawable.menu_1_4;
                break;
            case 105:
                picID = R.drawable.menu_1_5;
                break;
            case 201:
                picID = R.drawable.menu_2_1;
                break;
            case 202:
                picID = R.drawable.menu_2_2;
                break;
            case 203:
                picID = R.drawable.menu_2_3;
                break;
            case 204:
                picID = R.drawable.menu_2_4;
                break;
            case 301:
                picID = R.drawable.menu_3_1;
                break;
            case 302:
                picID = R.drawable.menu_3_2;
                break;
            case 303:
                picID = R.drawable.menu_3_3;
                break;
            case 304:
                picID = R.drawable.menu_3_4;
                break;
            case 305:
                picID = R.drawable.menu_3_5;
                break;
            case 401:
                picID = R.drawable.menu_4_1;
                break;
            case 402:
                picID = R.drawable.menu_4_2;
                break;
            case 403:
                picID = R.drawable.menu_4_3;
                break;
            case 404:
                picID = R.drawable.menu_4_4;

                break;
            case 405:
                picID = R.drawable.menu_4_5;
                break;
            case 406:
                picID = R.drawable.menu_4_6;
                break;
            case 501:
                picID = R.drawable.menu_5_4;
                break;

            case 601://欠款管理
                picID = R.drawable.menu_3_6;
                break;
            case 602://赊账管理
                picID = R.drawable.menu_2_5;
                break;
            case 701://员工管理
                picID = R.drawable.menu_7_1;
                break;
            case 702://管理员管理
                picID = R.drawable.menu_7_2;
                break;
            default:
                picID = R.drawable.more;
                break;
        }
        return picID;
    }


    public static void skipRoleBean(Context context, int roleID) {
        /**101 销售分析
         *102 利润分析
         *103 余额分析
         *104 欠款分析
         *
         *201 添加入库
         *202 入库管理
         *203 入库统计
         *204 供应商管理
         *
         *301 添加出库
         *302 出库管理
         *303 出库统计
         *304 客户管理
         *
         *401 添加新产品
         *402 产品库管理
         *403 库存预警
         *404 产品分类
         *405 产品品牌
         *406 产品单位
         *
         *501 售后管理
         *601 欠款管理
         *602 赊账管理
         *
         *701 员工管理
         *702 管理员管理
         *
         * **/

        http:

        switch (roleID) {
            case 1:
                UserListActivity.start(context, 1, "");
                break;
            case 2:
                UserListActivity.start(context, 2, "");
                break;
            case 3:
                AdminListActivity.start(context, null);
                break;
            case 4:
                ClassifyActivity.start(context,null,0);
                break;

            case 101:
                StatisticsActivity.start(context, 101, 1);
                break;
            case 102:
                StatisticsActivity.start(context, 102, 1);
                break;
            case 103:
                StatisticsActivity.start(context, 103, 1);
                break;
            case 104:
                StatisticsActivity.start(context, 104, 1);
                break;
            case 201:
                AZListActivity.start(context, 2, 2);
                break;
            case 202:
                StockOrderListActivity.start(context, 1, "");
                break;
            case 203:
                StatisticsActivity.start(context, 203, 1);
                break;
            case 204:
                UserListActivity.start(context, 2, "");
                break;
            case 301:
                AZListActivity.start(context, 1, 1);
                break;
            case 302:
                StockOrderListActivity.start(context, 2, "");
                break;
            case 303:
                StatisticsActivity.start(context, 303, 1);
                break;
            case 304:
                UserListActivity.start(context, 1, "");
                break;
            case 305:
                UserListActivity.start(context, 3, "");
                break;
            case 401:
                ProductActivity.start(context, null, 0, true);
                break;
            case 402:
                ProductListActivity.start(context, 0, 0, 0);
                break;
            case 403:
                ProductListActivity.start(context, 3, 0, 0);
                break;
            case 404:
                ClassifyActivity.start(context, null, 0);
                break;
            case 405:
                UnitBrandActivity.start(context, 2);
                break;
            case 406:
                UnitBrandActivity.start(context, 3);
                break;
            case 501:
                AfterServiceListActivity.start(context, "");
                break;

            case 601://欠款管理
                DebtManageActivity.start(context, 1);
                break;
            case 602://赊账管理
                DebtManageActivity.start(context, 2);
                break;
            case 701://员工管理

                break;
            case 702://管理员管理
                SkipUtil.skipActivity(context, AdminListActivity.class);
                break;
            default:
                break;
        }

    }

    public static CharSequence setPriceRed(String buyPrice) {
        if (StringUtil.isEmpty(buyPrice)) {
            buyPrice = "";
        }
        buyPrice = String.format("¥ %s", buyPrice);

        return SpannableUtil.setTextColor(buyPrice, 0, 1, Color.parseColor("#fe2552"));
    }

    public static CharSequence setPrice(String buyPrice) {
        if (StringUtil.isEmpty(buyPrice)) {
            buyPrice = "";
        }
        return String.format("¥ %s", buyPrice);
    }

    public static CharSequence setKg(String kg) {
        if (StringUtil.isEmpty(kg)) {
            kg = "";
        }
        kg = String.format("%s Kg", kg);
        return SpannableUtil.setTextColor(kg, kg.length() - 2, kg.length(), Color.parseColor("#fe2552"));
    }

    public static void getAppFucntion(RxAppCompatActivity context, EventConsume<Integer> consume) {
        RetrofitHttp.getRequest(HttpApi.class)
                .getAppFunction()
                .compose(RxThreadUtil.observableToMain())
                .compose(context.bindToLifecycle())
                .subscribe(new HttpObserver<JsonBean<List<KeyBean>>>(context, false) {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        super.onSubscribe(d);
                        showToast = false;
                    }

                    @Override
                    public void onSuccess(JsonBean<List<KeyBean>> bean) {
                        if (bean.code == 1) {
                            App.keyBeanList = bean.data;
                        }
                        if (consume != null) {
                            consume.accept(bean.code);
                        }
                    }
                });
    }


    /**
     * @param
     * @return
     * @author hukui
     * @time 2020/11/27
     * @Description 100 客户等级划分
     * 101 客户余额管理
     * 200 欠款管理
     * 300 售后管理
     * 400 返审核
     * 500 产品分类等级
     * 500 产品分类等级
     * 600 出票记录
     * 999 启用重量
     */
    public static int USERLEVEL = 100;
    public static int YUE = 101;
    public static int DEBT = 200;
    public static int SERVICE = 300;
    public static int RESET = 400;
    public static int PRODUCTLEVEL = 500;
    public static int KG = 999;
    public static int DRAWABILL = 600;

    public static KeyBean getKeyBean(int id) {
        if (isListEmpty(App.keyBeanList)) {
            return null;
        }
        for (KeyBean keyBean : App.keyBeanList) {
            if (id == keyBean.getKey()) {
                return keyBean;
            }
        }
        return null;
    }

    public static boolean isShowEntry(int i) {
        return getKeyBean(i) == null ? false : true;
    }

    public static List<LevelBean> getLevel(Context context, int level) {
        List<LevelBean> levelBeans = new ArrayList<>();
        for (int i = 0; i < level + 1; i++) {
            levelBeans.add(new LevelBean(i, getLevelText(context, i)));
        }
        return levelBeans;
    }

    public static CharSequence getLevelText(Context context, int level) {
        if (context == null) {
            return "";
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        ImageSpan span = SpannableUtil.getImagSpan(context, getLevelIocn(level), 16, 16);
        switch (level) {
            case 1:
                builder.append("V1");
                break;
            case 2:
                builder.append("V2");
                break;
            case 3:
                builder.append("V3");
                break;
            case 4:
                builder.append("V4");

                break;
            default:
                builder.append("普通客户");
                break;
        }
        if (level > 0) {
            builder.setSpan(span, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            builder.append("级别");
        }
        return builder;
    }

    public static int getLevelIocn(int level) {
        int id;
        switch (level) {
            case 1:
                id = R.drawable.v1;
                break;
            case 2:
                id = R.drawable.v2;
                break;
            case 3:
                id = R.drawable.v3;
                break;
            case 4:
                id = R.drawable.v4;
                break;
            default:
                id = R.drawable.v0;
                break;
        }
        return id;
    }

    public static String getLevelPrice(int level, ProductBean data) {
        String price = "";
        switch (level) {
            case 1:
                price = data.getProduct_Vip1_Price();
                break;
            case 2:
                price = data.getProduct_Vip2_Price();
                break;
            case 3:
                price = data.getProduct_Vip3_Price();
                break;
            case 4:
                price = data.getProduct_Vip4_Price();
                break;
            default:
                price = data.getProduct_Price();
                break;
        }
        return price;
    }


    public static TimePickerView getTimePickerView(@NonNull Context mContext, @NonNull Calendar startDate, @NonNull Calendar endDate, @NonNull OnTimeSelectListener selectListener) {

        TimePickerView pvTime = new TimePickerBuilder(mContext, selectListener).setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true)
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setSubCalSize(13)//确定和取消文字大小
                .setTitleSize(14)//标题文字大小
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setTitleColor(mContext.getResources().getColor(R.color.white))//标题文字颜色
                .setSubmitColor(mContext.getResources().getColor(R.color.white))//确定按钮文字颜色
                .setCancelColor(mContext.getResources().getColor(R.color.white))//取消按钮文字颜色
                .setTitleBgColor(mContext.getResources().getColor(R.color.blue))//标题背景颜色 Night mode
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        return pvTime;
    }


    public static <T extends BaseActivity> void esc(T t) {
        String url = SharedUtil.getSP(com.yiande.jxjxc.App.BASEURL);
        String ip = SharedUtil.getSP(com.yiande.jxjxc.App.IP);
        int ipType = SharedUtil.getIP(com.yiande.jxjxc.App.IPTYPE);
        SharedUtil.clear();
        SharedUtil.putSP(com.yiande.jxjxc.App.BASEURL, url);
        SharedUtil.putSP(com.yiande.jxjxc.App.IP, ip);
        SharedUtil.putIP(com.yiande.jxjxc.App.IPTYPE, ipType);
        com.yiande.jxjxc.App.initRetrofitHttp();
        SkipUtil.skipActivity(t, LoginActivity.class);
        t.activityManager.finishOutActivity(LoginActivity.class);
    }


    static final int GB_SP_DIFF = 160;
    // 存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList = {1601, 1637, 1833, 2078, 2274, 2302,
            2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
            4086, 4390, 4558, 4684, 4925, 5249, 5600};
    // 存放国标一级汉字不同读音的起始区位码对应读音
    static final char[] firstLetter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'W', 'X',
            'Y', 'Z'};

    public static String getSpells(String characters) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < characters.length(); i++) {
            char ch = characters.charAt(i);
            if ((ch >> 7) == 0) {
                // 判断是否为汉字，如果左移7为为0就不是汉字，否则是汉字

            } else {
                char spell = getFirstLetter(ch);
                buffer.append(String.valueOf(spell));
            }
        }
        return buffer.toString();
    }

    // 获取一个汉字的首字母
    public static Character getFirstLetter(char ch) {

        byte[] uniCode = null;
        try {
            uniCode = String.valueOf(ch).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
            return null;
        } else {
            return convert(uniCode);
        }
    }

    /**
     * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */
    static char convert(byte[] bytes) {
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i]
                    && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }


}
