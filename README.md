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



**注意：**

**完整笔记就不放这里了，阿里云容易欠费，需要的自取：https://gulimall-santiago.oss-cn-hangzhou.aliyuncs.com/README.md**

**项目的所有的静态资源都已经整合到了nginx里面了，直接docker拉取我的镜像：**

```shell
 docker pull registry.cn-hangzhou.aliyuncs.com/santiago_gulimall/gulimall-nginx:v1.2
```

**然后运行就可以，里面就有静态资源文件。**

**全项目采用bootstrap和nacos配置中心的方式做配置，所以运行前要先将db文件夹下的nacos文件在mysql里面运行，生成nacos持久化表，所有的配置都在里面，然后在nacos配置文件里开启mysql持久化配置，再运行nacos。nacos版本2.1.0。**

**我的联系邮箱：bin_root@163.com**
