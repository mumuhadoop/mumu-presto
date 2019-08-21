环境准备
========

presto是java程序开发的项目，所以项目最低依赖于java。

java安装
>>>>>>>>

1、从oracle下载jdk1.8.12+版本的java。
::

    wget https://download.oracle.com/otn/java/jdk/8u221-b11/230deb18db3e4014bb8e3e8324f81b43/jdk-8u221-linux-x64.tar.gz

2、解压jdk到、/usr/local目录

::

    tar -xzvf jdk-8u221-linux-x64.tar.gz -C /usr/local

3、配置JAVA_HOME和path
::

    JAVA_HOME=/usr/local/jdk1.8.0_221
    CLASSPATH=.:$JAVA_HOME/lib.tools.jar
    PATH=$JAVA_HOME/bin:$PATH
    export JAVA_HOME CLASSPATH PATH
