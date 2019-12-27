# ADEasy使用文档-自动集成说明-内部测试版

**注意，目前还在不断开发中，目前只会不断更新0001版本，不建议使用**

---
一套全广告平台的快速集成框架。

ADEasy的接入使用插件自动集成技术，只需要简单控制各个平台的开关，就能实现一条龙接入。同时，对于未接入的平台，我们不会携带其任何的的代码以及manifest声明，请放心使用。

当然，ADEasy本身也是有足够的优点的，功能上，它的权重系统能很好的提高聚合广告的收益。用法上，它统一了所有广告平台的接口，实现了简单统一的调用方式，完美利用kotlin的特性，给大家带来的是无比便捷的使用体验。

**目前支持的广告平台:**

adMob

Unity

Mi

Yomob

GDT(腾讯优量汇)

**下一步打算支持的广告平台:**

Baidu

Vungle

Facebook

IronSource

### 使用步骤

- ### Step1 接入自动集成插件到build.gradle(project)


```
buildscript {
     repositories {
        ...
         maven { url 'https://raw.githubusercontent.com/TJHello/publicLib/master'}
     }
      dependencies {
        ...
        classpath "com.TJHello.plugins:ADEasy:0.9.0001"
      }
}

allprojects {
     repositories {
        ...
        maven { url 'https://raw.githubusercontent.com/TJHello/publicLib/master'}
     }
}

```
- ### Step2 启动插件，配置参数到build.gradle(app)


```
....
apply plugin: 'ad-easy'

ADEasyExt{
    adSwitch = true  //广告总开关
    debug = true //是否输出详细日志
    adMobId = "ca-app-pub-755515620*****~*****61045" //adMob的id,接入admob必填
    adMob = true //admob开关
    adYomob = true //yomob开关
    adUnity = true //unity开关
    adMi = true //mi广告开关
    adGdt = true //腾讯优量汇(广点通)开关
}

//当方法数超限了，使用以下方法。
android {
    ...
    defaultConfig {
        ...
        multiDexEnabled true
    }
}

dependencies {
    //方法数超限
    implementation 'com.android.support:multidex:1.0.3'
}

```

- ### Step3 配置Application（[TJApplication](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/TJApplication.kt)）

```kotlin
class TJApplication : Application(),ADEasyApplicationImp{

    override fun onCreate() {
        super.onCreate()
        ADEasy.setDebug(true)
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
                return ADAppConfig.createYomob("2o0pxxxxxxxxxx","10053")
                    .setWeight(10)//配置权重
                    .addParameter("7UmIURYsIxxxxxxx",ADInfo.TYPE_VIDEO)//添加广告场景
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
```

- ### Step4 配置AppActivity（[AppActivity](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/AppActivity.kt)）


```kotlin
abstract class AppActivity : AppCompatActivity(),ADEasyActivityImp{
    protected val adEasy by lazy { ADEasy.getInstance(this,this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adEasy.isAutoShowInterstitial(true)
        onInitValue(savedInstanceState)
        onInitView()
        adEasy.onCreate()
        onLoadData()
    }

    override fun onStart() {
        super.onStart()
        adEasy.onStart()
    }

    override fun onPause() {
        super.onPause()
        adEasy.onPause()
    }

    override fun onResume() {
        super.onResume()
        adEasy.onResume()
    }

    override fun onStop() {
        super.onStop()
        adEasy.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        adEasy.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        adEasy.onActivityResult(requestCode,resultCode,data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        adEasy.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

    protected abstract fun onInitValue(savedInstanceState: Bundle?)

    protected abstract fun onInitView()

    protected abstract fun onLoadData()

    override fun isActivityFinish(): Boolean {
        return isFinishing
    }
}

```

### 使用示例

```kotlin

class TestActivity : AppActivity() {

    override fun onInitValue(savedInstanceState: Bundle?) {
        adEasy.isAutoShowBanner(true)
    }

    override fun onInitView() {
        setContentView(R.layout.test_activity_layout)
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

        btShowBanner.setOnClickListener {
            adEasy.showBanner()
        }

        btHideBanner.setOnClickListener {
            adEasy.hideBanner()
        }
    }

    override fun onLoadData() {

    }

    override fun onCreateBanner(): ViewGroup? {
        return bannerLayout
    }
}

```

### adEasy API说明

```kotlin
boolean hasBanner() //是否有banner(暂不支持)
boolean hasInterstitial() //是否有插屏广告
boolean hasVideo() //是否有激励视频
boolean showBanner() //显示banner(暂不支持)
boolean showInterstitial() //显示插屏
boolean showInterstitialVideo() //显示插屏视频
boolean showVideo() //显示激励视频
```

### 简单说明

该框架会自动按照权重分配广告，使用者只需要在Application里面配置好相关的广告信息就行了。如果需要动态刷新权重，可以调用ADEasy.changeWeight方法，group在ADInfo里面。



