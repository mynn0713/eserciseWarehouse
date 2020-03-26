### 1. Tomcat主要模块###
1）tomcat主要有两大职能

+ 一为通讯职能，接收tcp/ip通讯协议、接收http请求-响应超文本传输协议，对应的组件为Connector。
+ 二为servlet容器，对应的组件为Container。

2）Connector用于接收Http协议的Socket请求，将请求的流封装到Reqeust对象中，发送给servlet容器Container，再接收Container返回的Response对象，转换成响应的数据流返回给前端。

![1.tomcat整体流程.png] (C:\Users\91375\Desktop\tomcat结构)

![1.tomcat整体流程.png](C:/Users/91375/Desktop/tomcat结构/1.tomcat整体流程.png "1.tomcat整体流程")

### 2. Tomcat核心组件###

1）连接器组件Connector：

* Connector是客户端连接到Tomcat容器的服务点，它为引擎提供协议服务来将引擎与客户端各种协议隔离开来，如HTTP、HTTPS、AJP协议。Connector的基本属性都是它所需要监听的IP地址及端口号，以及所支持的协议。还有一个关键属性就是并发处理传入请求的最大线程数。注意，Connector关键的有 连接器（HTTP   HTTPS   HTTP1.1   AJP    SSL  proxy） 运行模式（BIO  NIO  NIO2/AIO  APR）多线程/线程池。

2）容器组件Container：

* Container是容器的父接口，用于封装和管理Servlet，以及具体处理Request请求，该容器的设计用的是典型的责任链的设计模式，它由四个自容器组件构成，分别是Engine、Host、Context、Wrapper。这四个组件是负责关系，存在包含关系。只包含一个引擎。
  * Engine 引擎：表示可运行的Catalina的servlet引擎实例，并且包含了servlet容器的核心功能。在一个服务中只能有一个引擎。同时，作为一个真正的容器，Engine元素之下可以包含一个或多个虚拟主机。它主要功能是将传入请求委托给适当的虚拟主机处理。如果根据名称没有找到可处理的虚拟主机，那么将根据默认的Host来判断该由哪个虚拟主机处理。
  * Host 虚拟主机：作用就是运行多个应用，它负责安装和展开这些应用，并且标识这个应用以便能够区分它们。它的子容器通常是 Context。一个虚拟主机下都可以部署一个或者多个Web App，每个Web App对应于一个Context，当Host获得一个请求时，将把该请求匹配到某个Context上，然后把该请求交给该Context来处理。主机组件类似于Apache中的虚拟主机，但在Tomcat中只支持基于FQDN(完全合格的主机名)的“虚拟主机”。Host主要用来解析web.xml。
  * Context上下文：代表 Servlet 的 Context，它具备了 Servlet 运行的基本环境，它表示Web应用程序本身。Context 最重要的功能就是管理它里面的 Servlet 实例，一个Context对应于一个Web Application，一个Web Application由一个或者多个Servlet实例组成。
  * Wrapper包装器: 代表一个 Servlet，它负责管理一个 Servlet，包括的 Servlet 的装载、初始化、执行以及资源回收。Wrapper 是最底层的容器

![2.server容器结构图.png](C:\Users\91375\Desktop\tomcat结构\2.server容器结构图.png)