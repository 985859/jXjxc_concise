package com.yiande.jxjxc.presenter;

import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mylibrary.api.dialog.MyDialog;
import com.mylibrary.api.utils.SharedUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.retrofithttp.api.LoadingDialog;
import com.retrofithttp.api.util.RxThreadUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.App;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.IPSetlcetAdapter;
import com.yiande.jxjxc.base.BaseActivity;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.bean.IPBean;
import com.yiande.jxjxc.databinding.ActivitySetIpBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.utils.DialogUtils;
import com.yiande.jxjxc.utils.ScanDeviceUtile;
import com.yiande.jxjxc.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/11/19 8:34
 */
public class SetIPPresenter extends BasePresenter<ActivitySetIpBinding> {
    private int type = 0;// 0 单机版  1 联网版
    private final int max = 50;//一次最大请求的个数
    private int maxIndex;// 总个数
    private int index;// 当前个数
    int maxNumber = 0;//最大请求次数
    int number = 0;//请求次数

    private IPSetlcetAdapter<IPBean> spinnerAdapter;
    private String urlPrefix = "http://";
    private ScanDeviceUtile deviceUtile;
    private List<String> deviceIP;
    private List<String> iPPortList = new ArrayList<>();
    private LoadingDialog dialog;
    List<Disposable> callList = new ArrayList<>();

    public SetIPPresenter(RxAppCompatActivity mContext, ActivitySetIpBinding binding) {
        super(mContext, binding);
        initIP();
        mBinding.setOnClick(onClik);
        deviceUtile = new ScanDeviceUtile();
        mBinding.setType(type);
        mBinding.setIPType.setType(type);
        iPPortList.add("8080");
        iPPortList.add("8098");


    }

    @Override
    protected void initData() {
        super.initData();
        List<IPBean> ipBeanList = new ArrayList<>();
        ipBeanList.add(new IPBean(0, "http://"));
        ipBeanList.add(new IPBean(1, "https://"));
        spinnerAdapter = new IPSetlcetAdapter(null);
        spinnerAdapter.setList(ipBeanList);
        mBinding.setIPSpinner.setAdapter(spinnerAdapter);
        mBinding.setIPSpinner.setPopWH(SystemUtil.dp2px(mContext, 70), SystemUtil.dp2px(mContext, 70));

        mBinding.setIPSpinner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                spinnerAdapter.setSelectIndex(position);
                urlPrefix = spinnerAdapter.getItem(position).getText();
            }
        });
        mBinding.setIPSpinner.setCheckIndex(0);


    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.setIPType.setOnselectListener(new EventConsume<Integer>() {
            @Override
            public void accept(Integer integer) {
                type = integer;
                mBinding.setType(type);
                if (type == 0) {
                    setIPFocus();
                } else {
                    mBinding.setIPEUrl.requestFocus();
                }
            }
        });
    }

    private EventConsume<Integer> onClik = (state) -> {
        switch (state) {
            case 0:// 单机版
                type = 0;
                mBinding.setType(type);
                break;
            case 1://联网版
                type = 1;
                mBinding.setType(type);
                break;
            case 2: //保存IP 地址
                saveIP();
                break;
            case 3: //搜索IP 地址
                if (SystemUtil.isWifiActive(mContext)) {
                    dialog = new LoadingDialog(mContext);
                    dialog.setMessage("IP查找中");
                    dialog.show();
                    dialog.setCancelable(false);
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            deviceIP = deviceUtile.scan();
                            if (Util.isNotListEmpty(deviceIP)) {
                                List<String> ipList = new ArrayList<>();
                                for (int i = 0; i < iPPortList.size(); i++) {
                                    for (String s : deviceIP) {
                                        ipList.add(s + ":" + iPPortList.get(i));
                                    }
                                }
                                deviceIP.addAll(ipList);
                                maxIndex = deviceIP.size();
                                number = 0;
                                if (max <= maxIndex) {
                                    int i = maxIndex % max;
                                    if (i > 0) {
                                        maxNumber = maxIndex / max + 1;
                                    } else {
                                        maxNumber = maxIndex / max;
                                    }
                                } else {
                                    maxNumber = 1;
                                }
                                secletIP();
                            } else {
                                handler.sendEmptyMessage(0);
                                dialog.dismiss();
                            }
                        }
                    }.start();

                } else {
                    DialogUtils.showDialog(mContext, "请打开 WIFI 网络");

                }
                break;
            default:
                break;
        }
    };


    private void secletIP() {
        index = 0;
        number += 1;
        int position = number * max;
        List<String> stringList = new ArrayList<>();
        if (position <= maxIndex) {
            int sIndex = (number - 1) * max;
            if (sIndex >= 0) {
                stringList.addAll(deviceIP.subList(sIndex, position));
            }
        } else {
            int sIndex = (number - 1) * max;
            if (sIndex >= 0) {
                stringList.addAll(deviceIP.subList(sIndex, maxIndex));
            }
        }
        if (Util.isNotListEmpty(stringList)) {
            for (String s : stringList) {
                init(s);
            }
        }
    }

    Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    DialogUtils.showDialog(mContext, "未找到IP地址，请手动输入");
                    break;

                case 1:
                    String ip = msg.obj == null ? "" : (String) msg.obj;
                    setIP(ip, true);
                    DialogUtils.showDialog(mContext, "服务器IP地址：" + ip + "\n是否保存", new MyDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                saveIP();
                            }
                        }
                    });
                    break;
                case 2:
                    index += 1;
                    if (number < maxNumber && index == max) {
                        secletIP();
                    }

                    if (((number - 1) * max + index) == maxIndex) {
                        dialog.dismiss();
                        DialogUtils.showDialog(mContext, "未找到IP地址，请手动输入");
                    }
                    break;
                default:
                    break;
            }

            return false;
        }
    });


    /**
     * 初始化必要对象和参数
     */
    public void init(String ip) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置连接超时时间
        builder.connectTimeout(5, TimeUnit.SECONDS)
                // 初始化okhttp
                .build();

        OkHttpClient client = builder.build();
        //初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://" + ip + "/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPService ipService = retrofit.create(IPService.class);
        Observable<ResponseBody> observable = ipService.getPosts();
        observable.compose(RxThreadUtil.observableToMain())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        callList.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ResponseBody responseBody) {


                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        handler.sendEmptyMessage(2);
                    }

                    @Override
                    public void onComplete() {
                        for (Disposable disposable : callList) {
                            if (!disposable.isDisposed()) {
                                disposable.dispose();
                            }

                        }
                        dialog.dismiss();
                        Message message = new Message();
                        message.obj = ip;
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                });


    }

    public interface IPService {
        @GET("api/Admin/Get")
        Observable<ResponseBody> getPosts();
    }


    private void initIP() {
        if (StringUtil.isNotEmpty(SharedUtil.getSP(App.IP))) {
            if (SharedUtil.getIP(App.IPTYPE) == 1) {
                mBinding.setIPEUrl.setText(SharedUtil.getSP(App.IP));
                type = 1;
            } else {
                setIP(SharedUtil.getSP(App.IP), true);
            }

        } else {
            if (SystemUtil.isWifiActive(mContext)) {
                String ip = SystemUtil.getIPAddress(mContext);
                setIP(ip, false);
            }
        }
    }


    private void setIP(String ip, boolean isselect) {
        if (StringUtil.isNotEmpty(ip)) {
            String[] protStrings = ip.split(":");
            if (protStrings.length > 0) {
                String[] ipAddress = protStrings[0].split("\\.");
                if (ipAddress != null && ipAddress.length > 0) {
                    mBinding.setIPE1.setText(ipAddress[0]);
                    if (ipAddress.length > 1) {
                        mBinding.setIPE2.setText(ipAddress[1]);
                    }
                    if (ipAddress.length > 2) {
                        mBinding.setIPE3.setText(ipAddress[2]);
                    }
                    if (isselect) {
                        if (ipAddress.length > 3) {
                            mBinding.setIPE4.setText(ipAddress[3]);
                        }
                    } else {
                        mBinding.setIPE4.setText("");
                    }
                }
            }
            if (protStrings.length > 1) {
                mBinding.setIPPort.setText(protStrings[1]);
            }
            setIPFocus();
        }
    }

    private void setIPFocus() {
        if (StringUtil.isEmpty(mBinding.setIPE1.getText().toString())) {
            mBinding.setIPE1.requestFocus();
        } else {
            if (StringUtil.isEmpty(mBinding.setIPE2.getText().toString())) {
                mBinding.setIPE2.requestFocus();
            } else {
                if (StringUtil.isEmpty(mBinding.setIPE3.getText().toString())) {
                    mBinding.setIPE3.requestFocus();
                } else {
                    if (StringUtil.isEmpty(mBinding.setIPE4.getText().toString())) {
                        mBinding.setIPE4.requestFocus();
                    } else {
                        mBinding.setIPPort.requestFocus();
                    }
                }
            }
        }
    }

    private void saveIP() {
        switch (type) {
            case 0:
                if (StringUtil.isEmpty(mBinding.setIPE1.getText().toString()) || StringUtil.isEmpty(mBinding.setIPE2.getText().toString())
                        || StringUtil.isEmpty(mBinding.setIPE3.getText().toString()) || StringUtil.isEmpty(mBinding.setIPE4.getText().toString())) {
                    ToastUtil.showShort( "请输入正确的IP地址");
                    return;
                }
                if (StringUtil.toInt(mBinding.setIPE1.getText().toString()) > 255 || StringUtil.toInt(mBinding.setIPE2.getText().toString()) > 255
                        || StringUtil.toInt(mBinding.setIPE3.getText().toString()) > 255 || StringUtil.toInt(mBinding.setIPE4.getText().toString()) > 255) {
                    ToastUtil.showShort( "请输入正确的IP地址 范围 0 -- 255");
                    return;
                }

                if (StringUtil.toInt(mBinding.setIPPort.getText().toString()) > 65535 || StringUtil.toInt(mBinding.setIPPort.getText().toString()) < 0) {
                    ToastUtil.showShort("请输入正确的端口号 范围 0 -- 65535");
                    return;
                }

                SharedUtil.putSP(App.BASEURL, getUrl(0));
                SharedUtil.putSP(App.IP, getIP());
                SharedUtil.putIP(App.IPTYPE, 0);
                App.initRetrofitHttp();
                break;
            case 1:
                if (StringUtil.isEmpty(mBinding.setIPEUrl.getText().toString())) {
                    ToastUtil.showShort( mContext.getString(R.string.hint_url));
                    return;
                }
                SharedUtil.putSP(App.BASEURL, getUrl(1));
                SharedUtil.putSP(App.IP, mBinding.setIPEUrl.getText().toString());
                SharedUtil.putIP(App.IPTYPE, 1);
                App.initRetrofitHttp();
                break;
            default:
                break;
        }
        Util.esc((BaseActivity) mContext);
    }

    private String getIP() {
        String ip = "";
        ip = mBinding.setIPE1.getText().toString() + "." + mBinding.setIPE2.getText().toString() + "." + mBinding.setIPE3.getText().toString() + "." + mBinding.setIPE4.getText().toString();
        if (StringUtil.isNotEmpty(mBinding.setIPPort.getText().toString().trim())) {
            ip += ":" + mBinding.setIPPort.getText().toString().trim();
        }
        return ip;
    }

    private String getUrl(int type) {
        String url = "";
        switch (type) {
            case 0:
                url = "http://" + getIP();
                break;
            case 1:
                url = urlPrefix + mBinding.setIPEUrl.getText().toString();
                break;
            default:
                break;
        }
        return url;
    }


}
