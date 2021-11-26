### -server and -client
```text
HotSpot JVM 提供
我们可以用 server 、client参数来设置使用客户端、服务端的VM 

Client VM(-client)，为在客户端环境中减少启动时间而优化；
Server VM(-server)，为在服务器环境中最大化程序执行速度而设计。
比较：Server VM启动比Client VM慢，运行比Client VM快。
配置文件
若为64位操作系统
{JRE_HOME}/lib/amd64/jvm.cfg
若为32位操作系统
{JRE_HOME}/lib/i386/jvm.cfg

谁在第一个谁就是默认
-server KNOWN
-client KNOWN
```
### 