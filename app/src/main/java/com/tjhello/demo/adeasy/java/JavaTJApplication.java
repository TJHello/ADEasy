package com.tjhello.demo.adeasy.java;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.multidex.MultiDex;

import com.tjhello.adeasy.ADEasy;
import com.tjhello.adeasy.base.anno.ADChannel;
import com.tjhello.adeasy.base.info.ADInfo;
import com.tjhello.adeasy.base.info.config.base.AdConfig;
import com.tjhello.adeasy.base.info.config.base.PlatformConfig;
import com.tjhello.adeasy.base.utils.ADEasyLog;
import com.tjhello.adeasy.imp.ADEasyApplicationImp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 作者:天镜baobao
 * 时间:2020/1/12  15:54
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
public class JavaTJApplication extends Application implements ADEasyApplicationImp {

    @Override
    public void onCreate() {
        super.onCreate();
        ADEasy.setDebug(true);//重要！！！发行版本必须设置为false
        ADEasy.setChannel(ADChannel.Order);
        ADEasy.toOfflineMode();
        ADEasyLog.addFilterType(
                ADEasyLog.TYPE_HANDLER_BASE,
                ADEasyLog.TYPE_ADEASY_DETAILED_STEPS,
                ADEasyLog.TYPE_TOOLS_UMENG
        );
        ADEasy.init(this,this);
    }

    //返回完成的关卡数(用于后期参数调控，多少关内部显示插屏banner等广告)
    @Override
    public int getCompleteLevel() {
        return 0;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    //ADEasy初始化完成
    @Override
    public void onInitAfter() {

    }

    //创建广告场景配置
    @Nullable
    @Override
    public PlatformConfig createAdPlatformConfig(@NotNull String s) {
        switch (s){
            case ADInfo.GROUP_ADMOB : {
                return AdConfig.createAdmob()
                        .addParameter("ca-app-pub-3940256099942544/6300978111", ADInfo.TYPE_BANNER)//测试id
                        .addParameter("ca-app-pub-3940256099942544/1033173712", ADInfo.TYPE_INTERSTITIAL)
                        .addParameter("ca-app-pub-3940256099942544/8691691433", ADInfo.TYPE_INTERSTITIAL_VIDEO)
                        .addParameter("ca-app-pub-3940256099942544/5224354917", ADInfo.TYPE_VIDEO)
                        .initWeight(10)
                        //允许添加多个同类型，不同code的Parameter(Banner暂不支持该特性)
                        //.addParameter("ca-app-pub-394025609994***/**354917",ADInfo.TYPE_VIDEO,10)
                        ;
            }
            case ADInfo.GROUP_GDT:{
                return AdConfig.createGDT("xxxxx",1)
                        .addParameter("5080497486414382", ADInfo.TYPE_VIDEO)
                        .addParameter("9040097521398143", ADInfo.TYPE_INTERSTITIAL)
                        .addParameter("2090198667848921", ADInfo.TYPE_BANNER)
                        .initWeight(100)
                        ;
            }
        }
        return null;
    }
}
