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
### JVM参数分类
### 第一类标准参数 -jar -server 
### 第二类 X参数
### 第三类 XX参数

### 打印所有 XX 参数及值
#### -XX:+PrintFlagsFinal and -XX:+PrintFlagsInitial
```shell
java -server -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions -XX:+PrintFlagsFinal Main
```
```text
uintx InitialHeapSize                     := 57505088         {product}
uintx MaxHeapSize                         := 920649728        {product}
uintx ParallelGCThreads                   := 4                {product}
 bool PrintFlagsFinal                     := true             {product}
 bool UseParallelGC                       := true             {product}
```
:=” 表明了参数被用户或者 JVM 赋值了。

#### -XX:+PrintCommandLineFlags
这个参数让 JVM 打印出那些已经被用户或者 JVM 设置过的详细的 XX 参数的名称和值。
```text
>java -server  -XX:+PrintCommandLineFlags  Main
-XX:InitialHeapSize=263597440 -XX:MaxHeapSize=4217559040 -XX:+PrintCommandLineFlags -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseLargePagesIndividualAllocation -XX:+UseParallelGC
hello world

```

### -Xms and -Xmx (or: -XX:InitialHeapSize and -XX:MaxHeapSize)

下面的命令启动了一个初始化堆内存为 128M，最大堆内存为 2G，名叫 “MyApp” 的 Java 应用程序。
```shell
java -Xms128m -Xmx2g MyApp
```

### -XX:+HeapDumpOnOutOfMemoryError and -XX:HeapDumpPath
当发生内存溢出，堆内存溢出的快照

默认情况下，堆内存快照会保存在 JVM 的启动目录下名为 java_pid.hprof 的文件里
也可以使用 HeapDumpPath来制定输出的路径

```shell
java -Xms5m -Xmx10m -XX:+PrintCommandLineFlags -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D://xxxx Main
```
### -XX:OnOutOfMemoryError
当发生内存溢出， 可以执行一些指令。运行一个脚本,去上传、清理hprof文件
```shell
java -Xms5m -Xmx10m -XX:+PrintCommandLineFlags -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D://xxxx -XX:OnOutOfMemoryError=D://xxxx//xxxx.bat  Main
```
### -XX:PermSize and -XX:MaxPermSize
永久代是堆内存中一块独立的区域，它包含了所有的jvm加载类的对象表示。
```shell
java -XX:PermSize=128m -XX:MaxPermSize=256m Main
```
这里设置的永久代大小并不会被包括在使用参数 - XX:MaxHeapSize 设置的堆内存大小中。
也就是说 通过 - XX:MaxPermSize 设置的永久代内存可能会需要由参数 - XX:MaxHeapSize 设置的堆内存以外的更多的一些堆内存

### -XX:InitialCodeCacheSize and -XX:ReservedCodeCacheSize
JVM 一个有趣的，但往往被忽视的内存区域是 “代码缓存”，它是用来存储已编译方法生成的本地代码。代码缓存确实很少引起性能问题，
但是一旦发生其影响可能是毁灭性的。如果代码缓存被占满，JVM 会打印出一条警告消息，并切换到 interpreted-only 模式：JIT 编译器被停用，
字节码将不再会被编译成机器码。因此，应用程序将继续运行，但运行速度会降低一个数量级，直到有人注意到这个问题。就像其他内存区域一样，
我们可以自定义代码缓存的大小。相关的参数是 - XX:InitialCodeCacheSize 和 - XX:ReservedCodeCacheSize，它们的参数和上面介绍的参数一样，都是字节值。

### -XX:+UseCodeCacheFlushing
如果代码缓存不断增长，例如，因为热部署引起的内存泄漏，那么提高代码的缓存大小只会延缓其发生溢出。
为了避免这种情况的发生，我们可以尝试一个有趣的新参数：当代码缓存被填满时让 JVM 放弃一些编译代码。
通过使用 - XX:+UseCodeCacheFlushing 这个参数，我们至少可以避免当代码缓存被填满的时候 JVM 切换到 interpreted-only 模式。
不过，我仍建议尽快解决代码缓存问题发生的根本原因，如找出内存泄漏并修复它。

## 新生代垃圾回收
新生代相关的 JVM 参数。
新生代存在的唯一理由是优化垃圾回收（GC）的性能
把堆划分成新生代、老生代的好处:
简化了新对象的分配（只在新生代分配），可以更有效的清除不再需要的对象，（新生代、老生代使用的不同的GC算法）

新生代又分为三个区域
最大的Eden（伊甸园区）
FROM幸存区survivor
TO幸存区survivor
按照规定，新对象首先分配在Eden区域（如果对象过大，会直接分配到老年代中）
在GC中，Eden中的对象被移动到survivor中,直至对象满足一定的年级（定义熬过GC的次数，），会被移动到老年代
![](https://wiki.jikexueyuan.com/project/jvm-parameter/images/1.png)
上图演示 GC 过程，黄色表示死对象，绿色表示剩余空间，红色表示幸存对象
**GC复制算法**  
就是指把某个空间里的活动对象复制到其它空间，把原空间里的所有对象都回收掉。在此，将复制活动对象的原空间称为From空间，将粘贴活动对象的新空间称为To空间。

基于大多数新生对象都会在 GC 中被收回的假设。新生代的 GC 使用复制算法。
在GC前 TO幸存区是保持情况的状态，对象保存在 Eden和From中，
GC运行时，Eden中的幸存对象被复制到To幸存区。针对From中的幸存对象，会考虑对象的年龄，如果年龄没达到阙值（tenuring threshold），对象会复制到To幸存区，
如果达到阀值对象被复制到老年代。复制阶段完成后，Eden 和 From 幸存区中只保存死对象。可视为清空，

如果在复制过程中 To 幸存区被填满了，剩余的对象会被复制到老年代中。最后 From 幸存区和 To 幸存区会调换下名字，在下次 GC 时，To 幸存区会成为 From 幸存区
jvm 堆监控数据
```
jvm_memory_used_bytes{area="heap",id="CMS Old Gen"}	864453920
jvm_memory_used_bytes{area="heap",id="Par Eden Space"}	3148248
jvm_memory_used_bytes{area="heap",id="Par Survivor Space"}	2770800

```

### -XX:NewSize and -XX:MaxNewSize
就像可以通过参数 (-Xms and -Xmx) 指定堆大小一样，可以通过参数指定新生代大小。
设置 XX:MaxNewSize 参数时，应该考虑到新生代只是整个堆的一部分，新生代设置的越大，老年代区域就会减少。
一般不允许新生代比老年代还大，因为要考虑 GC 时最坏情况，所有对象都晋升到老年代。(译者: 会发生 OOM 错误) -XX:MaxNewSize 最大可以设置为 - Xmx/2

### -XX:NewRatio
可以设置新生代和老年代的相对大小。这种方式的优点是新生代大小会随着整个堆大小动态扩展。参数 -XX:NewRatio 设置老年代与新生代的比例。例如 -XX:NewRatio=3 指定老年代 / 新生代为 3/1。 老年代占堆大小的 3/4，新生代占 1/4 。

```shell
 java -XX:NewSize=32m -XX:MaxNewSize=512m -XX:NewRatio=3 MyApp
```
以上设置，JVM 会尝试为新生代分配四分之一的堆大小，但不会小于 32MB 或大于 521MB

### -XX:SurvivorRatio
参数 -XX:SurvivorRatio 与 -XX:NewRatio 类似，作用于新生代内部区域。
-XX:SurvivorRatio 指定伊甸园区 (Eden) 与幸存区大小比例。 
例如， -XX:SurvivorRatio=10 表示伊甸园区 (Eden) 是 幸存区 To 大小的 10 倍 (也是幸存区 From 的 10 倍)。 
所以， 伊甸园区 (Eden) 占新生代大小的 10/12， 幸存区 From 和幸存区 To 每个占新生代的 1/12 。 注意， 两个幸存区永远是一样大的

最小化短命对象晋升到老年代的数量，同时也希望最小化新生代 GC 的次数和持续时间。 我们需要找到针对当前应用的折中方案， 寻找适合方案的起点是 了解当前应用中对象的年龄分布情况。

https://wiki.jikexueyuan.com/project/jvm-parameter/garbage-collection.html


### -XX:+PrintTenuringDistribution
指定 JVM 在每次新生代 GC 时，输出幸存区中对象的年龄分布。
```
Desired survivor size 11010048 bytes, new threshold 7 (max 15)
```
第一行说明幸存区 To 大小为 11010048 bytes。 也有关于老年代阀值 (tenuring threshold) 的信息， 老年代阀值，意思是对象从新生代移动到老年代之前，经过几次 GC(即， 对象晋升前的最大年龄)。 上例中， 老年代阀值为 7， 最大也是 15。


### -XX:InitialTenuringThreshold， -XX:MaxTenuringThreshold and -XX:TargetSurvivorRatio
参数 -XX:+PrintTenuringDistribution 输出中的部分值可以通过其它参数控制。
通过 -XX:InitialTenuringThreshold 和 -XX:MaxTenuringThreshold 可以设定老年代阀值的初始值和最大值。
另外， 可以通过参数 -XX:TargetSurvivorRatio 设定幸存区的目标使用率。 
例如， -XX:MaxTenuringThreshold=10 -XX:TargetSurvivorRatio=90 设定老年代阀值的上限为 10， 幸存区空间目标使用率为 90%。


## 吞吐量收集器

## CMS 收集器
HotSpot JVM 的并发标记清理收集器 (CMS 收集器) 的主要目标就是：低应用停顿时间。
### CMS 收集器的过程

## GC
```
[GC (Allocation Failure) 
[PSYoungGen: 9733K->5600K(11264K)] 34778K->33902K(39936K), 0.0440561 secs] 
[Times: user=0.25 sys=0.00, real=0.04 secs]
```
这是一次在young Generation中的GC ，他将已经使用的堆空间从34778K减少到了33902K，用时0.0440561s
Times表明了用户态和系统 GC的时间，real 表明真实的GC时间，  0.04 明显远小于 user+sys, 说明gc 是多线程执行的
```
[Full GC (Ergonomics) 
[PSYoungGen: 9216K->4074K(9216K)] 
[ParOldGen: 17180K->16995K(28160K)] 26396K->21069K(37376K), 
[Metaspace: 3571K->3571K(1056768K)], 0.3019287 secs] 
[Times: user=1.11 sys=0.01, real=0.30 secs]
```
