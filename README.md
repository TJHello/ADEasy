**中文** [https://github.com/TJHello/ADEasy/blob/master/README.md](https://github.com/TJHello/ADEasy/blob/master/README.md)

**English** [https://github.com/TJHello/ADEasy/blob/master/README-EN.md](https://github.com/TJHello/ADEasy/blob/master/README-EN.md)

# ADEasy(测试版)-集成说明

**QQ交流群(425219113)**

---
一套全平台的广告聚合SDK快速集成框架。

ADEasy使用了插件自动集成技术，只需要简单控制各个平台的开关，就能实现各平台接入。并且对于未接入的广告，不会有冗余的代码。

当然，ADEasy本身也有足够的优点。

- 统一接口。化繁为简，化难为易。系统再复杂，但接口很简实，助你快速上手。
- 权重系统。可动态根据权重来分配广告位，有效提高收益。
- 线程调度系统。全局可控调整广告加载队列，避免一窝蜂加载，不仅有效提高性能，还避免了多余的加载数。
- 控制体系统。让你轻松实现对广告的极致控制。
- 分级日志系统。从简至繁，可选需要打印的日志，帮助你快速定位问题。


**目前支持的广告平台:**

adMob(banner,interstitial,video,interstitialVideo)

Unity(banner,interstitial,video,interstitialVideo)

Mi(banner,interstitial,video)

Yomob(interstitial,video,interstitialVideo)

GDT(腾讯优量汇)(banner2.0,插屏2.0,激励视频)

Facebook(banner,interstitial,video)

ByteDance(穿山甲)(banner,interstitial,video,interstitialVideo(全屏视频))

Vungle(banner,interstitial,video,interstitialVideo)

**下一步打算支持的广告平台:**

Baidu

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
        classpath "com.TJHello.plugins:ADEasy:3.1.1303-t10"
      }
}

allprojects {
     repositories {
        ...
        maven { url 'https://raw.githubusercontent.com/TJHello/publicLib/master'}
     }
}

```
- ### Step2 启动插件，配置参数到[build.gradle(app)](https://github.com/TJHello/ADEasy/blob/master/app/build.gradle)


```groovy
....
apply plugin: 'ad-easy'

ADEasyExt{
    adSwitch = true  //广告总开关
    inChina = true //必须-国内true-国外false
    debug = true //该开关关联广告debug开关，release版会自动设置为false
    //以下参数选择性填写，默认false
    //adMobId = "ca-app-pub-755515620*****~*****61045" //adMob的id,接入admob必填，并且更改成正确的id，否则admob会闪退。
    //adMob = true //admob开关
    adYomob = true //yomob开关
    adUnity = true //unity开关
    adMi = true //mi广告开关
    adGdt = true //腾讯优量汇(广点通)开关
    adFacebook = true//Facebook开关
    adByteDance = false//ByteDance(穿山甲)开关
    adVungle = false//Vungle开关
    //umeng = ['key'] //是否让ADEasy托管友盟,['key','deviceType(可选，默认1)','pushSecret(可选，默认null)']
    //abTest = true //ABTest开关 https://github.com/TJHello/ABTest
    //exclude = ['xxxx'] //例外掉某个包
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

- ### Step3 配置Application（[TJApplication.kt](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/TJApplication.kt)）（[TJApplication.java](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/java/JavaTJApplication.java)）

```kotlin
class TJApplication : Application(),ADEasyApplicationImp{

    override fun onCreate() {
        super.onCreate()
        ADEasy.setDebug(true)
        ADEasy.channel = ADChannel.Order//关联友盟渠道与某些广告平台的渠道
        ADEasy.toOfflineMode()//离线模式
        ADEasyLog.addFilterType(
            ADEasyLog.TYPE_HANDLER_BASE,
            ADEasyLog.TYPE_ADEASY_DETAILED_STEPS,
            ADEasyLog.TYPE_TOOLS_UMENG
        )
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

- ### Step4 配置AppActivity（[AppActivity.kt](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/AppActivity.kt)）（[AppActivity.java](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/java/JavaAppActivity.java)）


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

### 示例([TestActivity.kt](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/TestActivity.kt))([TestActivity.java](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjbaobao/utils/demo/adeasy/java/JavaTestActivity.java))

```kotlin

class TestActivity : AppActivity() {

    override fun onInitValue(savedInstanceState: Bundle?) {
        adEasy.isAutoShowBanner(true)
    }

    override fun onInitView() {
        setContentView(R.layout.test_activity_layout)
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

    //退出应用时请调用 ADEasy.exitApp(context)

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
boolean hideBanner() //隐藏banner

void hangLifeUp() //挂起生命周期,用于弹起隐私协议等场景,需要在adEasy.onCreate前调用
void hangLifeDown() //放下挂起的生命周期，继续执行
void notShowInterstitialOnce() //忽略一次插屏显示请求，用于第一次进入首页不要显示插屏的场景,需要在adEasy.onCreate前调用
void isAutoShowBanner() //当前页面是否自动显示Banner,需要在adEasy.onCreate前调用
void isAutoShowInterstitial()//当前页面是否自动显示插屏,需要在adEasy.onCreate前调用
void closeAD() //关闭当前页面的广告功能，需要在adEasy.,需要在adEasy.onCreate前调用

```

### 其他

加QQ群咨询(425219113)

### ADEasy版本说明
```
例:1.0.1001-a01

1:自动化插件版本号[0-99]

0:广告SDK版本更新号[0-999]

1001->主程序版本号

1:框架性修改[0-9]

0:功能性修改[0-99]

01:小修改，bug修复[0-99]

a01->测试版本号

a:alpha测试(可以尝试性使用)

b:bate测试(经过了初步真实环境验证)

t:内部测试

```



### SDK版本对应

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



### 主程序更新日志

1303-t10 更新时间2020-05-20(情人节)
```
1、新增广告加载线程调度功能
2、新增在线配置功能
3、新增日志打印分级分类功能
4、新增自动化托管友盟的功能
5、新增支持ABTest的功能
6、修复若干bug，大量优化
```


0.9.xxxx 更新时间:2020-04-09 
```
该命名方式已经遗弃，不再维护
```


