# 基于springcloud alibaba的mirai服务

mirai_consumer
> 接受RabbitMq消息消费bot消息

mirai_instance
> bot实例，配置消息推送

mirai_common
> 公共组件


## 安装

## 1. 配置nacos配置
 1.1修改所有bootstrap.yaml中的server-addr为你的服务器地址

1.2 添加配置 （mirai-instance-dev.yaml）
```yaml
server:
  port: 8080
  servlet:
    context-path: /api
mirai:
  config:
    state: aaaa
    bot:
      - qq: 123456 #机器人账号
        pass: password #密码
spring:
  rabbitmq:
    host: localhost
    port: 5672

```
# 2.访问/api/login登录
