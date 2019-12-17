package com.tjbaobao.utils.demo.adeasy

import android.content.Intent
import android.os.Bundle
import com.tjbaobao.utils.adeasy.utils.LogUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppActivity() {

    override fun onInitValue(savedInstanceState: Bundle?) {

    }

    override fun onInitView() {
        setContentView(R.layout.activity_main)

        btShowVideo.setOnClickListener {
            adEasy.showVideo{adInfo, isReward ->
                LogUtil.i("[showVideo]:callback:$isReward")
            }
        }
        btShowInterstitialVideo.setOnClickListener {
            adEasy.showInterstitialVideo {
                LogUtil.i("[showInterstitialVideo]:callback")
            }
        }
        btShowInterstitial.setOnClickListener {
            adEasy.showInterstitial {
                LogUtil.i("[showInterstitial]:callback")
            }
        }
        btNextActivity.setOnClickListener {
            startActivity(Intent(this,TestActivity::class.java))
        }
    }

    override fun onLoadData() {

    }
}
