# 基于SpringBoot的仿牛客网社区平台

## 1.简介

### 1.1技术架构

- Spring Boot
- Spring，Spring MVC，MyBatis
- Redis，Kafka，Elasticsearch
- Spring Security，Spring Actuator

### 1.2开发环境

- 构建工具：Apache Maven
- 开发工具：IntelliJ IDEA
- 数据库：MySQL , Redis
- 应用服务器：Apache Tomcat
- 版本控制：Git

### 1.3项目功能

![image](C:\Users\HUAWEI\Desktop\2.png)

#### 1.3.1社区主页

![image](C:\Users\HUAWEI\AppData\Roaming\Typora\typora-user-images\image-20240524175017427.png)

#### 1.3.2帖子详情

![image](C:\Users\HUAWEI\AppData\Roaming\Typora\typora-user-images\image-20240524175117158.png)


#### 1.3.3个人主页

![image](C:\Users\HUAWEI\AppData\Roaming\Typora\typora-user-images\image-20240525212046396.png)

#### 1.3.4消息系统

![image](C:\Users\HUAWEI\AppData\Roaming\Typora\typora-user-images\image-20240525211934717.png)

### 1.4项目部署

![1](C:\Users\HUAWEI\Desktop\11.png)


## 2.切换开发和生产环境

为了将项目切换到生产或开发模式，只需打开application.properties并将spring.profiles.active的值更改为produce或develop。


## 3.在开发环境下运行项目

该项目是在IntelliJ Idea环境下开发的。 为了获得最佳体验，建议也使用 IntelliJ 运行该项目。
### 3.1 安装工具
- 安装Maven并将其添加到环境变量中
- 安装MySQL，运行sql文件夹下的init_data.sql 和 init_schema.sql以使用表和模拟数据初始化数据库
- 安装Redis并将其添加到环境变量中
- 安装wkhtmltopdf并将其bin文件夹的路径添加到系统变量中
- 安装Kafka并将其bin文件夹的路径添加到系统变量中
- 安装Elasticsearch-6.4.3并将其bin文件夹的路径添加到系统变量中
- 将项目中elasticsearch目录下的elasticsearch-analysis-ik-6.4.3.zip解压至Elasticsearch安装位置的plugins目录下
- 将相关工具的配置信息(如数据库的用户名，密码)填写在application-develop.properties文件中

### 3.2 修改配置文件
- 转到Kafka安装目录，然后打开config/server.properties文件， 将log.dirs的值更改为您想要保存日志的路径
- 同样，打开同一目录下的zookeeper.properties文件，并将dataDir的值更改为您希望日志所在的位置
- 转到Elasticsearch安装目录，然后打开config/elasticsearch.yml文件， 将cluster.name的值更改为nowcoder，将path.data和path.logs的值更改为您想要保存日志的路径

### 3.3 运行项目
- 转到您的Kafka目录，然后运行以下命令启动zookeeper服务器：

  ```cmd
  bin\windows\zookeeper-server-start.bat config\zookeeper.properties
  ```

- 同样，运行以下命令启动Kafka 服务：

  ```cmd
  bin\windows\kafka-server-start.bat config\server.properties
  ```

- 转到Elasticsearch安装目录，然后双击bin目录下的elasticsearch.bat文件以启动Elasticsearch服务

- 确保使用JDK 8运行该项目



## 4.在生产环境下运行项目

### 4.1准备步骤
安装 Maven、MySQL、Redis、Kafka、ElasticSearch、wkhtmltopdf、 Tomcat和Nginx。 还要确保将 maven 和 tomcat 添加到 /etc/profile文件中的PATH。此外同上更改Kafka和ElasticSearch的相关配置文件

### 4.2启动工具

#### 4.2.1 启动MySQL:
- 执行以下命令启动MySQL：
  ```cmd
  systemctl start mysqld
  ```
- 执行以下命令测试MySQL是否启动成功：
  ```cmd
  systemctl status mysqld
  ```
#### 4.2.2 启动Redis:
- 执行以下命令启动Redis：
  ```cmd
  systemctl start redis
  ```
- 执行以下命令测试Redis是否启动成功：
  ```cmd
  systemctl status redis
  ```
#### 4.2.3 启动Kafka:
- 首先转到kafka安装目录
- 执行以下命令确保zookeeper在后台运行：
  ```cmd
  bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
  ```
- 执行以下命令确保kafka在后台运行：
  ```cmd
  nohup bin/kafka-server-start.sh config/server.properties 1>/dev/null 2>&1 &
  ```
- 执行以下命令测试Kafka是否启动成功：
  ```cmd
  bin/kafka-topics.sh --list --bootstrap-server localhost:9092
  ```
#### 4.2.4 启动Elasticsearch:
- 以普通用户身份执行以下命令启动Elasticsearch：
  ```cmd
  bin/elasticsearch -d
  ```
- 执行以下命令测试Elasticsearch是否启动成功：
  ```cmd
  curl -X GET "localhost:9200/_cat/health?v"
  ```
#### 4.2.5 启动Tomcat:
- 执行命令`startup.sh`启动Tomcat：
- 打开浏览器输入网址`http://%Your external IP%:8080`验证Tomcat是否启动成功
#### 4.2.6 启动Ngnix:

- 打开/etc/nginx/nginx.conf，在http部分，附加以下代码：
  ```cmd
   upstream myserver {
          server 127.0.0.1:8080 max_fails=3 fail_timeout=30s;
   }
   server {
          listen 80;
          server_name %Your external IP%;
          location / {
                  proxy_pass http://myserver;
          }
  }
  ```
- 执行以下命令启动Ngnix：
  ```cmd
  systemctl start ngnix
  ```
- 执行以下命令测试Ngnix是否启动成功：
  ```cmd
  systemctl status ngnix
  ```

### 4.3项目部署

- 在本地对项目使用maven的clean命令以移除target目录，然后打包为zip文件上传至ESC
- 使用命令`shutdown.sh`关闭Tomcat，并将Tomcat安装目录下的webapps文件夹中的文件全部删除
- 使用命令`unzip community.zip -d /root`解压项目至root目录下
- 在community目录下执行命令`mvn package -Dmaven.test.skip=true`生成target文件夹
- 进入target目录下将ROOT.war文件移动至Tomcat安装目录下的webapps文件夹中
- 执行命令`startup.sh`启动Tomcat
- 再次进入Tomcat安装目录下的webapps文件夹中，若看到ROOT文件夹，则项目已经开始运行


## 5.项目权限角色说明

- 该项目将用户分为三组：普通用户、管理员和版主
- 管理员可以删除帖子、在/actuator路径下查询Bean和日志等网站数据，并在/data路径下查看网站的DAU和UV
- 版主可以将帖子置顶或加精。 
- 普通用户有权访问所有其他功能。
- 版主帐户示例：nowcoder22(用户名)  123456(密码)
- 管理员帐户示例：nowcoder11(用户名)  123456(密码)