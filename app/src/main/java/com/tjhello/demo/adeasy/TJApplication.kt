package com.tjhello.demo.adeasy

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.tjhello.adeasy.ADEasy
import com.tjhello.adeasy.base.anno.ADChannel
import com.tjhello.adeasy.base.info.ADInfo
import com.tjhello.adeasy.base.info.config.MintegralConfig
import com.tjhello.adeasy.base.info.config.VIVOConfig
import com.tjhello.adeasy.base.info.config.base.AdConfig
import com.tjhello.adeasy.base.info.config.base.AdParameter
import com.tjhello.adeasy.base.info.config.base.PlatformConfig
import com.tjhello.adeasy.base.utils.ADEasyLog
import com.tjhello.adeasy.imp.ADEasyApplicationImp

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
        ADEasy.setDebug(true)//这个关系到广告的测试模式，如使用自动集成插件，release模式下，会强行设置为false
        ADEasy.channel = ADChannel.Order//关联友盟渠道
        ADEasy.toOfflineMode()//关闭在线模式
//        ADEasy.toTestMode()//测试配置模式，此时访问的是测试在线配置(仅针对在线模式)
        ADEasyLog.addFilterType(
            ADEasyLog.TYPE_HANDLER_BASE,
            ADEasyLog.TYPE_ADEASY_DETAILED_STEPS,
            ADEasyLog.TYPE_TOOLS_UMENG
        )
        ADEasy.init(this,this)
    }

    override fun createAdPlatformConfig(group: String): PlatformConfig? {
        when(group){
            ADInfo.GROUP_MI->{
                return AdConfig.createMI("2882303xxxxxxxxx")
                    .addParameter("df680161af4eexxxxxxxxxxxxxxxxxx",ADInfo.TYPE_VIDEO)
                    .addParameter("1e1f1a4432c4fxxxxxxxxxxxxxxxxxx",ADInfo.TYPE_INTERSTITIAL)
                    .initWeight(0)
            }
            ADInfo.GROUP_ADMOB->{
                return AdConfig.createAdmob()
                    .addParameter("ca-app-pub-3940256099942544/6300978111",ADInfo.TYPE_BANNER)//测试id
                    .addParameter("ca-app-pub-3940256099942544/1033173712",ADInfo.TYPE_INTERSTITIAL)//测试id
                    .addParameter("ca-app-pub-3940256099942544/8691691433",ADInfo.TYPE_INTERSTITIAL_VIDEO)//测试id
                    .addParameter("ca-app-pub-3940256099942544/5224354917",ADInfo.TYPE_VIDEO)//测试id
                    //允许添加多个同类型，不同code的Parameter(Banner暂不支持该特性)
                    .addParameter("ca-app-pub-394025609994***/**354917",ADInfo.TYPE_VIDEO)
                    .initWeight(10)
            }
            ADInfo.GROUP_MINTEGRAL->{
                return AdConfig.createMintegral("appId","appKey")
                    .addParameter("ad_code","unit_id",ADInfo.TYPE_VIDEO)
                    .initWeight(1)
            }
        }
        return null
    }

    //是否去广告
    override fun isRemoveAd(): Boolean {
        return false
    }

    //ADEasy初始化完成
    override fun onInitAfter() {
        //可以本地修改控制策略
        val manager = ADEasy.getConfigManager()
        manager.getInsCtrlManager()
            .setAutoShow(true)
            .setIntervalTime(10000)
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //解决方法数超限问题
        MultiDex.install(base)
    }

}