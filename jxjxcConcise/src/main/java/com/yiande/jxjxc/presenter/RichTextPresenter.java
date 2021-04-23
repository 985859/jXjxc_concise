package com.yiande.jxjxc.presenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;

import com.mylibrary.api.interfaces.TypeValues;
import com.mylibrary.api.utils.LogUtil;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.SystemUtil;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.base.BasePresenter;
import com.yiande.jxjxc.databinding.ActivityRichTextBinding;
import com.yiande.jxjxc.myInterface.EventConsume;
import com.yiande.jxjxc.popwindow.ColorPop;
import com.yiande.jxjxc.popwindow.SizePop;
import com.yiande.jxjxc.utils.Util;

import java.util.List;

import static jp.wasabeef.richeditor.RichEditor.OnDecorationStateListener;
import static jp.wasabeef.richeditor.RichEditor.OnTextChangeListener;
import static jp.wasabeef.richeditor.RichEditor.Type;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/24 10:56
 */
public class RichTextPresenter extends BasePresenter<ActivityRichTextBinding> {
    private SizePop sizePop;
    private ColorPop colorPop;
    private boolean isEdit;
    private String richContent = "";
    //是否加粗
    private boolean isBold = false;

    //是否按ol排序
    private boolean isListOl = false;
    //是否按ul排序
    private boolean isListUL = false;
    //是否下倾斜字体
    private boolean isItalic = false;
    //是否左对齐
    private boolean isAlignLeft = false;
    //是否右对齐
    private boolean isAlignRight = false;
    //是否中对齐
    private boolean isAlignCenter = false;
    private int keyBoardHeight = 0;
    private boolean isShow = false;

    public RichTextPresenter(RxAppCompatActivity mContext, ActivityRichTextBinding binding) {
        super(mContext, binding);
        Intent intent = getIntent();
        if (intent != null) {
            richContent = intent.getStringExtra("content");
            String title = intent.getStringExtra("title");
            isEdit = intent.getBooleanExtra("isEdit", false);
            mBinding.richDetailTop.setTitle(title);
        }
        mBinding.setIsEdit(isEdit);
        mBinding.setOnClicek(onClik);
        initEditor();

    }


    /**
     * 初始化文本编辑器
     */
    private void initEditor() {

        //输入框显示字体的颜色
        mBinding.richDetailEditor.setEditorFontColor(Color.BLACK);
        //输入提示文本
        mBinding.richDetailEditor.setPlaceholder("请输入编辑内容");
        //是否允许输入
        mBinding.richDetailEditor.setInputEnabled(isEdit);
        //文本输入框监听事件
        mBinding.richDetailEditor.setOnTextChangeListener(new OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
            }
        });
        mBinding.richDetailEditor.setOnDecorationChangeListener(new OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<Type> types) {
                init();
                if (StringUtil.isNotEmpty(text)) {
                    String[] str = text.split(",");
                    for (int i = 0; i < str.length; i++) {
                        String s = str[i];
                        LogUtil.e(s);
                        switch (s) {
                            case "BOLD":
                                isBold = true;
                                break;
                            case "JUSTIFYLEFT":
                                isAlignLeft = true;
                                break;
                            case "JUSTIFYCENTER":
                                isAlignCenter = true;
                                break;
                            case "JUSTIFYRIGHT":
                                isAlignRight = true;
                                break;
                            case "ORDEREDLIST":
                                isListOl = true;
                                break;
                            case "UNORDEREDLIST":
                                isListUL = true;
                                break;
                            default:
                                break;
                        }

                    }

                }
                setInit();
            }
        });
        //输入框显示字体的大小
        mBinding.richDetailEditor.setEditorFontSize(14);
        mBinding.richDetailEditor.setFontSize(3);
        mBinding.richDetailEditor.setFocusable(true);
        mBinding.richDetailEditor.setFocusableInTouchMode(true);
        mBinding.richDetailEditor.requestFocus();
        if (StringUtil.isNotEmpty(richContent)) {
            mBinding.richDetailEditor.setHtml(richContent);

        }

    }

    private void init() {
        //是否加粗
        isBold = false;
        //是否按ol排序
        isListOl = false;
        //是否按ul排序
        isListUL = false;
        //是否下倾斜字体
        isItalic = false;
        //是否左对齐
        isAlignLeft = false;
        //是否右对齐
        isAlignRight = false;
        //是否中对齐
        isAlignCenter = false;
    }

    private void setInit() {
        //是否加粗
        setBold(isBold);
        //是否按ol排序
        setListOl(isListOl);
        //是否按ul排序
        setListUl(isListUL);
        //是否左对齐
        setLeft(isAlignLeft);
        //是否右对齐
        setRight(isAlignRight);
        //是否中对齐
        setCenter(isAlignCenter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mBinding.richDetailTop.setLeftViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResultData();
            }
        });
    }

    public void setResultData() {
        Intent intent = getIntent();
        intent.putExtra("content", mBinding.richDetailEditor.getHtml());
        mContext.setResult(TypeValues.DATA, intent);
    }


    private EventConsume<Integer> onClik = (type) -> {

        switch (type) {
            case 1:
                onPic();
                break;
            case 2:
                onBuld();
                break;
            case 3:
                iniColorPop();
                break;
            case 4:
                iniSizePop();
                break;
            case 5:
                onListOl();
                break;
            case 6:
                onListUl();
                break;
            case 7:
                onLeft();
                break;
            case 8:
                onCenter();
                break;
            case 9:
                onRight();
                break;
            default:
                break;
        }
    };

    private void onPic() {
        Util.openCamera(mContext, 1, true, false);
    }


    private void onBuld() {
        setBold(!isBold);
        mBinding.richDetailEditor.setBold();
    }

    private void setBold(boolean isBold) {
        this.isBold = isBold;
        if (isBold) { //加粗
            mBinding.richDetailBold.setImageResource(R.drawable.blud_b);
        } else {
            mBinding.richDetailBold.setImageResource(R.drawable.blud_g);

        }
    }


    private void onListOl() {
        isListUL = false;
        mBinding.richDetailList2.setImageResource(R.drawable.list_g2);
        setListOl(!isListOl);
        mBinding.richDetailEditor.setNumbers();
    }

    private void setListOl(boolean isListOl) {
        this.isListOl = isListOl;
        if (isListOl) {
            mBinding.richDetailList1.setImageResource(R.drawable.list_b);
        } else {
            mBinding.richDetailList1.setImageResource(R.drawable.list_g);
        }

    }

    private void onListUl() {
        isListOl = false;
        mBinding.richDetailList1.setImageResource(R.drawable.list_g);
        setListUl(!isListUL);
        mBinding.richDetailEditor.setBullets();
    }

    private void setListUl(boolean isListUL) {
        this.isListUL = isListUL;
        if (isListUL) {
            mBinding.richDetailList2.setImageResource(R.drawable.list_b2);
        } else {
            mBinding.richDetailList2.setImageResource(R.drawable.list_g2);
        }

    }

    private void initAlign() {
        mBinding.richDetailCenter.setImageResource(R.drawable.center_g);
        mBinding.richDetailLeft.setImageResource(R.drawable.left_g);
        mBinding.richDetailRight.setImageResource(R.drawable.right_g);
    }

    private void onLeft() {
        isAlignCenter = false;
        isAlignRight = false;
        if (isAlignLeft == false) {
            initAlign();
            setLeft(!isAlignLeft);
            mBinding.richDetailEditor.setAlignLeft();
        }

    }

    private void setLeft(boolean isAlignLeft) {
        this.isAlignLeft = isAlignLeft;
        if (isAlignLeft) {
            mBinding.richDetailLeft.setImageResource(R.drawable.left_b);
        } else {
            mBinding.richDetailLeft.setImageResource(R.drawable.left_g);
        }
    }


    private void onCenter() {
        isAlignLeft = false;
        isAlignRight = false;
        if (isAlignCenter == false) {
            initAlign();
            setCenter(!isAlignCenter);
            mBinding.richDetailEditor.setAlignCenter();
        }
    }

    private void setCenter(boolean isAlignCenter) {
        this.isAlignCenter = isAlignCenter;
        if (isAlignCenter) {
            mBinding.richDetailCenter.setImageResource(R.drawable.center_b);
        } else {
            mBinding.richDetailCenter.setImageResource(R.drawable.center_g);
        }
    }

    private void onRight() {
        isAlignLeft = false;
        isAlignCenter = false;
        if (isAlignRight == false) {
            initAlign();
            setRight(!isAlignRight);
            mBinding.richDetailEditor.setAlignRight();
        }
    }


    private void setRight(boolean isAlignRight) {
        this.isAlignRight = isAlignRight;
        if (isAlignRight) {
            mBinding.richDetailRight.setImageResource(R.drawable.right_b);
        } else {
            mBinding.richDetailRight.setImageResource(R.drawable.right_g);
        }
    }

    @SuppressLint("NewApi")
    private void iniSizePop() {
        if (sizePop == null) {
            sizePop = new SizePop(mContext);
            sizePop.setOnSetSizeListener(new SizePop.OnSetSizeListener() {
                @Override
                public void onSetSize(int size, int fontSize) {
                    mBinding.richDetailEditor.setEditorFontSize(size);
                    mBinding.richDetailEditor.setFontSize(fontSize);


                }
            });
        }
        int y = 0;
        if (isShow) {
            y = SystemUtil.getNavigationBarHeight(mContext) + keyBoardHeight;
        } else {
            y = SystemUtil.getNavigationBarHeight(mContext);
        }
        sizePop.showPopupWindow(mBinding.richDetailSize, Gravity.BOTTOM, 0, y);
    }

    @SuppressLint("NewApi")
    private void iniColorPop() {
        if (colorPop == null) {
            colorPop = new ColorPop(mContext);
            colorPop.setOnSetColorListener(new ColorPop.OnSetColorListener() {
                @Override
                public void onSetColor(int color) {
                    mBinding.richDetailEditor.setTextColor(color);
                }
            });
        }
        int y = 0;
        if (isShow) {
            y = SystemUtil.getNavigationBarHeight(mContext) + keyBoardHeight;
        } else {
            y = SystemUtil.getNavigationBarHeight(mContext);
        }
        colorPop.showPopupWindow(mBinding.richDetailColor, Gravity.BOTTOM, 0, y);
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片选择结果回调
//                    List<PicBean> selectList = Utils.obtainMultipleResult(data);
//                    if (selectList != null && selectList.size() > 0) {
//                        upFile(Utils.getPath(selectList.get(0)));
//                    } else {
//                        ToastUtil.showShort(this, "获取图片路径失败");
//                    }
//            }
//        }
//
//
//    }

    //上传图片
    private void upFile(String path) {
        mBinding.richDetailEditor.setInputEnabled(false);
//        if (StringUtil.isNotEmpty(path)) {
//            List<File> files = new ArrayList<>();
//            if (!path.startsWith("http")) {
//                files.add(new File(path));
//            }
//            OkGo.<JsonBean<String>>post(URLS.UploadImage + "?type=4")
//                    .addFileParams("param1", files)
//                    .execute(new JsonCallback<JsonBean<String>>(mContext) {
//                        @Override
//                        public void onSuccess(Response<JsonBean<String>> response) {
//                            super.onSuccess(response);
//                              mBinding.richDetailEditor.setInputEnabled(true);
//                            if ("1".equals(response.body().code)) {
//                                if (StringUtil.isNotEmpty(response.body().data)) {
//                                      mBinding.richDetailEditor.insertImage(response.body().data, "dachshund");
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onError(Response<JsonBean<String>> response) {
//                            super.onError(response);
//                              mBinding.richDetailEditor.setInputEnabled(true);
//                        }
//                    });
//        } else {
//            ToastUtil.showShort(mContext, "图片获取失败");
//        }


    }
}
