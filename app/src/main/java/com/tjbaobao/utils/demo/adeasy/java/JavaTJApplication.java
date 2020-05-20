package com.tjbaobao.utils.demo.adeasy.java;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.multidex.MultiDex;

import com.tjhello.adeasy.ADEasy;
import com.tjhello.adeasy.anno.ADChannel;
import com.tjhello.adeasy.imp.ADEasyApplicationImp;
import com.tjhello.adeasy.info.ADInfo;
import com.tjhello.adeasy.info.config.base.ADAppConfig;
import com.tjhello.adeasy.utils.ADEasyLog;

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

    //是否去广告(用于VIP去广告等场景)
    @Override
    public boolean isRemoveAd() {
        return false;
    }

    //创建广告场景配置
    @Nullable
    @Override
    public ADAppConfig createADAppConfig(@NotNull String group) {
        switch (group){
            case ADInfo.GROUP_ADMOB : {
                return ADAppConfig.createAdmob()
                        .initWeight(10)
                        .addParameter("ca-app-pub-3940256099942544/6300978111", ADInfo.TYPE_BANNER,1,null)//测试id
                        .addParameter("ca-app-pub-3940256099942544/1033173712", ADInfo.TYPE_INTERSTITIAL,1,null)
                        .addParameter("ca-app-pub-3940256099942544/8691691433", ADInfo.TYPE_INTERSTITIAL_VIDEO,1,null)
                        .addParameter("ca-app-pub-3940256099942544/5224354917", ADInfo.TYPE_VIDEO,10,null)
                        //允许添加多个同类型，不同code的Parameter(Banner暂不支持该特性)
                        //.addParameter("ca-app-pub-394025609994***/**354917",ADInfo.TYPE_VIDEO,10)
                    ;
            }
            case ADInfo.GROUP_GDT:{
                return ADAppConfig.createGDT("xxxxx",1)
                        .initWeight(100)
                        .addParameter("5080497486414382", ADInfo.TYPE_VIDEO,1,null)
                        .addParameter("9040097521398143", ADInfo.TYPE_INTERSTITIAL,1,null)
                        .addParameter("2090198667848921", ADInfo.TYPE_BANNER,1,null);
            }
        }
        return null;
    }

    //是否显示插屏，banner等广告(用于一键关闭所有广告)
    @Override
    public boolean canShowAd() {
        return true;
    }

    //返回完成的关卡数(用于后期参数调控，多少关内部显示插屏banner等广告)
    @Override
    public int getCompleteLevel() {
        return 0;
    }

    //返回应用版本号(用于后期参数调控某些版本不显示插屏banner等广告)
    @Override
    public int getAppVersion() {
        try {
            return getPackageManager().getPackageInfo(this.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
