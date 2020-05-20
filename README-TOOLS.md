#ADEasy-Tools使用说明

### 目录
- UmengHelper(友盟封装-关联ABTest)
- GoogleBilling(谷歌内购封装)
- ABTest(基于友盟与firebase的ABTest工具)

### 使用说明

#### 1. UmengHelper-ADeasy内部集成，直接调用

```

//初始化(如让ADEasy托管,则不需要理会)
fun init(context: Context,umengId:String,channel:String,deviceType:Int,pushSecret:String?)

//计数事件
fun onEvent(context: Context,eventId : String)

//多参数事件-关联ABTest
fun onEvent(context:Context,eventId : String,map:MutableMap<String,String>)

//提交错误日志
fun reportError(context: Context, e: Throwable)

//提交错误日志
fun reportError(context: Context, error: String)

//每个Activity都需要调用(如让ADEasy托管,则不需要理会)
fun onPause(context: Context)

//每个Activity都需要调用(如让ADEasy托管,则不需要理会)
fun onResume(context: Context)

//退出应用的时候需要调用(如让ADEasy托管，则只需要调用adEasy.exitApp())
fun onKillProcess(context: Context)

```
#### 2. GoogleBilling-谷歌内购封装([gitee](https://gitee.com/tjbaobao/GoogleBilling),[github](https://github.com/TJHello/GoogleBilling))


#### 3. ABTest-基于友盟与Firebase的ABTest工具([github](https://github.com/TJHello/ABTest))
