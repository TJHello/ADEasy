package com.tjbaobao.utils.demo.adeasy

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.tjhello.adeasy.ADEasy
import com.tjhello.adeasy.imp.ADEasyApplicationImp
import com.tjhello.adeasy.info.ADInfo
import com.tjhello.adeasy.info.config.ADAppConfig

/**
 * 作者:天镜baobao
 * 时间:2019/11/30  19:04
 * 说明:允许使用，但请遵循Apache License 2.0
 * 使用：
 * Copyright 2019/11/30 天镜baobao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class TJApplication : Application(), ADEasyApplicationImp {

    override fun onCreate() {
        super.onCreate()
        ADEasy.setDebug(true)//这个关系到广告的测试模式，正式版必须关闭。入使用了自动集成0.9.0002版本开始，release模式下，会强行设置为false
        ADEasy.init(this,this)
    }

    //是否去广告
    override fun isRemoveAd(): Boolean {
        return false
    }

    //创建广告配置
    override fun createADAppConfig(group: String): ADAppConfig? {
        when(group){
            ADInfo.GROUP_YOMOB->{
//                return ADAppConfig.createYomob("2o0pxxxxxxxxxx","10053")
//                    .initWeight(10)
//                    .addParameter("7UmIURYsIxxxxxxx",ADInfo.TYPE_VIDEO)
//                    .addParameter("4jNKxefqixxxxxxx",ADInfo.TYPE_INTERSTITIAL_VIDEO)
            }
            ADInfo.GROUP_MI->{
//                return ADAppConfig.createMI("2882303xxxxxxxxx","5911xxxxxxxx"
//                    ,"U3pS6Oxxxxxxx==")
//                    .initWeight(0)
//                    .addParameter("df680161af4eexxxxxxxxxxxxxxxxxx",ADInfo.TYPE_VIDEO)
//                    .addParameter("1e1f1a4432c4fxxxxxxxxxxxxxxxxxx",ADInfo.TYPE_INTERSTITIAL)
            }
            ADInfo.GROUP_UNITY->{
//                return ADAppConfig.createUnity("xxxxx")
//                    .initWeight(0)
//                    .addParameter("rewardedVideo",ADInfo.TYPE_VIDEO)
//                    .addParameter("interstitial",ADInfo.TYPE_INTERSTITIAL_VIDEO)
            }
            ADInfo.GROUP_ADMOB->{
                return ADAppConfig.createAdmob()
                    .initWeight(10)
                    .addParameter("ca-app-pub-3940256099942544/6300978111",ADInfo.TYPE_BANNER)//测试id
                    .addParameter("ca-app-pub-3940256099942544/1033173712",ADInfo.TYPE_INTERSTITIAL)//测试id
                    .addParameter("ca-app-pub-3940256099942544/8691691433",ADInfo.TYPE_INTERSTITIAL_VIDEO)//测试id
                    .addParameter("ca-app-pub-3940256099942544/5224354917",ADInfo.TYPE_VIDEO,10)//测试id
                    //允许添加多个同类型，不同code的Parameter(Banner暂不支持该特性)
//                    .addParameter("ca-app-pub-394025609994***/**354917",ADInfo.TYPE_VIDEO,10)
            }
            ADInfo.GROUP_FACEBOOK->{
//                return ADAppConfig.createFacebook()
//                    .initWeight(10)
//                    .addParameter("28914969342xxxxxxx_xxxxxxx97797568448",ADInfo.TYPE_INTERSTITIAL)
//                    .addParameter("28914969342xxxxxxx_xxxxxxx02807567947",ADInfo.TYPE_VIDEO)
//                    .addParameter("28914969342xxxxxxx_xxxxxxx21207556107",ADInfo.TYPE_BANNER)
            }
        }
        return null
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //解决方法数超限问题
        MultiDex.install(base)
    }

}