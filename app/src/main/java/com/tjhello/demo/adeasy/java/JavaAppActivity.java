package com.tjhello.demo.adeasy.java;


import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tjhello.adeasy.ADEasy;
import com.tjhello.adeasy.base.info.ADInfo;
import com.tjhello.adeasy.imp.ADEasyActivityImp;

import org.jetbrains.annotations.NotNull;

/**
 * 作者:天镜baobao
 * 时间:2020/1/12  16:38
 * 说明:允许使用，但请遵循Apache License 2.0
 * 使用：
 * Copyright 2020/1/12 天镜baobao
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public abstract class JavaAppActivity extends AppCompatActivity implements ADEasyActivityImp {

    protected ADEasy.ADEasyInstance adEasy = ADEasy.getInstance(this, this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adEasy.isAutoShowInterstitial(true);
        onInitValue(savedInstanceState);
        onInitView();
        adEasy.onCreate();
        onLoadData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adEasy.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adEasy.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adEasy.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adEasy.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adEasy.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adEasy.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        adEasy.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onAdLoad(@NotNull ADInfo adInfo, boolean isSelf) {
        //广告加载成功
    }

    @Override
    public void onAdClose(@NotNull ADInfo adInfo, boolean isReward) {
        //广告关闭
    }

    @Override
    public void onAdShow(@NotNull ADInfo adInfo) {
        //广告显示事件
    }

    @Override
    public void onAdError(@org.jetbrains.annotations.Nullable ADInfo adInfo, @org.jetbrains.annotations.Nullable String msg) {
        //广告错误事件
    }

    @Override
    public boolean isActivityFinish() {
        return this.isFinishing();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public ViewGroup onCreateBanner() {
        //创建banner，返回banner容器，返回null则代表该页面不显示banner。
        return null;
    }

    abstract void onInitValue(@Nullable Bundle savedInstanceState);
    abstract void onInitView();
    abstract void onLoadData();
}
