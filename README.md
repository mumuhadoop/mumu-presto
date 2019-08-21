# mumu-presto 基于内存的快速计算框架

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/mumuhadoop/mumu-presto/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/mumuhadoop/mumu-presto.svg?branch=master)](https://travis-ci.org/mumuhadoop/mumu-presto)
[![codecov](https://codecov.io/gh/mumuhadoop/mumu-presto/branch/master/graph/badge.svg)](https://codecov.io/gh/mumuhadoop/mumu-presto)
[![Documentation Status](https://readthedocs.org/projects/mumu-presto/badge/?version=latest)](https://mumu-presto.readthedocs.io/en/latest/?badge=latest)

*** mumu-presto是以presto为基础的学习项目，通过这个项目学习怎么使用presto和presto的思想。presto是一个集合多种数据源
的开源组件，可以通过sql的方式方便的操作集成在presto上的数据源。简化了数据操作的复杂度，尤其是想要用多种不同的数据源
进行关联查询的时候，会极大的增加开发效率。***

## presto特性
presto是Facebook开源的，完全基于内存的并⾏计算，分布式SQL交互式查询引擎是一种Massively parallel processing(MPP)架构，
多个节点管道式执⾏⽀持任意数据源（通过扩展式Connector组件），数据规模GB~PB级使用的技术，如向量计算，动态编译执⾏计划，
优化的ORC和Parquet Reader等presto不太支持存储过程，支持部分标准sqlpresto的查询速度比hive快5-10倍

## 丰富数据源
- Accumulo Connector
- Black Hole Connector
- Cassandra Connector
- Hive Connector
- JMX Connector
- Kafka Connector
- Kudu Connector
- Local File Connector
- Memory Connector
- MongoDB Connector
- MySQL Connector
- PostgreSQL Connector
- Redis Connector
- Redshift Connector
- SQL Server Connector
- System Connector
- Thrift Connector
- TPCDS Connector
- TPCH Connector
- Oracle Connector

## 可拓展性
presto提供了丰富的spi接口,可以通过编写自定义接口来接入数据源，这样数据源的数据就可以像表一样操作了。本项目中预计将elasticsearch、hbase这两
个数据源添加到presto框架中。

## 快速安装
### 下载presto
```
https://repo1.maven.org/maven2/com/facebook/presto/presto-server/0.215/presto-server-0.215.tar.gz
https://repo1.maven.org/maven2/com/facebook/presto/presto-cli/0.215/presto-cli-0.215-executable.jar
https://repo1.maven.org/maven2/com/facebook/presto/presto-verifier/0.215/presto-verifier-0.215-executable.jar
```
下载完成之后解压presto-server到安装目录，将presto-cli-0.215-executable.jar修改为presto,presto-verifier-0.215-executable.jar修改为verifier并
添加执行权限。

### 修改配置
创建etc目录，并且添加如下配置文件。

#### node.properties
```
node.environment=production
node.id=ffffffff-ffff-ffff-ffff-ffffffffffff
node.data-dir=/var/presto/data
```
集群环境中node.environment保持一致,node.id保持唯一,node.data-dir为presto插件日志数据保存的目录。

#### jvm.config
```
-server
-Xmx16G
-XX:+UseG1GC
-XX:G1HeapRegionSize=32M
-XX:+UseGCOverheadLimit
-XX:+ExplicitGCInvokesConcurrent
-XX:+HeapDumpOnOutOfMemoryError
-XX:+ExitOnOutOfMemoryError
```

#### config.properties
```
集群配置
coordinator=true
node-scheduler.include-coordinator=false
http-server.http.port=8080
query.max-memory=50GB
query.max-memory-per-node=1GB
query.max-total-memory-per-node=2GB
discovery-server.enabled=true
discovery.uri=http://localhost:8080
worker节点配置：
coordinator=false
http-server.http.port=8080
query.max-memory=50GB
query.max-memory-per-node=1GB
query.max-total-memory-per-node=2GB
discovery.uri=http://localhost:8080
测试配置
coordinator=true
node-scheduler.include-coordinator=true
http-server.http.port=8080
query.max-memory=5GB
query.max-memory-per-node=1GB
query.max-total-memory-per-node=2GB
discovery-server.enabled=true
discovery.uri=http://localhost:8080
```

#### log.properties
```
com.facebook.presto=INFO
```

#### jmx.properties
在etc目录下创建catalog目录,在catalog目录下创建jmx.properties文件
```
connector.name=jmx
```

#### presto启动
```
以后台的方式启动
bin/launcher start
以前台的方式启动
bin/launcher run
```

## 相关阅读
[presto官网文档](https://prestodb.io/docs/current/index.html)
[presto 0.166概述](https://www.cnblogs.com/sorco/p/7060166.html)
[presto 0.166概述](https://my.oschina.net/idealhp/blog/1863897)


## 联系方式
- email:<babymm@aliyun.com>
- github:[https://github.com/babymm](https://github.com/babymm)