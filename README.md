**中文** [https://github.com/TJHello/ADEasy/blob/master/README.md](https://github.com/TJHello/ADEasy/blob/master/README.md)

**English** [https://github.com/TJHello/ADEasy/blob/master/README-EN.md](https://github.com/TJHello/ADEasy/blob/master/README-EN.md)

# ADEasy(测试版)-自动集成说明

---
一套全广告平台的快速集成框架。

ADEasy的接入使用插件自动集成技术，只需要简单控制各个平台的开关，就能实现一条龙接入。同时，对于未接入的平台，我们不会携带其任何的的代码以及manifest声明，请放心使用。

当然，ADEasy本身也是有足够的优点的，功能上，它的权重系统能很好的提高聚合广告的收益。用法上，它统一了所有广告平台的接口，实现了简单统一的调用方式，完美利用kotlin的特性，给大家带来的是无比便捷的使用体验。

**目前支持的广告平台:**

adMob(banner,interstitial,video,interstitialVideo)

Unity(banner,interstitial,video,interstitialVideo)

Mi(banner,interstitial,video)

Yomob(interstitial,video,interstitialVideo)

GDT(腾讯优量汇)(banner2.0,插屏2.0,激励视频)

Facebook(banner,interstitial,video)

**下一步打算支持的广告平台:**

Baidu

Vungle

IronSource

### 使用步骤

- ### Step1 接入自动集成插件到build.gradle(project)


```groovy
buildscript {
     repositories {
        ...
         maven { url 'https://raw.githubusercontent.com/TJHello/publicLib/master'}
     }
      dependencies {
        ...
        classpath "com.TJHello.plugins:ADEasy:0.9.0003"
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


```groovy
....
apply plugin: 'ad-easy'

ADEasyExt{
    adSwitch = true  //广告总开关
    debug = true //该开关关联广告debug开关，正式版必须要设置为false,0.9.0002开始，release版本会自动设置为false
    //以下参数选择性填写，默认false
    adMobId = "ca-app-pub-755515620*****~*****61045" //adMob的id,接入admob必填，并且更改成正确的id，否则admob会闪退。
    adMob = true //admob开关
    adYomob = true //yomob开关
    adUnity = true //unity开关
    adMi = true //mi广告开关
    adGdt = true //腾讯优量汇(广点通)开关
    adFacebook = true//Facebook开关
}

android {
    ...
    defaultConfig {
        ...
        //当方法数超限了
        multiDexEnabled true
    }
    
    //支持java8,可以使用一些便捷的语法糖
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
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
            ADInfo.GROUP_ADMOB->{
                return ADAppConfig.createAdmob()
                     .initWeight(10)
                     .addParameter("ca-app-pub-3940256099942544/6300978111",ADInfo.TYPE_BANNER)//测试id
                     .addParameter("ca-app-pub-3940256099942544/1033173712",ADInfo.TYPE_INTERSTITIAL)
                     .addParameter("ca-app-pub-3940256099942544/5224354917",ADInfo.TYPE_VIDEO,10)
                     //允许添加多个同类型，不同code的Parameter(Banner暂不支持该特性)
                //   .addParameter("ca-app-pub-394025609994***/**354917",ADInfo.TYPE_VIDEO,10)
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

### 使用示例([TestActivity](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/TestActivity.kt))

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
boolean hasBanner() //是否有banner(暂支持 admob ,facebook,mi)
boolean hasInterstitial() //是否有插屏广告
boolean hasVideo() //是否有激励视频
boolean showBanner() //显示banner(暂支持 admob ,facebook,mi)
boolean showInterstitial() //显示插屏
boolean showInterstitialVideo() //显示插屏视频
boolean showVideo() //显示激励视频
boolean hideBanner() //隐藏banner(暂支持 admob ,facebook,mi)
boolean hideInterstitial(暂不支持)
```

### 简单说明

该框架会自动按照权重分配广告，使用者只需要在Application里面配置好相关的广告信息就行了。如果需要动态刷新权重，可以调用ADEasy.changeWeight方法，group在ADInfo里面。

### 版本更新日志

0.9.003 更新时间:2020-01-12

```
添加java调用示例。

ADEasy:0002->0003
1、支持admob设置TYPE_INTERSTITIAL_VIDEO。
2、对java方式调用进行一些友好性兼容。

```

0.9.002 更新时间:2020-01-08

```
插件更新日志：
1、release模式下，强行关闭AdEasy的Debug开关。

ADEasy:0001->0002
1、支持unity-banner，优化banner显示逻辑，以及修复相关bug。
2、支持自动化插件修改debug开关。

```


0.9.001 更新时间:2019-12-17

```
ADEasy:0001
Unity:3.3.0
GDTSDK:4.110.980
Yomob:1.8.5
AdMob:18.3.0
MI:2.5.0

```
