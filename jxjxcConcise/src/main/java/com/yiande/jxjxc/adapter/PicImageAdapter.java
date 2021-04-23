package com.yiande.jxjxc.adapter;


import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.mylibrary.api.utils.StringUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.utils.Util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author：luck
 * @date：2016-7-27 23:02
 * @describe：GridImageAdapter
 */
public class PicImageAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {

    public PicImageAdapter(@Nullable List<LocalMedia> data) {
        super(R.layout.gv_filter_image, data);
    }

    private boolean isShowDlear = true;

    public void setShowDlear(boolean showDlear) {
        isShowDlear = showDlear;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, LocalMedia media) {
        ImageView mIvDel = holder.getView(R.id.ll_del);
        if(isShowDlear){
        mIvDel.setVisibility(View.VISIBLE);
        }else {
            mIvDel.setVisibility(View.GONE);
        }
        mIvDel.setOnClickListener(view -> {
            remove(media);
        });
        int chooseModel = media.getChooseModel();
        String path = Util.getPath(media);
        long duration = media.getDuration();
        TextView tvDuration = holder.getView(R.id.tv_duration);
        tvDuration.setVisibility(PictureMimeType.isHasVideo(media.getMimeType()) ? View.VISIBLE : View.GONE);
        if (chooseModel == PictureMimeType.ofAudio()) {
            tvDuration.setVisibility(View.VISIBLE);
            tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.picture_icon_audio, 0, 0, 0);

        } else {
            tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds
                    (R.drawable.picture_icon_video, 0, 0, 0);
        }
        tvDuration.setText(DateUtils.formatDurationTime(duration));

        ImageView mImg = holder.getView(R.id.fiv);
        if (chooseModel == PictureMimeType.ofAudio()) {
            mImg.setImageResource(R.drawable.picture_audio_placeholder);
        } else {
            if ("url".equals(media.getMimeType())) {
                String url = media.getPath();
                if (StringUtil.isEmpty(media.getPath())) {
                    url = "http";
                }
                Glide.with(getContext())
                        .load(url)//加载图片
                        .error(R.drawable.icon_stub)////加载图片出错的情况下显示的默认图
                        .into(mImg);//显示图片

            } else {
                Uri uri = Uri.parse(path);
                if (path.startsWith("file://")) {
                    uri = Uri.parse(path);
                } else {
                    uri = Uri.parse("file://" + path);
                }

                Glide.with(getContext())
                        .load(uri)//加载图片
                        .error(R.drawable.icon_stub)////加载图片出错的情况下显示的默认图
                        .into(mImg);//显示图片

            }

        }
    }

}
