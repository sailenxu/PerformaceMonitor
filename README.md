# 未完待续……
# android性能测试平台
## 1.平台需求：
桌面级应用（可能是把，后边再说……），运行后桌面显示程序所有功能
### 1.设备信息查看
### 2.monkey测试
### 3.性能测试
多线程应用
通过adb实时监控device的性能，包括时间，内存，cpu，流量，fps
每执行一次，获取一次性能参数，保存到对象中，每个对象组成一个list或数组
将整体结果制作测试报告，extentreports框架
## 2.实现方式：
### 1.获取cpu命令行
adb shell dumpsys cpuinfo|findstr com.xstore.sevenfresh
结果会有多行，因为一般app都有server服务进程  
~~~
  13% 16464/com.jingdong.app.mall: 9.2% user + 4% kernel / faults: 10176 minor 15 major
  0% 16924/com.jingdong.app.mall:jdpush: 0% user + 0% kernel / faults: 40 minor
  0% 17019/com.jingdong.app.mall:WatchDogService: 0% user + 0% kernel / faults: 3 minor
~~~
将结果进行字符串解析，获得第一行的13%即可  
华为手机使用此命令时有延迟，打开应用后，刚开始无法获取到cpu数据
网上评论，cpuinfo是一段时间的平均值，受其他命令影响大，尤其是dumpsys meminfo,建议用top  
使用top时，包名显示不全，华为手机，不知道其他手机是否如此,暂时使用自定义显示内容来解决  
adb -s Q5S5T19529000632 shell top -o ARGS -o %CPU -d 1|findstr com.jingdong.app.mall
由于此命令会一直输出，并不是一次请求就结束的，执行命令后，后边的代码不再执行了，无法获取到cpu值  

### 2.获取内存命令行
方法1：adb shell dumpsys meminfo com.xstore.sevenfresh
返回指定应用的内存情况，返回值较多，不好解析<br>
方法2：adb shell dumpsys meminfo|findstr Foreground
返回当前应用的内存占用，好解析。但是无法确认当前应用，如果app闪退，抓到的是luncher的内存了<br>
### 3.获取fps
adb shell dumpsys gfxinfo com.xstore.sevenfresh
此指令会返回前120个fps数据，需要进行数据的处理才能得到实时的fps
### 4.获取流量
### 5.获取打开时间
