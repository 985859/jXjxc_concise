package com.yiande.jxjxc.adapter;


import android.app.Activity;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.mylibrary.api.R;
import com.mylibrary.api.widget.photoview.PhotoView;

import java.util.ArrayList;

public class ZoomPageAdapter extends PagerAdapter {
    private int screenWidth;
    private int screenHeight;
    private ArrayList<String> images = new ArrayList<>();
    private Activity mActivity;
    public PhotoViewClickListener listener;

    public ZoomPageAdapter(Activity activity, ArrayList<String> images) {
        this.mActivity = activity;
        if (images != null) this.images = images;
        DisplayMetrics dm = getScreenPix(activity);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

    }

    public void setNewData(ArrayList<String> images) {
        this.images.clear();
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<String> images) {
        this.images.addAll(images);
        notifyDataSetChanged();
    }

    public void setPhotoViewClickListener(PhotoViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoView.setLayoutParams(params);
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        if (images.get(position).startsWith("http")) {
            photoView.setImgURL(images.get(position), R.drawable.icon_stub);
        } else {
            Uri uri;
            if (images.get(position).startsWith("file://")) {
                uri = Uri.parse(images.get(position));
            } else {
                uri = Uri.parse("file://" + images.get(position));
            }
            Glide.with(mActivity)
                    .load(uri)//加载图片
                    .error(com.yiande.jxjxc.R.drawable.icon_stub)////加载图片出错的情况下显示的默认图
                    .into(photoView);//显示图片
        }
        container.addView(photoView);
        return photoView;
    }

    @Override
    public int getCount() {
        if (images != null) {
            return images.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface PhotoViewClickListener {
        void OnPhotoTapListener(View view, float v, float v1);
    }

    /**
     * 获取手机大小（分辨率）
     */
    public static DisplayMetrics getScreenPix(Activity activity) {
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
        return displaysMetrics;
    }

}
