## Alipay Cloudrun

小程序云模版，提供springboot框架快速搭建，实现简易http接口调用、微服务调用、数据库访问、日志打印等功能。


## 云托管部署
#### 1.下载代码
#### 2.快速部署
前往[小程序云托管快速部署](https://opendocs.alipay.com/pre-open/04n0zd),
可选择手工打镜像上传或直接上传代码文件夹部署，服务端口及探活端口填写80端口
#### 3.服务访问
在部署完成之后，前往云托管平台下的**服务列表**，找到自己的服务并进入，在**服务设置**开启公网域名，之后在浏览器里访问公网域名即进入欢迎页面。
#### 4.数据库连接
用户需要前往云托管平台开通[数据服务](https://opendocs.alipay.com/pre-open/04mphd),
设置账号密码并建库表，表创建可参考
```sql
USE database;
CREATE TABLE IF NOT EXISTS  `record_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `record` varchar(64) NOT NULL COMMENT '记录信息',
  PRIMARY KEY (`id`)
)DEFAULT CHARSET = utf8mb4 COMMENT = '操作信息';
```
建表之后，还需要在环境变量里设置自己的数据库账号、密码、数据库端口、数据库名称，设置步骤：
前往云托管平台下的**服务列表**，找到自己的服务并进入，在**服务设置**里找到**环境变量**并添加如下KV
```text
DATABASE_USERNAME:申请的数据库账号
DATABASE_PASSWORD:账号对于密码
DATABASE_HOST:数据库域名(需带端口，示例127.0.0.1:3306),在数据库服务申请成功时可以拿到
DATABASE_NAME:建立的数据库
```
最后便可以在欢迎页面上的测试数据库访问对该表进行操作。
#### 5.查看日志
前往云托管平台：首先在**服务列表**下找到自己的服务并进入，之后在**部署版本**里找到**发布详情**点击进入，找到**示例数量**点击之后会有引导登录实例机器，
登入之后，输入
```powershell
cd /app/logs
```
即可看到相关日志文件。
#### 6.查看监控
前往云托管平台：首先在**服务列表**下找到自己的服务并进入，点击**服务监控**，便可以看到自己服务器的CPU等指标。


## 本地调试
#### 1.下载代码
下载代码至本地。
#### 2.服务访问
运行AlipayCloudrunApplication中的main方法，快速启动，本地访问http://localhost会弹出欢迎页面
#### 3.数据库连接
前往application.properties，将数据库账号密码替换为自己本地数据库，同时将127.0.0.1:3306与db_test替换为自己本地数据库连接端口与数据库名。
示例建表语句(注意database是需要提前)：
```sql
USE database;
CREATE TABLE IF NOT EXISTS  `record_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `record` varchar(64) NOT NULL COMMENT '记录信息',
  PRIMARY KEY (`id`)
)DEFAULT CHARSET = utf8mb4 COMMENT = '操作信息';
```
之后，可在欢迎页面上的测试数据库访问对该表进行操作。
#### 4.查看日志
在项目路径下，执行如下命令：
```powershell
cd logs
```
即可看到相关日志文件。
## 目录结构说明

~~~
.
├── Dockerfile                                              Dockerfile 文件
├── LEGAL.md                                                LEGAL.md 文件
├── LICENSE                                                 LICENSE 文件               
├── .gitignore                                              .gitignore 文件
├── README.md                                               README.md 文件
├── pom.xml                                                 pom.xml 文件
└── src                                                     源码目录
    └── main                                                源码主目录
        ├── java                                            java目录
        │   └── com                                         包名
        │       └── alipay                                  包名
        │           └── cloudrun                            包名
        │               ├── AlipayCloudrunApplication.java  项目启动类
        │               ├── aop
        │               │   ├── ControllerAspect.java       mvc切面拦截
        │               │   ├── DalAspect.java              dal切面拦截
        │               │   ├── SalAspect.java              sal切面拦截
        │               │   └── annotation
        │               │       ├── ControllerPointCut.java mvc切面拦截
        │               │       ├── DalPointCut.java        dal切面拦截
        │               │       └── SalPointCut.java        sal切面拦截
        │               ├── client
        │               │   └── SimpleFeignClient.java      微服务调用类
        │               ├── dao
        │               │   ├── RecordDAO.java              记录信息接口
        │               │   └── RecordDAOImpl.java          记录信息相关接口实现
        │               └── web
        │                   ├── DataBaseController.java     数据库访问controller
        │                   ├── HttpTestController.java     服务访问controller
        │                   ├── request
        │                   │   └── RecordInfo.java         记录信息对象
        │                   └── response
        │                       ├── Result.java             返回结果实体
        │                       └── ResultCodeEnum.java     错误码枚举类
        └── resources
            ├── application.properties                      项目配置文件
            ├── log4j2.xml                                  日志配置文件
            └── static                                      静态资源
                ├── img                                     图片目录
                │   └── logo.png                            logo文件
                ├── index.html                              静态页面
                └── js                                      js目录
                    └── jquery-1.11.1.min.js                js文件
~~~

### LICENSE
MIT