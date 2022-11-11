**一个人能力有大小，但只要有这点精神，就是一个高尚的人，一个纯粹的人，一个有道德的人，一个脱离了低级趣味的人，一个有益于人民的人。——毛泽东**

**人最宝贵的东西是生命,生命属于人只有一次。一个人的一生应该是这样度过的：当他回首往事的时候，他不会因为虚度年华而悔恨，也不会因为碌碌无为而羞耻；这样，在临死的时候，他就能够说：“我的整个生命和全部精力，都已经献给世界上最壮丽的事业——为人类的解放而斗争。——保尔.柯察金**

**让我们面对现实，让我们忠于理想。——切.格瓦拉**

# 谷粒商城项目笔记

## 项目整体架构图

![谷粒商城-微服务架构图](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/谷粒商城-微服务架构图.jpg)

## docker-mysql

注意这是mysql5版本的开启方式，mysql8用这个命令开启会报错

![image-20220716104531779](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220716104531779.png)

### mysql8版本(ubuntu)：

~~~shell
sudo docker run  --name mysql  -p 3306:3306  -v /mydata/mysql/data:/var/lib/mysql  -v /mydata/mysql/log:/var/log/mysql  -v /mydata/mysql/my.cnf:/etc/mysql/my.cnf:rw  -e MYSQL_ROOT_PASSWORD=250258  --restart=always  -d mysql:latest

~~~

### centos7:

先建好文件,否则会导致无法启动容器，会被默认建立为一个文件夹：

~~~shell
touch /mydata/mysql/my.cnf
~~~

在docker run中加入 –privileged=true 给容器加上特定权限

~~~shell
docker run  --name mysql --privileged=true -p 3306:3306   -v /mydata/mysql/log:/var/log/mysql  -v /mydata/mysql/my.cnf:/etc/mysql/my.cnf:rw  -e MYSQL_ROOT_PASSWORD=250258   -d mysql:latest
~~~

### 查看docker某个容器的启动日志以排查问题：

~~~shell
sudo docker logs --since 30m 0952aad3d653
2022-07-16 03:33:06+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 8.0.29-1.el8 started.
2022-07-16 03:33:06+00:00 [ERROR] [Entrypoint]: mysqld failed while attempting to check config
	command was: mysqld --verbose --help --log-bin-index=/tmp/tmp.pFlQvdOoCP
	mysqld: Can't read dir of '/etc/mysql/conf.d/' (OS errno 2 - No such file or directory)
mysqld: [ERROR] Stopped processing the 'includedir' directive in file /etc/my.cnf at line 36.
mysqld: [ERROR] Fatal error in defaults handling. Program aborted!
2022-07-16 03:33:44+00:00 [Note] [Entrypoint]: Entrypoint script for MySQL Server 8.0.29-1.el8 started.
2022-07-16 03:33:44+00:00 [ERROR] [Entrypoint]: mysqld failed while attempting to check config
	command was: mysqld --verbose --help --log-bin-index=/tmp/tmp.g0bgHMQ5gA
	mysqld: Can't read dir of '/etc/mysql/conf.d/' (OS errno 2 - No such file or directory)
mysqld: [ERROR] Stopped processing the 'includedir' directive in file /etc/my.cnf at line 36.
mysqld: [ERROR] Fatal error in defaults handling. Program aborted!

~~~

## 控制器

![image-20220716205322149](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220716205322149.png)

## docker安装redis

![image-20220716211200130](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220716211200130.png)

## 新加入一个外部模块

![image-20220716234314221](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220716234314221.png)

![image-20220716234530988](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220716234530988.png)

## pom.xml的plugin里面爆红

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220716235813606.png)

![image-20220717003341151](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220717003341151.png)

![image-20220717003400350](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220717003400350.png)



## pom/relativePath

![image-20220717002957411](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220717002957411.png)

![image-20220717004433065](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220717004433065.png)

![image-20220717004651702](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220717004651702.png)

## 关于配置文件

![image-20220717212731424](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220717212731424.png)

## 调出spring的service启动面板

![image-20220717230237368](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220717230237368.png)

![image-20220717230256367](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220717230256367.png)

## 刷新配置文件值

![image-20220718113521482](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718113521482.png)

## nacos配置中心使用

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718113958408.png)

## 切换命名空间/分组

![image-20220718121551325](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718121551325.png)

### 最佳实践：

![image-20220718122136800](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718122136800.png)

![image-20220718122206513](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718122206513.png)

## 配置集群

![image-20220718122741647](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718122741647.png)

![image-20220718123406926](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718123406926.png)

## 指定默认配置文件的分组

![image-20220718123636059](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718123636059.png)



这样任何配置文件都可以放在nacos中了。

## Gateway

![image-20220718124953314](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718124953314.png)

## ubuntu切换输入法

![image-20220718154404887](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718154404887.png)

## 下载centos

![image-20220718165959490](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718165959490.png)

## centos7安装后联网

![image-20220718182715154](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718182715154.png)

![image-20220718182814210](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718182814210.png)

![image-20220718182828547](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718182828547.png)

## spring工程激活规则

![image-20220718195426750](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718195426750.png)

## centos7中docker运行redis

~~~shell
docker run -p 6379:6379 --name redis --privileged=true -v /mydata/redis/data:/data -v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf -d redis redis-server /etc/redis/redis.conf
~~~

## centos无法使用ifconfig

centos无法使用ifconfig命令是因为**安装系统时默认没有安装ifconfig命令**。 解决方法：在终端使用“yum install -y net-tools”命令安装ifconfig包即可。

![image-20220718213635455](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220718213635455.png)



## es5 vs es6

### 解构赋值/箭头函数

![image-20220719093535093](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719093535093.png)

![image-20220719093703239](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719093703239.png)

![image-20220719100025125](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719100025125.png)

### promise解决回调地狱

![image-20220719100155100](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719100155100.png)

![image-20220719100802984](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719100802984.png)

![image-20220719101325350](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719101325350.png)

![image-20220719101346217](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719101346217.png)

#### 链式调用

![image-20220719101759484](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719101759484.png)

![image-20220719101816675](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719101816675.png)

## vue

![image-20220719111054503](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719111054503.png)

![image-20220719111652690](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719111652690.png)

![image-20220719112547597](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719112547597.png)

![image-20220719112855311](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719112855311.png)

![image-20220719113007881](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719113007881.png)

## 引入nacos和openfeign

![image-20220719161900365](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719161900365.png)

## spring-cloud-alibaba版本仲裁

就是可以版本仲裁了。

![image-20220719163201406](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719163201406.png)

![image-20220719225357762](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719225357762.png)

![image-20220719225505522](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719225505522.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719233447248.png)

## maven的type和scope

### 单继承和多引用

maven只能单继承，但是可以多引用。

<scope>import</scope>通常用于多引用pom打包类型的依赖时，只能出现在dependencymanagement中，往往和<type>pom</type>组合用。type和引入依赖的packaging相对应。默认的type和packaging都是jar。如果指明<type>pom</type>

那么就只会引入属性和一些管理的依赖版本，而不引入jar包。因为pom的打包类型的文件中也有可能直接引入依赖而不是只进行依赖

管理。多引用就可以解决单继承导致的继承的父pom中管理了太多的依赖，将依赖管理分散到多个pom里面。

![image-20220719235551823](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719235551823.png)

![image-20220720000317916](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720000317916.png)

### scope

**compile** ：为**默认的**依赖有效范围。如果在定义依赖关系的时候，没有明确指定依赖有效范围的话，则默认采用该依赖有效范围。

此种依赖，在编译、运行、测试时均有效。

**provided** ：在编译、测试时有效，但是在运行时无效。

provided意味着打包的时候可以不用包进去，别的设施(Web Container)会提供。

事实上该依赖理论上可以参与编译，测试，运行等周期。相当于compile，但是在打包阶段做了exclude的动作。

例如：servlet-api，运行项目时，容器已经提供，就不需要Maven重复地引入一遍了。

**runtime** ：在运行、测试时有效，但是在编译代码时无效。

说实话在终端的项目（非开源，企业内部系统）中，和compile区别不是很大。比较常见的如JSR×××的实现，对应的API jar是compile的，具体实现是runtime的，compile只需要知道接口就足够了。

例如：JDBC驱动实现，项目代码编译只需要JDK提供的JDBC接口，只有在测试或运行项目时才需要实现上述接口的具体JDBC驱动。

另外runntime的依赖通常和optional搭配使用，optional为true。我可以用A实现，也可以用B实现。

**test** ：只在测试时有效，包括测试代码的编译，执行。例如：JUnit。

PS: test表示只能在src下的test文件夹下面才可以使用，你如果在a项目中引入了这个依赖，在b项目引入了a项目作为依赖，在b项目中这个注解不会生效，因为scope为test时无法传递依赖。

**system** ：在编译、测试时有效，但是在**运行时无效**。

和provided的区别是，使用system范围的依赖时必须通过**systemPath元素显式地指定依赖文件的路径**。由于此类依赖**不是通过Maven仓库解析的，而且往往与本机系统绑定**，可能造成构建的不可移植，因此应该谨慎使用。

![image-20220719231901122](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719231901122.png)

**不是多继承，是多引用（写错了）：**

![image-20220719235949277](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719235949277.png)

![image-20220720000013186](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720000013186.png)

![image-20220720211638280](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720211638280.png)

![image-20220720212054939](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720212054939.png)



### type

![image-20220719232439297](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719232439297.png)



![image-20220719165607039](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719165607039.png)

## org.springframework.cloud/com.alibaba.cloud

![image-20220719172843892](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719172843892.png)

![image-20220719172951879](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719172951879.png)

![image-20220719173031717](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719173031717.png)

## 使用nacos作为注册和配置中心

![image-20220719174316630](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719174316630.png)

### 坑1：bootstrp.yml不生效

高版本的springboot要使用bootstrap.yml来作为配置文件，务必先引入这个依赖，否则在项目启动时根本不会读取bootstrp.yml。

~~~xml
       <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
            <version>3.1.3</version>
        </dependency>
~~~

### 坑2：正确配置集群写法(yml)

![image-20220719193328667](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719193328667.png)

~~~yaml
spring:
  application:
    name: gulimall-coupon
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        namespace: 01d665f4-3dd1-456f-8ff8-727292e5db2c
        extension-configs:
          - data-id: datasource.yml
            refresh: true
            group: dev

          - data-id: mybatis-plus.yml
            refresh: true
            group: dev

          - data-id: other.yml
            refresh: true
            group: dev
        group: prod
~~~

如果是properties文件：extension-configs[0].data-id=....

​										  extension-configs[0].group=....

​										  extension-configs[1].data-id=....

​										  extension-configs[1].group=....



### 坑3：默认读取的配置文件在nacos中的命名

![image-20220719212004300](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719212004300.png)

![image-20220719193537421](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719193537421.png)

如果想配置其它格式的默认配置文件，那就需要在bootstrap.yml里面指明配置文件的默认后缀名。

![image-20220720134325227](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720134325227.png)

### 坑4：nacos配置持久化

![image-20220719212210635](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719212210635.png)

这一步可能会执行nacos-mysql.sql时报错，直接复制sql语句执行就好。

### 坑5：配置文件的dataid和bootstrap中的名字

是yaml，那两边都要写yaml，是yml，两边就都写yml，否则是无法找到配置文件的！

![image-20220731001205442](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731001205442.png)

## JSONView

修改完jsonview.css后要重新导入插件到chrome里面才会生效。

![image-20220719224205275](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719224205275.png)

![image-20220719224223601](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220719224223601.png)

## vue-cli脚手架

~~~shell
npm install -g vue-cli
~~~

~~~shell
vue init webpack appname 
~~~

## gateway

![image-20220720111333357](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720111333357.png)

## 跨域

![image-20220720111841974](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720111841974.png)

![image-20220720111929689](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720111929689.png)

![image-20220720111952727](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720111952727.png)

![image-20220720112616435](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720112616435.png)

## el-tree

![image-20220720113716706](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720113716706.png)

![image-20220720114052221](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720114052221.png)

![image-20220720114520542](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720114520542.png)

## renren-fast启动报错

![image-20220720134924161](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720134924161.png)

![image-20220720134939047](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720134939047.png)

![image-20220720134951404](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720134951404.png)

## yaml语法严格

![image-20220720151150547](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720151150547.png)

![image-20220720234408841](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720234408841.png)

## springcloudalibaba/springcloud/springboot版本对照

https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E

![image-20220720160857442](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720160857442.png)

![image-20220720201253514](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720201253514.png)

![image-20220720201738029](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720201738029.png)

![image-20220720201901548](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720201901548.png)

一般用spring Initializer脚手架搭建的spring项目一开始就会帮我们适配好我们选中的依赖版本，如果同时选了springcloudalibaba和

springcloud的组件的话，那么它就会帮我把三者的依赖版本都适配好，所以建议直接把脚手架的镜像地址改为https://start.aliyun.com/，这样的话，就可以同时选用阿里巴巴和springcloud的组件了，它也会帮我们适配好他们直接的版本，因为Https://start.spring.io/

这个官方镜像在初始化界面是没有alibaba的组件选择的，这就意味着我们在后续要自己添加阿里巴巴的pom依赖，那么这就可能会出现版本不适配的情况出现。

**同时用到springcloudalibaba和springcloud和springboot时，用脚手架来选中三者的依赖让脚手架帮我们适配三者的版本可以最大程度的避免版本不适配的问题。**

![image-20220720210556467](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720210556467.png)

![image-20220720203316107](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720203316107.png)

![image-20220720203602425](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720203602425.png)

## gateway-503

![image-20220720162659005](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720162659005.png)

![image-20220720162806977](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720162806977.png)

## gateway配置跨域

![image-20220720164220412](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720164220412.png)

~~~yaml
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowCredentials: true
            allowedHeaders: '*'
            allowedMethods: '*'
            allowedOrigins: '*'
~~~



或：

![image-20220720164235834](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720164235834.png)

~~~java
package net.youqu.micro.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * description
 * from www.fhadmin.org
 */
@Configuration
public class CorsConfig implements WebFluxConfigurer{
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
~~~

## equals

![image-20220720183638302](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720183638302.png)

![image-20220720183923747](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720183923747.png)

## gateway路由yaml文件

~~~yaml
server:
  port: 8000
spring:
  cloud:
    gateway:
      globalcors:
        corsConfigurations:
          '[/**]':
            allowCredentials: true
            allowedOrigins: "*"
            allowedMethods: "*"
            allowedHeaders: "*"
      discovery:
        locator:
          enabled: true
      routes:
        - id: gulimall-order
          uri: lb://gulimall-order
          predicates:
            - Path=/api/renren-fast/order/**
          filters:
            - RewritePath=/api/renren-fast/?(?<segment>.*),/$\{segment}

        - id: gulimall-member
          uri: lb://gulimall-merber
          predicates:
            - Path=/api/renren-fast/merber/**
          filters:
            - RewritePath=/api/renren-fast/?(?<segment>.*),/$\{segment}

        - id: gulimall-product
          uri: lb://gulimall-product
          predicates:
            - Path=/api/renren-fast/product/**
          filters:
            - RewritePath=/api/renren-fast/?(?<segment>.*),/$\{segment}

        - id: gulimall-coupon
          uri: lb://gulimall-coupon
          predicates:
            - Path=/api/renren-fast/coupon/**
          filters:
            - RewritePath=/api/renren-fast/?(?<segment>.*),/$\{segment}

        - id: gulimall-ware
          uri: lb://gulimall-ware
          predicates:
            - Path=/api/renren-fast/ware/**
          filters:
            - RewritePath=/api/renren-fast/?(?<segment>.*),/$\{segment}
        #短的应该放后面，否则请求会先执行这个路由，而不会路由到各个服务了。
        - id: renren-fast
          uri: lb://renren-fast
          predicates:
            - Path=/api/renren-fast/**
          filters:
            - RewritePath=/api/?(?<segment>.*),/$\{segment}

        
~~~



## internal java compiler error

![image-20220720230319083](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720230319083.png)

![image-20220720230330252](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720230330252.png)

![image-20220720230341605](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720230341605.png)

![image-20220720230352797](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720230352797.png)

![image-20220720230407787](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720230407787.png)

## maven创建工程的坑

![image-20220720231308057](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720231308057.png)



![image-20220720234619729](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220720234619729.png)

![image-20220721000552176](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220721000552176.png)

## SpringCloud启动LN:170 Cannot determine local hostname

![image-20220721001702344](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220721001702344.png)

## nacos地址书写规则：

![image-20220721002046861](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220721002046861.png)

![image-20220721002319493](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220721002319493.png)

## JSR303

![image-20220722114037758](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220722114037758.png)

![image-20220722114921705](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220722114921705.png)

![image-20220722115018032](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220722115018032.png)

## 后端传输数据到前端（JSON）序列化问题

![image-20220730212509544](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220730212509544.png)

![image-20220730212520080](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220730212520080.png)

## 前端中表格插入

![image-20220730212749292](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220730212749292.png)

## 是method不是methods

![image-20220730220724900](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220730220724900.png)

## 版本仲裁

![image-20220730230513815](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220730230513815.png)

## springcloud-alibaba文档地址

https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html#_%E4%BE%9D%E8%B5%96%E7%AE%A1%E7%90%86

![image-20220730233614823](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220730233614823.png)



## maven依赖顺序原则

maven依赖原则总结起来就两条：路径最短，申明顺序其次。

![image-20220731110808838](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731110808838.png)

![image-20220731111013500](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731111013500.png)

![image-20220731111024287](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731111024287.png)

![image-20220731111036509](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731111036509.png)

### bug分析

很明显，是matchesCharacter找不到。

~~~java
java.lang.NoSuchMethodError: org.springframework.util.StringUtils.matchesCharacter(Ljava/lang/String;C)Z
	at org.springframework.web.util.UrlPathHelper.getContextPath(UrlPathHelper.java:398) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.util.UrlPathHelper.getPathWithinApplication(UrlPathHelper.java:297) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.util.UrlPathHelper.getLookupPathForRequest(UrlPathHelper.java:186) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:363) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.getHandlerInternal(RequestMappingInfoHandlerMapping.java:110) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.getHandlerInternal(RequestMappingInfoHandlerMapping.java:59) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandler(AbstractHandlerMapping.java:396) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.getHandler(DispatcherServlet.java:1234) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1016) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:898) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:626) ~[tomcat-embed-core-9.0.41.jar:4.0.FR]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) ~[spring-webmvc-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:733) ~[tomcat-embed-core-9.0.41.jar:4.0.FR]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.12.RELEASE.jar:5.2.12.RELEASE]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202) ~[tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:143) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:374) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:888) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1597) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_333]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_333]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) [tomcat-embed-core-9.0.41.jar:9.0.41]
	at java.lang.Thread.run(Thread.java:750) [na:1.8.0_333]
~~~

![image-20220731111755219](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731111755219.png)

![image-20220731111240443](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731111240443.png)

原因就是我们分别单独引入了oss和common的依赖，第三方服务的模块使用的web依赖来自于common里面的，但是spring-core却是在oss和common中都有且层级都一样，那么层级一样时就根据声明顺序来决定使用谁，谁先声明的就使用谁，这里我们先声明了oss的依赖，那么会使用oss的spring-core-5.2.1，但spring-core-5.2.1中并没有matchesCharacter这个方法，所以就直接报错了。

![image-20220731112446337](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731112446337.png)

### 1、当我们排除了oss里面的spring-boot-starter时（spring-core在其里面）：

![image-20220731112621612](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731112621612.png)

![image-20220731112701551](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731112701551.png)

就可以正常访问了，因为这个时候用的就是common里面的spring-core-5.2.12了。

### 2、或者，当我们把oss依赖声明在后面时：

![image-20220731112849139](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731112849139.png)

![image-20220731112923412](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731112923412.png)

也可以正常运行，因为层级相同时，优先使用先声明的依赖，这里common先声明的，所以直接使用的是common里面的spring-core-5.2.12。

### 3、最后一种方法：直接把oss放到common里面：

![image-20220731113558594](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731113558594.png)



![image-20220731113537585](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731113537585.png)

根据层级的就近优先原则，也会直接使用common里面的spring-core-5.2.12。

![image-20220731113900510](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731113900510.png)

## 取nacos配置中心里面的值

![image-20220731114854338](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731114854338.png)

![image-20220903213938582](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220903213938582.png)

## 前端oss上传回显异常

![image-20220731171450815](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731171450815.png)

![image-20220731171512814](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731171512814.png)

## el-form

![image-20220731172456526](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731172456526.png)

## elementui的样式问题

![image-20220731190730452](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731190730452.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731191245758.png)

~~~xml
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
~~~

~~~vue
<el-table-column
        prop="logo"
        header-align="center"
        align="center"
        label="品牌logo地址"
      >
        <template slot-scope="scope">
          <div  class="block">
            <el-image :src="scope.row.logo" style="height: 80px;width: 100px" fit="fill">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </div>
        </template>
      </el-table-column>
~~~

## 使用mp的分页插件获取到的total一直为0

一般是没有配置mp的分页插件导致的：

![image-20220731204302446](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731204302446.png)

~~~java
package com.atguigu.common.mybatisplusconfig;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean  //让Spring容器进行管理
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor page = new PaginationInterceptor();
        return page;
    }

}

~~~

![image-20220731204543713](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731204543713.png)

## postman如何在请求中带json数据

很多新手不知道**post**man如何发送json参数，今天我就给大家分享一下。 在地址栏里输入请求url：http://127.0.0.1:8081/getmoney 接着选择“POST”方式。 在“headers”添加key:Content-Type , value:application/json 然后点击"body",''raw''并设定为JSON。 最后点击send发送即可。

## JSR303

前端传来的数据会存在一个转换的步骤。

![image-20220731225524231](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731225524231.png)

~~~
空检查 
@Null 验证对象是否为null 
@NotNull 验证对象是否不为null, 无法查检长度为0的字符串 Accepts any type.
@NotBlank 检查约束字符串是不是Null还有被Trim的长度是否大于0,只对字符串,且会去掉前后空格. Accepts CharSequence 
@NotEmpty 检查约束元素是否为NULL或者是EMPTY. Accepts CharSequence/Collection/Map/Array

Booelan检查 
@AssertTrue 验证 Boolean 对象是否为 true 
@AssertFalse 验证 Boolean 对象是否为 false

长度检查 
@Size(min=, max=) 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内 
@Length(min=, max=) Validates that the annotated string is between min and max included.

日期检查 
@Past 验证 Date 和 Calendar 对象是否在当前时间之前，验证成立的话被注释的元素一定是一个过去的日期 
@Future 验证 Date 和 Calendar 对象是否在当前时间之后 ，验证成立的话被注释的元素一定是一个将来的日期 
@Pattern 验证 String 对象是否符合正则表达式的规则，被注释的元素符合制定的正则表达式，regexp:正则表达式 flags: 指定 Pattern.Flag 的数组，表示正则表达式的相关选项。

数值检查 
建议使用在Stirng,Integer类型，不建议使用在int类型上，因为表单值为“”时无法转换为int，但可以转换为Stirng为”“,Integer为null 
@Min 验证 Number 和 String 对象是否大等于指定的值 
@Max 验证 Number 和 String 对象是否小等于指定的值 
@DecimalMax 被标注的值必须不大于约束中指定的最大值. 这个约束的参数是一个通过BigDecimal定义的最大值的字符串表示.小数存在精度 
@DecimalMin 被标注的值必须不小于约束中指定的最小值. 这个约束的参数是一个通过BigDecimal定义的最小值的字符串表示.小数存在精度 
@Digits 验证 Number 和 String 的构成是否合法 
@Digits(integer=,fraction=) 验证字符串是否是符合指定格式的数字，interger指定整数精度，fraction指定小数精度。 
@Range(min=, max=) 被指定的元素必须在合适的范围内 
@Range(min=10000,max=50000,message=”range.bean.wage”) 
@Valid 递归的对关联对象进行校验, 如果关联对象是个集合或者数组,那么对其中的元素进行递归校验,如果是一个map,则对其中的值部分进行校验.(是否进行递归验证) 
@CreditCardNumber信用卡验证 
@Email 验证是否是邮件地址，如果为null,不进行验证，算通过验证。 
@ScriptAssert(lang= ,script=, alias=) 
@URL(protocol=,host=, port=,regexp=, flags=)
~~~

![image-20220731230241015](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731230241015.png)

然后就可以看注释了：

![image-20220731230303988](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220731230303988.png)

### 自定义校验注解

![image-20220801131900826](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801131900826.png)

~~~java
/*注解本体
*/
package com.atguigu.gulimall.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ListValuesConstraintValidator.class })//一个注解可以有多个检验器
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ListValues {
    String message() default "{com.atguigu.gulimall.common.valid.ListValues.message}";//默认错误信息

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    int[] vals() default {};
}

~~~

~~~properties
com.atguigu.gulimall.common.valid.ListValues.message=必须提交指定值  #ValidationMessages.properties，为注解提供错误提示信息
~~~

~~~java
package com.atguigu.gulimall.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValuesConstraintValidator implements ConstraintValidator<ListValues,Integer> {
    private Set<Integer> set = new HashSet<>();
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
    @Override
    public void initialize(ListValues constraintAnnotation) {

        int[] vals = constraintAnnotation.vals();

        for (int val : vals) {
            set.add(val);
        }

    }
}
/*对注解的处理的关联校验器。
~~~



## 未解决bug:（8.1已解决）

### 1声明在公共模块中的全局异常处理失效，但包扫描是已经配置好的。

放到自己的模块中就可以，放到公共模块却失效了。

![image-20220801164915277](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801164915277.png)

![image-20220801165146295](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801165146295.png)

![image-20220801165225329](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801165225329.png)

![image-20220801165447239](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801165447239.png)

直接把RRExceptionHandler删掉就ok了。

## 父子组件通信

![image-20220801224347026](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801224347026.png)

![image-20220801224545787](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801224545787.png)

![image-20220801225756779](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801225756779.png)

![image-20220801230048850](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801230048850.png)

![image-20220801230134592](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801230134592.png)

![image-20220801230158347](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220801230158347.png)

## 人人后台登录界面控制台报访问sys/menu跨域错误

bug:在后端服务器网关已经配置了允许跨域访问并且也删除了人人后台模块里面自带的跨域配置类的情况下（保证全局只有一个跨域配置），在访问人人后台登录时仍然会报错：Access to XMLHttpRequest at 'http://localhost:8000/api/renren-fast/sys/menu/nav?t=1659409509905' from origin 'http://localhost:9000' has been blocked by CORS policy: The 'Access-Control-Allow-Origin' header contains multiple values 'http://localhost:9000, http://localhost:9000', but only one is allowed.

![image-20220802110616332](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802110616332.png)

虽然不影响使用，但是还是看着很膈应。

于是就想办法解决：

我们直接在项目中搜索访问的地址后缀/sys/menu/nav,发现在路由的index文件中发送了这个请求：

![image-20220802105430070](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802105430070.png)

![image-20220802110406703](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802110406703.png)

![image-20220802114945073](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802114945073.png)

![image-20220802115100267](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802115100267.png)

![image-20220802115603895](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802115603895.png)



![image-20220802110454698](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802110454698.png)

http://localhost:8000/api/renren-fast/sys/menu/nav?t=1659409509905

t=1659409509905就是默认参数

就是这里还没有登录就发送了这个请求导致报错，应该是被后台shiro拦截了（我猜测，因为我没用过shiro，因为我后端只在网关配置了跨域，人人自带的跨域CrosConfig类也删了，所以全局理论就只有一个跨域配置了）,有知道的大佬可以帮忙解释一下，谢谢。

不过，这并不影响问题的解决，既然是登录时就发送了请求被拒绝了，那么我们就让它登录之后获取到token后再发送嘛：

![image-20220802111717784](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802111717784.png)

![image-20220802111832358](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802111832358.png)

![image-20220802111938554](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802111938554.png)

当然，这个默认参数t其实并没有什么用，可以关闭

![image-20220802112103985](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802112103985.png)

![image-20220802112217901](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802112217901.png)

## @JsonInclude(JsonInclude.Include.NON_EMPTY)

![image-20220802140454294](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802140454294.png)

![image-20220802140606023](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802140606023.png)

## idea里面的注释

单行：ctrl+/

多行：ctrl+shift+/

## String并没有重写toString方法

![image-20220802184212182](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802184212182.png)

## 大数据下不建议连表查询

![image-20220802220515877](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220802220515877.png)

~~~java
 public PageUtils queryBaseAttrPage(Map<String, Object> params, String cateLogId) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
            	new QueryWrapper<AttrEntity>().eq(!cateLogId.equals("0"),"catelog_id",cateLogId).like(!StringUtils.isEmpty(params.get("key")),"attr_name",params.get("key")).or().like(!StringUtils.isEmpty(params.get("key")),"attr_id",params.get("key"))
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrRespVo> respVos = page.getRecords().stream().map(attr -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attr, attrRespVo);
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationService.getOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attr.getAttrId()));
            if (relationEntity != null) {
                Long attrGroupId = relationEntity.getAttrGroupId();
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attr.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(respVos);
        return pageUtils;
~~~

## 查询属性分组未关联的属性

要求是属于当前属性分组的分类下的属性，且不包括已经被自己关联的属性和被当前分类下其它分组关联的属性。

![image-20220803225842189](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220803225842189.png)

![image-20220803230758162](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220803230758162.png)



~~~java
public PageUtils queryNoRelationAttr(String attrgroupId, Map<String, Object> params) {
        AttrGroupEntity attrGroupEntity = this.getById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        List<AttrGroupEntity> otherAttrGroupList = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> usedAttrIds = new ArrayList<>();
        if (otherAttrGroupList.size()>0){
            otherAttrGroupList.stream().map((item) -> {
                List<AttrAttrgroupRelationEntity> list = attrAttrgroupRelationService.list(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", item.getAttrGroupId()));
                list.forEach(attrAttrgroupRelationEntity -> {
                    usedAttrIds.add(attrAttrgroupRelationEntity.getAttrId());
                });
                return null;
            }).collect(Collectors.toList());
        }
        IPage<AttrEntity> page = attrService.page(new Query<AttrEntity>().getPage(params), new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).notIn(usedAttrIds.size() > 0, "attr_id", usedAttrIds).eq(!StringUtils.isEmpty(params.get("key")), "attr_id", params.get("key")).or().like(!StringUtils.isEmpty(params.get("key")), "attr_name", params.get("key")));
        return new PageUtils(page);
    }
~~~

![image-20220803230106092](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220803230106092.png)

## 接口文档地址

https://easydoc.net/doc/75716633/ZUqEdvA4/VhgnaedC

## 引入openfeign的坑

![image-20220804201555948](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804201555948.png)

![image-20220804201711918](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804201711918.png)

报错缺失的类就在spring-boot-starter-web:2.3.7.RELEASE里面有，且这个包我们已经在项目中引入了的，所以只有一个可能，那就是因为openfeign的starter引入的太早了，先进行了自动装配，但是此时web还没有自动装配，所以报错了。

所以依赖在pom文件的引入先后是有顺序的。应该先引入web的场景启动器，再引入依赖了web的其它场景启动器。

![image-20220804202239860](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804202239860.png)

##  npm install --save pubsub-js

![image-20220804221910561](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804221910561.png)

## renren-fast-vue项目部署后npm无法使用

![image-20220804223748240](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804223748240.png)

https://nodejs.org/zh-cn/download/releases/对照表

![image-20220804223950428](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804223950428.png)

npm install node-sass报错问题的解决

![image-20220804224159621](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804224159621.png)

![image-20220804224233561](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804224233561.png)

![image-20220804224319029](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220804224319029.png)

## this.$还是this.取决于你如何设置原型对象的属性

![image-20220806162453393](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220806162453393.png)

![image-20220806162519070](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220806162519070.png)

![image-20220806162539886](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220806162539886.png)

## centos设置静态ip

VM和CentOS7安装参考: VM15安装、CentOS7安装

当我们安装好CentOS7后, 要分配一个ip给系统, 进入root权限, 使用dhclient动态分配一个ip地址给linux系统; 但是这样只是动态的分配一个, 下次启动后该ip就不可用了, 此时就需要将这个ip静态设置该这个系统;

设置静态ip, 首先进入vi /etc/sysconfig/network-scripts/ifcfg-ens33 编辑该文件, 修改为如下:

~~~vim
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
UUID=046cbff7-5153-4d7e-b10f-d872c098260e
DEVICE=ens33
ONBOOT=yes
IPADDR=192.168.61.132
NETMASK=255.255.255.0
GATEWAY=192.168.61.2
DNS1=119.29.29.29
DNS2=202.96.128.166
PEERROUTES=yes
IPV6_PEERDNS=yes
IPV6_PEERROUTES=yes
~~~

![image-20220806175542605](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220806175542605.png)

## 可以用map接收请求体以及请求参数

但不建议，因为不易维护啊

![image-20220806215113861](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220806215113861.png)

## 发布商品功能

![image-20220806224037790](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220806224037790.png)

## feign客户端里面的方法参数和远程调用的参数可以不是同一个TO

![image-20220806235553642](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220806235553642.png)

## 商品添加前端bug

如何解决添加完一个商品之后，回到第一步重新添加时，页面中仍然显示上一个商品中的数据：

![image-20220808132501468](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808132501468.png)

~~~javascript
resetSpuData() {
      this.spu = {
        catlogPath: [],
        spuName: "",
        spuDescription: "",
        catalogId: 0,
        brandId: "",
        weight: "",
        publishStatus: 0,
        decript: [],
        images: [],
        bounds: {
          buyBounds: 0,
          growBounds: 0
        },
        baseAttrs: [],
        skus: []
      };
      this.dataResp={
        //后台返回的所有数据
        attrGroups: [],
        baseAttrs: [],
        saleAttrs: [],
        tempSaleAttrs: [],
        tableAttrColumn: [],
        memberLevels: [],
        steped: [false, false, false, false, false]
      }
    }
~~~

注意，不仅要重置spu的数据，还要重置dataResp中的数据，否则会在后面的步骤中仍然包含上一个添加商品的数据

![image-20220808132641290](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808132641290.png)

并且，为了让这里也置空，我们做如下操作。

![image-20220808132753351](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808132753351.png)

![image-20220808132917271](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808132917271.png)

![image-20220808133031582](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808133031582.png)

![image-20220808133315057](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808133315057.png)

![image-20220808133500388](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808133500388.png)

![image-20220808134023213](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808134023213.png)

![image-20220808134237213](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808134237213.png)

![image-20220808191439405](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808191439405.png)

## Field ‘spu_id‘ doesn‘t have a default valu 解决办法

![image-20220808180443380](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808180443380.png)

![image-20220808180451197](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808180451197.png)

## 商品保存类业务的注意点：

![image-20220808180930844](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220808180930844.png)

~~~java
@Transactional
    @Override
    public void saveSpu(SpuSaveVo spuInfo) {
        //1、保存spu基本信息：pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfo,spuInfoEntity);
        this.save(spuInfoEntity);
        //2、保存spu的描述图片：pms_spu_info_desc
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        List<String> desc = spuInfo.getDecript();
        String join = String.join(",", desc);
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(join);
        spuInfoDescService.save(spuInfoDescEntity);

        //3、保存spu的图片集：pms_spu_images
        List<String> images = spuInfo.getImages();
        if (images!=null&&images.size()>0){
            List<SpuImagesEntity> collect = images.stream().map(item -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(spuInfoEntity.getId());
                spuImagesEntity.setImgUrl(item);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            spuImagesService.saveBatch(collect);
        }

        //4、保存spu的规格参数：pms_product_attr_value
        List<BaseAttrs> baseAttrs = spuInfo.getBaseAttrs();
        if (baseAttrs!=null && baseAttrs.size()>0){
            List<ProductAttrValueEntity> attrValueEntities = baseAttrs.stream().map(item -> {
                ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
                productAttrValueEntity.setSpuId(spuInfoEntity.getId());
                productAttrValueEntity.setAttrId(item.getAttrId());
                productAttrValueEntity.setAttrValue(item.getAttrValues());
                AttrEntity attrEntity = attrService.getById(item.getAttrId());
                productAttrValueEntity.setAttrName(attrEntity.getAttrName());
                productAttrValueEntity.setQuickShow(item.getShowDesc());
                return productAttrValueEntity;
            }).collect(Collectors.toList());
            productAttrValueService.saveBatch(attrValueEntities);
        }

        //5、保存spu的积分信息：gulimall_sms--->sms_spu_bounds
        Bounds bounds = spuInfo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds,spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        couponFeignClient.save(spuBoundTo);

        //6、保存当前spu对应的所有sku信息：pms_sku_info
        //6、1）、sku的基本信息:pms_sku_info
        List<Skus> skus = spuInfo.getSkus();
        if (skus!=null&&skus.size()>0){
            skus.forEach(sku->{
                String defaultImg="";
                for (Images img:sku.getImages()) {
                    if (img.getDefaultImg() == 1){
                        defaultImg = img.getImgUrl();
                    }
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(sku,skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoService.save(skuInfoEntity);
                Long skuId = skuInfoEntity.getSkuId();
                if (sku.getImages()!=null&&sku.getImages().size()>0){
                    List<SkuImagesEntity> skuImagesEntities = sku.getImages().stream().map(img -> {
                        SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                        skuImagesEntity.setSkuId(skuId);
                        skuImagesEntity.setImgUrl(img.getImgUrl());
                        skuImagesEntity.setDefaultImg(img.getDefaultImg());
                        return skuImagesEntity;
                    }).filter(item -> {
                        return !StringUtils.isEmpty(item.getImgUrl());
                    }).collect(Collectors.toList());
                    if (skuImagesEntities!=null&&skuImagesEntities.size()>0){
                        //6、2）、sku的图片信息：pms_sku_images
                        skuImagesService.saveBatch(skuImagesEntities);
                    }
                }
                //6、3）、sku的销售属性：pms_sku_sale_attr_value
                List<Attr> attrs = sku.getAttr();
                if (attrs!=null&&attrs.size()>0){
                    List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attrs.stream().map(attr -> {
                        SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                        skuSaleAttrValueEntity.setSkuId(skuId);
                        BeanUtils.copyProperties(attr, skuSaleAttrValueEntity);
                        return skuSaleAttrValueEntity;
                    }).collect(Collectors.toList());
                    if (skuSaleAttrValueEntities.size()>0){
                        skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);
                    }
                }
                //6、4）、sku的优惠、满减等信息：gulimall_sms--->sms_sku_ladder、sms_sku_full_reduction、sms_member_price
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(sku,skuReductionTo);
                System.out.println(skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount()>0&&skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO)==1){
                    couponFeignClient.saveInfo(skuReductionTo);
                }
            });
        }
    }
~~~



![image-20220809145103907](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220809145103907.png)

## 修改spu规格参数路由跳转404

![image-20220809211628025](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220809211628025.png)

点规格，404，前端改一下：
/src/router/index.js 在mainRoutes->children里面加上：{ path: ’/product-attrupdate’, component: _import(‘modules/product/attrupdate’), name: ‘attr-update’, meta: { title: ‘规格维护’, isTab: true } }
![image-20220809211724014](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220809211724014.png)

## 人人前端动态产生路由，代码很精髓啊

~~~js
/**
 * 全站路由配置
 *
 * 建议:
 * 1. 代码中路由统一使用name属性跳转(不使用path属性)
 */
import Vue from 'vue'
import Router from 'vue-router'
import http from '@/utils/httpRequest'
import { isURL } from '@/utils/validate'
import { clearLoginInfo } from '@/utils'


Vue.use(Router)

// 开发环境不使用懒加载, 因为懒加载页面太多的话会造成webpack热更新太慢, 所以只有生产环境使用懒加载
const _import = require('./import-' + process.env.NODE_ENV)

// 全局路由(无需嵌套上左右整体布局)
const globalRoutes = [
  { path: '/404', component: _import('common/404'), name: '404', meta: { title: '404未找到' } },
  { path: '/login', component: _import('common/login'), name: 'login', meta: { title: '登录' } }
]

// 主入口路由(需嵌套上左右整体布局)
const mainRoutes = {
  path: '/',
  component: _import('main'),
  name: 'main',
  redirect: { name: 'home' },
  meta: { title: '主入口整体布局' },
  children: [
    // 通过meta对象设置路由展示方式
    // 1. isTab: 是否通过tab展示内容, true: 是, false: 否
    // 2. iframeUrl: 是否通过iframe嵌套展示内容, '以http[s]://开头': 是, '': 否
    // 提示: 如需要通过iframe嵌套展示内容, 但不通过tab打开, 请自行创建组件使用iframe处理!
    { path: '/home', component: _import('common/home'), name: 'home', meta: { title: '首页' } },
    { path: '/theme', component: _import('common/theme'), name: 'theme', meta: { title: '主题' } },
    { path: '/demo-echarts', component: _import('demo/echarts'), name: 'demo-echarts', meta: { title: 'demo-echarts', isTab: true } },
    { path: '/demo-ueditor', component: _import('demo/ueditor'), name: 'demo-ueditor', meta: { title: 'demo-ueditor', isTab: true } },
    { path: '/product-attrupdate', component: _import('modules/product/attrupdate'), name: 'attr-update', meta: { title: '规格维护', isTab: true } }
  ],
  beforeEnter (to, from, next) {
    let token = Vue.cookie.get('token')
    if (!token || !/\S/.test(token)) {
      clearLoginInfo()
      next({ name: 'login' })
    }
    next()
  }
}

const router = new Router({
  mode: 'hash',
  scrollBehavior: () => ({ y: 0 }),
  isAddDynamicMenuRoutes: false, // 是否已经添加动态(菜单)路由
  routes: globalRoutes.concat(mainRoutes)
})

router.beforeEach((to, from, next) => {
  // 添加动态(菜单)路由
  // 1. 已经添加 or 全局路由, 直接访问
  // 2. 获取菜单列表, 添加并保存本地存储
  if (router.options.isAddDynamicMenuRoutes || fnCurrentRouteType(to, globalRoutes) === 'global') {
    next()
  } else {
    router.push({ name: 'login' })
    if (Vue.cookie.get("token")){
      http({
        url: http.adornUrl('/sys/menu/nav'),
        method: 'get',
        params: http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          fnAddDynamicMenuRoutes(data.menuList)
          router.options.isAddDynamicMenuRoutes = true
          sessionStorage.setItem('menuList', JSON.stringify(data.menuList || '[]'))
          sessionStorage.setItem('permissions', JSON.stringify(data.permissions || '[]'))
          next({ ...to, replace: true })
        } else {
          sessionStorage.setItem('menuList', '[]')
          sessionStorage.setItem('permissions', '[]')
          next()
        }
      }).catch((e) => {
        console.log(`%c${e} 请求菜单列表和权限失败，跳转至登录页！！`, 'color:blue')
      })
    }
    }

})

/**
 * 判断当前路由类型, global: 全局路由, main: 主入口路由
 * @param {*} route 当前路由
 */
function fnCurrentRouteType (route, globalRoutes = []) {
  var temp = []
  for (var i = 0; i < globalRoutes.length; i++) {
    if (route.path === globalRoutes[i].path) {
      return 'global'
    } else if (globalRoutes[i].children && globalRoutes[i].children.length >= 1) {
      temp = temp.concat(globalRoutes[i].children)
    }
  }
  return temp.length >= 1 ? fnCurrentRouteType(route, temp) : 'main'
}

/**
 * 添加动态(菜单)路由
 * @param {*} menuList 菜单列表
 * @param {*} routes 递归创建的动态(菜单)路由
 */
function fnAddDynamicMenuRoutes (menuList = [], routes = []) {
  var temp = []
  for (var i = 0; i < menuList.length; i++) {
    if (menuList[i].list && menuList[i].list.length >= 1) {
      temp = temp.concat(menuList[i].list)
    } else if (menuList[i].url && /\S/.test(menuList[i].url)) {
      menuList[i].url = menuList[i].url.replace(/^\//, '')
      var route = {
        path: menuList[i].url.replace('/', '-'),
        component: null,
        name: menuList[i].url.replace('/', '-'),
        meta: {
          menuId: menuList[i].menuId,
          title: menuList[i].name,
          isDynamic: true,
          isTab: true,
          iframeUrl: ''
        }
      }
      // url以http[s]://开头, 通过iframe展示
      if (isURL(menuList[i].url)) {
        route['path'] = `i-${menuList[i].menuId}`
        route['name'] = `i-${menuList[i].menuId}`
        route['meta']['iframeUrl'] = menuList[i].url
      } else {
        try {
          route['component'] = _import(`modules/${menuList[i].url}`) || null
        } catch (e) {}
      }
      routes.push(route)
    }
  }
  if (temp.length >= 1) {
    fnAddDynamicMenuRoutes(temp, routes)
  } else {
    mainRoutes.name = 'main-dynamic'
    mainRoutes.children = routes
    router.addRoutes([
      mainRoutes,
      { path: '*', redirect: { name: '404' } }
    ])
    sessionStorage.setItem('dynamicMenuRoutes', JSON.stringify(mainRoutes.children || '[]'))
    console.log('\n')
    console.log('%c!<-------------------- 动态(菜单)路由 s -------------------->', 'color:blue')
    console.log(mainRoutes.children)
    console.log('%c!<-------------------- 动态(菜单)路由 e -------------------->', 'color:blue')
  }
}

export default router
~~~

## 关于spu规格维护的bug

![image-20220810134657528](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810134657528.png)

![image-20220810141239645](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810141239645.png)

![image-20220810141323282](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810141323282.png)

![image-20220810141752949](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810141752949.png)

解决方案：

![image-20220810142502552](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810142502552.png)

![image-20220810142717399](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810142717399.png)

![image-20220810142839818](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810142839818.png)

![image-20220810142928589](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810142928589.png)

## 前端Uncaught (in promise) cancel

![image-20220810175445035](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810175445035.png)

![image-20220810175614289](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220810175614289.png)

## feign客户端接口最好加上参数注解

![image-20220812075255631](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220812075255631.png)

## 安装elastic search

 dokcer中安装elastic search

（1）下载ealastic search和kibana

```shell
docker pull elasticsearch:7.6.2
docker pull kibana:7.6.2
```

（2）配置

```shell
mkdir -p /mydata/elasticsearch/config  创建目录
mkdir -p /mydata/elasticsearch/data
echo "http.host: 0.0.0.0" >/mydata/elasticsearch/config/elasticsearch.yml

//将mydata/elasticsearch/文件夹中文件都可读可写
chmod -R 777 /mydata/elasticsearch/
```



（3）启动Elastic search

centos7一定不要忘了加--privileged=true

```shell
docker run --name elasticsearch --privileged=true -p 9200:9200 -p 9300:9300 \
-e  "discovery.type=single-node" \
-e ES_JAVA_OPTS="-Xms64m -Xmx512m" \
-v /mydata/elasticsearch/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
-v /mydata/elasticsearch/data:/usr/share/elasticsearch/data \
-v  /mydata/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
-d elasticsearch:7.6.2 
```

设置开机启动elasticsearch

```shell
docker update elasticsearch --restart=always
```



（4）启动kibana：

```shell
docker run --name kibana -d -p 5601:5601 --link elasticsearch -e "ELASTICSEARCH_URL=http://192.168.61.132:9200" kibana:7.6.2

```

设置开机启动kibana

```shell
docker update kibana  --restart=always
```





（5）测试

查看elasticsearch版本信息： http://192.168.61.132:9200/ 

```json
{
    "name": "0adeb7852e00",
    "cluster_name": "elasticsearch",
    "cluster_uuid": "9gglpP0HTfyOTRAaSe2rIg",
    "version": {
        "number": "7.6.2",
        "build_flavor": "default",
        "build_type": "docker",
        "build_hash": "ef48eb35cf30adf4db14086e8aabd07ef6fb113f",
        "build_date": "2020-03-26T06:34:37.794943Z",
        "build_snapshot": false,
        "lucene_version": "8.4.0",
        "minimum_wire_compatibility_version": "6.8.0",
        "minimum_index_compatibility_version": "6.0.0-beta1"
    },
    "tagline": "You Know, for Search"
}
```



显示elasticsearch 节点信息http://192.168.61.132:9200/_cat/nodes ，

```json
127.0.0.1 76 95 1 0.26 1.40 1.22 dilm * 0adeb7852e00
```



访问Kibana： http://192.168.61.132:5601/app/kibana 

![image-20200501192629304](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20200501192629304.png)

## es的restful风格操纵数据

![image-20220813214814081](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220813214814081.png)

index是随机id的不指定,create是指定id的

index操作会覆盖文档，而create操作则不会。

## es测试数据地址

https://gitee.com/xlh_blog/common_content/blob/master/es%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE.json#

## es检索

### 1）search Api

ES支持两种基本方式检索；

* 通过REST request uri 发送搜索参数 （uri +检索参数）；
* 通过REST request body 来发送它们（uri+请求体）；

![image-20220813220711506](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220813220711506.png)

![image-20220813221955914](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220813221955914.png)

![image-20220813232255662](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220813232255662.png)

![image-20220814083642411](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814083642411.png)

![image-20220814084028854](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814084028854.png)

![image-20220814084428768](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814084428768.png)

![image-20220814085203007](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814085203007.png)

![image-20220814085534108](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814085534108.png)

![image-20220814085636946](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814085636946.png)

![image-20220814090145594](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814090145594.png)

![image-20220814091233376](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814091233376.png)

![image-20220814091456311](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814091456311.png)

![image-20220814092150061](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814092150061.png)

![image-20220814094425560](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814094425560.png)

![image-20220814094842490](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814094842490.png)

![image-20220814095254729](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814095254729.png)

![image-20220814095738113](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814095738113.png)

![image-20220814163316513](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814163316513.png)

### 2）数据迁移

先创建new_twitter的正确映射。然后使用如下方式进行数据迁移。

```json
POST reindex [固定写法]
{
  "source":{
      "index":"twitter"
   },
  "dest":{
      "index":"new_twitters"
   }
}
```



将旧索引的type下的数据进行迁移

```json
POST reindex [固定写法]
{
  "source":{
      "index":"twitter",
      "twitter":"twitter"
   },
  "dest":{
      "index":"new_twitters"
   }
}
```

更多详情见： https://www.elastic.co/guide/en/elasticsearch/reference/7.6/docs-reindex.html 

### 3）自定义词库

* 修改/usr/share/elasticsearch/plugins/ik/config中的IKAnalyzer.cfg.xml
  /usr/share/elasticsearch/plugins/ik/config

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
	<comment>IK Analyzer 扩展配置</comment>
	<!--用户可以在这里配置自己的扩展字典 -->
	<entry key="ext_dict"></entry>
	 <!--用户可以在这里配置自己的扩展停止词字典-->
	<entry key="ext_stopwords"></entry>
	<!--用户可以在这里配置远程扩展字典 -->
	<entry key="remote_ext_dict">http://192.168.61.132/es/fenci.txt</entry> 
	<!--用户可以在这里配置远程扩展停止词字典-->
	<!-- <entry key="remote_ext_stopwords">words_location</entry> -->
</properties>
```

原来的xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
	<comment>IK Analyzer 扩展配置</comment>
	<!--用户可以在这里配置自己的扩展字典 -->
	<entry key="ext_dict"></entry>
	 <!--用户可以在这里配置自己的扩展停止词字典-->
	<entry key="ext_stopwords"></entry>
	<!--用户可以在这里配置远程扩展字典 -->
	<!-- <entry key="remote_ext_dict">words_location</entry> -->
	<!--用户可以在这里配置远程扩展停止词字典-->
	<!-- <entry key="remote_ext_stopwords">words_location</entry> -->
</properties>

```

修改完成后，需要重启elasticsearch容器，否则修改不生效。

更新完成后，es只会对于新增的数据用更新分词。历史数据是不会重新分词的。如果想要历史数据重新分词，需要执行：

```shell
POST my_index/_update_by_query?conflicts=proceed
```





http://192.168.6.128/es/fenci.txt，这个是nginx上资源的访问路径

在运行下面实例之前，需要安装nginx（安装方法见安装nginx），然后创建“fenci.txt”文件，内容如下：

```shell
echo "樱桃萨其马，带你甜蜜入夏" > /mydata/nginx/html/fenci.txt 
```

## 打包插件

![image-20220814232834695](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814232834695.png)

![image-20220814232939748](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814232939748.png)

![image-20220814233112405](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220814233112405.png)

## springboot整合es

bootstrap.yml

~~~yml
spring:
  elasticsearch:
    rest:
      uris: http://192.168.61.132:9200
  application:
    name: gulimall-elasticsearch
  cloud:
    inetutils:
      ignored-interfaces: 'VMware Virtual Ethernet Adapter for VMnet1,VMware Virtual Ethernet Adapter for VMnet8'
    nacos:
      discovery:
        server-addr: localhost:8848
server:
  port: 8008
~~~

~~~xml
  <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>7.6.2</version>
        </dependency>
~~~

## 防止es出现扁平化错误

对于内部的对象使用type:nested

![image-20220815102640226](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220815102640226.png)

![image-20220815102808887](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220815102808887.png)

## 检索和查询

例：不能从搜索框找某个图片（检索），但是查找华为的时候会有华为的图片（查询）

~~~json
PUT product
{
  "mappings":{
    "properties":{
      "skuId":{
        "type":"long"
      },
      "spuId":{
        "type":"keyword"
      },
      "skuTitle":{
        "type": "text",
        "analyzer": "ik_smart"
      },
      "skuPrice":{
        "type": "keyword"
      },
      "skuImg":{
        "type": "keyword",
        "index": false, //不支持检索
        "doc_values": false//不支持聚合
      },
      "saleCount":{
        "type": "long"
      },
      "hasStock":{
        "type":"boolean"
      },
      "hotScore":{
        "type": "long"
      },
      "brandId":{
        "type": "long"
      },
      "brandName":{
        "type": "keyword",
        "index":false,
        "doc_values": false
      },
      "brandImg":{
        "type": "keyword",
        "index": false,
        "doc_values": false
      },
      "catelogName":{
        "type": "keyword", 
        "index": false,
        "doc_values": false
      },
      "attrs":{
        "type": "nested",
        "properties": {
          "attrId":{
            "type":"long"
          },
          "attrName":{
            "type":"keyword",
            "index":false,
            "doc_value":false
          },
          "attrValue":{
            "type":"keyword"
          }
        }
      }
    }
  }
}
~~~

## es里面text和keyword的区别

ElasticSearch 5.0以后，string类型有重大变更，移除了string类型，string字段被拆分成两种新的数据类型: text用于全文搜索的,而keyword用于关键词搜索。

ElasticSearch字符串将默认被同时映射成text和keyword类型，将会自动创建下面的动态映射(dynamic mappings):

~~~json
{
    "foo": {
        "type": "text",
        "fields": {
            "keyword": {
                "type": "keyword",
                "ignore_above": 256
            }
        }
    }
}
~~~

这就是造成部分字段还会自动生成一个与之对应的“.keyword”字段的原因。

**Text：**
 会分词，然后进行索引
 支持模糊、精确查询
 不支持聚合
 **keyword：**
 不进行分词，直接索引
 支持模糊、精确查询
 支持聚合

## 如何将Object转换为指定的类对象

~~~java
R r = wareFeignClient.getSkuHasStock(skuIds);
List<WareHasStockTo> data = r.getData(new TypeReference<List<WareHasStockTo>>() {
});
stockMap = data.stream().collect(Collectors.toMap(WareHasStockTo::getSkuId, item -> item.getHasStock()));
~~~

```java
public<T> T getData(TypeReference<T> typeReference){
		Object data = get("data");
		String s = JSON.toJSONString(data);
		T t = JSON.parseObject(s, typeReference);
		return t;
	}
```

## thymeleaf

![image-20220816151835691](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220816151835691.png)

## 关于maven公共模块与其它模块间依赖的引入

![image-20220816154721931](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220816154721931.png)

每次要重新package时，先install一下公共模块。

最好不要将某个特定模块中使用的依赖放到公共模块，防止其它模块打包时依赖冗余。

## 正向代理和反向代理

![image-20220816182730722](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220816182730722.png)

## nginx配置文件

nginx配置文件里面的每一行记得加分号！！！

![image-20220816184921708](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220816184921708.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817090005542.png)

![image-20220817110121108](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817110121108.png)



## 前台检索页json数据模型

![image-20220816210705167](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220816210705167.png)

## 获取前端json数据的接口业务代码

~~~java
public Map<String, List<Catelog2Vo>> getIndexJsonData() {
        List<CategoryEntity> totalList = this.list();
        List<CategoryEntity> list = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0).select("cat_id"));
        Map<String, List<Catelog2Vo>> map = list.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {//一开始就应该用map直接收集，指定key和value。
            AtomicReference<Integer> indexForCate2 = new AtomicReference<>(1);
            AtomicReference<Integer> indexForCate3 = new AtomicReference<>(1);
            List<Catelog2Vo> catelog2VoList = totalList.stream().filter(cate -> {
                return cate.getParentCid().equals(v.getCatId());
            }).map((forCate2) -> {
                Catelog2Vo catelog2Vo = new Catelog2Vo();
                catelog2Vo.setCatelog1Id(v.getCatId().toString());
                catelog2Vo.setId(indexForCate2.toString());
                indexForCate2.getAndSet(indexForCate2.get() + 1);
                catelog2Vo.setName(forCate2.getName());
                List<Catelog2Vo.Catelog3Vo> catelog3VoList = totalList.stream().filter(cate -> {
                    return cate.getParentCid().equals(forCate2.getCatId());
                }).map(forCate3 -> {
                    Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo();
                    catelog3Vo.setCatelog2Id(forCate2.getCatId().toString());
                    catelog3Vo.setId(indexForCate3.toString());
                    catelog3Vo.setName(forCate3.getName());
                    indexForCate3.getAndSet(indexForCate3.get() + 1);
                    return catelog3Vo;
                }).collect(Collectors.toList());
                catelog2Vo.setCatelog3List(catelog3VoList);
                return catelog2Vo;
            }).collect(Collectors.toList());
            return catelog2VoList;
        }));
        return map;
    }
~~~

## host文件无法修改

![image-20220816233521069](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220816233521069.png)

![image-20220816233538998](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220816233538998.png)

![image-20220816233636276](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220816233636276.png)

## VM Option

-Xms 堆内存的初始大小，默认为物理内存的1/64。 -Xmx 堆内存的最大大小，默认为物理内存的1/4~1/2。 -Xmn 堆内新生代的大小。通过这个值也可以得到老生代的大小：-Xmx减去-Xmn。

-[Xss](https://so.csdn.net/so/search?q=Xss&spm=1001.2101.3001.7020) 设置每个线程可使用的内存大小，即栈的大小。

## redis堆外内存溢出

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817144830657.png)

## 缓存问题/redis作分布式锁

![image-20220817145246609](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817145246609.png)

![image-20220817145551871](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817145551871.png)

![image-20220817145913163](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817145913163.png)

![image-20220817151142972](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817151142972.png)

![image-20220817152325295](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817152325295.png)

![image-20220817152723310](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817152723310.png)

![image-20220817193728604](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817193728604.png)

![image-20220817194235486](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817194235486.png)

![image-20220817194320834](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817194320834.png)

![image-20220817195217719](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817195217719.png)

![image-20220817200309486](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817200309486.png)

![image-20220817200811303](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817200811303.png)

![image-20220817201134710](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817201134710.png)

![image-20220817201618559](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817201618559.png)

### 分布式锁代码

~~~java
 @Override
    public Map<String, List<Catelog2Vo>> getIndexJsonData() {
        String catelogJSON = redisTemplate.opsForValue().get("catelogJSON");
        if (StringUtils.isEmpty(catelogJSON)) {
            Map<String, List<Catelog2Vo>> indexJsonDataFromDb = getIndexJsonDataFromDbWithRedisLock();
            String s = JSON.toJSONString(indexJsonDataFromDb);
            redisTemplate.opsForValue().set("catelogJSON", s);
            return indexJsonDataFromDb;
        }
        System.out.println("缓存命中");
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catelogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });
        return result;
    }


    public Map<String, List<Catelog2Vo>> getIndexJsonDataFromDbWithRedisLock(){
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 300, TimeUnit.SECONDS);
        if (lock){
            System.out.println("获取分布式锁成功");
            Map<String, List<Catelog2Vo>> indexJsonDataFromDb;
            try {
                indexJsonDataFromDb = getIndexJsonDataFromDb();
            }finally {
                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
                redisTemplate.execute(new DefaultRedisScript<Long>(script,Long.class),Arrays.asList("lock"),uuid);
            }
            return indexJsonDataFromDb;
        }else {
            System.out.println("获取分布式锁失败...等待重试");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getIndexJsonDataFromDbWithRedisLock();
        }
    }

//从数据库查询并封装数据
    public Map<String, List<Catelog2Vo>> getIndexJsonDataFromDb() {
        String catelogJSON = redisTemplate.opsForValue().get("catelogJSON");
        if (StringUtils.isEmpty(catelogJSON)){
            System.out.println("查询数据库");
            List<CategoryEntity> totalList = this.list();
            List<CategoryEntity> list = this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0).select("cat_id"));
            Map<String, List<Catelog2Vo>> map = list.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                AtomicReference<Integer> indexForCate2 = new AtomicReference<>(1);
                AtomicReference<Integer> indexForCate3 = new AtomicReference<>(1);
                List<Catelog2Vo> catelog2VoList = totalList.stream().filter(cate -> {
                    return cate.getParentCid().equals(v.getCatId());
                }).map((forCate2) -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo();
                    catelog2Vo.setCatelog1Id(v.getCatId().toString());
                    catelog2Vo.setId(indexForCate2.toString());
                    indexForCate2.getAndSet(indexForCate2.get() + 1);
                    catelog2Vo.setName(forCate2.getName());
                    List<Catelog2Vo.Catelog3Vo> catelog3VoList = totalList.stream().filter(cate -> {
                        return cate.getParentCid().equals(forCate2.getCatId());
                    }).map(forCate3 -> {
                        Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo();
                        catelog3Vo.setCatelog2Id(catelog2Vo.getId());
                        catelog3Vo.setId(forCate3.getCatId().toString());
                        catelog3Vo.setName(forCate3.getName());
                        indexForCate3.getAndSet(indexForCate3.get() + 1);
                        return catelog3Vo;
                    }).collect(Collectors.toList());
                    catelog2Vo.setCatelog3List(catelog3VoList);
                    return catelog2Vo;
                }).collect(Collectors.toList());
                return catelog2VoList;
            }));
            String s = JSON.toJSONString(map);
            redisTemplate.opsForValue().set("catelogJSON",s);
            return map;
        }
        Map<String, List<Catelog2Vo>> result = JSON.parseObject(catelogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
        return result;
    }
~~~



## 程序多开

![image-20220817153305027](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817153305027.png)

![image-20220817153238516](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817153238516.png)

![image-20220817230917560](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220817230917560.png)

## Redisson

### 可重入锁

![image-20220818092344409](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818092344409.png)

redisson的锁会自动续期，如果业务超长，运行期间会自动续上新的时长，保证锁不会被自动过期被删掉。

这里讲一下，当你宕机了，你宕机的那个设备还有看门狗嘛？所以宕机是没有看门狗给他续期的，他会自动过期。

如果线程段了，就不会续期了，到了30s之后就会被释放。如果业务时间很长，看门狗发现还在运行，才会续期。

自动续期是业务用时过长，超过了锁过期时间，会自动续期，但是面临宕机或异常中断时，就不会自动续期，而是在锁过期时间到了就自动释放了。

![image-20220818103151944](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818103151944.png)

![image-20220818094355026](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818094355026.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818095634211.png)

![image-20220818095939197](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818095939197.png)

### 公平锁

![image-20220818100031372](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818100031372.png)

### 读写锁

![image-20220818100856561](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818100856561.png)

![image-20220818100912549](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818100912549.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818102332454.png)

### 信号量

![image-20220818105423328](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818105423328.png)

### 闭锁

![image-20220818110703244](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818110703244.png)

## 使用redisson重写代码

![image-20220818112507175](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818112507175.png)

![image-20220818113319820](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818113319820.png)

![image-20220818113828445](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818113828445.png)

![image-20220818115257255](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818115257255.png)

![image-20220818115845296](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818115845296.png)

![image-20220818120413619](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818120413619.png)

![image-20220818120442116](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818120442116.png)

![image-20220818165026107](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818165026107.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818172223097.png)

![image-20220818172501633](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220818172501633.png)

~~~java
public Map<String, List<Catelog2Vo>> getCatalogJsonFromDbWithRedissonLock() {

        //1、占分布式锁。去redis占坑
        //（锁的粒度，越细越快:具体缓存的是某个数据，11号商品） product-11-lock
        //RLock catalogJsonLock = redissonClient.getLock("catalogJson-lock");
        //创建读锁
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("catalogJson-lock");

        RLock rLock = readWriteLock.readLock();

        Map<String, List<Catelog2Vo>> dataFromDb = null;
        try {
            rLock.lock();
            //加锁成功...执行业务
            dataFromDb = getIndexJsonDataFromDb();
        } finally {
            rLock.unlock();
        }
        //先去redis查询下保证当前的锁是自己的
        //获取值对比，对比成功删除=原子性 lua脚本解锁
        // String lockValue = stringRedisTemplate.opsForValue().get("lock");
        // if (uuid.equals(lockValue)) {
        //     //删除我自己的锁
        //     stringRedisTemplate.delete("lock");
        // }

        return dataFromDb;
~~~

## 浏览器缓存

如果更改了一些静态资源，但是浏览器始终无法更新，那么要么nginx没有重启，要么就是浏览器缓存没有清理，还是用到的之前缓存的数据。要让thymeleaf热更新的话：就要关闭thymeleaf的缓存。

~~~yml
spring:
	thymeleaf:
		cache: false
~~~

## 设置底部元素块居中

![image-20220819140442868](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220819140442868.png)

![image-20220819140515824](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220819140515824.png)

设置

~~~html
style="width: 1210px;margin: 0 auto"
~~~

## 程序画面自适应

![image-20220819153109555](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220819153109555.png)

## RestHighLevelClient

![image-20220819222405556](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220819222405556.png)

![image-20220819222527397](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220819222527397.png)

## @Bean

只要是@Component注解修饰的类里面都可以定义@Bean，并且都可以注册到Spring容器里面。其实不仅仅是@Component，只要是被@Component修饰的注解同样也可以定义@Bean，比如：@Repository、@Service、@Controller @Configuration，甚至是接口的default方法上的@Bean也可以被扫描加入到Spring容器里面。

## 修改带缓存的数据的接口

测试时一定记得先清理缓存！！！！！

## 获取原生的查询字符串

![image-20220821215605686](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220821215605686.png)

## 商品查询bug

### bool查询

![image-20220821221831516](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220821221831516.png)

### ik分词器

![image-20220822105129261](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822105129261.png)

### 搜索js

```js
function searchByKeyword(){
    var href = location.href
    if (href.indexOf("keyword")!=-1){
        location.href = href.replace(/(?<=keyword=).*?(?=(""|&|$))/,$("#keyword").val());
     }else {
         this.searchProducts("keyword",$("#keyword").val())
     }
    location.href="http://search.gulimall.com/list.html?keyword="+$("#keyword").val();
}
```

### 一般不使用lambda修改外部变量

## 多线程

### 方式

1）、继承 Thread

 2）、实现 Runnable 接口 

3）、实现 Callable 接口 + FutureTask （可以拿到返回结果，可以处理异常）

4）、线程池 

方式 1 和方式 2：主进程无法获取线程的运算结果。不适合当前场景方式 3：主进程可以获取线程的运算结果，但是不利于控制服务器中的线程资源。可以导致服务器资源耗尽。 方式 4：通过如下两种方式初始化线程池

~~~java
Executors.newFiexedThreadPool(3);
//或者
new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit unit, workQueue, threadFactory, handler);
~~~

通过线程池性能稳定，也可以获取执行结果，并捕获异常。但是，在业务复杂情况下，一个异步调用可能会依赖于另一个异步调用的执行结果。

### 七大参数

~~~java
* @param corePoolSize the number of threads to keep in the pool, even* if they are idle, unless {@code allowCoreThreadTimeOut} isset池中一直保持的线程的数量，即使线程空闲。除非设置了 allowCoreThreadTimeOut* @param maximumPoolSize the maximum number of threads to allow inthe* pool
池中允许的最大的线程数
* @param keepAliveTime when the number of threads is greater than* the core, this is the maximum time that excess idle threads* will wait for new tasks before terminating. 当线程数大于核心线程数的时候，线程在最大多长时间没有接到新任务就会终止释放，最终线程池维持在 corePoolSize 大小
* @param unit the time unit for the {@code keepAliveTime} argument时间单位
* @param workQueue the queue to use for holding tasks before they are executed. This queue will hold only the {@code Runnable}
* tasks submitted by the {@code execute} method. 阻塞队列，用来存储等待执行的任务，如果当前对线程的需求超过了corePoolSize大小，就会放在这里等待空闲线程执行。
* @param threadFactory the factory to use when the executor
* creates a new thread
创建线程的工厂，比如指定线程名等
* @param handler the handler to use when execution is blocked
* because the thread bounds and queue capacities are reached拒绝策略，如果线程满了，线程池就会使用拒绝策略。
~~~

![image-20220822204541922](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822204541922.png)

### 运行流程

1、线程池创建，准备好 core 数量的核心线程，准备接受任务 

2、新的任务进来，用 core 准备好的空闲线程执行。

 (1) 、core 满了，就将再进来的任务放入阻塞队列中。空闲的core 就会自己去阻塞队列获取任务执行

 (2) 、阻塞队列满了，就直接开新线程执行，最大只能开到 max 指定的数量

(3) 、max 都执行好了。Max-core 数量空闲的线程会在 keepAliveTime 指定的时间后自动销毁。最终保持到 core 大小

 (4) 、如果线程数开到了 max 的数量，还有新任务进来，就会使用reject 指定的拒绝策略进行处理 3、所有的线程创建都是由指定的 factory 创建的。

面试： 一个线程池 core 7； max 20 ，queue：50，100 并发进来怎么分配的；先有 7 个能直接得到执行，接下来 50 个进入队列排队，在多开 13 个继续执行。现在70 个被安排上了。剩下 30 个默认拒绝策略。

![image-20220822210401247](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822210401247.png)

Future可以获取到异步结果，FutureTask是它的一个实现。

### 任务组合

![image-20220822214222487](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822214222487.png)

![image-20220822214255547](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822214255547.png)

![image-20220822214322074](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822214322074.png)

![image-20220822214329815](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822214329815.png)

![image-20220822214344087](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822214344087.png)

**![image-20220822214358895](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822214358895.png)**

![image-20220822215545460](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220822215545460.png)

## mysql查询顺序

![image-20220824211533251](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220824211533251.png)

![image-20220929220913142](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220929220913142.png)



~~~mysql
		SELECT
            ssav.attr_id attr_id,
            ssav.attr_name attr_name,
            ssav.attr_value,
			group_concat( DISTINCT info.sku_id ) sku_ids //聚合分组后每一个分组内的sku_id
        FROM
            pms_sku_info info
                LEFT JOIN pms_sku_sale_attr_value ssav ON ssav.sku_id = info.sku_id
        WHERE
            info.spu_id = 29
        GROUP BY
            ssav.attr_id,
            ssav.attr_name,
            ssav.attr_value
~~~

## 商品详情页bug

~~~js
$(".sku_attr_value").click(function () {
        // 1、点击的元素添加上自定义的属性
        let skus = new Array();
        let curr = $(this).attr("skus").split(",");

        $(this).parent().parent().find(".sku_attr_value").removeClass("checked");
        $(this).addClass("checked");
        changeCheckedStyle();

        $("a[class='sku_attr_value checked']").each(function () {
            skus.push($(this).attr("skus").split(","));
        });
        let filterEle = skus[0];
        for (let i = 1; i < skus.length; i++) {
            //多个数组取交集，由多个attr属性选择确定一个sku
            filterEle = filterEle.filter(v => skus[i].includes(v));
        }
        location.href = "http://item.gulimall.com/" + filterEle[0] + ".html";

        return false;
    });
~~~

如果无法跳转到item.gulimall.com/xx.html，检查：

是否配了本地host文件的域名配置；

是否在nginx里面配置了server_name  *.gulimall.com;并且要带上允许host的配置；

是否在gateway网关里面配置了路由；

是否写好了controller接口。

## controller报错post请求不被允许？

![image-20220825102751856](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825102751856.png)

## session

![image-20220825181917129](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825181917129.png)

![image-20220825182415979](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825182415979.png)

![image-20220825182443376](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825182443376.png)

![image-20220825182723922](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825182723922.png)

![image-20220825183202665](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825183202665.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825203837765.png)

## 单点登录

![image-20220825210238089](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825210238089.png)

![image-20220825212637825](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825212637825.png)

![image-20220825232500150](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220825232500150.png)

![单点登录流程](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/单点登录流程.png)

子系统到sso认证中心登录后，本地浏览器会以cookie的形式留下sso认证中心的登录痕迹并且sso认证中心会把cookie值和用户信息存入redis里面，sso认证中心重定向到子系统之前的访问地址并带上生成的cookie值，子系统再次验证是否登录，发现有返回了cookie值，说明已登录，所以直接再次访问sso认证中心去取用户信息，并且因为之前浏览器已经对sso认证中心留下了cookie痕迹，于是再次访问时会带上这个域名下的所有cookie去访问，而sso认证中心判断发现对方带了cookie来的，直接判定已登录过了，所以直接会拿着这个cookie里面的token到redis里面查找对应的用户信息，并将其返回给对方，对方拿到用户信息后存入本系统的session里面，这样下次再次访问时就先会去看session中是否有用户信息，有表明是已登录的，就不用再次去认证了。

事实上当用户访问服务器的时候会为每一个用户开启一个session，浏览器是怎么判断这个session到底是属于哪个用户呢？jsessionid的作用就体现出来了：jsessionid就是用来判断当前用户对应于哪个session。换句话说服务器识别session的方法是通过jsessionid来告诉服务器该客户端的session在内存的什么地方。做web开发的同学都知道，http是无状态的会话协议，也就是说无法保存用户的信息。那如果有一些信息需要在用户的浏览活动中一直保持，该怎么做呢？我们可以把这些信息在每次请求的时候作为参数传递给服务器，但这样做既麻烦又耗费资源，这时候就体现出了session的重要性。session是web开发中不可或缺的一个特性。它是对于一个特定的用户请求，在 web服务器上保存的一个全局变量。有了它我们就可以把用户的一些信息保存在服务器上，而不用在服务器和客户端之间来回传递。知道了session的作用，那session是怎么实现的呢？服务器上为每个用户都保存了一个session，那当用户请求过来的时候是怎么知道某一个用户应该对应哪个 session呢？这时jsessionid就派上用场了。每一个session都有一个id来作为标识，这个id会传到客户端，每次客户端请求都会把这个id传到服务器，服务器根据id来匹配这次请求应该使用哪个session。jsessionid就是客户端用来保存sessionid的变量，主要是针对j2ee实现的web容器，没有研究过其他语言是用什么变量来保存的。一般对于web应用来说，客户端变量都会保存在cookie 中，jsessionid也不例外。不过与一般的cookie变量不同，jsessionid是保存在内存cookie中的，在一般的cookie文件中是看不到它的影子的。内存cookie在打开一个浏览器窗口的时候会创建，在关闭这个浏览器窗口的时候也同时销毁。这也就解释了为什么session变量不能跨窗口使用，要跨窗口使用就需要手动把jsessionid保存到cookie里面。



## rabbitmq

![image-20220827085939688](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827085939688.png)

![image-20220827085958983](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827085958983.png)







![image-20220827085449868](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827085449868.png)

![image-20220827085506661](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827085506661.png)

![消息队列流程](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/消息队列流程.jpg)

![image-20220827085816766](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827085816766.png)

![image-20220827094141365](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827094141365.png)

![image-20220827100106366](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827100106366.png)

![image-20220827103426546](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827103426546.png)

![image-20220827103708737](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827103708737.png)

![image-20220827104350974](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827104350974.png)

![image-20220827104717180](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827104717180.png)

![image-20220827104939231](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827104939231.png)

![image-20220827105235125](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827105235125.png)

![image-20220827105316913](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827105316913.png)

![image-20220827105400148](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827105400148.png)

![image-20220827105922132](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220827105922132.png)

![image-20220827110041317](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827110041317.png)

![image-20220827111040934](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827111040934.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827111731340.png)

![image-20220827111854758](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827111854758.png)

![image-20220827112416363](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827112416363.png)

![image-20220827154405815](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827154405815.png)

![image-20220827155545309](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827155545309.png)

![image-20220827155646177](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827155646177.png)

![image-20220827155915473](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827155915473.png)

一但消费者启动起来，消息就会交给消费者，消费者挨个处理后就会确认消息送达，就是ack了。

![image-20220827160713262](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827160713262.png)

不签收的话，消息会在最后回到队列的ready状态。

生产者端的消息确认机制是在发布者-broker/交换机-消息队列这条线路上的；

消费者端的消息确认机制是在消息队列-消费者之间的。

![image-20220827161406558](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827161406558.png)

![image-20220827161751037](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827161751037.png)

![image-20220830202733945](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830202733945.png)

![image-20220830202756053](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830202756053.png)

![image-20220830202915156](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830202915156.png)

![image-20220830203713548](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830203713548.png)

![image-20220830204401889](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830204401889.png)

![消息队列流程](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/消息队列流程.jpg)

![image-20220831102603997](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220831102603997.png)

![image-20220831102737661](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220831102737661.png)

![image-20220831103057098](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220831103057098.png)



## Feign远程调用丢失请求头

~~~java
package com.atguigu.gulimall.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class GuliFeignClient {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = requestAttributes.getRequest();
                if (request != null){
                    String cookies = request.getHeader("Cookie");
                    requestTemplate.header("Cookie",cookies);
                }
            }
        };
    }
}

~~~



![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221004130029821.png)

![image-20220827181546707](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827181546707.png)

![image-20220827181752586](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827181752586.png)

## Feign远程调用丢失上下文

![image-20220827182807563](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827182807563.png)

![image-20220827183040143](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827183040143.png)

![image-20220827183320100](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827183320100.png)

![image-20220827183443130](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827183443130.png)

## @RequestBody只能接收json

![image-20220827225355754](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827225355754.png)



![image-20220827225502350](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827225502350.png)

## addAttribute（）与addFlashAttribute（）

![image-20220827232806625](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827232806625.png)

![image-20220827232921315](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220827232921315.png)

## 复习ajax

```js
 $.ajax({
   type:"post",
   url:"http://auth.gulimall.com/register",
   data:JSON.stringify({"phone":$( "input[name='phone']").val(),"userName":$( "input[name='userName']").val(),"code":$( "input[name='code']").val(),"password":$( "input[name='password']").val()}),//以json形式发送数据，要把数据转为json
   contentType:'application/json',
   success:function (res){//成功回调的函数名为success
      console.log(res)
      if (res.code == 0){
         alert("注册成功！正在跳转登录...")
         windows.href="http://auth.gulimall.com/login.html"
      }else {
         console.log(res)
         if (res.phone){
            $( "div[name='error_phone']").text(res.phone)
         }
         if (res.userName){
            $( "div[name='error_username']").text(res.userName)
         }
         if (res.error_code){
            $( "div[name='error_code']").text(res.error_code)//给指定name的div赋值
         }
      }
   }
})
```

![image-20220828005509874](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828005509874.png)

![image-20220828011225641](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828011225641.png)

## 接口幂等性

![image-20220828104236225](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828104236225.png)

![image-20220828104447892](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828104447892.png)

### 解决方案

![image-20220828105030709](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828105030709.png)

![image-20220828105108281](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828105108281.png)

![image-20220828105127916](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828105127916.png)

![image-20220828105138258](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828105138258.png)

#### 神奇的lua脚本

![image-20220828114044781](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220828114044781.png)



## 基础坑

```java
@Test
void contextLoads() {
    Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 7, 7, 3, 29, 1, 90};
    List<Integer> collect = Arrays.stream(ints).distinct().collect(Collectors.toList());
    System.out.println(collect);
}//list里面只能是引用数据类型对象，不能是基本数据类型。
```



## springsession未生效

![image-20220830090132470](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830090132470.png)

~~~java
@Configuration
public class GulimallSessionConfig {
    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setDomainName("gulimall.com");
        defaultCookieSerializer.setCookieName("GULISESSION");
        return defaultCookieSerializer;
    }
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
~~~

springsession的配置被放到了common模块，所以必须在使用了该bean的模块加上@ComponentScan({"com.atguigu"})注解！

![image-20220830090355892](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830090355892.png)

## 事务传播行为：本地事务失效

![image-20220830110304090](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830110304090.png)

![image-20220830110630496](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830110630496.png)

![image-20220830110728915](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830110728915.png)

## CAP

![image-20220830111946445](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830111946445.png)

![image-20220830112155022](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830112155022.png)

![image-20220830112421371](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830112421371.png)

http://thesecretlivesofdata.com/raft/

![image-20220830115625481](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830115625481.png)

![image-20220830115355902](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830115355902.png)

![image-20220830115519277](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830115519277.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830122323930.png)

![image-20220830121727005](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830121727005.png)

![image-20220830124428273](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830124428273.png)

![image-20220830124537523](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830124537523.png)

![image-20220830124653510](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830124653510.png)

## 分布式事务方案

### 2PC模式

![image-20220830124956952](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830124956952.png)

### 柔性事务-TCC 事务补偿型方案

![image-20220830125437829](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830125437829.png)

### 柔性事务-最大努力通知型方案

![image-20220830125511035](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830125511035.png)

### 柔性事务-可靠消息+最终一致性方案（异步确保型）

![image-20220830125524035](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830125524035.png)

## seata

![image-20220830130141667](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830130141667.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830130129576.png)

![image-20220830130442523](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220830130442523.png)

## 加密

![image-20220904085337036](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220904085337036.png)

![image-20220904085350389](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220904085350389.png)

![image-20220904085829293](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220904085829293.png)

![image-20220904090750094](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220904090750094.png)

![image-20220904091632450](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220904091632450.png)

![image-20220904092459932](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220904092459932.png)

![image-20220904093617754](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220904093617754.png)

![image-20220906201447738](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220906201447738.png)

![image-20220906203218046](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220906203218046.png)



## thymleaf行内表达式

![image-20220904232229059](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220904232229059.png)

## 两个非常神奇的bug

一个是无法通过@ModelRedirect获取到redirectAttribute发来的flashAttribute;

一个是通过整合github联合登录可以将session通过springsession放到redis里面，并且可以被其它加入了springsession的服务获取到对应的session,但是却在整合微信联合登录时，同样的操作其它服务却无法获取到对应的session。



## Schedule

![image-20220907200658362](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220907200658362.png)

![image-20220907201030513](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220907201030513.png)

![image-20220907201144192](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220907201144192.png)

![image-20220907202626813](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20220907202626813.png)

![image-20220907203541113](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220907203541113.png)

![image-20220907203738615](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220907203738615.png)

![image-20220907203806473](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220907203806473.png)

![image-20220907203854244](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220907203854244.png)

![image-20220907223729178](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220907223729178.png)

## sentinel

![image-20220910170621052](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220910170621052.png)

![image-20220910171455350](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220910171455350.png)

## HashMap定制容量

![image-20220918211853732](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220918211853732.png)

~~~java
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }
     public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

~~~

~~~java
    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;//>>>是无符号位运算
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

~~~

![image-20220918212029913](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220918212029913.png)

![image-20220923111909082](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20220923111909082.png)

## css：position:absolute/relative

![image-20221002203030763](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002203030763.png)

![image-20221002203132305](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002203132305.png)

![image-20221002203149014](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002203149014.png)

![image-20221002203208093](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002203208093.png)

![image-20221002203230160](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002203230160.png)

![image-20221002203239697](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002203239697.png)

![image-20221002203255345](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002203255345.png)

absolute布局不占空间,脱离文档流

![image-20221002210637127](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002210637127.png)



float属于半脱离文档流，浮动与浮动之间仍然相互影响位置，但是浮动不会占据文档流元素的位置，float不影响absolute盒子的空间。

absolute是先确定原位置，再根据非static的父元素来确定最终位置。没有设置top,left,right,bottom的话position:absolute，元素停留在原位置，但是浮动效果依然在。

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div class="div-t">
    <div>
        <div class="div1">
            <div class="div2">//float的元素不影响position:absolute的元素
            </div>
            <div class="div3">//position:absolute的元素没有设置top,left,right,bottom，依然由浮动效果，不占据空间。
                position: absolute
            </div>
            <div style="width: 200px;height: 200px;border: black 2px solid;"></div>
        </div>
    </div>
    <div style="height: 100px;width: 100px;border: blue 2px solid;position: absolute;">
        position:absolute
    </div>
    <div style="height: 300px;width: 300px;border:indigo 2px solid;"></div>
</div>

</body>

</html>

<style type="text/css">
    .div-t{
        position: relative;
        /*overflow: hidden;*/
        z-index: 999;
    }
    .div1{
        position: relative;
        height: 300px;
        border: green 2px solid;
        width: 1600px;
    }
    .div2{
        float: left;
        width:100px;
        height: 50px;
        border: #0BB2D4 2px solid;
    }
    .div3{
        position: absolute;
        /*float: right;*/
        /*top: 0;*/
        /*right: 0;*/
        width: 100px;
        height: 100px;
        border: red 2px solid;
}
</style>

~~~

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221003010349319.png)

值得注意的是：如果float被overflow:hidden清除了浮动，float元素的浮动效果依然在，但是它不占据position:absolute的元素

的空间，但是对于非position:absolute的元素会导致其虽然可以在其位置上放置元素但是内容会被清除了浮动的元素排挤。这可能

就是因为float的半脱离文档流状态导致的。***脱离文档流但是又占据位置。清除浮动只是从父元素的角度看撑开了自己，从观感上达到了清除内部浮动的效果，但是浮动元素的半脱离文档流效果依然存在。***

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div class="div-t">
    <div>
        <div class="div1">
            <div class="div2">
            </div>
            <div style="width: 300px;height: 60px;border: darkorange 2px solid;left: 5px;position: absolute"> position:absolute</div>
            <div style="float: left;width: 50px;height: 50px;border: black 2px solid;"></div>
            <div class="div3">
                position: static
            </div>
            <div style="width: 200px;height: 200px;border: black 2px solid;"></div>
        </div>
    </div>
    <div style="height: 100px;width: 100px;border: blue 2px solid;position: absolute;">position: absolute</div>
    <div style="height: 300px;width: 300px;border:indigo 2px solid;"></div>
</div>
</body>

</html>

<style type="text/css">
    .div-t{
        position: relative;
        overflow: hidden;//即使加了清除浮动，本质上不会清除子元素的float效果，而是计算div-t高度时会把内部浮动元素的高度加入                            进来，在没有清除浮动前，内部浮动元素由于脱离文档流，不会将外部父元素撑开导致未设置大小的父元素坍                            塌。清除浮动只是从父元素的角度看清除了，但是浮动元素的半脱离文档流效果依然在。
        z-index: 999;
    }
    .div1{
        position: relative;
        overflow: hidden;
        /*height: 300px;*/
        border: green 2px solid;
        width: 1600px;
    }
    .div2{
        float: left;
        width:100px;
        height: 100px;
        border: #0BB2D4 2px solid;
    }
    .div3{
        /*position: absolute;*/
        /*float: right;*/
        /*top: 0;*/
        /*left: 100px;*/
        width: 200px;
        height: 150px;
        border: red 2px solid;
}
</style>

~~~

![image-20221003092550106](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221003092550106.png)

受到排挤就是因为float块是半脱离文档流的。

![image-20221002211317218](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002211317218.png)

![image-20221002212215826](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002212215826.png)



## z-index只对设置了position的盒子生效

![image-20221002222258991](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221002222258991.png)

## css高级-BFC清除浮动

### 一

overflow:hidden 的意思是超出的部分要裁切隐藏掉

那么如果 float 的元素不占普通流位置

普通流的包含块要根据内容高度裁切隐藏

如果高度是默认值auto

那么不计算其内浮动元素高度就[裁切](https://www.zhihu.com/search?q=裁切&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A50036974})

就有可能会裁掉float

这是反布局常识的

所以如果没有明确设定容器高情况下

它要计算内容全部高度才能确定在什么位置hidden

浮动的高度就要被计算进去

顺带达成了清理浮动的目标

### 二

首先，这确实是BFC（Block fomatting context [块级格式化](https://www.zhihu.com/search?q=块级格式化&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A324582230})上下文）渲染的结果。

导致这种结果的原因如下：

[BFC](https://www.zhihu.com/search?q=BFC&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A324582230})的定义：

BFC是一个独立的渲染区域，只有Block-level box参与， 它规定了内部的Block-level Box如何布局，**并且与这个区域外部毫不相干**。

BFC的规则（只列出需要的）：

1. BFC就是页面上的一个隔离的独立容器，容器里面的子元素不会影响到外面的元素。
2. 计算BFC的高度时，[浮动元素](https://www.zhihu.com/search?q=浮动元素&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra={"sourceType"%3A"answer"%2C"sourceId"%3A324582230})也参与计算。

哪些元素会产生BFC（只列出需要的）：

1. overflow不为visible

好了，让我们根据上面的条件推导出结果：

1. 当你为一个元素添加overflow属性为hidden时，这个元素就会产生自己的BFC。
2. 根据规则2，**在计算BFC的高度时，浮动元素的高度也要参与计算**，即父元素不会忽略自己里面的浮动元素的高度，如果你的父元素的高度设置的auto的话，那么它的高度就会等于浮动元素的高度（假设这个BFC里面只有一个浮动元素）。
3. 规则2也是为了保证规则1的所述，即BFC就是页面上的一个隔离的独立容器，容器里面的子元素不应该影响到外面的元素，如果父元素不计算浮动元素的高度而是坍塌的话，那么浮动元素就会影响到其后面的元素，所以它需要规则3来保证这么一个行为。
4. 当然，**你可以说我就选择坍塌，然后把多余的浮动元素裁剪掉**，这样也不影响后面的元素，难道不行吗？**当然可以，所以才有了规则3明确表示我们采取的措施就是计算浮动元素的高度来不影响后面的元素**。
5. 最后，这并没有违背overflow: hidden;的功能，**它的裁剪的功能依旧存在，只是说现在它的高度被自动设置成了能够包含浮动元素的高度罢了**（如果height: auto; ）。所以如果你显示的声明父元素的高度，那么浮动元素超出的部分也是会被裁剪掉的，**这跟违背hidden的本意没有任何关联**。

![image-20221003000236959](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221003000236959.png)

![image-20221003000757870](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221003000757870.png)

overflow:hidden不会将position:absolute的子元素加入裁剪的高度计算，并且只要overflow:hidden不放到position:absolute的子元素用于定位的父元素上，那么这个子元素将也不受裁剪的影响。

![image-20221003002157724](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221003002157724.png)

## springboot里面默认禁止直接访问templates里面的内容

![image-20221003222009808](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221003222009808.png)

可以通过controller来进行视图跳转。



## feign调用允许双方返回类型不一致

![image-20221004161458662](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221004161458662.png)

## 支付宝沙箱

SDK&DEMO

https://opendocs.alipay.com/open/270/106291?ref=api

使用说明

https://opendocs.alipay.com/common/02kkv7#%E7%AC%AC%E4%B8%80%E6%AD%A5%EF%BC%9A%E9%85%8D%E7%BD%AE%E6%B2%99%E7%AE%B1%E5%BA%94%E7%94%A8%E7%8E%AF%E5%A2%83

## 注册拦截器正确姿势

~~~java
@Configuration
public class OrderWebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginUserInterceptor loginUserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserInterceptor).addPathPatterns("/**");
    }
}

~~~

## natapp内网穿透

### natapp的免费隧道提供的域名每次重启都会改变

![image-20221008181538441](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221008181538441.png)

![image-20221008181758008](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221008181758008.png)

## 熔断和降级

![image-20221015200606876](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221015200606876.png)

https://github.com/alibaba/Sentinel/wiki/%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8#%E5%AE%9A%E4%B9%89%E8%B5%84%E6%BA%90



## mq对比

![mq对比](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/mq对比.jpg)

## zipkin与reids造成死锁

![image-20221016131033827](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221016131033827.png)

## 虚拟机的三种网络连接模式

![image-20221021202145955](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021202145955.png)

![image-20221021202215173](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021202215173.png)

![image-20221021202250033](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021202250033.png)

![image-20221021202827090](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021202827090.png)

![image-20221021202845252](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021202845252.png)

![image-20221021202858632](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021202858632.png)

192.168.1.0---网络号/网络地址，不可用于主机ip

192.168.1.1---一般被路由器占用

192.168.1.255---一般作为广播地址

![image-20221021205949500](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021205949500.png)

![image-20221021210035135](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210035135.png)

![image-20221021210051800](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210051800.png)

![image-20221021210120143](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210120143.png)

![image-20221021210139500](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210139500.png)

![image-20221021210158462](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210158462.png)

![image-20221021210219919](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210219919.png)

![image-20221021210247192](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210247192.png)

![image-20221021210255746](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210255746.png)

![image-20221021210307963](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210307963.png)

![image-20221021210330211](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221021210330211.png)

## 关于重装virtualbox后host-only网络适配器不可用

请务必卸载电脑中的电脑管家以及其它软件安装的应用以及其它第三方安全软件；

其次，重装好新的virtualbox后，可能出现设备管理器中已经有VirtualBox Host-Only Ethernet Adapter这个网络适配器，但是上面有黄色感叹号，提示不可用，这时请重启电脑，就会变为可用了。

还有一点，当前virtualbox7.x版本还没有任何vagrant可以与之适配，建议用virtualbox6.x及以下版本。

## k8s

![image-20221022123310465](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221022123310465.png)

![image-20221022123346950](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221022123346950.png)

![image-20221022130111242](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221022130111242.png)

最好先把sshd允许密码登录改了再去重置mac地址，否则改了mac地址后vagrant ssh k8s-node就无法连接到虚拟机了。



## No package kubelet-1.18.0 available

![image-20221022143823612](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221022143823612.png)

~~~shell
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://5r3ytd7m.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
~~~

~~~shell
cat > /etc/yum.repos.d/kubernetes.repo << EOF
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
~~~

## 初始化kube主节点前最好先重启虚拟机

并且先swapoff -a关闭虚拟内存

![image-20221022152344366](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221022152344366.png)

~~~shell
kubeadm join 10.0.2.15:6443 --token snace0.5oe41d85ozewamu5 \
    --discovery-token-ca-cert-hash sha256:ab2fec51dee717fbf1d55fc707319ad42d4db50bbc37f1c98e2456ed59a60717 
~~~

## kube主节点配置了flannel后一直notready

翻墙获取最新的kube-fannel.yml替换虚拟机内旧的kube-fannel.yml文件，再次kubectl apply -f  kube-flannel.yml即可

~~~shell
---
kind: Namespace
apiVersion: v1
metadata:
  name: kube-flannel
  labels:
    pod-security.kubernetes.io/enforce: privileged
---
kind: ClusterRole
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: flannel
rules:
- apiGroups:
  - ""
  resources:
  - pods
  verbs:
  - get
- apiGroups:
  - ""
  resources:
  - nodes
  verbs:
  - list
  - watch
- apiGroups:
  - ""
  resources:
  - nodes/status
  verbs:
  - patch
---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: flannel
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: flannel
subjects:
- kind: ServiceAccount
  name: flannel
  namespace: kube-system
---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: flannel
  namespace: kube-system
---
kind: ConfigMap
apiVersion: v1
metadata:
  name: kube-flannel-cfg
  namespace: kube-system
  labels:
    tier: node
    app: flannel
data:
  cni-conf.json: |
    {
      "name": "cbr0",
      "cniVersion": "0.3.1",
      "plugins": [
        {
          "type": "flannel",
          "delegate": {
            "hairpinMode": true,
            "isDefaultGateway": true
          }
        },
        {
          "type": "portmap",
          "capabilities": {
            "portMappings": true
          }
        }
      ]
    }
  net-conf.json: |
    {
      "Network": "10.244.0.0/16",
      "Backend": {
        "Type": "vxlan"
      }
    }
---
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: kube-flannel-ds
  namespace: kube-system
  labels:
    tier: node
    app: flannel
spec:
  selector:
    matchLabels:
      app: flannel
  template:
    metadata:
      labels:
        tier: node
        app: flannel
    spec:
      affinity:
        nodeAffinity:
          requiredDuringSchedulingIgnoredDuringExecution:
            nodeSelectorTerms:
            - matchExpressions:
              - key: kubernetes.io/os
                operator: In
                values:
                - linux
      hostNetwork: true
      priorityClassName: system-node-critical
      tolerations:
      - operator: Exists
        effect: NoSchedule
      serviceAccountName: flannel
      initContainers:
      - name: install-cni-plugin
       #image: flannelcni/flannel-cni-plugin:v1.1.0 for ppc64le and mips64le (dockerhub limitations may apply)
        image: docker.io/rancher/mirrored-flannelcni-flannel-cni-plugin:v1.1.0
        command:
        - cp
        args:
        - -f
        - /flannel
        - /opt/cni/bin/flannel
        volumeMounts:
        - name: cni-plugin
          mountPath: /opt/cni/bin
      - name: install-cni
       #image: flannelcni/flannel:v0.20.0 for ppc64le and mips64le (dockerhub limitations may apply)
        image: docker.io/rancher/mirrored-flannelcni-flannel:v0.20.0
        command:
        - cp
        args:
        - -f
        - /etc/kube-flannel/cni-conf.json
        - /etc/cni/net.d/10-flannel.conflist
        volumeMounts:
        - name: cni
          mountPath: /etc/cni/net.d
        - name: flannel-cfg
          mountPath: /etc/kube-flannel/
      containers:
      - name: kube-flannel
       #image: flannelcni/flannel:v0.20.0 for ppc64le and mips64le (dockerhub limitations may apply)
        image: docker.io/rancher/mirrored-flannelcni-flannel:v0.20.0
        command:
        - /opt/bin/flanneld
        args:
        - --ip-masq
        - --kube-subnet-mgr
        resources:
          requests:
            cpu: "100m"
            memory: "50Mi"
          limits:
            cpu: "100m"
            memory: "50Mi"
        securityContext:
          privileged: false
          capabilities:
            add: ["NET_ADMIN", "NET_RAW"]
        env:
        - name: POD_NAME
          valueFrom:
            fieldRef:
              fieldPath: metadata.name
        - name: POD_NAMESPACE
          valueFrom:
            fieldRef:
              fieldPath: metadata.namespace
        - name: EVENT_QUEUE_DEPTH
          value: "5000"
        volumeMounts:
        - name: run
          mountPath: /run/flannel
        - name: flannel-cfg
          mountPath: /etc/kube-flannel/
        - name: xtables-lock
          mountPath: /run/xtables.lock
      volumes:
      - name: run
        hostPath:
          path: /run/flannel
      - name: cni-plugin
        hostPath:
          path: /opt/cni/bin
      - name: cni
        hostPath:
          path: /etc/cni/net.d
      - name: flannel-cfg
        configMap:
          name: kube-flannel-cfg
      - name: xtables-lock
        hostPath:
          path: /run/xtables.lock
          type: FileOrCreate
~~~

## 为何coreDNS在running仍然会ready 0/1(巨坑)

**总结：一定要在master节点先装好flannel后，再将node节点join到master节点来，要不然会导致coredns装到node节点上去了，就会导致这个问题！！！**

![image-20221031222702311](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221031222702311.png)



![image-20221022200427095](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221022200427095.png)

![image-20221022205625861](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221022205625861.png)

![image-20221022210000572](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221022210000572.png)

一个pod里面会存在多个容器。

## 创建部署-暴露部署

创建部署后会生成一个部署、一个pod、并至少在一个node上生成一个存活的容器

暴露部署会把一个pod里面的一个容器作为为一个服务暴露给外部访问。

1、在主节点上部署一个tomcat

```shell
kubectl create deployment tomcat6 --image=tomcat:6.0.53-jre8
```



获取所有的资源：

```shell
[root@k8s-node1 k8s]# kubectl get all
NAME                           READY   STATUS              RESTARTS   AGE
pod/tomcat6-7b84fb5fdc-cfd8g   0/1     ContainerCreating   0          41s

NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   70m

NAME                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/tomcat6   0/1     1            0           41s

NAME                                 DESIRED   CURRENT   READY   AGE
replicaset.apps/tomcat6-7b84fb5fdc   1         1         0       41s
[root@k8s-node1 k8s]# 
```



kubectl get pods -o wide 可以获取到tomcat部署信息，能够看到它被部署到了k8s-node2上了

```shell
[root@k8s-node1 k8s]# kubectl get all -o wide
NAME                           READY   STATUS    RESTARTS   AGE    IP           NODE        NOMINATED NODE   READINESS GATES
pod/tomcat6-7b84fb5fdc-cfd8g   1/1     Running   0          114s   10.244.2.2   k8s-node2   <none>           <none>

NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE   SELECTOR
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   71m   <none>

NAME                      READY   UP-TO-DATE   AVAILABLE   AGE    CONTAINERS   IMAGES               SELECTOR
deployment.apps/tomcat6   1/1     1            1           114s   tomcat       tomcat:6.0.53-jre8   app=tomcat6

NAME                                 DESIRED   CURRENT   READY   AGE    CONTAINERS   IMAGES               SELECTOR
replicaset.apps/tomcat6-7b84fb5fdc   1         1         1       114s   tomcat       tomcat:6.0.53-jre8   app=tomcat6,pod-template-hash=7b84fb5fdc
[root@k8s-node1 k8s]# 
```



查看node2节点上，下载了哪些镜像：

```shell
[root@k8s-node2 opt]# docker images
REPOSITORY                                                       TAG                 IMAGE ID            CREATED             SIZE
registry.cn-hangzhou.aliyuncs.com/google_containers/kube-proxy   v1.17.3             0d40868643c6        2 weeks ago         117MB
registry.cn-hangzhou.aliyuncs.com/google_containers/pause        3.2                 80d28bedfe5d        2 months ago        683kB
quay.io/coreos/flannel                                           v0.11.0-amd64       ff281650a721        15 months ago       52.6MB
tomcat                                                           6.0.53-jre8         49ab0583115a        2 years ago         290MB
[root@k8s-node2 opt]# 
```



查看Node2节点上，正在运行的容器：

```shell
[root@k8s-node2 opt]# docker ps
CONTAINER ID        IMAGE                                                            COMMAND                  CREATED             STATUS              PORTS               NAMES
9194cc4f0b7a        tomcat                                                           "catalina.sh run"        2 minutes ago       Up 2 minutes                            k8s_tomcat_tomcat6-7b84fb5fdc-cfd8g_default_0c9ebba2-992d-4c0e-99ef-3c4c3294bc59_0
f44af0c7c345        registry.cn-hangzhou.aliyuncs.com/google_containers/pause:3.2    "/pause"                 3 minutes ago       Up 3 minutes                            k8s_POD_tomcat6-7b84fb5fdc-cfd8g_default_0c9ebba2-992d-4c0e-99ef-3c4c3294bc59_0
ef74c90491e4        ff281650a721                                                     "/opt/bin/flanneld -…"   20 minutes ago      Up 20 minutes                           k8s_kube-flannel_kube-flannel-ds-amd64-5xs5j_kube-system_11a94346-316d-470b-9668-c15ce183abec_0
c8a524e5a193        registry.cn-hangzhou.aliyuncs.com/google_containers/kube-proxy   "/usr/local/bin/kube…"   25 minutes ago      Up 25 minutes                           k8s_kube-proxy_kube-proxy-mvlnk_kube-system_519de79a-e8d8-4b1c-a74e-94634cebabce_0
4590685c519a        registry.cn-hangzhou.aliyuncs.com/google_containers/pause:3.2    "/pause"                 26 minutes ago      Up 26 minutes                           k8s_POD_kube-flannel-ds-amd64-5xs5j_kube-system_11a94346-316d-470b-9668-c15ce183abec_0
54e00af5cde4        registry.cn-hangzhou.aliyuncs.com/google_containers/pause:3.2    "/pause"                 26 minutes ago      Up 26 minutes                           k8s_POD_kube-proxy-mvlnk_kube-system_519de79a-e8d8-4b1c-a74e-94634cebabce_0
[root@k8s-node2 opt]# 
```



在node1上执行：

```shell
[root@k8s-node1 k8s]# kubectl get pods
NAME                       READY   STATUS    RESTARTS   AGE
tomcat6-7b84fb5fdc-cfd8g   1/1     Running   0          5m35s

[root@k8s-node1 k8s]# kubectl get pods --all-namespaces
NAMESPACE     NAME                                READY   STATUS    RESTARTS   AGE
default       tomcat6-7b84fb5fdc-cfd8g            1/1     Running   0          163m
kube-system   coredns-546565776c-9sbmk            1/1     Running   0          3h52m
kube-system   coredns-546565776c-t68mr            1/1     Running   0          3h52m
kube-system   etcd-k8s-node1                      1/1     Running   0          3h52m
kube-system   kube-apiserver-k8s-node1            1/1     Running   0          3h52m
kube-system   kube-controller-manager-k8s-node1   1/1     Running   0          3h52m
kube-system   kube-flannel-ds-amd64-5xs5j         1/1     Running   0          3h6m
kube-system   kube-flannel-ds-amd64-6xwth         1/1     Running   0          3h24m
kube-system   kube-flannel-ds-amd64-fvnvx         1/1     Running   0          3h6m
kube-system   kube-proxy-7tkvl                    1/1     Running   0          3h6m
kube-system   kube-proxy-mvlnk                    1/1     Running   0          3h6m
kube-system   kube-proxy-sz2vz                    1/1     Running   0          3h52m
kube-system   kube-scheduler-k8s-node1            1/1     Running   0          3h52m
[root@k8s-node1 ~]# 
```



从前面看到tomcat部署在Node2上，现在模拟因为各种原因宕机的情况，将node2关闭电源，观察情况。

```shell
[root@k8s-node1 ~]# kubectl get nodes
NAME        STATUS     ROLES    AGE     VERSION
k8s-node1   Ready      master   4h4m    v1.17.3
k8s-node2   NotReady   <none>   3h18m   v1.17.3
k8s-node3   Ready      <none>   3h18m   v1.17.3
[root@k8s-node1 ~]# 
```



```shell
[root@k8s-node1 ~]# kubectl get pods -o wide
NAME                       READY   STATUS    RESTARTS   AGE    IP           NODE        NOMINATED NODE   READINESS GATES
tomcat6-7b84fb5fdc-cfd8g   1/1     Running   0          177m   10.244.2.2   k8s-node2   <none>           <none>
[root@k8s-node1 ~]# 
```



![image-20200504104925236](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20200504104925236.png)



2、暴露nginx访问

在master上执行

```shell
kubectl expose deployment tomcat6 --port=80 --target-port=8080 --type=NodePort 
```

pod的80映射容器的8080；server会带来pod的80

查看服务：

```shell
[root@k8s-node1 ~]# kubectl get svc
NAME         TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP        12h
tomcat6      NodePort    10.96.24.191   <none>        80:30526/TCP   49s
[root@k8s-node1 ~]# 
```



```shell
[root@k8s-node1 ~]# kubectl get svc -o wide
NAME         TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE     SELECTOR
kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP        12h     <none>
tomcat6      NodePort    10.96.24.191   <none>        80:30526/TCP   3m30s   app=tomcat6
[root@k8s-node1 ~]# 
```

 http://192.168.56.100:30526/ 

![image-20200504105723874](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20200504105723874.png)



```shell
[root@k8s-node1 ~]# kubectl get all
NAME                           READY   STATUS    RESTARTS   AGE
pod/tomcat6-7b84fb5fdc-qt5jm   1/1     Running   0          13m

NAME                 TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
service/kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP        12h
service/tomcat6      NodePort    10.96.24.191   <none>        80:30526/TCP   9m50s

NAME                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/tomcat6   1/1     1            1           11h

NAME                                 DESIRED   CURRENT   READY   AGE
replicaset.apps/tomcat6-7b84fb5fdc   1         1         1       11h
[root@k8s-node1 ~]#
```



3、动态扩容测试

kubectl get deployment

```shell
[root@k8s-node1 ~]# kubectl get deployment
NAME      READY   UP-TO-DATE   AVAILABLE   AGE
tomcat6   2/2     2            2           11h
[root@k8s-node1 ~]# 
```


应用升级： kubectl set image (--help查看帮助)
扩容：kubectl scale --replicas=3 deployment tomcat6

```shell
[root@k8s-node1 ~]# kubectl scale --replicas=3 deployment tomcat6
deployment.apps/tomcat6 scaled
[root@k8s-node1 ~]# 

[root@k8s-node1 ~]# kubectl get pods -o wide
NAME                       READY   STATUS    RESTARTS   AGE   IP           NODE        NOMINATED NODE   READINESS GATES
tomcat6-7b84fb5fdc-hdgmc   1/1     Running   0          61s   10.244.2.5   k8s-node2   <none>           <none>
tomcat6-7b84fb5fdc-qt5jm   1/1     Running   0          19m   10.244.1.2   k8s-node3   <none>           <none>
tomcat6-7b84fb5fdc-vlrh6   1/1     Running   0          61s   10.244.2.4   k8s-node2   <none>           <none>
[root@k8s-node1 ~]# kubectl get svc -o wide    
NAME         TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE   SELECTOR
kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP        13h   <none>
tomcat6      NodePort    10.96.24.191   <none>        80:30526/TCP   16m   app=tomcat6
[root@k8s-node1 ~]#
```





扩容了多份，所有无论访问哪个node的指定端口，都可以访问到tomcat6

 http://192.168.56.101:30526/ 

![image-20200504111008668](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20200504111008668.png)

 http://192.168.56.102:30526/ 

![image-20200504111028496](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20200504111028496.png)

缩容：kubectl scale --replicas=2 deployment tomcat6

```
[root@k8s-node1 ~]#  kubectl scale --replicas=2 deployment tomcat6
deployment.apps/tomcat6 scaled
[root@k8s-node1 ~]# kubectl get pods -o wide                       
NAME                       READY   STATUS        RESTARTS   AGE     IP           NODE        NOMINATED NODE   READINESS GATES
tomcat6-7b84fb5fdc-hdgmc   0/1     Terminating   0          4m47s   <none>       k8s-node2   <none>           <none>
tomcat6-7b84fb5fdc-qt5jm   1/1     Running       0          22m     10.244.1.2   k8s-node3   <none>           <none>
tomcat6-7b84fb5fdc-vlrh6   1/1     Running       0          4m47s   10.244.2.4   k8s-node2   <none>           <none>
[root@k8s-node1 ~]# 
```





4、以上操作的yaml获取
参照k8s细节

5、删除
kubectl get all

```shell
#查看所有资源
[root@k8s-node1 ~]# kubectl get all
NAME                           READY   STATUS    RESTARTS   AGE
pod/tomcat6-7b84fb5fdc-qt5jm   1/1     Running   0          26m
pod/tomcat6-7b84fb5fdc-vlrh6   1/1     Running   0          8m16s

NAME                 TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
service/kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP        13h
service/tomcat6      NodePort    10.96.24.191   <none>        80:30526/TCP   22m

NAME                      READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/tomcat6   2/2     2            2           11h

NAME                                 DESIRED   CURRENT   READY   AGE
replicaset.apps/tomcat6-7b84fb5fdc   2         2         2       11h
[root@k8s-node1 ~]#
#删除deployment.apps/tomcat6 
[root@k8s-node1 ~]# kubectl delete  deployment.apps/tomcat6 
deployment.apps "tomcat6" deleted

#查看剩余的资源
[root@k8s-node1 ~]# kubectl get all   
NAME                 TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
service/kubernetes   ClusterIP   10.96.0.1      <none>        443/TCP        13h
service/tomcat6      NodePort    10.96.24.191   <none>        80:30526/TCP   30m
[root@k8s-node1 ~]# 
[root@k8s-node1 ~]#
#删除service/tomcat6 
[root@k8s-node1 ~]# kubectl delete service/tomcat6  
service "tomcat6" deleted
[root@k8s-node1 ~]# kubectl get all
NAME                 TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
service/kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   13h
[root@k8s-node1 ~]#

```

删了部署，pod也跟着没了呀，service只有在pod存在时才有意义，所以service已经无法访问了

kubectl delete deploye/nginx
kubectl delete service/nginx-service

主节点可以直接访问到子节点上部署的服务。

## k8s中可以用yaml创建自定义部署，pod,service等

![image-20221023163009138](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023163009138.png)

![image-20221023163133888](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023163133888.png)

![image-20221023163712296](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023163712296.png)

![image-20221023163732926](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023163732926.png)

![image-20221023163853986](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023163853986.png)

![image-20221023164825079](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023164825079.png)

![image-20221023165016848](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023165016848.png)

![image-20221023165344662](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023165344662.png)

![image-20221023170121584](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023170121584.png)

![image-20221023170253404](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023170253404.png)

***一次部署就会在一个node或多个node中产生一个或多个pod，暴露一次部署会把这些pod组合成一个service。***

***暴露的service有一个port，当外部访问这个service包含的pod所在的node的ip加上service的port时，就可以访问到pod。***

***service相当于对其内部的pod的一个负载均衡。***

***service并不一定只以端口的方式来暴露自己。***

## labels

![image-20221023172052937](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023172052937.png)

![image-20221023172225404](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023172225404.png)

![image-20221023172320772](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023172320772.png)

![image-20221023173157838](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023173157838.png)

![image-20221023173433044](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023173433044.png)

一次部署就是在各个node间创建pod。

![image-20221023185631103](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023185631103.png)

## ingress

![image-20221023190114452](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023190114452.png)

![image-20221023190641191](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023190641191.png)

![image-20221023191331509](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023191331509.png)

![image-20221023192010385](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023192010385.png)

不能配master域名解析，因为ingress只在node上，只有代理到node上才能正确解析。

![image-20221023192252979](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023192252979.png)



## 微软的helm源

~~~shell
 helm repo remove stable
 helm repo add stable http://mirror.azure.cn/kubernetes/charts/
 helm repo add incubator http://mirror.azure.cn/kubernetes/charts-incubator/
~~~



~~~shell
配置微软的镜像源可以解决下面的问题：
~~~

## Error: failed to download "stable/openebs" (hint: running `helm repo update` may help)



![image-20221031230633213](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221031230633213.png)

## kubesphere.minimal.yaml

![image-20221101090454631](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221101090454631.png)

~~~yaml
---
apiVersion: v1
kind: Namespace
metadata:
  name: kubesphere-system

---
apiVersion: v1
data:
  ks-config.yaml: |
    ---
    persistence:
      storageClass: ""
    etcd:
      monitoring: False
      endpointIps: 192.168.0.7,192.168.0.8,192.168.0.9
      port: 2379
      tlsEnable: True
    common:
      mysqlVolumeSize: 20Gi
      minioVolumeSize: 20Gi
      etcdVolumeSize: 20Gi
      openldapVolumeSize: 2Gi
      redisVolumSize: 2Gi
    metrics_server:
      enabled: False
    console:
      enableMultiLogin: False  # enable/disable multi login
      port: 30880
    monitoring:
      prometheusReplicas: 1
      prometheusMemoryRequest: 400Mi
      prometheusVolumeSize: 20Gi
      grafana:
        enabled: False
    logging:
      enabled: False
      elasticsearchMasterReplicas: 1
      elasticsearchDataReplicas: 1
      logsidecarReplicas: 2
      elasticsearchMasterVolumeSize: 4Gi
      elasticsearchDataVolumeSize: 20Gi
      logMaxAge: 7
      elkPrefix: logstash
      containersLogMountedPath: ""
      kibana:
        enabled: False
    openpitrix:
      enabled: False
    devops:
      enabled: False
      jenkinsMemoryLim: 2Gi
      jenkinsMemoryReq: 1500Mi
      jenkinsVolumeSize: 8Gi
      jenkinsJavaOpts_Xms: 512m
      jenkinsJavaOpts_Xmx: 512m
      jenkinsJavaOpts_MaxRAM: 2g
      sonarqube:
        enabled: False
        postgresqlVolumeSize: 8Gi
    servicemesh:
      enabled: False
    notification:
      enabled: False
    alerting:
      enabled: False
kind: ConfigMap
metadata:
  name: ks-installer
  namespace: kubesphere-system

---
apiVersion: v1
kind: ServiceAccount
metadata:
  name: ks-installer
  namespace: kubesphere-system

---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRole
metadata:
  creationTimestamp: null
  name: ks-installer
rules:
- apiGroups:
  - ""
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - apps
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - extensions
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - batch
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - rbac.authorization.k8s.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - apiregistration.k8s.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - apiextensions.k8s.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - tenant.kubesphere.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - certificates.k8s.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - devops.kubesphere.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - monitoring.coreos.com
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - logging.kubesphere.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - jaegertracing.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - storage.k8s.io
  resources:
  - '*'
  verbs:
  - '*'
- apiGroups:
  - admissionregistration.k8s.io
  resources:
  - '*'
  verbs:
  - '*'

---
kind: ClusterRoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: ks-installer
subjects:
- kind: ServiceAccount
  name: ks-installer
  namespace: kubesphere-system
roleRef:
  kind: ClusterRole
  name: ks-installer
  apiGroup: rbac.authorization.k8s.io

---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ks-installer
  namespace: kubesphere-system
  labels:
    app: ks-install
spec:
  replicas: 1
  selector:
    matchLabels:
      app: ks-install
  template:
    metadata:
      labels:
        app: ks-install
    spec:
      serviceAccountName: ks-installer
      containers:
      - name: installer
        image: kubesphere/ks-installer:v2.1.1
        imagePullPolicy: "Always"
~~~

## kubesphere安装

account:admin 

password:250258Dsb

![image-20221023231000456](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221023231000456.png)

如果一直有pod安装不上，可以先取消 master 节点的 taint 后安装 kubesphere ，kubesphere 安装完成了再给 master 打 taint，

耐心等待，也许要很久才能安装好。。。

## 多租户管理（重要）

![image-20221024113614186](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221024113614186.png)

有状态服务在重新拉起一个服务后，可以用到之前挂掉的服务的数据，例如mysql集群

## DevOps

![image-20221024135721140](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221024135721140.png)

## CICD

![image-20221024135940649](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221024135940649.png)

![image-20221024135956371](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221024135956371.png)

![image-20221024140014580](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221024140014580.png)

![image-20221024140027762](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221024140027762.png)

![cicd-tools-fullsize](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/cicd-tools-fullsize.jpeg)

kubesphere里面一个项目对应一个名称空间

## Jenkinsfile(devops-java-sample)

~~~Jenkinsfile
pipeline {
  agent {
    node {
      label 'maven'
    }
  }

    parameters {
        string(name:'TAG_NAME',defaultValue: '',description:'')
    }

    environment {
        DOCKER_CREDENTIAL_ID = 'dockerhub-id'
        GITHUB_CREDENTIAL_ID = 'github-id'
        KUBECONFIG_CREDENTIAL_ID = 'kubeconfig-demo'
        GITHUB_TOKEN = 'github-token'
        REGISTRY = 'docker.io'
        DOCKERHUB_NAMESPACE = 'santiagosky'
        GITHUB_ACCOUNT = 'santiagoBin'
        APP_NAME = 'devops-java-sample'
        SONAR_CREDENTIAL_ID = 'sonar-qube'
        
    }

    stages {
        stage ('checkout scm') {
            steps {
                checkout(scm)
            }
        }

        stage ('unit test') {
            steps {
                container ('maven') {
                    sh 'mvn clean  -gs `pwd`/configuration/settings.xml test'
                }
            }
        }
        
        stage('sonarqube analysis') {
          steps {
            container ('maven') {
              withCredentials([string(credentialsId: "$SONAR_CREDENTIAL_ID", variable: 'SONAR_TOKEN')]) {
                withSonarQubeEnv('sonar') {
                 sh "mvn sonar:sonar -gs `pwd`/configuration/settings.xml -Dsonar.branch=$BRANCH_NAME -Dsonar.login=$SONAR_TOKEN"
                }
              }
              timeout(time: 1, unit: 'HOURS') {
                waitForQualityGate abortPipeline: true
              }
            }
          }
        }
 
        stage ('build & push') {
            steps {
                container ('maven') {
                    sh 'mvn  -Dmaven.test.skip=true -gs `pwd`/configuration/settings.xml clean package'
                    sh 'docker build -f Dockerfile-online -t $REGISTRY/$DOCKERHUB_NAMESPACE/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER .'
                    withCredentials([usernamePassword(passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,credentialsId : "$DOCKER_CREDENTIAL_ID" ,)]) {
                        sh 'echo "$DOCKER_PASSWORD" | docker login $REGISTRY -u "$DOCKER_USERNAME" --password-stdin'
                        sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER'
                    }
                }
            }
        }

        stage('push latest'){
           when{
             branch 'master'
           }
           steps{
                container ('maven') {
                  sh 'docker tag  $REGISTRY/$DOCKERHUB_NAMESPACE/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER $REGISTRY/$DOCKERHUB_NAMESPACE/$APP_NAME:latest '
                  sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$APP_NAME:latest '
                }
           }
        }

        stage('deploy to dev') {
          when{
            branch 'master'
          }
          steps {
            input(id: 'deploy-to-dev', message: 'deploy to dev?')
            kubernetesDeploy(configs: 'deploy/dev-ol/**', enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID")
          }
        }
        stage('push with tag'){
          when{
                expression{
                    return params.TAG_NAME =~ /v.*/
                }
            }
          steps {
              container ('maven') {
                input(id: 'release-image-with-tag', message: 'release image with tag?')
                  withCredentials([usernamePassword(credentialsId: "$GITHUB_CREDENTIAL_ID", passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                    sh 'git config --global user.email "2457039825@qq.com" '
                    sh 'git config --global user.name "santiagoBin" '
                    
                    sh 'git tag -a $TAG_NAME -m "$TAG_NAME" '
                    sh 'git push https://$GIT_PASSWORD@github.com/$GITHUB_ACCOUNT/devops-java-sample.git --tags --ipv4'
                  }
                sh 'docker tag  $REGISTRY/$DOCKERHUB_NAMESPACE/$APP_NAME:SNAPSHOT-$BRANCH_NAME-$BUILD_NUMBER $REGISTRY/$DOCKERHUB_NAMESPACE/$APP_NAME:$TAG_NAME '
                sh 'docker push  $REGISTRY/$DOCKERHUB_NAMESPACE/$APP_NAME:$TAG_NAME '
          }
          }
        }
        stage('deploy to production') {
            when{
                expression{
                    return params.TAG_NAME =~ /v.*/
                }
            }
          steps {
            input(id: 'deploy-to-production', message: 'deploy to production?')
            kubernetesDeploy(configs: 'deploy/prod-ol/**', enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID")
          }
        }
    }
}
~~~

注意：1、改变shell脚本中的user.email以及user.name

​			2、'git push https://$GIT_PASSWORD@github.com/$GITHUB_ACCOUNT/devops-java-sample.git --tags --ipv4'

​			3、创建的github凭证的密码使用本账户在github取得的token，github已经不允许远程push时使用username和password来push

​			最新的提交，并且请使用加密的https协议来push。这是官方的解释：

The `https://` clone URLs are available on all repositories, regardless of visibility. `https://` clone URLs work even if you are behind a firewall or proxy.

When you `git clone`, `git fetch`, `git pull`, or `git push` to a remote repository using HTTPS URLs on the command line, Git will ask for your GitHub username and password. When Git prompts you for your password, enter your personal access token. Alternatively, you can use a credential helper like [Git Credential Manager](https://github.com/GitCredentialManager/git-credential-manager/blob/main/README.md). Password-based authentication for Git has been removed in favor of more secure authentication methods. For more information, see "[Creating a personal access token](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token)."

If you are accessing an organization that uses SAML SSO and you are using a personal access token (classic), you must also authorize your personal access token to access the organization before you authenticate. For more information, see "[About authentication with SAML single sign-on](https://docs.github.com/en/github/authenticating-to-github/about-authentication-with-saml-single-sign-on)" and "[Authorizing a personal access token for use with SAML single sign-on](https://docs.github.com/en/github/authenticating-to-github/authorizing-a-personal-access-token-for-use-with-saml-single-sign-on)."

​			 4、在本地linux服务器的k8s集群里面使用devops流水线部署您处于github中的应用时，请关闭代理，代理可能会修改本机dns,

导致虚拟机通过主机网卡访问github域名时解析失败。



### fastgithub原理

- 修改本机的 DNS 服务指向 FastGithub 自身
- 解析匹配的域名为 FastGithub 自身的 IP
- 请求安全 DNS 服务 (dnscrypt-proxy) 获取相应域名的 IP
- 选择最优的 IP 进行 SSH 或 HTTPS 反向代理

![image-20221025200644209](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221025200644209.png)

![image-20221025205529723](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221025205529723.png)

### 域名解析

***\*1、客户机解析过程\****

客户机解析就是先查询自己的DNS缓存，如果在缓存中找到对应记录，就使用该记录作为解析结果。客户机DNS缓存中的记录有两种来源：本地hosts文件和以前本机进行的DNS查询记录。本地hosts文件保存在“%systemroot\system32\drivers\etc”文件夹下，可以使用记事本编辑。默认hosts文件只有一个127.0.0.1---localhost记录，用户可以在该文件内添加新的记录，如下图所示。用户可以使用“ipconfig /displaydns”命令查看DNS缓存，使用“ipconfig /flushdns”命令刷新DNS缓存。

[![182529704.png](https://s1.51cto.com/attachment/201308/182529704.png)](https://s1.51cto.com/attachment/201308/182529704.png)

如果没有找到就将查询请求转发给DNS服务器，客户机存储着一张服务器查询列表，表内有首选DNS服务器和备用DNS服务器的记录。如果主机没能在本地缓存中找到映射条目，就会查询首选DNS服务器，如果首选DNS服务器不可用或无法解析，那么本机将会向备用DNS服务器发送查询请求。

注意：

刷新DNS缓存将清除以前的DNS查询记录，此时缓存内的记录就是hosts文件内的记录。当hosts被修改并保存后会直接刷新DNS缓存，效果跟使用“ipconfig /flushdns”命令相同。

***\*2、服务器解析过程\****

当服务器收到客户端的查询信息后，先判断所查询的域名是否属于本地区域。如果属于本地区域，DNS服务器会查询自己的记录，并回应该查询信息。如果不属于本地区域，DNS服务器将会查看本地缓存，查看是否有匹配的条目。如果在缓存中依然不能找到该记录，那么，默认情况下查询请求会依据该DNS服务器的配置继续向其他DNS服务器请求查询，解析域名。

### 域名（DNS）污染、劫持

**域名劫持**

域名服务器上都会保存一大堆的域名记录（每条记录包含“域名”和“IP地址”）。当收到域名查询的时候，域名服务器会从这堆记录中找到对方想要的，然后回应给对方。

如果域名服务器上的某条记录被【人为修改】了（改成错的），那么一旦要查询这条记录，得到的就是错误的结果。这种情况称之为“域名劫持”。

★谁有“域名劫持”的企图？

“域名劫持”通常是电信运营商（ISP）干的好事儿。很多宽带用户用的域名服务器就是 ISP 提供给你的。

举例：

前几年曾经出现过：某个 ISP 跟某网站勾结，把维基百科的流量重定向到XX。具体搞法是：该 ISP 篡改自己的域名服务器的记录，把里面跟维基百科 相关的域名记录的 IP地址 修改为XX的 IP地址。如此一来，假设你用的是这个 ISP 的域名服务器，当你在浏览器输入 http://zh.wikipedia.org/的时候，你的电脑查询到的 IP地址 其实是XX的 IP地址，所以浏览器打开的是“XX”的主页。

★如何对付“域名劫持”？

刚才说了，“域名劫持”的根源在于：域名服务器上的记录被人给改了。要对付这种耍流氓，最直接的办法就是不要使用这种流氓 ISP 提供的域名服务器，改用国外那些比较靠谱的。目前口碑最好的，大概是 Google 提供的两个域名服务器，IP地址 分别是 8.8.8.8 和 8.8.4.4 ——这俩不光是地址好记，更重要的是，不会耍流氓。



**域名污染**

先提醒一下：“域名污染”这个词还有其它几个别名，分别是“域名欺骗”、“域名缓存投毒”（洋文叫：DNS cache poisoning）。今后看到这几个别名，要晓得是同一个意思。

“域名污染”的原理，简单说来是这样滴：当你的电脑向域名服务器发送了“域名查询”的请求，然后域名服务器把回应发送给你的电脑，这之间是有一个时间差的。如果某个攻击者能够在域名服务器的“DNS应答”还没有到达你的电脑之前，先伪造一个错误的“DNS应答”发给你电脑。那么你的电脑收到的就是错误的信息，并得到一个错误的 IP地址。

★谁有“域名污染”的企图？

从技术上讲，只要攻击者能够位于“你”和“域名服务器”的传输线路中间，那么攻击者就有机会搞“域名污染”。能够做到这点的，可能是一个黑客/骇客，也可能是 ISP。

★某国家防火墙的两种“域名污染”

刚才俺解释了“域名污染”的原理，那种形式不妨称为“直接污染”。由于某国家防火墙的特殊性，它不但可以做到“直接污染”，还可以做到“间接污染”。而普通的骇客顶多只能做到“直接污染”，难以做到“大范围的间接污染”。

那么这两种污染有啥区别捏？且听俺细细道来。

◇某国家防火墙部署在哪？

首先有必要先扫盲一下“某国家防火墙（其实是一种IDS，也就是入侵检测系统）的部署位置”。X国互联网只有少数几个国际出口（名气较大的是：A出口、B出口、C出口）。如果你要访问国外网站，你的网络数据流就必定会经过其中的某个“国际出口”。

◇某国家防火墙的直接污染

因为某国家防火墙部署在国际出口。如果你用的是【国外的】域名服务器，你的“DNS请求”必定会经过国际出口；同样，域名服务器的“DNS应答”必定也会经过国际出口才能到你的电脑。这一来一回就给某国家防火墙 提供了机会。

这种污染就是俺所说的“直接污染”。

◇某国家防火墙 的间接污染

刚才介绍了“使用国外域名服务器会被直接污染”。那如果你用的是【国内的】域名服务器捏？就会被“间接污染”。过程如下：

1. 比方说你用的是电信的 DNS服务器，然后你想要访问某个被不受欢迎的网站。

2. 对于不受欢迎的网站，其网站服务器必定在国外，而且网站的域名肯定也不会使用 CN 之下的域名。所以，被封锁的网站，其上级域名的“权威域名服务器”肯定也是在国外。

3. 当你向“电信的DNS服务器”查询反共网站的域名，这台“电信的DNS服务器”就会去找这个不受欢迎的网站的上一级域名对应的“权威域名服务器”去进行“域名查询”。

4. 因为是从国外进行域名查询，相关的数据流必定要经过国际出口。一旦经过国际出口，就会被 某国家防火墙 污染。

5. 如此一来，“电信的域名服务器”拿到的是已经被污染的域名记录（里面的IP是错的）。而且“电信的域名服务器”会把这条错误的记录保存在自己的域名缓存中。

6. 下次如果有另一个网友也找这台“电信的域名服务”查询这个不受欢迎的网站，也会查到错误的结果。

上述过程不断重复，最终会导致：全国所有的域名服务器，它们的缓存中只要是包含了不受欢迎的网站的记录，记录中的 IP地址 必定是错的（这个错误的 IP地址 也就是 某国家防火墙 伪造的那个）。所以说“间接污染”是很牛逼的，可以把错误的域名记录扩散到全国。

刚才俺说了，“域名污染”也叫“域名缓存投毒”。“投毒”一词真的非常形象——就好象在某条河流的源头下毒，从而把整条河流的水都污染。“域名污染”直接破坏了互联网的基础设施。

### 代理

![image-20221025224113919](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221025224113919.png)

## ShadingSphere

主从复制策略：

![image-20221026104125843](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026104125843.png)

读写分离策略：

![image-20221026104220168](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026104220168.png)

![image-20221026111045405](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026111045405.png)

![image-20221026111643802](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026111643802.png)

***后面只要微服务连接到sharding数据库，操作这个数据库，就能映射改变真正存储数据的分库分表了的数据库了。***

![image-20221026130027845](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026130027845.png)

![image-20221026130320898](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026130320898.png)

当然这两个文件我们在demo设定时统一设定sharding的数据库的schemaName都是sharding_db,被代理的数据源的数据库是两主（demo_ds_0(3307),demo_ds_1(3317)）和两从（demo_ds_0(3307),demo_ds_1(3317)）。

![image-20221026131707527](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026131707527.png)

## Mysql集群

### 创建 Master 实例并启动

centos7里：

先建好文件,否则会导致无法启动容器，会被默认建立为一个文件夹：

~~~shell
touch /mydata/mysql/master/conf/my.cnf
~~~

~~~shell
docker run  --privileged=true --name mysql-master  -p 3307:3306 -v /mydata/mysql/master/log:/var/log/mysql
-v /mydata/mysql/master/data:/var/lib/mysql -v /mydata/mysql/master/conf/my.cnf:/etc/mysql/my.cnf:rw 
-e MYSQL_ROOT_PASSWORD=root -d mysql:5.7 
~~~

参数说明 -p 3307:3306：将容器的 3306 端口映射到主机的 3307 端口 -v /mydata/mysql/master/conf:/etc/mysql：将配置文件夹挂在到主机-v /mydata/mysql/master/log:/var/log/mysql：将日志文件夹挂载到主机-v /mydata/mysql/master/data:/var/lib/mysql/：将配置文件夹挂载到主机-e MYSQL_ROOT_PASSWORD=root：初始化 root 用户的密码

修改 master 基本配置 

vim /mydata/mysql/master/conf/my.cnf

 [client]

default-character-set=utf8

 [mysql] 

default-character-set=utf8 

[mysqld] 

init_connect='SET collation_connection = utf8_unicode_ci' 

init_connect='SET NAMES utf8'

 character-set-server=utf8

 collation-server=utf8_unicode_ci

 skip-character-set-client-handshake 

skip-name-resolve

 注意：skip-name-resolve 一定要加，不然连接 mysql 会超级慢

添加 master 主从复制部分配置 

server_id=1 

log-bin=mysql-bin

read-only=0 

binlog-do-db=gulimall-ums 

binlog-do-db=gulimall-pms

binlog-do-db=gulimall-oms 

binlog-do-db=gulimall-sms 

binlog-do-db=gulimall-wms 

binlog-do-db=gulimall-admin

replicate-ignore-db=mysql 

replicate-ignore-db=sys 

replicate-ignore-db=information_schema 

replicate-ignore-db=performance_schema

### 创建 Slave 实例并启动

先建好文件,否则会导致无法启动容器，会被默认建立为一个文件夹：

~~~shell
touch /mydata/mysql/slave/conf/my.cnf
~~~

~~~shell
docker run  --privileged=true --name mysql-slave  -p 3307:3306 -v /mydata/mysql/slave/log:/var/log/mysql
-v /mydata/mysql/slave/data:/var/lib/mysql -v /mydata/mysql/slave/conf/my.cnf:/etc/mysql/my.cnf:rw 
-e MYSQL_ROOT_PASSWORD=root -d mysql:5.7 
~~~

修改 slave 基本配置

 vim /mydata/mysql/slaver/conf/my.cnf

 [client] 

default-character-set=utf8

 [mysql] 

default-character-set=utf8 

[mysqld] 

init_connect='SET collation_connection = utf8_unicode_ci' 

init_connect='SET NAMES utf8' 

character-set-server=utf8 

collation-server=utf8_unicode_ci 

skip-character-set-client-handshake 

skip-name-resolve

添加 master 主从复制部分配置 

server_id=2 

log-bin=mysql-bin 

read-only=1

binlog-do-db=gulimall-ums 

binlog-do-db=gulimall-pms 

binlog-do-db=gulimall-oms 

binlog-do-db=gulimall-sms 

binlog-do-db=gulimall-wms 

binlog-do-db=gulimall-admin 

replicate-ignore-db=mysql 

replicate-ignore-db=sys 

replicate-ignore-db=information_schema 

replicate-ignore-db=performance_schema

重启 slaver

### 为 master 授权用户来他的同步数据

1、进入 master 容器 

docker exec -it mysql /bin/bash 

2、进入 mysql 内部 （mysql –uroot -p） 

1）、授权 root 可以远程访问（ 主从无关，为了方便我们远程连接mysql）

grant all privileges on  *.* to 'root'@'%' identified by 'root' with grant option; 

flush privileges; 

2）、添加用来同步的用户 

GRANT REPLICATION SLAVE ON *.* to 'backup'@'%' identified by '123456'; 

3）、查看 master 状态 

show master status\G;

### 配置 slaver 同步 master 数据

1、进入 slaver 容器 

docker exec -it mysql-slaver-01 /bin/bash 

2、进入 mysql 内部（mysql –uroot -p） 

1）、授权 root 可以远程访问（ 主从无关，为了方便我们远程连接mysql）

grant all privileges on *.* to 'root'@'%' identified by 'root' with grant option; flush privileges;

2）、设置主库连接 

change master to master_host='mysql-master.gulimall',master_user='backup',master_password='123456',mas ter_log_file='mysql-bin.000003',master_log_pos=0,master_port=3306; 

3）、启动从库同步 

start slave; 

4）、查看从库状态 

show slave status\G;

![image-20221026134815167](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026134815167.png)

至此主从配置完成； 

总结： 

1）、主从数据库在自己配置文件中声明需要同步哪个数据库，忽略哪个数据库等信息。并且 server-id 不能一样 

2）、主库授权某个账号密码来同步自己的数据 

3）、从库使用这个账号密码连接主库来同步数据

![image-20221026135429665](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20221026135429665.png)

![image-20221026135517606](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026135517606.png)

![image-20221026135614116](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026135614116.png)

![image-20221026135741687](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026135741687.png)

![image-20221026140125019](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026140125019.png)

## Redis集群

![image-20221026150832624](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026150832624.png)

![image-20221026150847648](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026150847648.png)

### redis集群模式（主从，哨兵模式，槽和一致性hash）

#### 主从模式

Redis 的主从模式跟 mysql 主从复制原理差不多，在主从复制中，数据库分为两类：主数据库（master）和从数据库（slave）。
主从复制主要有如下特点：

主数据库可以进行读写操作，从库只能进行读操作（可以配置从库支持读写操作，不建议）
当主数据库的读写操作导致数据变化时会自动将数据同步给从数据库
主从模式可以是一主多从，即一个 master 可以拥有多个 slave，但只能一从一主，即一个 slave 只能对应一个 master
slave 挂了之后不会影响其它 slave 读和 master 读写，重启启动 slave 之后会自动从 master 同步数据过来
master 挂了以后，不影响 slave 读，但 Redis 不再提供写服务，master 重启后 Redis 将重新对外提供写服务
master 挂了以后，不会在 slave 节点中重新选一个 master
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210114223417945.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0NTk2Mjky,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2021011516574467.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0NTk2Mjky,size_16,color_FFFFFF,t_70)

当slave启动后，主动向master发送SYNC命令。master接收到SYNC命令后在后台保存快照（RDB持久化）和缓存保存快照这段时间的命令，然后将保存的快照文件和缓存的命令发送给slave。slave接收到快照文件和命令后加载快照文件和缓存的执行命令。
  复制初始化后，master每次接收到的写命令都会同步发送给slave，保证主从数据一致性。

主从模式的缺点：
  master 节点在主从模式中唯一，若 master 挂掉，则 Redis 无法对外提供写服务。

#### 哨兵模式

Redis-Sentinel 是 Redis 给我们提供的一种高可用解决方案，Redis-sentinel本身也是一个独立运行的进程，它能监控多个master-slave集群，发现master宕机后能进行自动切换。Sentinel可以监视任意多个主服务器以及主服务器属下的从服务器，并在被监视的主服务器下线时，自动执行故障转移操作。

sentinel 中文含义为哨兵，所以又可以叫哨兵模式，其特点如下：

sentinel 模式是建立在主从模式的基础上，如果只有一个 Redis 节点，sentinel 就没有任何意义
当 master 挂了以后，sentinel 会在 slave 中选择一个做为 master，并修改它们的配置文件，其他 slave 的配置文件也会被修改，比如 slaveof 属性会指向新的 master
当master重新启动后，它将不再是master而是做为slave接收新的master的同步数据
sentinel因为也是一个进程有挂掉的可能，所以sentinel也会启动多个形成一个sentinel集群
多sentinel配置的时候，sentinel之间也会自动监控
当主从模式配置密码时，sentinel也会同步将配置信息修改到配置文件中，不需要担心
一个sentinel或sentinel集群可以管理多个主从Redis，多个sentinel也可以监控同一个redis
sentinel最好不要和Redis部署在同一台机器，不然Redis的服务器挂了以后，sentinel也挂了
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210116184114360.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0NTk2Mjky,size_16,color_FFFFFF,t_70)

Sentinel 模式的工作机制：

每个 sentinel 以每秒钟一次的频率向它所知的 master，slave 以及其他 sentinel 实例发送一个 PING 命令
如果一个实例距离最后一次有效回复 PING 命令的时间超过 down-after-milliseconds 选项所指定的值， 则这个实例会被 sentinel 标记为主观下线。
如果一个 master 被标记为主观下线，则正在监视这个 master 的所有 sentinel 要以每秒一次的频率确认 master 的确进入了主观下线状态
当有足够数量的 sentinel（大于等于配置文件指定的值）在指定的时间范围内确认 master 的确进入了主观下线状态， 则 master 会被标记为客观下线
在一般情况下， 每个 sentinel 会以每 10 秒一次的频率向它已知的所有 master，slave 发送 INFO 命令
当 master 被 sentinel 标记为客观下线时，sentinel 向下线的 master 的所有 slave 发送 INFO 命令的频率会从 10 秒一次改为 1 秒一次
若没有足够数量的 sentinel 同意 master 已经下线，master 的客观下线状态就会被移除；若 master 重新向 sentinel 的 PING 命令返回有效回复，master 的主观下线状态就会被移除
当使用 sentinel 模式的时候，客户端就不要直接连接 Redis，而是连接sentinel 的 ip 和 port，由 sentinel 来提供具体的可提供服务的 Redis 实现，这样当 master 节点挂掉以后，sentinel 就会感知并将新的 master 节点提供给使用者。

#### 槽

**主节点提供读写服务，从节点不提供服务，只作为主节点的一个备份。**

![image-20221026150936579](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026150936579.png)

![image-20221026150946689](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026150946689.png)

![image-20221026151016520](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026151016520.png)

**一致性hash的由2^32个位置分布在一个hash环上面。**

**蓝颜色的是key，绿颜色的是节点。**

**key计算hash后，找离其最近的节点存储。**

#### 一致性hash

![image-20221026151532963](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026151532963.png)

![image-20221026151546885](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026151546885.png)

![image-20221026151612860](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026151612860.png)

### 部署 Redis Cluster

3 主 3 从方式，从为了同步备份，主进行 slot 数据分片

~~~shell
for port in $(seq 7001 7006); \
do \
mkdir -p /mydata/redis/node-${port}/conf
touch /mydata/redis/node-${port}/conf/redis.conf
cat << EOF >/mydata/redis/node-${port}/conf/redis.conf
port ${port}
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
cluster-announce-ip 192.168.56.10
cluster-announce-port ${port}
cluster-announce-bus-port 1${port}
appendonly yes
EOF
docker run -p ${port}:${port} -p 1${port}:1${port} --name redis-${port} \ -v /mydata/redis/node-${port}/data:/data \ -v /mydata/redis/node-${port}/conf/redis.conf:/etc/redis/redis.conf \ -d redis:5.0.7 redis-server /etc/redis/redis.conf; \
done
~~~

使用 redis 建立集群

~~~shell
docker exec -it redis-7001 bash
redis-cli --cluster create 192.168.56.10:7001 192.168.56.10:7002 192.168.56.10:7003192.168.56.10:7004 192.168.56.10:7005 192.168.56.10:7006 --cluster-replicas 1
~~~

测试集群效果

~~~shell
随便进入某个 redis 容器
docker exec -it redis-7002 /bin/bash
使用 redis-cli 的 cluster 方式进行连接
redis-cli -c -h 192.168.56.10 -p 7006
cluster info； 获取集群信息
cluster nodes；获取集群节点
Get/Set 命令测试，将会重定向
节点宕机，slave 会自动提升为 master，master 开启后变为 slave
~~~

![image-20221026155556779](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026155556779.png)

![image-20221026155650684](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026155650684.png)

![image-20221026160136936](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026160136936.png)

主节点m1挂了，它的从节点s1会被选举为新的主节点，保证整个集群的可用性。

## ElasticSearch集群

![image-20221026162552622](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162552622.png)

![image-20221026162603132](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162603132.png)

![image-20221026162620991](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162620991.png)

![image-20221026162629765](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162629765.png)

![image-20221026162650602](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162650602.png)

![image-20221026162733949](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162733949.png)

![image-20221026162749063](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162749063.png)

![image-20221026162758444](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162758444.png)

![image-20221026162809136](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162809136.png)

![image-20221026162820099](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162820099.png)

![image-20221026162829472](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162829472.png)

![image-20221026162842336](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162842336.png)

![image-20221026162929614](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162929614.png)

![image-20221026162942042](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162942042.png)

![image-20221026162956743](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026162956743.png)

### 集群搭建

所有之前先运行：sysctl -w vm.max_map_count=262144

 我们只是测试，所以临时修改。永久修改使用下面 #防止 JVM 报错 

echo vm.max_map_count=262144 >> /etc/sysctl.conf sysctl -p



Docker 创建容器时默认采用 bridge 网络，自行分配 ip，不允许自己指定。

在实际部署中，我们需要指定容器 ip，不允许其自行分配 ip，尤其是搭建集群时，固定ip是必须的。 我们可以创建自己的 bridge 网络 ： mynet，创建容器的时候指定网络为mynet 并指定ip即可。 

查看网络模式

 docker network ls； 

创建一个新的 bridge 网络 

docker network create --driver bridge --subnet=172.18.12.0/16 --gateway=172.18.1.1mynet 

查看网络信息 

docker network inspect mynet 

以后使用--network=mynet --ip 172.18.12.x 指定 ip

### Master 节点创建

~~~shell
for port in $(seq 1 3); \
do \
mkdir -p /mydata/elasticsearch/master-${port}/config
mkdir -p /mydata/elasticsearch/master-${port}/data
chmod -R 777 /mydata/elasticsearch/master-${port}
cat << EOF >/mydata/elasticsearch/master-${port}/config/elasticsearch.yml
cluster.name: my-es #集群的名称，同一个集群该值必须设置成相同的node.name: es-master-${port} #该节点的名字
node.master: true #该节点有机会成为 master 节点
node.data: false #该节点可以存储数据
network.host: 0.0.0.0
http.host: 0.0.0.0 #所有 http 均可访问
http.port: 920${port}
transport.tcp.port: 930${port}
#discovery.zen.minimum_master_nodes: 2 #设置这个参数来保证集群中的节点可以知道其它 N 个有 master 资格的节点。官方推荐（N/2）+1
discovery.zen.ping_timeout: 10s #设置集群中自动发现其他节点时 ping 连接的超时时间
discovery.seed_hosts: ["172.18.12.21:9301", "172.18.12.22:9302", "172.18.12.23:9303"]
#设置集群中的 Master 节点的初始列表，可以通过这些节点来自动发现其他新加入集群的节点，es7的新增配置
cluster.initial_master_nodes: ["172.18.12.21"] #新集群初始时的候选主节点，es7 的新增配置EOF
docker run --name elasticsearch-node-${port} \ -p 920${port}:920${port} -p 930${port}:930${port} \ --network=mynet --ip 172.18.12.2${port} \
-e ES_JAVA_OPTS="-Xms300m -Xmx300m" \ -v
/mydata/elasticsearch/master-${port}/config/elasticsearch.yml:/usr/share/elasticsearch/config/el
asticsearch.yml \ 
-v /mydata/elasticsearch/master-${port}/data:/usr/share/elasticsearch/data \ 
-v /mydata/elasticsearch/master-${port}/plugins:/usr/share/elasticsearch/plugins \ 
-d elasticsearch:7.4.2
done
~~~

### Data-Node 创建

~~~shell
for port in $(seq 4 6); \
do \
mkdir -p /mydata/elasticsearch/node-${port}/config
mkdir -p /mydata/elasticsearch/node-${port}/data
chmod -R 777 /mydata/elasticsearch/node-${port}
cat << EOF >/mydata/elasticsearch/node-${port}/config/elasticsearch.yml
cluster.name: my-es #集群的名称，同一个集群该值必须设置成相同的node.name: es-node-${port} #该节点的名字
node.master: false #该节点有机会成为 master 节点
node.data: true #该节点可以存储数据
network.host: 0.0.0.0
#network.publish_host: 192.168.56.10 #互相通信 ip，要设置为本机可被外界访问的ip，否则无法通信
http.host: 0.0.0.0 #所有 http 均可访问
http.port: 920${port}
transport.tcp.port: 930${port}
#discovery.zen.minimum_master_nodes: 2 #设置这个参数来保证集群中的节点可以知道其它 N 个有 master 资格的节点。官方推荐（N/2）+1
discovery.zen.ping_timeout: 10s #设置集群中自动发现其他节点时 ping 连接的超时时间
discovery.seed_hosts: ["172.18.12.21:9301", "172.18.12.22:9302", "172.18.12.23:9303"] 
#设置集群中的 Master 节点的初始列表，可以通过这些节点来自动发现其他新加入集群的节点，es7的新增配置
cluster.initial_master_nodes: ["172.18.12.21"] #新集群初始时的候选主节点，es7 的新增配置EOF
docker run --name elasticsearch-node-${port} \ -p 920${port}:920${port} -p 930${port}:930${port} \ --network=mynet --ip 172.18.12.2${port} \ -e ES_JAVA_OPTS="-Xms300m -Xmx300m" \ 
-v
/mydata/elasticsearch/node-${port}/config/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml \ 
-v /mydata/elasticsearch/node-${port}/data:/usr/share/elasticsearch/data \ 
-v /mydata/elasticsearch/node-${port}/plugins:/usr/share/elasticsearch/plugins \ 
-d elasticsearch:7.4.2
done

~~~

![image-20221026170042072](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026170042072.png)

## RabbitMQ集群

![image-20221026172437551](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026172437551.png)

### 搭建集群

~~~shell
mkdir /mydata/rabbitmq
cd rabbitmq/
mkdir rabbitmq01 rabbitmq02 rabbitmq03

docker run -d --hostname rabbitmq01 --name rabbitmq01 -v/mydata/rabbitmq/rabbitmq01:/var/lib/rabbitmq -p 15673:15672 -p 5673:5672 -eRABBITMQ_ERLANG_COOKIE='atguigu' rabbitmq:management

docker run -d --hostname rabbitmq02 --name rabbitmq02 -v /mydata/rabbitmq/rabbitmq02:/var/lib/rabbitmq -p 15674:15672 -p 5674:5672 -eRABBITMQ_ERLANG_COOKIE='atguigu' --link rabbitmq01:rabbitmq01rabbitmq:management

docker run -d --hostname rabbitmq03 --name rabbitmq03 -v/mydata/rabbitmq/rabbitmq03:/var/lib/rabbitmq -p 15675:15672 -p 5675:5672 -eRABBITMQ_ERLANG_COOKIE='atguigu' --link rabbitmq01:rabbitmq01 --linkrabbitmq02:rabbitmq02 rabbitmq:management

--hostname 设置容器的主机名
RABBITMQ_ERLANG_COOKIE 节点认证作用，部署集成时 需要同步该值


~~~

### 节点加入集群

~~~shell
docker exec -it rabbitmq01 /bin/bash

rabbitmqctl stop_app
rabbitmqctl reset
rabbitmqctl start_app
Exit

进入第二个节点
docker exec -it rabbitmq02 /bin/bash
rabbitmqctl stop_app
rabbitmqctl reset
rabbitmqctl join_cluster --ram rabbit@rabbitmq01
rabbitmqctl start_app
exit

进入第三个节点
docker exec -it rabbitmq03 bash
rabbitmqctl stop_app
rabbitmqctl reset
rabbitmqctl join_cluster --ram rabbit@rabbitmq01
rabbitmqctl start_app
exit
~~~

### 实现镜像集群

~~~shell
docker exec -it rabbitmq01 bash

rabbitmqctl set_policy -p / ha "^" '{"ha-mode":"all","ha-sync-mode":"automatic"}' 可以使用 rabbitmqctl list_policies -p /；查看 vhost/下面的所有 policy

在 cluster 中任意节点启用策略，策略会自动同步到集群节点
rabbitmqctl set_policy-p/ha-all"^"’{“ha-mode”:“all”}’ 策略模式 all 即复制到所有节点，包含新增节点，策略正则表达式为“^” 表示所有匹配所有队列名称。“^hello”表示只匹配名为 hello 开始的队列
~~~

## k8s搭建有状态服务

![image-20221026200839354](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026200839354.png)

![image-20221026204751797](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026204751797.png)

![image-20221026205143019](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026205143019.png)

**一切的操作基于docker原生命令而来：**

~~~shell
docker run  --privileged=true --name mysql-master  -p 3307:3306 -v /mydata/mysql/master/log:/var/log/mysql
-v /mydata/mysql/master/data:/var/lib/mysql -v /mydata/mysql/master/conf/my.cnf:/etc/mysql/my.cnf:rw 
-e MYSQL_ROOT_PASSWORD=root -d mysql:5.7 
~~~

![image-20221026205712276](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026205712276.png)

![image-20221026205951935](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026205951935.png)

![image-20221026212658232](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026212658232.png)



![image-20221026223318751](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026223318751.png)

![image-20221026224502818](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026224502818.png)

![image-20221026224709688](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026224709688.png)

![image-20221026225119292](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221026225119292.png)

![image-20221027094752083](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027094752083.png)

![image-20221027095950786](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027095950786.png)

![image-20221027100300192](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027100300192.png)

![image-20221027102601492](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027102601492.png)

![image-20221027102622528](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027102622528.png)

![image-20221027134449857](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027134449857.png)



## 指定工作负载的有状态副本来自定义创建服务

![image-20221027135256817](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027135256817.png)

![image-20221027135502868](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027135502868.png)

![image-20221027140131039](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027140131039.png)

![image-20221027140143330](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027140143330.png)

**创建有状态服务时，一般没有暴露外网访问的选项，因为有状态服务一般涉及数据存储，暴露不太安全，**

**创建无状态服务或自定义创建时，则有该选项。**

## k8s部署应用

![image-20221027150027459](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027150027459.png)

![image-20221027153110845](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027153110845.png)

![image-20221027153423608](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027153423608.png)

![image-20221027153528341](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027153528341.png)

![image-20221027160356000](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027160356000.png)

![image-20221027193134347](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027193134347.png)

**Dockerfile里面:**

![image-20221027194620219](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027194620219.png)

![image-20221027195003498](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027195003498.png)

## 关于Dockerfile里面的EXPOSE参数（重要）

![image-20221027195719271](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027195719271.png)

![image-20221027201322331](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027201322331.png)

**expose的作用其实没有想象中的那么大：**

![image-20221027205539214](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027205539214.png)

**EXPOSE在指明-p端口映射关系时是没有作用的，只有在没有明确指明端口映射，即使用-P时，才会使用宿主机的随机端口来映射到我们暴露的这个端口上。**

**如果没有写EXPOSE，那么-P也不会自动映射，-p手动指定仍然生效。也就是说，-p的手动映射不受EXPOSE参数影响，如-p 8080:8006,**

**即使EXPOSE了8080,那么访问宿主机的8080端口，仍然访问的是容器内的8006端口，如果这时候服务恰好不是部署在容器的8006端口上，那么肯定是访问不到这个服务的！并且端口映射规则不妨碍服务在容器中运行在哪一个端口，服务运行在容器哪一个端口只由服务的配置文件中的server.port保证！端口映射只保证宿主机的一个端口映射到容器的一个端口上，这并不能保证能正确访问到服务，因为如果映射到的容器端口如果与服务在容器内运行的端口不一致，就肯定访问不到啊！**

**但是为了保证在用户没有指明-p手动端口映射时，能够访问到正确的端口，把我们EXPOSE的端口和服务实际的server.port保持一致，还是很有必要的,这是外部访问映射规则的宿主机端口时能够正确映射到容器中真正服务端口的保证。**

![image-20221027210602155](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027210602155.png)

![image-20221027210700552](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027210700552.png)

![image-20221027210926038](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027210926038.png)

![image-20221027214617488](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027214617488.png)

![image-20221027215433707](C:\Users\Lenovo\AppData\Roaming\Typora\typora-user-images\image-20221027215433707.png)

![image-20221027220058277](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027220058277.png)

保证集群的可用性：最大不可用为整个集群的25%，最大存活数不超过25%.

![image-20221027220739208](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027220739208.png)

![image-20221027221525619](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027221525619.png)



## Shift+tab可以缩进一个tab

## port、NodePort、targetPort（重要）

![image-20221027225104222](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027225104222.png)

![image-20221027225227458](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027225227458.png)

**节点端口不能重复**

![image-20221027225626353](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027225626353.png)

![image-20221027231054450](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027231054450.png)

![image-20221027231437499](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027231437499.png)

![image-20221027232410894](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027232410894.png)

![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027232922012.png)

![image-20221027232619177](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221027232619177.png)

**nodeport一旦创建会在所有node创建，所以访问任一节点的nodeport，即使这个node上并没有部署这个服务的副本，也会转到其它部署了服务副本的node上，这保证了服务在整个集群的可用性。**

**service中包含了多个相同的服务示例pod时，会自动负载均衡到一个pod上，天生的负载均衡能力。**

## 流水线

### 参数化构建

![image-20221028104608833](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028104608833.png)

![image-20221028104708335](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028104708335.png)

![image-20221028104746599](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028104746599.png)

![image-20221028105157499](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028105157499.png)

![image-20221028105235051](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028105235051.png)

![image-20221028112530303](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028112530303.png)

![image-20221028115641937](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028115641937.png)

![image-20221028120336272](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028120336272.png)

![image-20221028121246292](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028121246292.png)

## 数据库转储[ERR] 1273 - Unknown collation: 'utf8mb4_0900_ai_ci'

**报错原因：**
**生成转储文件的数据库版本为8.0,要导入sql文件的数据库版本为5.6,因为是高版本导入到低版本，引起1273错误**

**解决方法：**
**打开sql文件，将文件中的所有**
**utf8mb4_0900_ai_ci替换为utf8_general_ci**
**utf8mb4替换为utf8**
**保存后再次运行sql文件，运行成功**

![image-20221028162411797](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028162411797.png)

## k8s部署微服务OOM

![image-20221028162533838](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028162533838.png)

![image-20221028162803398](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221028162803398.png)

## No matching configuration files found for gulimall-gateway-deploy.yaml

**之前的Jenkinsfile中的部署步骤：**

~~~shell
stage('部署到k8s') {
            when{
                branch 'main'
            }
            steps {
                    input(id: "deploy-to-dev-$PROJECT_NAME", message: "是否将项目 $PROJECT_NAME 部署到集群中?")
                    sh 'ls'
                    kubernetesDeploy(configs: "$PROJECT_NAME/deploy/**", enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID")
            }
        }
~~~

![image-20221030204038083](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221030204038083.png)

**把代码推送到gitee：**

![image-20221030204140474](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221030204140474.png)

![image-20221030211001580](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221030211001580.png)



![](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221030205903107.png)

### 目前的解决方案：

**先把gulimall-gateway-deploy.yaml直接放到父工程下面，让jenkins执行到部署这一步时，把这个yaml作为k8s部署的配置文件。**

![image-20221030203248378](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221030203248378.png)

**修改后的Jenkinsfile中的部署步骤：**

~~~shell
stage('部署到k8s') {
            when{
                branch 'main'
            }
            steps {
                    input(id: "deploy-to-dev-$PROJECT_NAME", message: "是否将项目 $PROJECT_NAME 部署到集群中?")
                    sh 'ls'
                    kubernetesDeploy(configs: "$PROJECT_NAME-deploy.yaml", enableConfigSubstitution: true, kubeconfigId: "$KUBECONFIG_CREDENTIAL_ID")
            }
        }
~~~

![image-20221030204631522](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221030204631522.png)

**通过shell可以看到deploy文件夹确实存在，为什么Jenkins访问不到？这究竟是服务器的问题，还是jenkins内部的bug？就很迷。。。**

![image-20221029102147853](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221029102147853.png)

### ensubst

![image-20221029104453357](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221029104453357.png)

![image-20221029104731539](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221029104731539.png)

## github/gitee的k8s推送

~~~shell
git push https://$GIT_PASSWORD@github.com/$GITHUB_ACCOUNT/devops-java-sample.git --tags --ipv4

git push http://$GIT_USERNAME:$GIT_PASSWORD@gitee.com/$GITEE_ACCOUNT/gulimall.git --tags --ipv4
~~~

**注意推送到gitee/github的tag不能已存在。**

## k8s查看nodeport端口

![image-20221031103142032](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221031103142032.png)

**ca787e3698bf79334eedc4d597167fd5b46e69ab**

**a4e7a321970602b16cd4c7e322f1a656fa261878**

~~~shell

~~~

## 注意mysql现在好像要多一个挂载的配置文件了

![image-20221102094718747](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102094718747.png)

![image-20221102094735268](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102094735268.png)

## mysql8修改密码的语句

![image-20221102100623877](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102100623877.png)

![image-20221102103727771](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102103727771.png)

## 允许远程登录mysql

~~~mysql
 use mysql;
 select user,host from user;
 +------------------+-----------+
| user             | host      |
+------------------+-----------+
| root             | %         |
| admin            | localhost |
| mysql.infoschema | localhost |
| mysql.session    | localhost |
| mysql.sys        | localhost |
| zhangj           | localhost |
+------------------+-----------+如果是这样就不要改了root %
不是的话
update user set host='%' where user='root';
再改密码：
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123';

~~~

![image-20221102104123216](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102104123216.png)

change master to master_host='mysql-master.gulimall',master_port=3306,master_user='slave',master_password='123456',master_log_file='mysql-bin.000010',master_log_pos=688;


## Docker容器中使用PING命令报错：bash: ping: command not found

~~~shell
apt-get update

apt install iputils-ping

上面完成如果ping不通，再执行：
apt install net-tools
~~~

Worker 1 failed executing transaction 'ANONYMOUS' at master log mysql-bin.000009, end_log_pos 422; Error 'Can't create database 'gulimall-oms'; database exists' on query. Default database: 'gulimall-oms'. Query: 'CREATE DATABASE `gulimall-oms` CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci''

这个错误明显就是从库中已经存在数据库，主库又要从库重新创建就报错了。

原因应该就是在配置主从时ops写成0导致从头开始写。



## 重启k8s集群会导致mysql主从失效

**其主要的原因就是binlog日志名变化了，与重启之前在从库中设置的不一样了，所以导致从库无法正确读取相应的binlog日志信息来同步数据。**

![image-20221102190928427](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102190928427.png)

**解决办法就是重启之后重新设置主从。**

~~~shell
在从库mysql中：
stop slave;

change master to master_host='mysql-master.gulimall',master_port=3306,master_user='slave',master_password='123456',master_log_file='mysql-bin.000016',master_log_pos=771;
//设置pos为0，可以把重启后在主库中变化的数据同步到从库中去。如果设置为688，那么重启后变化的数据就无法同步过去了，因为是从binlog日志pos处开始同步的。

start slave;

~~~

![image-20221102195611930](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102195611930.png)

## es-conf注意

![image-20221102202022121](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102202022121.png)

## docker 提交

![image-20221102215028348](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102215028348.png)

![image-20221102215811600](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102215811600.png)

![image-20221102223043843](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221102223043843.png)

## **no protocol，没有指定通信协议异常**

![image-20221104185411431](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221104185411431.png)

![image-20221105215713342](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105215713342.png)

![image-20221103131713870](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103131713870.png)

## nacos-config线上问题

![image-20221103185446435](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103185446435.png)

**如果引入了nacos config最好加上config的服务器地址配置，防止线上报错。并且：**

![image-20221104194511717](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221104194511717.png)

**一定要保证prod配置文件的服务器地址要覆盖掉默认的配置文件的地址，防止服务发现不了。**

![image-20221103185616339](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103185616339.png)

## 80是http默认端口，443是https默认访问端口

## project-admin拥有创建网关的权限

![image-20221103193021824](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103193021824.png)

## 应用路由（加一层）

![image-20221103194732554](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103194732554.png)

**ingress为集群拦截域名请求，在80端口。再转给我们部署的项目的nginx,再又项目的nginx转给网关，网关再来分发到特定微服务上**

## docker commit并不会打包挂载的静态资源

![image-20221103200854045](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103200854045.png)

## tar 压缩

~~~shell
#tar -zcvf 压缩后的文件名 压缩的文件
tar -zcvf html.tar.gz html

#如果压缩的文件处为*则表示把当前文件夹下的所有文件压缩
tar -zcvf html.tar.gz *

~~~

## kubesphere添加应用路由报错域名格式错误

**一般是多了空格**

![image-20221103213116493](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103213116493.png)

## 服务滚动升级

![image-20221103224041246](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103224041246.png)

![image-20221103224114033](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103224114033.png)

![image-20221103224215491](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103224215491.png)

**lars****yufn****egbz****ebba**



![image-20221103230658628](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103230658628.png)

![image-20221103230901282](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221103230901282.png)

## springboot工程配置文件和nacos配置中心文件

### springboot项目内配置文件加载规则

![image-20221105110527427](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105110527427.png)

### 项目外配置

![image-20221105110602103](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105110602103.png)

![img](https://ask.qcloudimg.com/http-save/yehe-6158873/k8qbz44lly.png?imageView2/2/w/1620)

![image-20221105111158446](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105111158446.png)

**同一配置优先级高的被先读取，默认使用优先级更高的配置。不冲突的属性会形成互补配置。**

### 关于nacos配置中心

![image-20221105112829654](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105112829654.png)

![image-20221105113429128](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105113429128.png)

**如果没有指定extension-configs为a.yaml的话，那么默认就只会去找1，2，3这三个配置文件，想要使用其它的配置文件，必须在extension-configs中指定。**

**不指定spring.profiles.active的话，就直接使用默认的配置文件，表现为在resources里面的application.properties(yaml)以及nacos配置中心中的default-group下的配置文件。如果使用了nacos配置中心的yaml格式的默认配置文件，那么resources里面的application.properties(yaml)与nacos里的同名配置项会被覆盖。**

![image-20221105115207697](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105115207697.png)

**只要指定了使用的group就不会使用到其它分组去。**

**注意resource下面的默认配置文件会和激活的配置文件互补，但是nacos配置中心可不会出现default-group与其它分组下的配置互补哦，**

**这是两个不同的概念，nacos你指定了使用哪个group就只会使用这个分组下的配置。**

**resources下的默认的配置文件一定会被加载使用，同样的配置会被激活环境的配置文件覆盖，不同的会互补使用。**

**使用了nacos配置中心的文件后，nacos配置中心的值会直接覆盖掉resource下的同名配置，其它不同的会互补。**

**互补：**

![image-20221105130809102](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105130809102.png)

**覆盖：**

![image-20221105124502154](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105124502154.png)

![image-20221105124917825](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105124917825.png)

### 最佳实践

**1、如果不使用nacos配置中心，那么建议生成application.properties(yml),application-prod.properties(yml),application-test.properties(yml),application-dev.properties(yml),在application.properties(yml)中写一些各个环境中通用的配置，然后在其它文件中写各自环境下的专属配置，然后在开发期间在application.properties(yml)中使用spring.profiles.active=dev激活开发环境配置就行，这样即能使用开发环境配置，又可以使用默认的配置文件application.properties(yml)的配置，形成互补。在测试或线上时使用同样的方式切换即可，或者不用在application.properties(yml)中使用spring.profiles.active=dev，而是直接使用命令行方式**

**--spring.profiles.active=xxx也行，删掉application.properties(yml)的spring.profiles.active=dev就行使用命令行即可。**

**2、如果使用的是nacos的配置中心，那么就建议在resources下面生成bootstrap.yaml文件，然后在文件内写多个环境的文件块，用---隔开，注意，同样可以写四个文件块，一个默认环境文件块，一个prod环境文件块，一个test环境文件块，一个dev环境文件块，可以在bootstrap里面直接指定spring.profiles.active=xxx激活哪个文件块的配置，也可以使用命令行的方式等等，在各自的文件块中最好指明要使用的配置中心的哪个group分组下的配置文件，如果有多个配置文件要用，要使用extension.configs配置好。**

**当然不在一个bootstrap里面写多个文件块也是可以的，可以建立多个bootstrap文件，如bootstrap.yaml做默认配置，bootstrap-dev.yaml做开发环境配置等等，只不过写到一个文件里更加简洁。**

![image-20221105121640173](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105121640173.png)

![image-20221105122215385](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105122215385.png)



## nacos2.1+mysql8+: No datasource set

~~~properties
spring
server.servlet.contextPath=${SERVER_SERVLET_CONTEXTPATH:/nacos}
server.contextPath=/nacos
server.port=${NACOS_APPLICATION_PORT:8848}
spring.datasource.platform=${SPRING_DATASOURCE_PLATFORM:""}
nacos.cmdb.dumpTaskInterval=3600
nacos.cmdb.eventTaskInterval=10
nacos.cmdb.labelTaskInterval=300
nacos.cmdb.loadDataAtStart=false
db.num=${MYSQL_DATABASE_NUM:1}
db.url.0=jdbc:mysql://${MYSQL_SERVICE_HOST}:${MYSQL_SERVICE_PORT:3306}/${MYSQL_SERVICE_DB_NAME}?${MYSQL_SERVICE_DB_PARAM:characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false}
db.url.1=jdbc:mysql://${MYSQL_SERVICE_HOST}:${MYSQL_SERVICE_PORT:3306}/${MYSQL_SERVICE_DB_NAME}?${MYSQL_SERVICE_DB_PARAM:characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false}
db.user=${MYSQL_SERVICE_USER}
db.password=${MYSQL_SERVICE_PASSWORD}
### The auth system to use, currently only 'nacos' is supported:
nacos.core.auth.system.type=${NACOS_AUTH_SYSTEM_TYPE:nacos}
~~~



![image-20221105160133652](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221105160133652.png)

~~~shell
连接mysql8一定要配serverTimezone=UTC
docker run -d
-p 8848:8848
-p 9848:9848
-p 9849:9849
-e MODE=standalone
-e SPRING_DATASOURCE_PLATFORM=mysql
-e MYSQL_SERVICE_HOST=192.168.xx.xx
-e MYSQL_SERVICE_PORT=3306
-e MYSQL_SERVICE_DB_NAME=nacos
-e MYSQL_SERVICE_USER=user
-e MYSQL_SERVICE_PASSWORD=xxxxxx
-e MYSQL_DATABASE_NUM=1
-e MYSQL_SERVICE_DB_PARAM= characterEncoding=utf8&serverTimezone=UTC&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
-v /usr/local/docker/nacos/logs:/home/nacos/logs
–restart always
–name nacos nacos/nacos-server:latest

这是nacos单机部署，所以加了-e MODE=standalone \，
新版nacos必须要映射加1000和加1001的端口号，我这里是8848，9848，9849，如果配置觉得这样写麻烦可以把容器中的nacos配置文件application.properties挂载出来,在配置文件中改配置mysql等信息。

~~~

~~~yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  namespace: gulimall
  labels:
    app: nacos
  name: nacos-gly8dp
  annotations:
    kubesphere.io/alias-name: nacos
spec:
  replicas: 1
  selector:
    matchLabels:
      app: nacos
  template:
    metadata:
      labels:
        app: nacos
      annotations:
        kubesphere.io/containerSecrets: null
    spec:
      containers:
        - name: container-nzi3mn
          type: worker
          imagePullPolicy: IfNotPresent
          resources:
            requests:
              cpu: '0.01'
              memory: 10Mi
            limits:
              cpu: '1'
              memory: 1500Mi
          image: 'nacos/nacos-server:v2.1.0'
          ports:
            - name: http-8848
              protocol: TCP
              containerPort: 8848
              servicePort: 8848
          env:
            - name: MODE
              value: standalone
            - name: SPRING_DATASOURCE_PLATFORM
              value: mysql
            - name: MYSQL_SERVICE_HOST
              value: mysql-master.gulimall
            - name: MYSQL_SERVICE_PORT
              value: '3306'
            - name: MYSQL_SERVICE_DB_NAME
              value: nacos
            - name: MYSQL_SERVICE_USER
              value: root
            - name: MYSQL_SERVICE_PASSWORD
              value: '123456'
            - name: MYSQL_DATABASE_NUM
              value: '1'
            - name: MYSQL_SERVICE_DB_PARAM
              value: >-
                characterEncoding=utf8&serverTimezone=UTC&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
      serviceAccount: default
      affinity:
        podAntiAffinity:
          preferredDuringSchedulingIgnoredDuringExecution:
            - weight: 100
              podAffinityTerm:
                labelSelector:
                  matchLabels:
                    app: nacos
                topologyKey: kubernetes.io/hostname
      initContainers: []
      imagePullSecrets: null
  updateStrategy:
    type: RollingUpdate
    rollingUpdate:
      partition: 0
  serviceName: nacos
---
apiVersion: v1
kind: Service
metadata:
  namespace: gulimall
  labels:
    app: nacos
  annotations:
    kubesphere.io/serviceType: statefulservice
  name: nacos
spec:
  sessionAffinity: None
  selector:
    app: nacos
  ports:
    - name: http-8848
      protocol: TCP
      port: 8848
      targetPort: 8848
  clusterIP: None

~~~

## 低版本的springboot和高版本的springboot之间指定激活环境

![image-20221106093021263](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221106093021263.png)



![image-20221106093036050](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221106093036050.png)

## 关于部署前台renren-fast-vue

**我是直接把dist里面的内容放到了gulimall-nginx的html文件夹下面了，然后用ingress-nginx将直接访问192.168.56.100（101/102）的请求应用路由到gulimall-nginx,这样直接访问到后台内容。**

**按照老师的做法好像无法登录进去人人后台，所以还是自己先放到自己的虚拟机中的nginx的html目录下保证访问nginx能直接访问到后台并能登录进去成功，这样再打包成镜像放到仓库，最后直接在k8s里面拉取部署就一定会成功了。**

**1、先把dist里面的内容放到虚拟机的nginx的html目录下：**

![image-20221106210347884](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221106210347884.png)

**2、然后分别压缩html和conf目录里面的内容为tar.gz压缩包：**

![image-20221106210200402](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221106210200402.png)

![image-20221106211553425](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221106211553425.png)

**3、docker build**

~~~shell
docker build -t gulimall-nginx:v1.2 -f Dockerfile .
~~~



**4、docker login**

~~~shell
docker login --username=santiago1006 registry.cn-hangzhou.aliyuncs.com
~~~



**5、docker tag**

~~~shell
 docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/santiago_gulimall/gulimall-nginx:[镜像版本号]
~~~



**6、docker push**

~~~shell
docker push registry.cn-hangzhou.aliyuncs.com/santiago_gulimall/gulimall-nginx:[镜像版本号]
~~~



**7、k8s部署无状态nginx服务**

![image-20221106211255308](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221106211255308.png)

**8、设置应用路由规则**

![image-20221106211152385](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/image-20221106211152385.png)

**建议使用k8s流水线能跑过一个就行，不要全部用流水线部署，真的很费时，很折磨人！直接把镜像push到阿里云仓库，k8s部署服务就可以了，而且流水线很容易因为电脑性能不足出问题。**

##  Markdown 编辑器 Typora 图片自动上传服务器设置

![动图封面](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-b638e8c52ecd30b6188a8114ea710222_b.jpg)



**Typora 0.9.84** 开始支持自动将图片自动上传至服务器，并返回url地址。

### **先来一起康康效果如何**

![动图封面](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-f7f6bf48cfae71b5a94803561549d58a_b.jpg)



 此处图片复制进 **Typora** 后，自动上传至配置好的服务器，本地的路径最后转换为了服务器url地址。以前，必须先将图片上传至图床，再插入图片链接，现在配置好服务器后，直接插入图片就OK了，完成 **Markdown** 文本的编写后，就可以直接发布了。

------

### 配置一 ：下载或更新PicGo-Core及相关设置

 首先，下载安装最新版 **[Typora[下载地址\]](https://link.zhihu.com/?target=https%3A//www.typora.io/%23windows)** ，根据自己电脑系统下载32位或64位安装包。

![img](https://pic3.zhimg.com/80/v2-ef0d8aedc914ccc8c8de362e6a7bb152_1440w.webp)

 安装完毕后，打开 **Typora** ,点击左上角 ”**菜单**“，进入 ”**偏好设置**“ ，”**图像**“ 选项卡设置如下：

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-2112e982fc14f2d2084da385c687d47e_1440w.webp)

 当选择 “**上传图片**” 选项时，在图片复制进 **Typora** 时，图片将立即被自动上传至已经配置好的服务器；选择 “**无特殊操作**” 时，将不会自动上传，稍后可通过右键图片弹出菜单 “**上传图片**” 进行手动上传。

![img](https://pic3.zhimg.com/80/v2-82ed110b7a637748428460c0f90980fe_1440w.webp)

![img](https://pic3.zhimg.com/80/v2-13437a21eeb0cbc0c4449aea895d4a8e_1440w.webp)

 “**上传服务器设定**” 中，推荐选择 “**PicGo-Core (Command line)**” 以命令行上传图片，仅在上传时运行 “**PicGo-Core**“ 命令行，上传成功(或失败)后立即退出，占用系统资源低；

 如果选择 “**PicGo (app)**" ,将占用相对命令行更多的系统资源，因为 **PicGo.app** 需常驻后台，但 **PicGo.app** 将提供更多其他功能，如上传历史记录、自动重命名等。

![img](https://pic2.zhimg.com/80/v2-68400e0470b365a98485d8ffebb36dcd_1440w.webp)

 选定 “**PicGo-Core (Command line)**” 后，点击 ”**下载或更新**“ 按钮，下载或更新 "**PicGo-Core**" 。

------

### 配置二：服务器设置(1.阿里云;2.腾讯云;3.smms;4.其他)

#### 1. 配置阿里云

 阿里云安全稳定，是国内首屈一指的云服务供应商。阿里云配置较复杂，如果不喜欢复杂操作，可直接跳过阿里云配置这部分，smms配置最为简单且免费，其中，阿里云、腾讯云服务器需收费，阿里云40G存储空间半年仅需4.98元，外网下载\读取流量0.12 元/GB/月，腾讯也差不多也这个价，海星吧！



##### 1.1. 注册阿里云账号

[阿里云](https://link.zhihu.com/?target=https%3A//www.aliyun.com/)链接如下：

```antlr
https://www.aliyun.com/       
```

支付宝绑定注册即可，注册后完成实名认证，新人可获得一项云服务免费体验一个月。

##### 1.2. 开通对象存储OSS服务并创建bucket

 注册成功后，右上角点击进入 “**控制台**” ，在控制台左上角菜单栏选择 “**对象存储OSS**” ，并开通该服务。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-b5e8d4390d49b78253e0d8f96142145e_1440w.webp)

点击左侧 “**概览**” ，再点击右侧 “**创建bucket**” ，如图：

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-576fd8887c0be117d579801e3d1e8ed8_1440w.webp)

 按要求填写 “**Bucket名称**” ，“**区域**” 可任意选择，其他选项均可不改动，唯一需要改动的是 “**读写权限**” ，一定要改成 “**公共读**” ，复制保存一下 “**Bucket名称**” 和节点 “**Endpoint**” 信息，之后点击 “**确定**”。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-4393ac373c99a52d8c40483ecc0d81ac_1440w.webp)

##### 1.3. 购买存储资源包

 点击左上角菜单，进入 “**资源包管理**” ，点击 ”**购买资源包**“ ，新人可免费领取一个月的100G标准存储包，此外半年的40G存储包也仅需4.98元！

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-0b7cdd9f0f08eaffc3bd93da76326593_1440w.webp)

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-2dd3b6d2d453748f9b53b01b91e0bb9e_1440w.webp)

##### 1.4. AccssKey管理

 点击右上角头像，进入 ”**AcessKey管理**“ ，安全起见，点击 ”**开始使用子用户AcessKey**“ ：

![img](https://pic2.zhimg.com/80/v2-8e4528abb658512da525739b4af4e33d_1440w.webp)

![img](https://pic1.zhimg.com/80/v2-f6f9672e1aa4e0782655b424039e1470_1440w.webp)

 创建子用户账户登录和显示名称(随便取即可)，最后勾选 ”**编程访问**“ ，点击 "**确认"** 。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-59b6b790de7784cf97f0e135ca902a93_1440w.webp)

 创建完用户后出现用户信息页面，该页面关闭后将无法再次获取相关信息，一定要保存好 "**AcessKey ID**" 和 ”**AcessKey Secret**“ ，在配置文件中会用到，可以下载CSV文件保存信息。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-3f96bda9e310cdd116ac2275faba7f7b_1440w.webp)

 保存好相关信息后，点击 ”**添加权限**“ ，为该子用户添加访问权限，在搜索栏输入 ”**oss**" ，搜索所有关于 "**对象存储oss**" 的权限，一共两个，全部选中即可，右侧可看到当前已选择权限。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-c0c27f510b977195d1476da5da3b7154_1440w.webp)



##### 1.5. 创建配置文件模板

 复制以下配置代码到记事本备用，其中 "**xxxx**" 需要替换，每个人均不一样，其他可不改动。

```text
{
    "picBed": {
        "uploader": "aliyun",
        "aliyun": {
        "accessKeyId": "xxxx",
        "accessKeySecret": "xxxx",
        "bucket": "xxxx",
        "area": "xxxx", 
        "path": "img/", 
        "customUrl": "xxxx",
        "options": "" 
      }
    },
    "picgoPlugins": {}
  }
```

其中，“**acesskeyId**" 和 "**acesskeySecret**" 即为 “**1.4. AcessKey管理**” 中保存的用户信息；

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-3f96bda9e310cdd116ac2275faba7f7b_1440w.webp)

其中， ”**bucket**“ 和 "**area**" 即为 ”**1.2. 创建bucket**“ 中保存的 ”**Bucket名称**“ 和节点 ”**Endpoint**" 信息，节点信息只需要取到前面的区域信息，后面的 “**.[http://aliyuncs.com](https://link.zhihu.com/?target=http%3A//aliyuncs.com)**” 删掉，以此处为例，“**area**” 对应为 “**oss-cn-chengdu**" 。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-886ac65673ef89f81a45de082b1af341_1440w.webp)

最后， “**customUrl**” 为外网访问链接，格式为：“**http://**" + "**Bucket名称**" + ”**Endpoint**“ ，此处最终的 ”**customUrl**“ 为:

```text
http://typroa-test.oss-cn-chengdu.aliyuncs.com
```

“**customUrl**” 也可以从 “**Bucket列表**” 中直接获得，只需点击已创建的bucket将其展开就能获得外网访问链接。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-54ffa43c70756e9c11b7171f9d1c494c_1440w.webp)

##### 1.6. Typora配置文件

 将之前记事本中的配置信息填好，就准备好了 ”**Typora**“ 中 ”**PicGo-Core**" 的配置文件，这里给出一份配置文件参考，密钥内容隐去一部分。

```text
{
    "picBed": {
        "uploader": "aliyun",
        "aliyun": {
            "accessKeyId": "LTAI4GJx2DGB1xxxxxxxx",
            "accessKeySecret": "Ep6ynvPCG6bhEQPrCBksxxxxxxx",
            "bucket": "typroa-test",
            "area": "oss-cn-chengdu", 
            "path": "img/", 
            "customUrl": "http://typroa-test.oss-cn-chengdu.aliyuncs.com",
            "options": "" 
        }
    },
    "picgoPlugins": {}
  }
```

打开 “**Typora**" 依次打开 ”**文件 ----> 偏好 ----> 图像**“ ，点击 ”**打开配置文件**“ ，将上面的配置代码完整复制到配置文件中，”**Ctrl+S**" 保存后关闭配置文件。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-63e1a4ed28df1d2bfd96b021dae1eb3a_1440w.webp)

##### 1.7. 大功告成！

 最后，在 “**打开配置文件**”左侧 有个 “**上传服务**” ，点击 “**验证图片上传选项**” ，提示 “**验证成功**” ，则大功告成！

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-6ad8dbb2c9c224cd1576d128fc534886_1440w.webp)



------

#### 2. 配置腾讯云

 腾讯云配置与阿里云大同小异，基本流程都差不多。

##### 2.1. 注册腾讯云账号

[腾讯云](https://link.zhihu.com/?target=https%3A//cloud.tencent.com/)链接如下：

```text
https://cloud.tencent.com/
```

用微信或QQ均可登录，注册后首先完成实名认证。

##### 2.2. 开通对象存储COS并创建存储桶

 点击 “**云产品**” ，在下拉菜单中找到 “**对象存储**” ，点击后开通该服务；

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-e709168ee0bb95ee6f5eaa186afafc4a_1440w.webp)

然后 “**创建存储桶**” ，其中 “**访问权限**” 一定要选择 “**公有读私有写**” ，“**存储桶标签**” 随便添几个就行；

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-e6aba2532616def6fafa41a4b017c60e_1440w.webp)

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-71961a953bd56e15f99c42bfd090d2ac_1440w.webp)

##### 2.3. 购买对象存储(COS)资源包

 新手免费赠送了6个月50G标准存储容量，此外格外购买6个月10G标准存储包也只要4.96元。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-772f0d6d52eabb29902d3915ce289d5b_1440w.webp)

##### 2.4. 密钥管理

 进入 “**控制台**” — “**访问管理**” ，然后点击 “**API密钥管理**” — “**切换使用子账号密钥**” 。

![img](https://pic1.zhimg.com/80/v2-2ade29ed01c86f80b7cfca71d114fdf0_1440w.webp)

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-519e44c355436d6de971c5a6558c4f6d_1440w.webp)

点击 “**快速创建**” ，快速创建子账号，创建完毕后，点击进入左侧 “**用户列表**”，点击创建好的子账户用户名，进入用户详情页。

![img](https://pic1.zhimg.com/80/v2-8d878cffa9c9a47795e750ffd271dab8_1440w.webp)

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-54749b83db5b6c389a4285202f7bb3dd_1440w.webp)

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-cbaf7a02a1fae8cb8903cd3cc0211e0d_1440w.webp)

在用户详情页中找到 “**API密钥**” 选项卡，点击 “**新建密钥**” ，几秒钟后生成密钥 “**Secretid**" 和 "**SecretKey**" ，复制保存以备用。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-4e0c8e30237bbf8470986b3ec67a79e2_1440w.webp)

##### 2.5. 配置文件

腾讯云的配置文件如下：

```text
{
  "picBed": {
    "uploader": "tcyun",
    "tcyun": {
      "secretId": "AKIDCBvVor9u85lFZcwuYx5xxxxxxxxxx",
      "secretKey": "RenmX1IJ3YoGTRAlzwc3eyxxxxxxxxxx",
      "bucket": "typora-1302184631",
      "appId": "1302184631",
      "area": "ap-chengdu",
      "path": "img/",
      "customUrl": "https://typora-1302184631.cos.ap-chengdu.myqcloud.com",
      "version": "v5"
    }
  },
  "picgoPlugins": {}
}
```

其中 "**secretId**" 和 "**secretKey**" 即为 ”**2.4. 密钥管理**“ 中获得的密钥。

剩余信息在 ”**云产品**“ — ”**对象存储**“ — ”**存储桶列表**“ 中，

![img](https://pic4.zhimg.com/80/v2-7550a9227e2c6b794403b7f7de64bda3_1440w.webp)

![img](https://pic1.zhimg.com/80/v2-a2e92c90a3c68d928db16d1672ca5a24_1440w.webp)

点击存储桶名，选择 ”**基础配置**“ 选项卡，右侧 ”**基本信息**“ 中包含配置文件中剩余所需信息。

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-b87f05013b138aa6b05cae8fdc8a42cd_1440w.webp)

配置文件中，”**bucket**“ 对应 ”**空间名称**“ ；"**appId**" 对应 ”**空间名称**“ 后面的数字；”**area**“ 对应 ”**所属地域**“ 后面括号中的英文；”**customUrl**" 对应 “**访问域名**”。

##### 2.6. 大功告成

打开 **Typora** 设置文件，将上面的配置文件代码完整复制进去，保存后关闭配置文件，最后

点击 “**验证图片上传选项**” ，提示验证成功，则大功告成！

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-351b4985867892eace78882e5ccfde3d_1440w.webp)



![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-7ce809ddeece17e23997463cf0b942c8_1440w.webp)

------

#### 3. 配置smms

 “**smms**" 简单实用，可快速上手，而且免费，由于服务器在国外，不一定稳定。

##### 3.1. 注册smms账号

[smms](https://link.zhihu.com/?target=https%3A//sm.ms/home/apitoken)账号注册链接如下：

```text
https://sm.ms/home/apitoken
```

##### 3.2. 生成 Secret Token

注册完成后，再次复制该链接打开如下界面：

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-463f42e8e1f4316d34846e170ad66039_1440w.webp)

点击 “**Generate Secret Token**" 生成 ”**Secret Token**" ，复制以备用。

##### 3.3. 配置文件

 打开 "**Typora**" 配置文件，将以下方代码复制到配置文件，其中 ”**token**" 即为上面图片中的 "**Secret Token**" ，保存配置文件。



![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-63e1a4ed28df1d2bfd96b021dae1eb3a_1440w.webp)

```text
{
  "picBed": {
    "uploader": "smms",
    "smms": {
      "token": "Fg8H9wYgYOdoOVmT94YlamD5hosy3nYX"
    }
  },
  "picgoPlugins": {}
}
```

##### 3.4. 大功告成

最后 ，点击 “**验证图片上传选项**” ，提示 “**验证成功**” ，则大功告成！

![img](https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/typroa_img/v2-ec011dff4b82e2aa25d748a486e730bb_1440w.webp)

------

#### 4. 其他云服务器配置

 其他云服务配置与前面三种云服务配置基本一致，故不再仔细列出，仅将各云服务与其对应配置文件给出如下：（双斜杠和后面的文字最后需删掉）

详细设置见见PicGo官方指南：[https://picgo.github.io/PicGo-Doc/](https://link.zhihu.com/?target=https%3A//picgo.github.io/PicGo-Doc/)

##### 4.1. 七牛图床

```text
{
  "picBed": {
    "uploader": "qiniu",
    "qiniu": {
      "accessKey": "",
      "secretKey": "",
      "bucket": "", // 存储空间名
      "url": "", // 自定义域名
      "area": "z0" | "z1" | "z2" | "na0" | "as0", // 存储区域编号
      "options": "", // 网址后缀，比如?imgslim
      "path": "" // 自定义存储路径，比如img/
    }
  },
  "picgoPlugins": {}
}
```

##### 4.2. 又拍云

```text
{
  "picBed": {
    "uploader": "upyun",
    "upyun": {
      "bucket": "", // 存储空间名，及你的服务名
      "operator": "", // 操作员
      "password": "", // 密码
      "options": "", // 针对图片的一些后缀处理参数
      "path": "", // 自定义存储路径，比如img/
      "url": "" // 加速域名，注意要加http://或者https://  
    }
  },
  "picgoPlugins": {}
}
```

##### 4.3. GitHub

```text
{
  "picBed": {
    "uploader": "github",
    "github": {
        "repo": "", // 仓库名，格式是username/reponame
        "token": "", // github token
        "path": "", // 自定义存储路径，比如img/
        "customUrl": "", // 自定义域名，注意要加http://或者https://
        "branch": "" // 分支名，默认是master 
    }
  },
  "picgoPlugins": {}
}
```

##### 4.4. Imgur

```text
{
  "picBed": {
    "uploader": "imgur",
    "imgur": {
        "clientId": "", // imgur的clientId
        "proxy": "" // 代理地址，仅支持http代理
    }
  },
  "picgoPlugins": {}
}
```

## **排错多看项目日志！！！**

