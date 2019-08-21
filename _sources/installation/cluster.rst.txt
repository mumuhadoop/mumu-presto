集群安装
========

github下载安装包
>>>>>>>>>>>>>>>>

::

   wget https://github.com/prestodb/presto/archive/0.217.tar.gz

解压安装包
>>>>>>>>>>

::

   tar -xzvf presto-server-0.217.tar.gz -C /usr/local

.. image:: presto-dirs.png

coordinator配置
>>>>>>>>>>>>>>>

coordinator服务器配置信息，主要包含config.properties和node.properties两方面配置信息。

config.properties配置
:::::::::::::::::::::

::

  coordinator=true
  node-scheduler.include-coordinator=false
  http-server.http.port=9001
  query.max-memory=5GB
  query.max-memory-per-node=512MB
  query.max-total-memory-per-node=512MB
  discovery-server.enabled=true
  discovery.uri=http://172.31.134.225:9001

node.properties配置
:::::::::::::::::::

::

  node.environment=production
  node.id=ffffffff-ffff-ffff-ffff-ffffffffff01
  node.data-dir=/opt/presto/data217

worker配置
>>>>>>>>>>

worker服务器配置信息，主要包含config.properties和node.properties两方面配置信息。

config.properties配置
:::::::::::::::::::::

::

  coordinator=false
  node-scheduler.include-coordinator=false
  http-server.http.port=9001
  query.max-memory=5GB
  query.max-memory-per-node=512MB
  query.max-total-memory-per-node=512MB
  discovery-server.enabled=false
  discovery.uri=http://172.31.134.225:9001

node.properties配置
:::::::::::::::::::

node.environment配置信息在worker节点和coordinator节点需要保持一致。node.id配置信息必须要唯一。node.data-dir配置目录建议保持一致，方便日志定位。

::

  node.environment=production
  node.id=ffffffff-ffff-ffff-ffff-ffffffffff02
  node.data-dir=/opt/presto/data217

修改jvm.properties配置
>>>>>>>>>>>>>>>>>>>>>>

::

  -server
  -Xmx1G
  -XX:+UseG1GC
  -XX:G1HeapRegionSize=32M
  -XX:+UseGCOverheadLimit
  -XX:+ExplicitGCInvokesConcurrent
  -XX:+HeapDumpOnOutOfMemoryError
  -XX:+ExitOnOutOfMemoryError


- server

  以java服务端进程启动

- xmx

  jvm最大堆内存，该值需要和query.max-memory-per-node之间有关联，不能够query.max-memory-per-node的值大于jvm最大堆大小。

::

  query.max-memory-per-node < xmx*0.8
  xmx > query.max-memory-per-node/0.8

- xx

  UseG1GC使用的是g1的gc收集器进行新生代和老年代gc收集，目前g1是最新性能比较好的gc收集器。


修改log.properties配置
>>>>>>>>>>>>>>>>>>>>>>>

::

  com.facebook.presto=INFO

- com.facebook.presto

  presto的log日志级别


presto服务启动
>>>>>>>>>>>>>>

::

  ./launcher start
  ./launcher restart

   2019-08-20T17:07:15.791+0800    INFO    main    Bootstrap       elasticsearch.max-request-retries          5                  5                  Maximum number of Elasticsearch request retries
   2019-08-20T17:07:15.791+0800    INFO    main    Bootstrap       elasticsearch.max-request-retry-time       10.00s             10.00s             Use exponential backoff starting at 1s up to the value specified by this configuration when retrying failed requests
   2019-08-20T17:07:15.791+0800    INFO    main    Bootstrap       elasticsearch.request-timeout              100.00ms           120.00s            Elasticsearch request timeout
   2019-08-20T17:07:15.791+0800    INFO    main    Bootstrap       elasticsearch.scroll-size                  1000               1000000            Scroll batch size
   2019-08-20T17:07:15.791+0800    INFO    main    Bootstrap       elasticsearch.scroll-timeout               1.00s              60000.00ms         Scroll timeout
   2019-08-20T17:07:15.791+0800    INFO    main    Bootstrap       elasticsearch.table-description-directory  etc/elasticsearch  etc/elasticsearch  Directory that contains JSON table description files
   2019-08-20T17:07:18.813+0800    INFO    main    stderr  ERROR StatusLogger No log4j2 configuration file found. Using default configuration: logging only errors to the console. Set system property 'log4j2.debug' to show Log4j2 internal initialization logging.
   2019-08-20T17:07:24.028+0800    INFO    main    com.facebook.presto.metadata.StaticCatalogStore -- Added catalog elasticsearch using connector elasticsearch --
   2019-08-20T17:07:24.035+0800    INFO    main    com.facebook.presto.security.AccessControlManager       -- Loading system access control --
   2019-08-20T17:07:24.037+0800    INFO    main    com.facebook.presto.security.AccessControlManager       -- Loaded system access control allow-all --
   2019-08-20T17:07:24.225+0800    INFO    main    com.facebook.presto.server.PrestoServer ======== SERVER STARTED ========
