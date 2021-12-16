# 深入理解Java虚拟机
[学习传送门](https://wiki.jikexueyuan.com/project/java-vm/overwise.html)
### Java各个组成部分以及功能
![](https://wiki.jikexueyuan.com/project/java-vm/images/jvmstructure.gif)

JDK >>> JRE >>> JVM
### 技术划分
```text
Java Card：支持一些Java小程序（Applets）运行在小内存设备（如智能卡）上的平台。

Java ME（Micro Edition）：支持Java程序运行在移动终端（手机、PDA）上的平台，对 Java API 有所精简，并加入了针对移动终端的支持，这个版本以前称为J2ME。

Java SE（Standard Edition）：支持面向桌面级应用（如 Windows 下的应用程序）的 Java 平台，提供了完整的 Java 核心 API，这个版本以前称为 J2SE。

Java EE（Enterprise Edition）：支持使用多层架构的企业应用（如 ERP、CRM 应用）的 Java 平台，除了提供 Java SE API 外，还对其做了大量的扩充并提供了相关的部署支持，这个版本以前称为 J2EE。
```
### JVM物理结构

![](https://wiki.jikexueyuan.com/project/java-vm/images/jvm.gif)


### What is JVM

jvm 是java的核心与基础，在java编译器和os系统之间的虚拟处理器，他是一种基于下层的操作系统和硬件平台合并利用软件方法来实现的抽象虚拟机，可以在上面执行Java字节码程序

Java 编译器只需面向 JVM，生成 JVM 能理解的代码或字节码文件。Java 源文件经编译器，编译成字节码程序，通过 JVM 将每一条指令翻译成不同平台机器码，通过特定平台运行。

简单的说，JVM 就相当于一台柴油机,它只能用 Java (柴油)运行,JVM 就是 Java 的虚拟机,有了 JVM 才能运行 Java 程序

## Java 代码编译和执行的整个过程
`Java 代码编译是由 Java 源码编译器来完成`
![](https://wiki.jikexueyuan.com/project/java-vm/images/javadebug.gif)
`Java 字节码的执行是由 JVM 执行引擎来完成`
![](https://wiki.jikexueyuan.com/project/java-vm/images/jvmdebug.gif)

### 代码编译、执行三大步骤机制
    * Java源码编译
    * 类加载
    * 类执行
#### Java程序编译机制
    * 分析和输入到符号表
    * 注解处理
    * 语义分析和生成class文件

![](https://wiki.jikexueyuan.com/project/java-vm/images/workflow.gif)

#### 类加载机制

#### 类执行机制

