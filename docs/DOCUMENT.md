# NCG Android海外游戏1.1版本SDK 对接文档 2024/08/28

## 1.相关依赖引入
在工程级别的build.gradle 的android内加入以下代码
注：从2024年8月31日起, 在Google市场上架应用时必须targetSdkVersion>=34, 2024年8月31日起, 在Google市场更新应用时必须targetSdkVersion>=34, 详细[适配文档]()


v1.1.0 更新:
从1.0.1版本迁移时请注意下列更新操作

1.更新NCGsdk_1.1.0.aar SDK文件

2.更新targetSdkVersion为34, 请注意适配其他代码

2.1更新AndroidManifest.xml文件内修改namespace代码

2.2更新App级别的gradle文件，对ToDo 内容中的 1.1.0版本新增或修改 代码进行修改替换

2.3更新项目级别的gradle文件和gradle-wrapper.properties文件，符合更新targetSdkVersion为34所对应的编辑器和gradle插件版本
```
    namespace 'com.immortals.tw'
    compileSdkVersion 34
    defaultConfig {
        applicationId "com.wuxia001.discount.coupon.free.sale.code.cheap.gp"
        minSdkVersion 21
        targetSdkVersion 34
        versionCode 1
        versionName "1.0.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled true
    }
    
    sourceSets {
        main {
            assets.srcDirs = ['../assets', 'src/main/assets', 'src/main/assets/']
            jniLibs.srcDirs = ['libs']
        }
    }
    
    //指定jdk版本
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
```

引入以下依赖：

```
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation(name: 'cafeSdk-4.4.1', ext: 'aar')
    implementation(name: 'NCGsdk_1.1.0', ext: 'aar')
    //谷歌需要依赖
//    implementation 'com.google.android.play:core:1.10.3'
    implementation 'com.google.android.play:review:2.0.1'//todo 1.1.0版本新增
    implementation 'com.google.gms:google-services:4.3.15'
    implementation 'com.google.android.gms:play-services-analytics:18.0.4'
    implementation 'com.google.android.gms:play-services-auth:20.7.0'

    //谷歌支付SDK 请勿单独升级
    implementation 'com.android.billingclient:billing:6.0.1'

    //FB依赖
    implementation 'com.facebook.android:facebook-android-sdk:latest.release'
    api 'com.facebook.android:audience-network-sdk:6.16.0'

    //广告SDK
    implementation 'com.google.android.gms:play-services-ads:23.3.0'//todo 1.1.0版本修改
    implementation 'com.google.ads.mediation:applovin:12.6.0.0'//todo 1.1.0版本修改
    implementation 'com.google.ads.mediation:facebook:6.16.0.0'

    // firebase相关SDK
    implementation platform('com.google.firebase:firebase-bom:33.1.2')//todo 1.1.0版本修改
    implementation 'com.google.firebase:firebase-crashlytics'
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.firebase:firebase-messaging'
    implementation 'com.google.firebase:firebase-perf'
    implementation 'com.google.firebase:firebase-dynamic-links'

    // 基础类依赖
    api 'androidx.appcompat:appcompat:1.1.0'
    api 'androidx.annotation:annotation:1.1.0'
    api 'androidx.vectordrawable:vectordrawable-animated:1.1.0'
    api 'androidx.legacy:legacy-support-v4:1.0.0'
    api 'androidx.core:core:1.2.0'
    api 'android.arch.work:work-runtime:1.0.1'
    api 'com.google.guava:guava:28.0-jre'
    api 'androidx.constraintlayout:constraintlayout:1.1.3'
    api 'androidx.multidex:multidex:2.0.1'
    api 'androidx.recyclerview:recyclerview:1.1.0'
    api 'com.alibaba:fastjson:1.1.70.android'
    implementation 'org.apache.httpcomponents:httpcore:4.4.10'
    implementation 'com.braintreepayments.api:braintree:2.22.0'
    implementation 'com.squareup.okhttp3:okhttp:4.8.0'
    //推特登陆和分享相关
    implementation 'com.twitter.sdk.android:twitter-core:3.1.1'
    implementation 'com.twitter.sdk.android:tweet-ui:3.1.1'
    implementation 'com.twitter.sdk.android:tweet-composer:3.1.1'
    //line
    api 'com.linecorp:linesdk:5.0.1'
    //aihelp
    implementation 'net.aihelp:android-aihelp-aar:4.2.+'
```

在工程级别的build.gradle 文件内增加以下插件

```
    apply plugin: 'com.google.gms.google-services'
    apply plugin: 'com.google.firebase.crashlytics'
    apply plugin: 'com.google.firebase.firebase-perf'
```

在项目级别的build.gradle 文件内增加以下内容

```
buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            url 'https://maven.aliyun.com/repository/public/'
        }
        maven {
            url 'https://maven.aliyun.com/repository/jcenter/'
        }
        maven {
            url 'https://maven.google.com/'
            name 'Google'
        }
        maven { url 'https://maven.google.com' }
        maven {
            url "https://android-sdk.is.com"
        }

    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.1.0'
        classpath 'com.google.gms:google-services:4.3.15'
        classpath 'com.google.firebase:firebase-crashlytics-gradle:2.9.9'
        classpath 'com.google.firebase:perf-plugin:1.4.2'
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url 'https://maven.aliyun.com/repository/public/'
        }
        maven {
            url 'https://maven.aliyun.com/repository/jcenter/'
        }
        flatDir {
            dirs 'libs'
        }
        maven {
            url 'https://maven.google.com/'
            name 'Google'
        }
        maven {
            url 'https://android-sdk.is.com/'
        }
    }
}
```

## 2.相关资源引入

### 创建assets文件夹。拷贝资源内的NCGConfig.xml

1）请修改ncgsdk标签内的appId参数为运营提供的游戏id，appReleaseId为提供的发布记录id(必须)。
2) 出正式包时请修改host标签内的url链接为正式服链接，SDK采用每个项目独立的域名的形式，具体项目使用域名会由运营提供(必须)
3）Google标签内的clientId，为运营提供的谷歌ClientID；billing为Google支付秘钥(必须)。

### 拷贝运营提供的google-services.json文件

拷贝运营提供的google-services.json文件到工程级别的主目录。如果是android studio开发，拷贝到app目录下即可
**请注意，demo中没有添加google-services.json，漏加会影响项目编译**

### 添加libs下相关aar依赖

请添加并引入libs内的全部aar依赖

### 清单文件内容添加

添加Demo内的清单文件内容到游戏Manifest内，并修改To-do标签内的相关value，具体value值运营会提供，FacebookContentProvider下{facebook_app_id}替换为facebook_app_id参数，并去掉括号；AdMob应用ID下的{AdMob_cpkey}替换为Google Admob cpkey并去掉括号

```
    <!--todo  facebook参数-->
    <meta-data
        android:name="com.facebook.sdk.ApplicationId"
        android:value="@string/facebook_app_id" />
    <meta-data
        android:name="com.facebook.sdk.ClientToken"
        android:value="@string/facebook_client_token"/>
    <provider
        android:name="com.facebook.FacebookContentProvider"
        android:authorities="com.facebook.app.FacebookContentProvider{facebook_app_id}"
        android:exported="true" />
    <activity
        android:name="com.facebook.CustomTabActivity"
        android:exported="true">
    </activity>

    <receiver
        android:name="com.appsflyer.SingleInstallBroadcastReceiver"
        android:exported="true">
        <intent-filter>
            <action android:name="com.android.vending.INSTALL_REFERRER" />
        </intent-filter>
    </receiver>

    <!--todo  广告参数 AdMob应用ID-->
    <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="{AdMob_cpkey}" />
```

在application节点内添加：

```
android:networkSecurityConfig="@xml/network_security_config"
```

并在xml下创建network_securitdefalutConfigy_config.xml文件，xml内添加

```
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">127.0.0.1</domain>
    </domain-config>
</network-security-config>
```

在启动activity下添加：

```
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="@string/fb_login_protocol_scheme" />
    </intent-filter>
```

### 资源文件添加

在项目的values文件夹内，添加如下的strings：

```
<string name="facebook_app_id">facebook_app_id</string>
<string name="fb_login_protocol_scheme">fbfacebook_app_id</string>
<string name="facebook_client_token">fb_client_token</string>
```

**请注意，请将facebook_app_id，替换为运营提供的id，fb_login_protocol_scheme中须保留fb开头, facebook_client_token替换为运营提供的token**

## 3.SDK方法文档

### 3.1Application内的初始化(必接)


在Application的onCreate方法中实现以下：

```
NCGSDK.initApplication(this);
```

在Application的attachBaseContext方法中实现以下：

```
MultiDex.install(this);
```

### 3.2MainActivity内的初始化(必接)

SDK使用统一的Callback，在MainActivity(游戏主Activity)的onCreate方法内实现以下(监听回调根据所需添加)：

```
NCGSDK.setCallBack(new NCGCallback() {
    @Override
    public void onCallBack(final Message msg) {
        switch (msg.what) {
            case NCGActionCode.ACTION_INIT_SUCC://初始化成功

                break;
            case NCGActionCode.ACTION_INIT_FAILED://初始化失败

                break;
            case NCGActionCode.ACTION_LOGIN_SUCC://登录成功，返回User

                break;
            case NCGActionCode.ACTION_LOGIN_CANCEL://退出登录

                break;
            case NCGActionCode.ACTION_LOGIN_FAILED://登录失败

                break;
            case NCGActionCode.ACTION_LOGOUT_SUCC://登出成功

                break;
            case NCGActionCode.ACTION_GAME_EXIT://退出游戏
         
                break;
            case NCGActionCode.ACTION_LOGOUT_FAILED://登出失败，一般不会出现，出现代表有问题

                break;
            case NCGActionCode.ACTION_PAY_SUCC://支付成功，前端回调只做辅助界面展示使用，支付成功回调请以后端为准进行发货

                break;
            case NCGActionCode.ACTION_PAY_CANCEL://用户退出支付

                break;
            case NCGActionCode.ACTION_PAY_FAILED://支付失败

                break;
            case NCGActionCode.ACTION_ADSHOW_SUCCESS://广告播放完成

                break;
            case NCGActionCode.ACTION_ADSHOW_FAILED:// 广告播放失败

                break;
            case NCGActionCode.ACTION_ADSHOW_BANED:// 用户所在地区不支持播放广告

                break;
            case NCGActionCode.ACTION_SHARE_SUCCESS://分享成功

                break;
            case NCGActionCode.ACTION_SHARE_FAILED://分享失败

                break;
            case NCGActionCode.ACTION_SHARE_CANCEL://分享取消

                break;
            case NCGActionCode.ACTION_QUERY_NOTBIND://未绑定

                break;
            case NCGActionCode.ACTION_QUERY_BIND_FAILED://查询绑定失败

                break;
            case NCGActionCode.ACTION_QUERY_ISBIND://已绑定

                break;
            case NCGActionCode.ACTION_BIND_SUCCESS://绑定成功

                break;
            case NCGActionCode.ACTION_BIND_FAILED://绑定失败

                break;  
            default:
                break;
        }
    }
});
NCGSDK.initMainActivity(MainActivity.this);
```

### 3.3发起登录(必接)

当游戏收到初始化成功后，可发起登录
接口定义：

```
NCGSDK.doLogin();
```

登录成功或者失败，都可以在回调中得到结果。返回token和平台唯一uid，示例:

```
JSONObject loginResult = new JSONObject(String.valueOf(msg.obj));
String token = loginResult.getString("token");
String uid =loginResult.getString("uid");
```

请使用token调用后端接口获得用户标识，收到回调的token，建议通过服务端验证token，获取用户id，详情查看服务端文档

### 3.4发起支付(必接)

当游戏内需发起支付时，应调用此接口
接口定义：

```
NCGSDK.doPay(Map<String, String> payJson)
```

**payJson参数**

| 字段           | 类型     | 说明                                                                     |
| ------------ | ------ |------------------------------------------------------------------------|
| productId    | string | 商品ID                                                                   |
| productName  | string | 商品名称，会显示在相应支付界面上                                                       |
| productPrice | string | 商品价格, 价格必须传美金单位                                                        |
| extra        | string | 订单透传参数，这些参数会在支付回调时一并回传给CP，请CP自行解析（透传参数每笔订单需要变化，否则无法重复拉起支付）             |
| roleId       | string | 待支付角色ID                                                                |
| roleName     | string | 待支付角色名                                                                 |
| serverId     | string | 待支付角色区服ID                                                              |
| serverName   | string | 待支付角色区服名称                                                              |
| notifyUrl    | string | 支付通知地址，对接支付时请将通知地址告知我方运营进行配置，我方后台配置后通知地址后将以后台配置为准这里无需传入，如果这里传入则以传入地址为准 |

**请注意，productName、productId需按照计费表内数据传入，否则不会创建订单**

调用示例：

```
Map<String, String> payinfo = new HashMap<>();
payinfo.put("productName", "1001-60元寶");
payinfo.put("productPrice", "0.99");
payinfo.put("productId", "1001");
payinfo.put("roleId", "1");
payinfo.put("roleName", "1");
payinfo.put("serverId", "1");
payinfo.put("serverName", "1");
payinfo.put("notifyUrl", "");
payinfo.put("extra", System.currentTimeMillis() / 1000 + "");
NCGSDK.doPay(payJson);
```

### 3.5角色变更接口(必接)

当游戏内角色状态变化时，应调用此接口
接口定义：

```
NCGSDK.doSpot(String spotJson)
```

| 字段       | 类型     | 说明                                                                                                                                                                                                                                                             |
| -------- | ------ |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| spotType | string | 事件类型,取值为:1:创建角色 2:完成新手引导 3:玩家等级变化后上传（升级） 4:玩家选择完区服进入游戏                                                                                                                                                                                                         |
| extra    | json   | 这是角色具体信息,格式为Json,包括信息:roleId:角色ID(必传),roleName:角色名(必传),roleServer:区服ID(必传),serverName:区服名字(必传),roleLevel:角色等级(必传),vipLevel:角色Vip等级(无vip系统可传0),zone:大区ID(游戏有大区分区例如东西区,传入大区id,游戏没有此设定可不传),zoneName:大区名(同大区id),globalRoleId:全局唯一的角色ID(游戏唯一角色id,如果角色id是唯一的可传如角色id) |

**请注意，玩家选择完区服上报进入游戏 （spotType为4）必须接入，否则会影响SDK功能例如个人中心账号列表里将无法看到历史账号，其余上报不接入会影响打点数据准确性**
调用示例：

```
JSONObject spotJson = new JSONObject();
try {
    spotJson.put("spotType","3");
    JSONObject extra = new JSONObject();
    extra.put("roleName","等级回传");
    extra.put("roleLevel","11");
    extra.put("roleId","111");
    extra.put("roleServer","22");
    extra.put("serverName","1服");
    extra.put("vipLevel","1");
    extra.put("zone","1");
    extra.put("zoneName","东区");
    extra.put("globalRoleId","ncg_70_10691603");
    spotJson.put("extra",extra);
} catch (JSONException e) {
    e.printStackTrace();
}
NCGSDK.doSpot(spotJson.toString())
```

### 3.6生命周期接入(必接)

需要在游戏主Activity内重写以下生命周期方法并接入

```
//重写的方法为
onResume();
onPause();
onStop();
onActivityResult(int requestCode, int resultCode, Intent data);
onDestroy();
```

```
//接入的方法为
NCGSDK.onResume();
NCGSDK.onPause();
NCGSDK.onStop();
NCGSDK.onActivityResult(int requestCode, int resultCode, Intent data);
NCGSDK.onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
NCGSDK.onDestroy();
```

如果游戏没有对返回键进行处理，请重写以下方法

```
onBackPressed();
```

接入的方法为

```
NCGSDK.onBackPressed();
```

### 3.7发起分享接口（需要集成分享功能时接入）

SDK分享接口有三种选择，可从NCG后台分享，或直接传入链接或图片进行分享。

#### 3.7.1 NCG后台分享

当游戏需要拉起分享的时候，想从NCG后台读取内容分享时，应调用此接口
接口定义：

```
NCGSDK.share(String shareInfo);
```

**shareInfo 示例**

| 字段        | 类型     | 说明              |
| --------- | ------ | --------------- |
| shareID   | int    | 分享内容Id(运营提供)    |
| shareName | string | 分享内容Name(运营提供)  |
| uName     | string | 分享者游戏名          |
| server    | string | 分享者所在区服         |
| code      | string | 邀请码(可供接受分享者使用等) |

调用示例：

```
JSONObject shareinfo = new JSONObject();
try {
    shareinfo.put("shareID", "1");
    shareinfo.put("shareName", "分享");
    shareinfo.put("uName", "11");
    shareinfo.put("server", "2");
    shareinfo.put("code", "3");
} catch (JSONException e) {
    e.printStackTrace();
}
NCGSDK.doShare(shareinfo.toString());
```

#### 3.7.2 直接分享（链接形式）

当游戏需要拉起分享的时候，想直接分享链接时，应调用此接口，现阶段此接口支持分享到Facebook和Twitter
接口定义：

```
NCGSDK.doCPShareLink(String title, String content, String link);
```

** 传入参数示例**

| 字段        | 类型     | 说明              |
| --------- | ------ | --------------- |
| title | string | 分享标题  |
| content     | string | 分享内容          |
| link    | string | 分享链接         |


#### 3.7.3 直接分享（网络图片形式）

当游戏需要拉起分享的时候，想直接分享图片时，应调用此接口，现阶段此接口支持分享到Facebook
接口定义：

```
NCGSDK.doCPShareImage(String title, String content, String imageUrl);
```

** 传入参数示例**

| 字段        | 类型     | 说明              |
| --------- | ------ | --------------- |
| title | string | 分享标题  |
| content     | string | 分享内容          |
| imageUrl    | string | 分享图片url         |

#### 3.7.4 直接分享（本地图片形式）

当游戏需要拉起分享的时候，想直接分享手机本地图片时，应调用此接口，请注意本地分享只能分享包名路径下的地址，例如/data/data/包名/files/Screenshot/Share.png
接口定义：

```
NCGSDK.doCPShareLocalImage(String title, String content, String imageUrl);
```

** 传入参数示例**

| 字段        | 类型     | 说明              |
| --------- | ------ | --------------- |
| title | string | 分享标题  |
| content     | string | 分享内容          |
| photoUrl    | string | 分享图片本地地址        |

#### 3.7.5 单独渠道直接分享（本地图片形式）

当游戏需要直接拉起某一分享渠道进行分享的时候，应调用此接口，请注意本地分享只能分享包名路径下的地址，例如/data/data/包名/files/Screenshot/Share.png
接口定义：

```
NCGSDK.doShareImage(int type, String imageUrl, String link, String text, String tag);
```

** 传入参数示例**

| 字段        | 类型     | 说明              |
| --------- | ------ | --------------- |
| type | int | 分享渠道  | type = 1为facebook 2为ins 3为twitter 4为line  |
| imageUrl    | string | 分享图片本地地址        |
| link     | string | 分享携带的链接          |
| text    | string | 分享携带的文本       |
| tag    | string | 分享携带的话题标签       |



### 3.8调起广告接口（需要激励视频功能时对接）

当游戏需要拉起广告的时候，应调用此接口，播放广告时若播放失败，SDK会回调ACTION_ADSHOW_BANED或ACTION_ADSHOW_FAILED；播放完成时会回调ACTION_ADSHOW_SUCCESS
接口定义：

```
ADSDK.getInstance().doShowAD(String extra);
```

| 字段名称   | 类型     | 属性                         |
| ------ | ------ | -------------------------- |
| adType | int    | 广告形式(目前仅支持激励视频，请直接传int型13) |
| extra  | String | 广告透传参数，在成功的回调内，会原样返回       |

调用示例：

```
String extra = "{"adType":"13","info":"infos"}";
ADSDK.getInstance().doShowAD(extra);
```

### 3.9游戏绑定账号接口（需要使用绑定入口时接入）

游戏内需提供显示绑定账号页面的入口，点击入口时调用此接口
接口定义：

```
NCGSDK.showBind();
```

调用示例：

```
NCGSDK.showBind();
```

### 3.10查询绑定账号接口（需要查询用户绑定状态时接入）

游戏内需查询当前账号绑定状态时时调用此接口
接口定义：

```
NCGSDK.doQueryBind();
```

调用示例：

```
NCGSDK.doQueryBind();
```

调用后会给游戏对应的回调

### 3.11查询当前游戏需要展示给用户查看的货币（需要做支付界面货币本地化展示时接入）

多语言环境下，当游戏需要查询当前游戏需要展示给用户查看的货币时调用次接口查询
接口定义：

```
NCGSDK.getPurchaseList(GlobalCallback callback);
```

调用示例：

```
NCGSDK.getPurchaseList(new GlobalCallback() {
    @Override
    public void onSuccess(String o) {
        SDKLog.d(TAG, "doPurchaseListDone=" + o);
    }   

    @Override
    public void onFailed(String msg) {
        SDKLog.d(TAG, "doPurchaseListDone=" + msg);
    }
});
```

| 字段           | 类型      | 说明                                                                         |
| ------------ | ------- | -------------------------------------------------------------------------- |
| status       | boolean | true : 查询成功    false:  查询失败                                                |
| purchaselist | string  | 商品信息实体beanlist                                                             |
| errorno      | string  | 仅在status 为false 的时候返回，    0：查询失败，游戏可以再次拉起查询，请不要一直尝试。  -1: 因为某些特殊原因，请显示默认货币 |

purchaselist 示例:

```
String purchaselist = "{"1001":"$0.99","1002":"$4.99"}";
```

| 字段        | 类型     | 说明                      |
| --------- | ------ | ----------------------- |
| productId | string | 商品id                    |
| price     | string | 商品当前价格 带货币符号   ￥6/$0.99 |

## 4.SDK方法选接文档

以下接口为选接接口，按需求接入

### 4.1游戏内行为打点

当游戏内发生相关行为后，应调用此接口，如需接入运营会提供打点表进行接入
接口定义：

```
NCGSDK.doEventInfo(String eventInfo);
```

| 字段        | 类型     | 说明             |
| --------- | ------ | -------------- |
| eventInfo | string | 行为事件名(该值由运营提供) |

调用示例：

```
NCGSDK.doEventInfo(eventInfo);
```

### 4.2将文字存入剪贴板

当游戏需要将文字存入剪贴板时，可以调用此方法
接口定义：

```
NCGSDK.doSetPasteboard(String extra);
```

| 字段    | 类型     | 说明         |
| ----- | ------ | ---------- |
| extra | string | 需要存入剪贴板的文字 |

调用示例：

```
NCGSDK.doEventInfo(extra);
```

### 4.3获取当前手机系统语言和地区、获取当前手机时区

当游戏需要区分当前手机系统语言时，可以调用此方法来获取
接口定义：

```
NCGSDK.doLanguage();
```

调用示例：

```
String language = NCGSDK.doLanguage();
```

响应：
返回示例: "zh-CN"。

其中，前半部分表示语言，zh代表中文，后半部分代码地区，CN代表中国。如果只要按语言判断，请只判断前半部分，如果只需按地区判断，请只判断后半部分。因为一种语言会在多个地区出现，一个地区也会有多种语言。

语言码的ISO标准：[ISO 639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes)

地区码的ISO标准：[ISO 3166-2](https://en.wikipedia.org/wiki/ISO_3166-2)

当游戏需要获取当前用户手机时间所在时区时，可以调用此方法来获取

```
NCGSDK.doTimeZone();
```

调用示例：

```
String timeZone = NCGSDK.doTimeZone();
```

响应：
返回示例: "+8"。(表示东八区)

### 4.4打开外部网页接口（外部浏览器打开）

当游戏需要通过外部浏览器打开一个网页时，可以使用此方法，包括但不限定打开Facebook粉丝页，Lobi，Twitter，巴哈姆特等
调用示例：

```
NCGSDK.doOpenURLbyWeb(String url);
```

| 字段  | 类型     | 说明          |
| --- | ------ | ----------- |
| url | string | 需要打开的外部网页地址 |

### 4.5打开webview网页接口（webview打开）

当游戏需要通过webview打开一个网页时，可以使用此方法，请注意打开的url需要为https协议
调用示例：

```
NCGSDK.doOpenURLbyWebView(String url);
NCGSDK.doOpenURLbyWebView(String url, ScreenOrientation orientation);
```

| 字段  | 类型     | 说明                                        |
| --- | ------ |-------------------------------------------|
| url | string | 需要打开的webview网页地址                          |
| orientation | ScreenOrientation | 可选参数,如果不指定打开webview方向则默认打开和游戏方向一致的webview |



### 4.6打开账号选择界面接口

当游戏内需要显示打开账号选择界面页面的入口，点击入口时调用此接口
调用示例：

```
NCGSDK.showLogin();
```

### 4.7打开客服中心

调用示例：
```
NCGSDK.showServiceCenter();
```

### 4.8 打开个人中心界面

用户中心界面包含了展示用户账号绑定信息、绑定账号、修改密码、切换账号、查看最新订单。
注：个人中心应该在已登录状态下调用。

调用示例
```
NCGSDK.showUserCenter();
```







