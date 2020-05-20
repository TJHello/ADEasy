package com.tjbaobao.utils.demo.adeasy

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import com.tjhello.adeasy.ADEasy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppActivity() {

    override fun onInitValue(savedInstanceState: Bundle?) {
        adEasy.notShowInterstitialOnce()//首页进来，免一次插屏广告
        adEasy.isAutoShowInterstitial(true)
        adEasy.isAutoShowBanner(true)
        //如果有隐私协议或年龄选择弹窗，可以调用以下方法挂起生命周期
        /*
        * adEasy.hangLifeUp()
        * //隐私协议确定的时候调用
        * adEasy.hangLifeDown()
        *
        */

        adEasy.closeAD()
    }

    override fun onInitView() {
        setContentView(R.layout.activity_main)

        btShowVideo.setOnClickListener {
            adEasy.showVideo{adInfo, isReward ->
                Toast.makeText(this,"Close Video :$isReward", Toast.LENGTH_LONG).show()
            }
        }
        btShowInterstitialVideo.setOnClickListener {
            adEasy.showInterstitialVideo {
                Toast.makeText(this,"Close InterstitialVideo",Toast.LENGTH_LONG).show()
            }
        }
        btShowInterstitial.setOnClickListener {
            adEasy.showInterstitial {
                Toast.makeText(this,"Close Interstitial",Toast.LENGTH_LONG).show()
            }
        }
        btNextActivity.setOnClickListener {
            startActivity(Intent(this,TestActivity::class.java))
        }
    }

    override fun onLoadData() {

    }

    /**
     * 创建banner，如果不需要显示banner则返回null，否则传入父容器
     */
    override fun onCreateBanner(): ViewGroup? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        ADEasy.exitApp(this)
    }
}
