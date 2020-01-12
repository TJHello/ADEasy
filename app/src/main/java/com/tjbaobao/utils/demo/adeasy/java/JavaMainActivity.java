package com.tjbaobao.utils.demo.adeasy.java;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.tjbaobao.utils.demo.adeasy.R;

import kotlin.Unit;

/**
 * 作者:天镜baobao
 * 时间:2020/1/12  16:50
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
public class JavaMainActivity extends JavaAppActivity {


    @Override
    void onInitValue(@Nullable Bundle savedInstanceState) {
        adEasy.notShowInterstitialOnce();//首次打开，不显示插屏广告一次
    }

    @Override
    void onInitView() {
        setContentView(R.layout.activity_main);
        Button btShowVideo = this.findViewById(R.id.btShowVideo);
        Button btShowInterstitialVideo = this.findViewById(R.id.btShowInterstitialVideo);
        Button btShowInterstitial = this.findViewById(R.id.btShowInterstitial);
        Button btNextActivity = this.findViewById(R.id.btNextActivity);

        btShowVideo.setOnClickListener(v->{
            adEasy.showVideo((adInfo, aBoolean) -> {
                //看视频完成回调
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

                return Unit.INSTANCE;
            });
        });

        btNextActivity.setOnClickListener(v->{
            startActivity(new Intent(this, JavaTestActivity.class));
        });
    }

    @Override
    void onLoadData() {

    }
}
