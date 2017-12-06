Sms Forward   转发安卓手机收到短信
-----------

## 功能描述

转发安卓手机上新收到的短信到特定的微信公众号，或者slack中指定的群组（group）
> 开发SmsForward是为了满足自己的一项实际使用需求: *换了iPhone，没了双卡双待，不想随身携带两个手机又不想错过另一张卡的来电和短信，解决办法就是就此app转发短信，同时启用来电转接功能*

增加漏接来电的文字转发提醒

### 截图

<img src=./docs/app-home.png width=216 height=384 />      <img src=./docs/app-setting.png width=216 height=384 />  

### 下载安装

<img src=./docs/fir-im-release.png width=400 height=400 />  


## 使用方法

**[Slack账户token设置指南](./docs/setup_slack.md)**  


**[飞鸽快信设置指南](./docs/setup_feige.md)**

按照上面两条指南中的任意一条设置好，并且按照指南里的说明获取到对应的`token`和`id`填在app的对应的设置页面中。


## 声明
本App从Android手机上读取短信，并通过用户设置的第三方服务帐号进行转发，第三方服务是否会保留转发记录并侵犯用户隐私，本App不能提供任何保证。

除了用户设置的第三方服务的帐号之外，本App保证不会将短信内容泄漏给任何其它地方。

## Todo

- [x] 直接在app上修改`feigeuid`和`feigesecret` 
- [ ] 权限检测在sdk版本低于23时检查结果不正确
- [x] 支持开启、关闭转发服务
- [ ] 网络转发短信失败时直接通过本机短信接口转发


## 注意事项

* MIUI 系统中后台转发失败
MIUI系统中的神隐模式会在应用进入后台后限制应用的网络访问，导致转发失败，
在MIUI中使用应关闭神隐模式。


## 维护　

作者本人会不定期更新项目，让这个小工具更好用，交互更好　　

朋友们如果有一些对本项目的建议或者想法，欢迎issues，欢迎pull request

