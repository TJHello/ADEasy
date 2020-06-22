package com.tjhello.demo.adeasy

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import com.tjhello.adeasy.info.ADInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppActivity() {

    override fun onInitValue(savedInstanceState: Bundle?) {
        adEasy.notShowInterstitialOnce()
        adEasy.isAutoShowBanner(true)
    }

    override fun onInitView() {
        setContentView(R.layout.activity_main)

        refreshBtVideo()
        refreshBtIns()
        refreshBtInsVideo()
        refreshBtBanner()

        btShowVideo.setOnClickListener {
            adEasy.showVideo{adInfo, isReward ->
                refreshBtVideo()
                Toast.makeText(this,"Close Video :$isReward",Toast.LENGTH_LONG).show()
            }
        }
        btShowInterstitialVideo.setOnClickListener {
            adEasy.showInterstitialVideo {
                refreshBtInsVideo()
                Toast.makeText(this,"Close InterstitialVideo",Toast.LENGTH_LONG).show()
            }
        }
        btShowInterstitial.setOnClickListener {
            adEasy.showInterstitial {
                refreshBtIns()
                Toast.makeText(this,"Close Interstitial",Toast.LENGTH_LONG).show()
            }
        }
        btShowBanner.setOnClickListener {
            adEasy.showBanner()
        }
        btNextActivity.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }

    override fun onLoadData() {

    }

    private fun refreshBtVideo(){
        if(adEasy.hasVideo()){
            btShowVideo.alpha = 1f
        }else{
            btShowVideo.alpha = 0.5f
        }
    }

    private fun refreshBtIns(){
        if(adEasy.hasInterstitial()){
            btShowInterstitial.alpha = 1f
        }else{
            btShowInterstitial.alpha = 0.5f
        }
    }

    private fun refreshBtInsVideo(){
        if(adEasy.hasInterstitialVideo()){
            btShowInterstitialVideo.alpha = 1f
        }else{
            btShowInterstitialVideo.alpha = 0.5f
        }
    }

    private fun refreshBtBanner(){
        if(adEasy.hasBanner()){
            btShowBanner.alpha = 1f
        }else{
            btShowBanner.alpha = 0.5f
        }
    }

    override fun onAdLoad(adInfo: ADInfo, isSelf: Boolean) {
        when(adInfo.type){
            ADInfo.TYPE_VIDEO->refreshBtVideo()
            ADInfo.TYPE_INTERSTITIAL->refreshBtIns()
            ADInfo.TYPE_INTERSTITIAL_VIDEO->refreshBtInsVideo()
            ADInfo.TYPE_BANNER->refreshBtBanner()
        }
    }

    override fun onCreateBanner(): ViewGroup? {
        return frameBanner
    }

}
