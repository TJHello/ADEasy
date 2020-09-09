package com.tjhello.demo.adeasy.java;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.tjhello.adeasy.base.info.ADInfo;
import com.tjhello.demo.adeasy.R;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;

/**
 * 作者:天镜baobao
 * 时间:2020/1/12  16:59
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
public class JavaTestActivity extends JavaAppActivity {

    private Button btShowVideo ;
    private Button btShowInterstitialVideo ;
    private Button btShowInterstitial ;
    private Button btShowBanner ;

    @Override
    void onInitValue(@Nullable Bundle savedInstanceState) {
        adEasy.isAutoShowBanner(true);
    }

    @Override
    void onInitView() {
        setContentView(R.layout.test_activity_layout);

        btShowVideo = this.findViewById(R.id.btShowVideo);
        btShowInterstitialVideo = this.findViewById(R.id.btShowInterstitialVideo);
        btShowInterstitial = this.findViewById(R.id.btShowInterstitial);
        btShowBanner = this.findViewById(R.id.btShowBanner);
        Button btHideBanner = this.findViewById(R.id.btHideBanner);

        //刷新按钮的UI状态
        refreshBtVideo();
        refreshBtIns();
        refreshBtInsVideo();
        refreshBtBanner();

        btShowVideo.setOnClickListener(v->{
            adEasy.showVideo((adInfo, aBoolean) -> {
                //看视频完成回调
                refreshBtVideo();
                return Unit.INSTANCE;
            });
        });
        btShowInterstitialVideo.setOnClickListener(v->{
            adEasy.showInterstitialVideo((adInfo) -> {
                //看插屏视频完成回调
                return Unit.INSTANCE;
            });
        });
        btShowInterstitial.setOnClickListener(v->{
            adEasy.showInterstitial((adInfo) -> {
                //看插屏完成回调
                refreshBtIns();
                return Unit.INSTANCE;
            });
        });
        btShowBanner.setOnClickListener(v->{
            adEasy.showBanner();
        });

        btHideBanner.setOnClickListener(v->{
            adEasy.hideBanner();
        });

    }

    @Override
    void onLoadData() {

    }

    private void refreshBtVideo(){
        if(adEasy.hasVideo()){
            btShowVideo.setAlpha(1f);
        }else{
            btShowVideo.setAlpha(0.5f);
        }
    }

    private void refreshBtIns(){
        if(adEasy.hasInterstitial()){
            btShowInterstitial.setAlpha(1f);
        }else{
            btShowInterstitial.setAlpha(0.5f);
        }
    }

    private void refreshBtInsVideo(){
        if(adEasy.hasInterstitialVideo()){
            btShowInterstitialVideo.setAlpha(1f);
        }else{
            btShowInterstitialVideo.setAlpha(0.5f);
        }
    }

    private void refreshBtBanner(){
        if(adEasy.hasBanner()){
            btShowBanner.setAlpha(1f);
        }else{
            btShowBanner.setAlpha(0.5f);
        }
    }

    //广告加载成功回调
    @Override
    public void onAdLoad(@NotNull ADInfo adInfo, boolean isSelf) {
        super.onAdLoad(adInfo, isSelf);
        switch (adInfo.getType()){
            case ADInfo.TYPE_VIDEO:refreshBtVideo();break;
            case ADInfo.TYPE_INTERSTITIAL:refreshBtIns();break;
            case ADInfo.TYPE_INTERSTITIAL_VIDEO:refreshBtInsVideo();break;
            case ADInfo.TYPE_BANNER:refreshBtBanner();break;
        }
    }

    @Override
    public void onAdClick(@NotNull ADInfo adInfo) {

    }
}
