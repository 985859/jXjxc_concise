package com.yiande.jxjxc.popwindow;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.mylibrary.api.widget.GangedView;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePopupWindow;
import com.yiande.jxjxc.databinding.PopGandedSelectBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.myInterface.LevelData;
import com.yiande.jxjxc.utils.Util;

import java.util.List;

public class GangedSelectPop extends BasePopupWindow<PopGandedSelectBinding> {

    private GangedView.OnSelcetLinsenter onSelectListener;
    private TextView showTextView;
    private String defultText = "";

    boolean isReset = false;

    public GangedSelectPop(RxAppCompatActivity context) {
        super(context);
        mBinding.setOnClick(onClik);
    }

    @Override
    public void onShowListener() {
        super.onShowListener();
        SystemUtil.toggleSoftInput(mBinding.popGandedTitle);
    }

    public void setOnSelectListener(GangedView.OnSelcetLinsenter onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public int getContentViewID() {
        return R.layout.pop_ganded_select;
    }

    @Override
    public void init(View view) {
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBinding.popGandedLevelView.setMaxLevel(3);
    }

    @Override
    public void setListener() {
        super.setListener();

    }

    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {

            case 0:
                dismiss();
                break;
            case 1:
                mBinding.popGandedLevelView.setselectID(null);
                isReset = true;
                if (onSelectListener != null) {
                    onSelectListener.onSelcet(null, null);
                }
                if (showTextView != null) {
                    if (StringUtil.isNotEmpty(defultText)) {
                        showTextView.setText(defultText);
                    } else {
                        showTextView.setText("");
                    }

                }
                dismiss();
                break;
            case 2:
                isReset = false;
                if (onSelectListener != null) {
                    onSelectListener.onSelcet(mBinding.popGandedLevelView.getselectIds(), mBinding.popGandedLevelView.getselectNames());
                }
                if (showTextView != null) {
                    String text = Util.isListEmpty(mBinding.popGandedLevelView.getselectNames()) ? defultText : StringUtil.slipListToString(mBinding.popGandedLevelView.getselectNames(), " / ");
                    showTextView.setText(text);
                }
                dismiss();
                break;
            default:
                break;
        }
    };


    public void setTitle(String title) {
        mBinding.popGandedTitle.setText(title);
    }


    public void setselectID(List<String> ids) {
        mBinding.popGandedLevelView.setselectID(ids);
    }


    public <T extends LevelData> void setData(List<T> data) {
        setData(data, true);
    }

    public <T extends LevelData> void setData(List<T> data, boolean isReSelect) {
        mBinding.popGandedLevelView.setData(data, isReSelect);
    }


    public void setShowTextView(TextView showTextView) {
        this.showTextView = showTextView;
    }

    public void setShowTextView(TextView showTextView, String defultText) {
        this.showTextView = showTextView;
        this.defultText = defultText;
    }

    public void setMaxLevel(int level) {
        if (level < 0) {
            level = 0;
        }
        mBinding.popGandedLevelView.setMaxLevel(level);
    }
}
