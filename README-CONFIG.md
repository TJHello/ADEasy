# ADEasy-在线配置说明-v3

- adSwitch----------------------------------------------------------广告总开关
- ads---------------------------------------------------------------广告平台列表
   - appId---------------------------------------平台参数
   - appKey--------------------------------------平台参数
   - appToken------------------------------------平台参数
   - group---------------------------------------平台名称(见ADEasy->ADInfo.GROUP_XXX)
   - weight--------------------------------------平台权重
   - ctrl----------------------------------------控制体
      - whiteMap----------白名单列表
         - country----国家列表
         - model------手机型号列表
         - brand------手机品牌列表
         - channel----渠道列表
      - blackMap----------黑名单列表
         - country----国家列表
         - model------手机型号列表
         - brand------手机品牌列表
         - channel----渠道列表
      - appVer------------app版本范围，正数代表白名单，负数代表黑名单
      - androidVer--------Android版本范围，正数代表白名单，负数代表黑名单
   - placements----------------------------------广告位列表
      - code--------------广告位代号
      - type--------------广告位类型(video,ins,ins_video,banner,splash)
      - weight------------权重
      - ctrl--------------控制体(同上)
- parameterMap-------------------------------------------------------在线参数体
   - key1-----------------------------------------参数列表1(一个key有多个条件下的值)
      - ctrl--------------控制体(同上)
      - value-------------参数值
   - key2-----------------------------------------参数列表2(一个key有多个条件下的值)
      - ctrl--------------控制体(同上)
      - value-------------参数值
- insCtrl-------------------------------------------------------------插屏控制体
   - autoShow--------------------------------------自动显示插屏开关
   - intTime------------------------------------------显示了插屏后，N毫秒内不再显示插屏
   - offset----------------------------------------从第N关开始显示插屏
   - switch----------------------------------------显示插屏的开关
   - maxFill---------------------------------------单页面最大填充数
- videoCtrl-----------------------------------------------------------视频控制体
   - switch----------------------------------------视频开关
   - maxFill---------------------------------------单页面最大填充数
- bannerCtrl----------------------------------------------------------Banner控制体
   - switch----------------------------------------Banner开关
   - maxFill---------------------------------------单页面最大填充数
   - refreshTime-----------------------------------自动轮播间隔(秒)，小于等于0则关闭轮播
   - autoShow--------------------------------------是否自动展示
- splashCtrl----------------------------------------------------------Banner控制体
   - switch----------------------------------------Banner开关
   - maxWaitTime---------------------------------------最大等待时间
- nativeCtrl----------------------------------------------------------Native控制体
   - switch----------------------------------------Native开关
- nativeList----------------------------------------------------------Native广告列表
   - tag-------------------------------------------标签(必须)
   - weight----------------------------------------权重
   - parameterList---------------------------------广告位列表
      - code--------------广告位代码
      - type--------------广告位类型(native)
      - weight------------广告位权重
      - group-------------广告位平台   
- ctrl---------------------------------------------------------------控制体(同上)

## 示例

```
{
	"adSwitch": true,
	"ads": [
		{
			"appId": "xxxxx",
			"group": "mi",
			"weight": 1,
			"ctrl": {
				"whiteMap": {
					"country":["CN"],
					"brand":["Xiaomi"],
					"model":["Xiaomi 10"]
				},
				"blackMap": {
					"country":["JP"]
				},
				"appVer": [1,1],
				"androidVer": [-1,-1]
			},
			"placements": [
				{
					"code": "df680161af4ee6a79552cb330b0fcc8a",
					"type": "video",
					"weight": 1
				},
				{
					"code": "1e1f1a4432c4f3c7aa1f630a022d0485",
					"type": "ins",
					"weight": 1
				}
			]
		},
		{
			"group": "adMob",
			"weight": 1,
			"placements": [
				{
					"code": "ca-app-pub-3940256099942544/5224354917",
					"type": "video",
					"weight": 1
				},
				{
					"code": "ca-app-pub-3940256099942544/1033173712",
					"type": "ins",
					"weight": 1
				},
				{
					"code": "ca-app-pub-3940256099942544/6300978111",
					"type": "banner",
					"weight": 1
				}
			]
		}
	],
	"parameterMap": {
		"parameter1": [
			{
				"value": "在线参数1"
			}
		]
	},
    "nativeList": [
	    {
            "tag": "home",
            "weight": 1,
            "parameterList": [
                {
                    "code": "xxxxxxxx",
                    "type": "native",
                    "weight": 100,
                    "group": "byteDance"
                }
            ]
        }
    ]
}
```

### Group-平台代码对照表(区分大小写)

平台 | 代码
---|---
小米 | mi
穿山甲 | byteDance
Unity | unity
AdMob | adMob
腾讯优量汇 | gdt
Facebook | facebook
Vungle | vungle
VIVO | vivo
OPPO | oppo
百度 | baidu
Mintegral | mintegral
MintegralGp | mintegral_gp
oneWay | oneWay

### Type-广告类型代码对照表(区分大小写)
类型 | 代码
--- | ---
激励视频 | video
插屏 | ins
视频插屏 | ins_video
banner | banner
开屏 | splash
信息流 | native

### 控制体配置详细说明
```json
{
	"ctrl": {
		"whiteMap": {
			"country": ["CN"],
			"brand": ["Xiaomi"],
			"model": ["Xiaomi 10"],
            "channel":["GooglePlay"]
		},
		"blackMap": {
			"country": ["CN"],
			"brand": ["Xiaomi"],
			"model": ["Xiaomi 10"],
            "channel":["GooglePlay"]
		},
		"appVer": [1,1],
		"androidVer": [-1,-1]
	}
}

```

### API说明

- #### 全局静态方法
    1. 进入离线模式
    ```kotlin
          ADEasy.toOfflineMode()//需要在ADEasy.init()前调用
    ```
    2. 进入在线配置测试模式，进入该模式之后，程序获取的是后台"发布到测试"的在线配置，该模式在release下自动关闭。
    ```kotlin
          ADEasy.toTestMode()//需要在ADEasy.init()前调用
    ```
    3. 开关调试模式，该模式关联广告的debug模式，同时打开日志，release下自动关闭。
    ```kotlin
          ADEasy.setDebug(boolean)//需要在ADEasy.init()前调用
    ```
    4. 打开日志。
    ```kotlin
          ADEasy.openLog()//需要在ADEasy.init()前调用
    ```    
    5. 获取在线参数。需要在后台配置在线参数
    ```kotlin
          ADEasy.getOLParameter(String)
    ```
    6. 获取控制体管理器
    ```kotlin
          ADEasy.getConfigManager()//需要在onInitAfter里调用。
    ```
  


