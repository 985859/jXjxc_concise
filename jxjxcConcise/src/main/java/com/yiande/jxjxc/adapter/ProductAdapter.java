package com.yiande.jxjxc.adapter;

import android.text.SpannableStringBuilder;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.mylibrary.api.utils.SpannableUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.bean.ProductBean;
import com.yiande.jxjxc.databinding.ItmProductBinding;
import com.yiande.jxjxc.utils.Util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/9/18 20:21
 */
public class ProductAdapter extends BaseQuickAdapter<ProductBean, BaseDataBindingHolder> {
    public ProductAdapter(@Nullable List<ProductBean> data) {
        super(R.layout.itm_product, data);
    }

    private int type = 0;//0 产品列表 1 入库 2 出库
    private int level = 0;//客户级别
    private boolean isKg;//是否显示Kg

    public void setIsKg(boolean isKg) {
        this.isKg = isKg;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder holder, ProductBean productBean) {
        ItmProductBinding binding = (ItmProductBinding) holder.getDataBinding();
        binding.setType(type);
        binding.setLevel(level);
        binding.setIskg(isKg);
        binding.setData(productBean);
        String picUrl = productBean.getProduct_PicUrl();
        List<String> picList = StringUtil.slipStringToList(productBean.getProduct_Pic(), ",");
        if (Util.isNotListEmpty(picList)) {
            picUrl += picList.get(0);
        }
        binding.itmProductPic.setImageUrl(picUrl, R.drawable.icon_stub);
        if (productBean.getProduct_IsOK() == 0) {
            SpannableStringBuilder builder = SpannableUtil.setStartTip(getContext().getString(R.string.disable), productBean.getProduct_Title(),
                    SystemUtil.sp2px(getContext(), 10), SystemUtil.sp2px(getContext(), 12), SystemUtil.sp2px(getContext(), 6),
                    getContext().getResources().getColor(R.color.yellow), getContext().getResources().getColor(R.color.white));
            binding.itmProductTitel.setRightText(builder);
        } else {
            binding.itmProductTitel.setRightText(productBean.getProduct_Title());
        }
        if (type == 2) {
            binding.itmProductPrice.setLeftIcon(Util.getLevelIocn(level));
            if (level == 0) {
                binding.itmProductPrice.setLeftText("销售价");
            } else {
                binding.itmProductPrice.setLeftText("售价");
            }
            binding.itmProductPrice.setRightText(Util.setPrice(Util.getLevelPrice(level, productBean)));
        } else {
            binding.itmProductPrice.setLeftText("销售价");
            binding.itmProductPrice.setLeftIcon(Util.getLevelIocn(0));
            binding.itmProductPrice.setRightText(Util.setPrice(productBean.getProduct_Price()));
        }
    }


}
