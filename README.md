**中文** [https://github.com/TJHello/ADEasy/blob/master/README.md](https://github.com/TJHello/ADEasy/blob/master/README.md)

**English** [https://github.com/TJHello/ADEasy/blob/master/README-EN.md](https://github.com/TJHello/ADEasy/blob/master/README-EN.md)

**手动集成文档(海外平台)** [https://github.com/TJHello/ADEasy/blob/master/README-MANUAL-v2.md](https://github.com/TJHello/ADEasy/blob/master/README-MANUAL-v2.md)

**其他工具说明** [https://github.com/TJHello/ADEasy/blob/master/README-TOOLS.md](https://github.com/TJHello/ADEasy/blob/master/README-TOOLS.md)

# ADEasy-自动集成说明

**QQ交流群(425219113)**

使用该程序之前，请仔细阅读：[《免责声明与许可协议》](https://github.com/TJHello/ADEasy/blob/master/LICENSE.md)

---
一套全平台的广告聚合SDK快速集成框架。

**简介：**

- 自动化集成技术，只需要简单控制平台开关就能实现广告接入。并且对于未接入的广告，不会有冗余的代码。
- 更直观更简洁更高效的对外接口。
- 可动态根据权重来分配广告位，有效提高收益。
- 全局可控广告加载队列，有效提高性能，广告加载数可控。
- 可精确到某个机型、国家、系统、渠道来控制广告。
- 可选日志输出，精确定位问题，掌控流程。


**目前支持的广告平台:**

adMob(banner,interstitial,video,interstitialVideo)

Unity(banner,interstitial,video,interstitialVideo)

Mi(banner,interstitial,video)

GDT(腾讯优量汇2.0)(banner,interstitialVideo,video,splash)

Facebook(banner,interstitial,video)

ByteDance(穿山甲)(banner,interstitial,video,interstitialVideo(全屏视频),splash)

Vungle(banner,interstitial,video,interstitialVideo)

Baidu(banner,interstitial,video,interstitialVideo,splash)

Oppo(banner,interstitial,video,interstitialVideo,splash)

Vivo(banner,interstitial,video,splash)

Mintegral(banner,interstitial,video,interstitialVideo,splash)

Mintegral-GP(banner,interstitial,video,interstitialVideo,splash)

**下一步打算支持的广告平台:**

IronSource

### 使用环境


```

- Android Studio 3.6以上（建议环境，其余需要进一步测试）

- AndroidX(必须)

- Java/Kotlin

- JAVA 1.8

```


### 使用步骤

- ### Step1 接入自动集成插件到build.gradle(project)


```groovy
buildscript {
     repositories {
        maven { url 'http://maven.tjhello.com/publicLib'}
     }
      dependencies {
        classpath "com.TJHello.plugins:ADEasy:5.3.2001-t34"
      }
}

allprojects {
     repositories {
        maven { url 'http://maven.tjhello.com/publicLib'}
     }
}

```
- ### Step2 启动插件，配置参数到[build.gradle(app)](https://github.com/TJHello/ADEasy/blob/master/app/build.gradle)


```groovy
apply plugin: 'ad-easy'

ADEasyExt{
    adSwitch = true  //广告总开关
    inChina = true //必须-国内true-国外false
    debug = true //该开关关联广告debug开关，release版会自动设置为false
    //以下参数选择性填写，默认false
    //adMobId = "ca-app-pub-755515620*****~*****61045" //adMob的id,接入admob必填，并且更改成正确的id，否则admob会闪退。
    //adMob = true //admob开关
    adUnity = true //unity开关
    adMi = true //mi广告开关
    adGdt = true //腾讯优量汇(广点通)开关
    adFacebook = true//Facebook开关
    adByteDance = false//ByteDance(穿山甲)开关
    adVungle = false//Vungle开关
    adOppo = false//支持oppo联运情景
    adVivo = false//支持vivo联运情景
    adMintegral = false//Mintegral-中国
    adMintegralGp = false//Mintegral-海外
    //umeng = ['key'] //是否让ADEasy托管友盟,['key','deviceType(可选，默认1)','pushSecret(可选，默认null)']
    //abTest = true //ABTest开关 https://github.com/TJHello/ABTest
    //exclude = ['xxxx'] //例外掉某个包
}

android {
    defaultConfig {
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

- ### Step3 配置Application（[TJApplication.kt](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjhello/demo/adeasy/TJApplication.kt)）（[TJApplication.java](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjhello/demo/adeasy/java/JavaTJApplication.java)）

```kotlin
class TJApplication : Application(),ADEasyApplicationImp{

    override fun onCreate() {
        super.onCreate()
        ADEasy.setDebug(true)//关联广告平台debug模式，release版会自动设置为false
        ADEasy.channel = ADChannel.Order//关联友盟渠道与某些广告平台的渠道
        ADEasy.toOfflineMode()//离线模式
        ADEasyLog.addFilterType(//添加日志(默认显示基础日志)
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


    //创建广告配置(必须，离线模式或者获取在线配置失败时用到)
    override fun createAdPlatformConfig(group: String): PlatformConfig? {
        when(group){
            ADInfo.GROUP_ADMOB->{
                return AdConfig.createAdmob()
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

- ### Step4 配置AppActivity（[AppActivity.kt](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjhello/demo/adeasy/AppActivity.kt)）（[AppActivity.java](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjhello/demo/adeasy/java/JavaAppActivity.java)）


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

    override fun onPause() {
        super.onPause()
        adEasy.onPause()
    }

    override fun onResume() {
        super.onResume()
        adEasy.onResume()
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

### 示例([TestActivity.kt](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjhello/demo/adeasy/TestActivity.kt))([TestActivity.java](https://github.com/TJHello/ADEasy/blob/master/app/src/main/java/com/tjhello/demo/adeasy/java/JavaTestActivity.java))

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
boolean showSplash()//显示开屏

void hangLifeUp() //挂起生命周期,用于弹起隐私协议等场景,需要在adEasy.onCreate前调用
void hangLifeDown() //放下挂起的生命周期，继续执行
void notShowInterstitialOnce() //忽略一次插屏显示请求，用于第一次进入首页不要显示插屏的场景,需要在adEasy.onCreate前调用
void isAutoShowBanner() //当前页面是否自动显示Banner,需要在adEasy.onCreate前调用
void isAutoShowInterstitial()//当前页面是否自动显示插屏,需要在adEasy.onCreate前调用
void closeAD() //关闭当前页面的广告功能，需要在adEasy.onCreate前调用

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

a:alpha测试(经过了内部项目真实环境验证)

b:bate测试(经过了客户真实环境验证)

t:内部测试

```

### SDK版本对应
x.3.xxxx
```
~~Yomob(已删除)~~
MI:5.0.3
Unity:3.4.8(修复Android11崩溃的问题)
ByteDance:3.2.5.1
Admob:19.3.0
GDTSDK:4.251.1121(修复Android11崩溃问题)
Vungle:6.7.0
Facebook:5.9.1
Baidu:5.86
Vivo:4.2.0.0
Oppo:3.5.1
Mintegral:14.4.41
```


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

### 自动化插件更新日志
v5
```
1、重构整个模块，改为纯Kotlin实现。
2、即将支持多渠道（开发中）
3、新增oppo、vivo、mintegral平台。
```
v4
```
1、修复某些情况下，热修改manifests时，删除条目不生效的问题。
2、修复某些情况下，热修改平台开关，在添加manifests的地方没有动态改变的问题。
3、新增支持百度广告自动化接入。
```
v3
```
1、增加对友盟、ABTest，inChina，exclude以及appkey、appToken的支持
```

### 主程序更新日志

5.3.2001-t34
```
1、重构代码，将广告平台的逻辑完全分离开来到单独模块。解决了ov联运包检测不通过的问题。
2、新增oppo、vivo、mintegral平台。
3、开放本地控制广告，修改控制体的api。eg:ADEasy.getConfigManager()
4、修复和优化大量问题，系统整体更加稳定了。
```

4.2.1306-t05 更新时间2020-07-31
```
1、支持GDT、Bytedance、Baidu的开屏广告
2、支持在代码中修改广告控制配置的参数
3、支持在线功能的测试模式
4、其他的优化
```

4.2.1304-t03 更新时间2020-06-29
```
1、全平台SDK升级，新增支持Baidu广告。
2、修复使用在线配置时，可能会报错找不到类的问题。
3、精简与整理代码，优化广告按权加载的整体逻辑。
4、修复Yomob广告回调的问题
5、修复手机品牌判断不准确的问题
```

4.1.1303-t11 更新时间2020-06-25
```
1、小bug修复，以及一些细微调整
```

3.1.1303-t10 更新时间2020-05-20(情人节)
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


