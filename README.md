# 未完待续……
# android性能测试平台
## 1.平台需求：
桌面级应用（可能是把，后边再说……），运行后桌面显示程序所有功能
有刷新按钮，刷新后对device和package进行重置，device下拉框显示所有已连接设备，选择deviceis，显示设备信息、package下拉框显示所有已安装应用，选择package包名，就可以对该应用的性能指数进行监控
### 1.设备信息查看
### 2.monkey测试
点击执行monkey，进行monkey测试，实时打印日志  
如何做到实时日志获取并打印到前台？  
方法1：monkey命令执行结果输出到本地一个txt文件中，代码读取文件内容，实时打印  
我自己想出来的方法，并没有去写，仔细想一下就特么不好搞……txt一边读一边写，那不是搞死，哈哈  
方法2：执行cmd命令时，肯定需要流来读取结果的呀，把流返回来，直接将流输出到前台不就可以了，看来IO那块没白学  
具体见cmdtool类
### 3.性能测试
多线程应用  
通过adb实时监控device的性能，包括时间，内存，cpu，流量，fps  
每执行一次，获取一次性能参数，保存到对象中，每个对象组成一个list或数组  
将整体结果制作测试报告，extentreports框架  
20200427：好像我最初的想法和现在不一样了，最初是收集数据，最后统一输出做测试报告，现在想的是实时监控工具，让所有数据实时输出到前台，我擦，需求不一样实现上肯定也不一样啊，获取到的性能值还有必要存入对象集合吗？好像没必要……  
也没有必要去触发性能监控吧？只要打开程序，就一直跑一直抓数据输出到前台，这样才是监控嘛
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
网上评论，cpuinfo是一段时间的平均值，受其他命令影响大，尤其是dumpsys meminfo,建议用top，并且top命令执行需要2-3s时间，建议使用busybox，通过搜索发现busybox需要root才可以，在手机上装bosybox工具，因此无法使用busybox，只用top即可，增加-d 0.5来设定执行时间0.5s，不知道是否管用，不管了，就这样吧  
使用top时，包名显示不全，华为手机，不知道其他手机是否如此,暂时使用自定义显示内容来解决  
~~~
adb -s Q5S5T19529000632 shell top -o ARGS -o %CPU -d 0.5|findstr com.jingdong.app.mall
~~~
由于此命令会一直输出，并不是一次请求就结束的，执行命令后，后边的代码不再执行了，无法获取到cpu值  
命令行中增加-n 1,只执行一次，这样会自动结束  
~~~
adb -s Q5S5T19529000632 shell top -o ARGS -o %CPU -d 0.5 -n 1|findstr com.jingdong.app.mall
~~~
这样成功获取cpu值
### 2.获取内存命令行
方法1：adb shell dumpsys meminfo com.jingdong.app.mall  
返回指定应用的内存情况，获取total值作为内存参数  
方法2：adb shell dumpsys meminfo|findstr Foreground  
返回当前应用的内存占用，好解析。但是无法确认当前应用，如果app闪退，抓到的是luncher的内存了  
使用方法1获取成功
### 3.获取fps
adb shell dumpsys gfxinfo com.xstore.sevenfresh
此指令会返回前120个fps数据，需要进行数据的处理才能得到实时的fps
### 4.获取流量

### 5.获取打开时间
