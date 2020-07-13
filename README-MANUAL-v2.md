# ADEasy手动集成文档(海外平台)-v2

### 更新日志

update ADEasy 1.1303-t10->2.1304-t03
```groovy
    implementation 'com.TJHello:ADEasy:2.1304-t03'
```
remove ads-identifier

- ~~implementation 'androidx.ads:ads-identifier:1.0.0-alpha04'~~

- ~~implementation 'com.google.guava:guava:28.0-android'~~

update Admob 19.0.1->19.2.0
```groovy
    implementation 'com.google.android.gms:play-services-ads:19.2.0'
```

update Facebook 5.8.0->5.9.1
```groovy
    implementation 'com.facebook.android:audience-network-sdk:5.9.1'
```

update Vungle 6.5.2->6.7.0
```groovy
    implementation 'com.vungle:publisher-sdk-android:6.7.0'
    implementation 'com.google.android.gms:play-services-basement:17.3.0'
    implementation 'com.google.android.gms:play-services-ads-identifier:17.0.0'
    implementation "androidx.localbroadcastmanager:localbroadcastmanager:1.0.0"
```

update Unity 3.4.2->3.4.6
```groovy
    implementation 'com.unity3d.ads:unity-ads:3.4.6'
```

### Step 1. 配置远程仓库地址build.gradle(project)

```groovy

allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://raw.githubusercontent.com/TJHello/publicLib/master" }
        maven { url "https://jitpack.io" }
        maven { url "https://dl.bintray.com/umsdk/release" }
    }
}

```

### Step 2. 配置build.gradle(app)


```groovy

dependencies {

    //必须接入
    implementation 'com.TJHello:ADEasy:2.1304-t03'
    
    //广告平台，按需接入
    
    //admob
    implementation 'com.google.android.gms:play-services-ads:19.2.0'
    
    //facebook
    implementation 'com.facebook.android:audience-network-sdk:5.9.1'
    
    //Vungle
    implementation 'com.vungle:publisher-sdk-android:6.7.0'
    implementation 'com.google.android.gms:play-services-basement:17.3.0'
    implementation 'com.google.android.gms:play-services-ads-identifier:17.0.0'
    implementation "androidx.localbroadcastmanager:localbroadcastmanager:1.0.0"
    
    //Unity
    implementation 'com.unity3d.ads:unity-ads:3.4.6'

    //工具，按需接入

    //Umeng
    implementation 'com.umeng.umsdk:analytics:8.1.6'
    implementation 'com.umeng.umsdk:common:2.2.5'
    
    //ABTest
    implementation 'com.TJHello:ABTest:0.9.36'
    
    //GoogleBilling
    implementation 'com.TJHello:GoogleBilling:1.2.2.13'
    
}
```

### Step 3. 配置manifests


```
<!-- 需要自主配置最基础的网络访问权限，以及Android 9版本以上的网络配置 -->    
<application>
    
    <!-- 如使用离线模式可删除 -->
    <meta-data 
            android:name="adEasyKey"
            android:value="xxxxxxxxxxxxxx"
            />
    <!-- 如使用离线模式可删除 -->
    <meta-data android:name="adEasyToken"
            android:value="xxxxxxxxxxxxxx"
            />    
    <!-- 关联广告debug模式，发布版本请设置为false -->
    <meta-data android:name="adEasyDebug"
            android:value="false"
            />
    <!-- 是否由ADEasy托管友盟 -->        
    <meta-data android:name="adEasyUmengSwitch"
            android:value="false"
            />
    <meta-data android:name="adEasyUmengKey"
            android:value="xxxxxxxxxx"
            />
    <!-- 默认可删除，1代表DEVICE_TYPE_PHONE，2代表DEVICE_TYPE_BOX -->
    <meta-data android:name="adEasyUmengDeviceType"
            android:value="1"
            />
    <!-- 没有则务必删除该项 -->
    <meta-data android:name="adEasyUmengPushSecret"
            android:value="xxxxx"
            />
    
    <!-- 广告配置开始，按需添加 -->
    
    <!-- admob -->
    <meta-data android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="xxxxxxxxxxxxxxxxxxxxxxxxxxx"
            />
    
    
    <!-- Vungle -->
    <activity
            android:name="com.vungle.warren.ui.VungleActivity"
            android:configChanges="keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize"
            android:launchMode="singleTop"
            android:theme="@android:style/Theme.NoTitleBar.Fullscreen" />    
    <activity
            android:name="com.vungle.warren.ui.VungleFlexViewActivity"
            android:configChanges="keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize"
            android:launchMode="singleTop"
            android:hardwareAccelerated="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />    
    <receiver
            android:name="com.vungle.warren.NetworkProviderReceiver"
            android:enabled="false">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
    </receiver>    
    
    
    
</application>
```

### Step 4. 代码示例-见项目首页([https://github.com/TJHello/ADEasy](https://github.com/TJHello/ADEasy))

### Step 5. 方法数超限处理

- 配置build.gradle(app)
```
android {
    defaultConfig {
        multiDexEnabled true
    }
}

dependencies {
    implementation 'androidx.multidex:multidex:2.0.1'
}
```
- 配置Application
```

override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

```

