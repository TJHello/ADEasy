# ADEasy使用文档-自动集成说明

---
一套可以让你快速集成各种广告平台的聚合框架。

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
    adMobId = "ca-app-pub-7555156208216777~9662161045" //adMob的id,接入admob必填
    adMob = true //admob开关
    adYomob = true //yomob开关
    adUnity = true //unity开关
    adMi = true //mi广告开关
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


```
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

### adEasy API说明

```kotlin
boolean hasBanner() //是否有banner
boolean hasInterstitial() //是否有插屏广告
boolean hasVideo() //是否有激励视频
boolean showBanner() //显示banner
boolean showInterstitial() //显示插屏
boolean showInterstitialVideo() //显示插屏视频
boolean showVideo() //显示激励视频
```

### 简单说明

该框架会自动按照权重分配广告，使用者只需要在Application里面配置好相关的广告信息就行了。如果需要动态刷新权重，可以调用ADEasy.changeWeight方法，group在ADInfo里面。



