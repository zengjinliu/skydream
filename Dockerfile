#项目运行环境
FROM hub.c.163.com/library/java:8-jre
#维护者信息
MAINTAINER Jayson zengjin_job@163.com
#添加项目打包后的jar文件
ADD target/*.jar sky-dream-0.0.1-SNAPSHOT.jar
#项目端口号
EXPOSE 8761
#类似于 CMD 指令，但其不会被 docker run 的命令行参数指定的指令所覆盖，而且这些命令行参数会被当作参数送给 ENTRYPOINT 指令指定的程序
ENTRYPOINT ["java","-jar","sky-dream-0.0.1-SNAPSHOT.jar"]