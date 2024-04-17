:::danger 说明
前端->练习类项目  

练习uniapp+vue3的实际开发

查看完整开发流程，直接移步：开发日志
:::
## 介绍
采用uniapp开发的跨平台仿网易云音乐项目。  
基本功能和网易云音乐类似。

关于从零开始的开发过程，可以直接查看：开发日志。
- [github源码]()
- [项目文档]()
- [接口](https://github.com/Binaryify/NeteaseCloudMusicApi)
- [接口文档](https://binaryify.github.io/NeteaseCloudMusicApi/#/)

#### 1. 接口
该项目在v1版本会调用网易云官方接口（包括登录），采用的是第三方nodejs项目提供的代理数据。

后续版本自己准备一套接口，未来的升级版可能是完全重新设计的音乐播放器项目，届时将另建新项目。

#### 2. 参考项目
- [B站视频](https://www.bilibili.com/video/BV19P411N7xZ/?p=3)
- [gitee uniapp仿网易云音乐项目源码](https://gitee.com/fengxian_duck/netease-cloud-music)
- [API](https://github.com/Binaryify/NeteaseCloudMusicApi)


## 版本控制
#### v1版
v1版是只有前端的，后端采用第三方接口。

核心架构：uniapp(vue3) + 第三方API

#### v2版
这个还在考虑要不要做，看情况更新。

核心架构：
- 前端：uniapp（vue3）
- 后端：bootstrap
- data：MySQL + Redis
- server：centos

## 体验
- H5端
- 微信小程序
- 抖音小程序
- 安卓
- IOS
