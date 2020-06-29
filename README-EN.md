**中文** [https://github.com/TJHello/ADEasy/blob/master/README.md](https://github.com/TJHello/ADEasy/blob/master/README.md)

**English** [https://github.com/TJHello/ADEasy/blob/master/README-EN.md](https://github.com/TJHello/ADEasy/blob/master/README-EN.md)

# ADEasy-Automatic integration

---
A fast integration framework for a all advertising platform.

ADEasy used plugin automatic integration technology.
You only need to control the switch of each advertisement to complete the advertisement access.

In addition, ADEasy itself has enough advantages.
 Functionally, its weighting system can improve the revenue of aggregated ads. In usage, 
 it unifies the interface of all advertising platforms, 
 realizes a simple and unified calling method, 
 perfectly utilizes the characteristics of kotlin,
  and brings an extremely convenient and convenient user experience.

**Currently supported advertising platforms:**

adMob(banner,interstitial,video,interstitialVideo)

Unity(banner,interstitial,video,interstitialVideo)

Mi(banner,interstitial,video)

Yomob(interstitial,video,interstitialVideo)

GDT(interstitial,video,interstitialVideo)

Facebook(banner,interstitial,video)

ByteDance(banner,interstitial,video,interstitialVideo)

Vungle(banner,interstitial,video,interstitialVideo)

**Ad platforms that we plan to support next:**

Baidu

IronSource

### Steps for usage

- ### Step1 Add plugin -> build.gradle (project)


```groovy
buildscript {
     repositories {
        ...
         maven { url 'https://raw.githubusercontent.com/TJHello/publicLib/master'}
     }
      dependencies {
        ...
        classpath "com.TJHello.plugins:ADEasy:4.1.1303-t11"
      }
}

allprojects {
     repositories {
        ...
        maven { url 'https://raw.githubusercontent.com/TJHello/publicLib/master'}
     }
}

```
- ### Step2 Apply plugin -> build.gradle(app)


```groovy
....
apply plugin: 'ad-easy'

ADEasyExt{
    adSwitch = true  //Master switch
    debug = true //Test mode(Automatically set to false in relearse mode)
    inChina = false //much be
    adMobId = "ca-app-pub-755515620*****~*****61045" //adMob id
    adMob = true //admob
    adYomob = true //yomob
    adUnity = true //unity
    adVungle = false//Vungle
    adMi = true //mi
    adGdt = true //GDT
    adFacebook = true//Facebook
    adByteDance = false//ByteDance
    //abTest = true //ABTest switch https://github.com/TJHello/ABTest
    //exclude = ['xxxx'] //exclude package

}

android {
    ...
    defaultConfig {
        ...
        //Unable to execute dex: method ID not in [0, 0xffff]: 65536
        multiDexEnabled true
    }
    
    //Support java8
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    //Unable to execute dex: method ID not in [0, 0xffff]: 65536
    implementation 'com.android.support:multidex:1.0.3'
}

```

- ### Step3 Create Application（[TJApplication](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/TJApplication.kt)）

```kotlin
class TJApplication : Application(),ADEasyApplicationImp{

    override fun onCreate() {
        super.onCreate()
        ADEasy.setDebug(true)
        ADEasy.init(this,this)
        ADEasy.channel = ADChannel.Order
        ADEasy.toOfflineMode()//Offline Mode
        ADEasyLog.addFilterType(
            ADEasyLog.TYPE_HANDLER_BASE,
            ADEasyLog.TYPE_ADEASY_DETAILED_STEPS,
            ADEasyLog.TYPE_TOOLS_UMENG
        )
        ADEasy.init(this,this)
    }

    //Whether to remove ads
    override fun isRemoveAd(): Boolean {
        return false
    }

    //Create ad configuration
    override fun createADAppConfig(group: String): ADAppConfig? {
        when(group){
            ADInfo.GROUP_ADMOB->{
                return ADAppConfig.createAdmob()
                     .initWeight(10)
                     .addParameter("ca-app-pub-3940256099942544/6300978111",ADInfo.TYPE_BANNER)//Test ID
                     .addParameter("ca-app-pub-3940256099942544/1033173712",ADInfo.TYPE_INTERSTITIAL)
                     .addParameter("ca-app-pub-3940256099942544/5224354917",ADInfo.TYPE_VIDEO,10)//video1
                     .addParameter("ca-app-pub-394025609994***/**354917",ADInfo.TYPE_VIDEO,10)//video2
            }
        }
        return null
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //Unable to execute dex: method ID not in [0, 0xffff]: 65536
        MultiDex.install(base)
    }
}
```

- ### Step4 Create AppActivity（[AppActivity](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/AppActivity.kt)）


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

### Example([TestActivity](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/TestActivity.kt))

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

### ADEasy API description

```kotlin
boolean hasBanner() 
boolean hasInterstitial() 
boolean hasVideo()
boolean showBanner() 
boolean showInterstitial() 
boolean showInterstitialVideo()
boolean showVideo() 
boolean hideBanner() 
boolean hideInterstitial(Not Support)
```

### Other

If you need to refresh the weights, you can call the ADEasy.changeWeight method.

### AD SDK Version
x.2.xxxx
```
Yomob:1.8.7
MI:5.0.0
Unity:3.4.6
ByteDance:3.1.0.0
Admob:19.2.0
GDTSDK:4.232.1102
Vungle:6.7.0
Facebook:5.9.1
Baidu:5.86
```
x.1.xxxx
```
Yomob:1.8.7
MI:3.0.0
Unity:3.3.0
ByteDance:2.9.5.0
Admob:19.0.1
GDTSDK:4.190.1060
Vungle:6.5.2
Facebook:5.8.0
```
x.0.xxxx
```
Yomob:1.8.5
MI:2.5.0
Unity:3.3.0
ByteDance:2.8.0
Admob:18.3.0
GDTSDK:4.110.980
Vungle:6.5.2
Facebook:5.6.0
```

### Change log

1303-t10 date:2020-05-20(Valentine's Day)
```
1、Add ad loading thread scheduling function
2、Add online mode
3、Order
```


0.9.xxxx date:2020-04-09 
```
Abandoned
```