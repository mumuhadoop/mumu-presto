单节点安装
==========

github下载安装包
>>>>>>>>>>>>>>>>

::

   wget https://github.com/prestodb/presto/archive/0.217.tar.gz

解压安装包
>>>>>>>>>>

::

   tar -xzvf presto-server-0.217.tar.gz -C /usr/local

.. image:: presto-dirs.png

修改config.properties配置
>>>>>>>>>>>>>>>>>>>>>>>>>

.. image:: presto-config.png

- coordinator

  是否该服务器作为presto的主节点(coordinator服务)。单机模式需要将该值配置为true。

- node-scheduler.include-coordinator

  是否启用coordinator进程来进行数据计算。单机模式需要将该值配置为true。

- http-server.http.port

  presto服务端口

- query.max-memory

  单个query查询最大内存

- query.max-memory-per-node

  单个节点最大内存

- query.max-total-memory-per-node

  单个节点总内存

- discovery-server.enabled

  是否开启discovery服务，默认discovery服务和coordinator服务绑定在一起。如果coordinator配置为true，那么discovery-server.enabled也需要配置为true。

- query.discovery.uri

  discovery服务uri地址信息，worker节点通过discovery服务来找到master。

修改jvm.properties配置
>>>>>>>>>>>>>>>>>>>>>>

.. image:: presto-jvm.png


- server

  以java服务端进程启动

- xmx

  jvm最大堆内存，该值需要和query.max-memory-per-node之间有关联，不能够query.max-memory-per-node的值大于jvm最大堆大小。

::

  query.max-memory-per-node < xmx*0.8
  xmx > query.max-memory-per-node/0.8

- xx

  UseG1GC使用的是g1的gc收集器进行新生代和老年代gc收集，目前g1是最新性能比较好的gc收集器。

修改node.properties配置
>>>>>>>>>>>>>>>>>>>>>>>

.. image:: presto-node.png

- node.environment

  presto集群名称

- node.id

  presto的worker节点id

- node.data-dir

  presto的日志目录，该目录无需手动创建，程序会自动创建。

修改log.properties配置
>>>>>>>>>>>>>>>>>>>>>>>

.. image:: presto-log.png

- com.facebook.presto

  presto的log日志级别


presto服务启动
>>>>>>>>>>>>>>

::

  ./launcher start
  ./launcher restart


.. image:: presto-startuplog.png
