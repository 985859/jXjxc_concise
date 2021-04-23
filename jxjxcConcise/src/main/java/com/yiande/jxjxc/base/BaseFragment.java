package com.yiande.jxjxc.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionProxy;
import com.trello.rxlifecycle4.components.support.RxFragment;

import io.reactivex.rxjava3.annotations.NonNull;


/**
 * 可以使用沉浸式的Fragment基类
 *
 * @author geyifeng
 * @date 2017/4/7
 */
public abstract class BaseFragment<T extends BaseFragmentPresenter<B>, B extends ViewDataBinding> extends RxFragment implements SimpleImmersionOwner {
    protected RxFragment mContext;
    protected Toolbar toolbar;
    protected View statusBarView;
    private boolean isFirstLoad = true; // 是否第一次加载
    protected ImmersionBar mImmersionBar;
    protected B mBinding;
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBeforeView(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mPresenter = getPresenter();
        toolbar = getToolbar();
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        return mBinding.getRoot();


    }

    /**
     * Gets layout id.
     *
     * @return the layout id
     */
    protected abstract int getLayoutId();

    protected abstract T getPresenter();

    protected abstract Toolbar getToolbar();

    protected View getStatusBarView() {
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fitsLayoutOverlap();
        initView();
        setListener();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //旋转屏幕为什么要重新设置布局与状态栏重叠呢？因为旋转屏幕有可能使状态栏高度不一样，如果你是使用的静态方法修复的，所以要重新调用修复
        fitsLayoutOverlap();
    }

    protected void initDataBeforeView(Bundle savedInstanceState) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init();
    }

    /**
     * 初始化数据 在用户可见是调用
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {
    }

    /**
     * 设置监听
     */
    protected void setListener() {
    }

    private void fitsLayoutOverlap() {
        statusBarView = getStatusBarView();
        if (statusBarView != null) {
            ImmersionBar.setStatusBarView(this, statusBarView);
        } else {
            ImmersionBar.setTitleBar(this, toolbar);
        }
    }

    /**
     * ImmersionBar代理类
     */
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            // 只有在第一次时调用 initData, 若每次 在fragment 可见是调用  直接重写 onResume
            initData();
            isFirstLoad = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }


    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean immersionBarEnabled() {
        return true;
    }

}
