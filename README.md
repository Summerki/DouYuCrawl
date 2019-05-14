[TOC]



> 灵感：最近在看MSI，突然感觉有时候弹幕很好笑，所以决定实现爬取斗鱼弹幕
>
> 爬取斗鱼实时弹幕



### 获取斗鱼弹幕服务器API

+ http://dev-bbs.douyutv.com/forum.php?mod=viewthread&tid=399&extra=page%3D1
+ 在上面这个网站搜索`斗鱼弹幕服务器第三方接入协议`即可找到相关文件





### Socket 的方法 getOutputStream() 和 getInputStream()

+ https://blog.csdn.net/u011951979/article/details/77825555



### 大端数与小端数

+ https://blog.csdn.net/w1300048671/article/details/77982242



### 进度

+ 已完成的：
  + 根据斗鱼开放的第三方弹幕服务器接入协议进行实时弹幕爬取
  + 将爬取得到的弹幕按照一定的规则存入文本文件之中
  + 存放弹幕文件是为了以后的分析做准备
+ 将要实现的：
  + 根据爬取得到的弹幕文件做文本分析
  + 制作词云
+ 将来还要实现的：爬取B站相关信息（感觉B站弹幕质量高一些，哈哈
  + https://www.zhihu.com/question/56924570?sort=created
  + 



