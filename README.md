Sms Forward
-----------


## 功能描述

转发安卓手机上新收到的短信到特定的微信公众号，或者slack中指定的group（channel）
> 开发SmsForward是为了满足自己的一项实际使用需求: *换了iPhone，没了双卡双待，不想随身携带两个手机又不想错过另一张卡的来电和短信，
解决办法就是就此app转发短信，同时启用来电转接功能*


## 使用方法

在[飞鸽快信](http://www.ifeige.cn/)上注册，按照网站上的说明关注微信公众号，并且在网站上查看uid和secret

重命名 `config.gradle` 为 `secret.gradle`

并且修改 `feigesecret` 和 `feigeuid` 为飞鸽快信网站提供的值  
修改 `slacktoken` `slackchannel` 为从slack中获取的值

**注意事项**  
MIUI 系统中的神隐模式会在应
用进入后台后限制应用的网络访问，导致转发失败，
在MIUI中使用应关闭神隐模式。

## Todo

- [x] 直接在app上修改`feigeuid`和`feigesecret`
- [ ] 权限检测在sdk版本低于23时检查结果不正确
- [x] 支持开启、关闭转发服务
- [ ] 网络转发短信失败时直接通过本机短信接口转发





set slack bot
add a bot
get bot token
https://personal-baishuai.slack.com/services/B56MG7Z6F
https://my.slack.com/apps/manage/custom-integrations


new a private channel
add bot to private channel


get channel(group ) id
https://api.slack.com/methods/groups.list/test