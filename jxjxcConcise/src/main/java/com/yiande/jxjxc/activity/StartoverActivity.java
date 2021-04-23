package com.yiande.jxjxc.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mylibrary.api.utils.SharedUtil;
import com.mylibrary.api.utils.StringUtil;

/**
 * @Description: 启动白屏处理
 * @Author: hukui
 * @Date: 2020/7/13 10:48
 */
public class StartoverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent();
        String baseUrl = SharedUtil.getSP(com.yiande.jxjxc.App.BASEURL);
        if (StringUtil.isEmpty(baseUrl)) {
            intent.setClass(this, SetIPActivity.class);
        } else {
            if (com.yiande.jxjxc.App.isEnter.get()) {
                intent.setClass(this, HomeActivity.class);
            } else {
                intent.setClass(this, LoginActivity.class);
            }
        }
//        if (App.isEnter.get()) {
//            intent.setClass(this, HomeActivity.class);
//        } else {
//            intent.setClass(this, LoginActivity.class);
//        }
        startActivity(intent);
        finish();
    }
}
