# android性能测试平台
## 1.平台需求：
通过adb监控device的性能，包括时间，内存，cpu，流量，fps
每执行一次，获取一次性能参数，保存到数组或集合中
将整体结果制作测试报告，extentreports框架
## 2.实现方式：
### 1.获取cpu命令行
adb shell dumpsys cpuinfo|findstr com.xstore.sevenfresh
将结果进行字符串解析
### 2.获取内存命令行
adb shell dumpsys meminfo com.xstore.sevenfresh
结果进行字符串解析
