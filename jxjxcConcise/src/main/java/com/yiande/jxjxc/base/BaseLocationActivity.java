/**
 *
 */
package com.yiande.jxjxc.base;

import android.Manifest;

import androidx.databinding.ViewDataBinding;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 继承了Activity，实现Android6.0的运行时权限检测
 * 需要进行运行时权限检测的Activity可以继承这个类
 * @创建时间：2016年5月27日 下午3:01:31 
 * @项目名称： AMapLocationDemo
 * @author hongming.wang
 * @文件名称：PermissionsChecker.java
 * @类型名称：PermissionsChecker
 * @since 2.5.0
 */
public abstract class BaseLocationActivity<T extends BasePresenter<B>, B extends ViewDataBinding> extends BaseActivity<T,B> {



    /**
     * 需要进行检测的权限数组
     */
    public String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    protected boolean isLocation = true;
    int max = 0;
    /**
     * 定位监听
     */
    protected AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if (location.getErrorCode() == 0) {
                    stopLocation();
                    destroyLocation();
                    isLocation = false;
                }
            } else {
                max += 1;
                if (max > 99) {
                    stopLocation();
                    destroyLocation();
                }
            }
            locationData(location);
        }
    };



    @Override
    protected void onResume() {
        super.onResume();
        if (EasyPermissions.hasPermissions(this, needPermissions)) {
            initLocation();
        }
    }


    protected void showPermissionDialog() {

    }


    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    protected void initLocation() {
        //初始化client
        if (locationClient == null) {
            locationClient = new AMapLocationClient(this.getApplicationContext());
        }
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        startLocation();
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }


    public void locationData(AMapLocation location) { }

    /**
     * 开始定位
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
