# 采用java官方镜像做为构建镜像
FROM registry.run.alipay.net/cloudrun/maven:3.6.0-jdk-8-slim AS build

# 设置应用工作目录
WORKDIR /app

# 将所有文件拷贝到容器中
COPY . .

# 编译项目
RUN mvn -B -e -U -s settings.xml clean package

# 采用java或者alpine官方镜像做为运行时镜像
FROM registry.run.alipay.net/cloudrun/alpine:3.16

# 设置应用工作目录
WORKDIR /app

# 将构建产物拷贝到运行时的工作目录中
COPY --from=build /app/**/*.jar ./

# 暴露端口
# 此处端口必须与构建小程序服务端时填写的服务端口和探活端口一致，不然会部署失败
EXPOSE 80

# 安装基础命令
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
    apk add --update --no-cache ca-certificates curl openjdk8-jre-base tzdata && \
    rm -f /var/cache/apk/*

# 设置时区
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo Asia/Shanghai > /etc/timezone

## 下载蚂蚁版本链路追踪agent
#RUN curl https://ant-trace.opentrscdn.com/skywalking-agent.tar.gz --output /app/skywalking-agent.tar.gz
## 解压至指定路径
#RUN tar -zxvf /app/skywalking-agent.tar.gz -C /app


# 运行启动命令
CMD java -jar antcloud-0.0.1-SNAPSHOT.jar
