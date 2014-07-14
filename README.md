#LBase-Android 基础框架
* 项目类型：Library


----
#LBase 简介
* 项目集合了开发中经常使用的工具类，并简化开发代码的编写
* 如：T类（Toast）、L类（Log）、LSharePreference类（SharedPreferences） 等
* 并将代码层次划分，网络请求、结果解析、结果处理等繁琐并且常用的代码变得简单使用
* Activity、Fragment、Adapter 只需处理 View 相关操作，Handler 处理请求数据解析等相关操作，使用者不需要再写着大量重复的代码去创建线程，只需几行便可解决
* 常用的 BaseAdapter 也不需要再写着大量重复的代码，使用者只需要把工作重点放在 getView 上面就可以，处理好 Item 的数据，其它功能让 LBase 帮你解决


## 使用 LBase 框架需要有以下权限：

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
```
* 第一个是访问网络
* 第二个是访问sdcard
* 第三个是读写手机状态和身份


## 第三方开源程序的使用

* [universal-image-loader-1.9.2.jar](https://github.com/nostra13/Android-Universal-Image-Loader) 由 nostra13 提供开源


## 使用 LBase 框架

* 请下载 LBaseExample 查看

# 关于作者

* 陈磊（Chen Lei）
* Email:objectes@126.com
* 博客:[http://blog.csdn.net/objectes](http://blog.csdn.net/objectes)