presto特点
==========

presto是专门为大数据实时查询而设计和开发的产品。由于presto是使用java开发，所以极易学习和使用，并且对于特定的场景进行自定义开发和性能优化。
无论是对多数据源的支持，还是高性能、可拓展、易用性等，presto都是大数据产品中的佼佼者，presto的特性如下所示:

多数据源
>>>>>>>>

    - Postgresql Connector
    - Mysql Connector
    - Cassandra Connector
    - Hive Connector
    - Kafka Connector
    - jmx Connector
    - Accumulo Connector
    - Elasticsearch Connector
    - Kudu Connector
    - Local File Connector
    - Memory Connector
    - MongoDB Connector
    - Redis Connector
    - SQL Server Connector
    - Redshift Connector
    - Thrift Connector
    - System Connector
    - TPCDS Connector
    - TPCH Connector

    目前presto可以支持数据源包含上面多种connector数据源。而且可以通过spi接口进行自定义数据源接入开发。

支持sql
>>>>>>>

    presto可以完全支持ANSI SQL，并提供了一个shell供客户端查询计算。

可拓展
>>>>>>

   presto有很好的拓展性，开发人员可以使用spi来编写自己的connector，并且使用sql语句查询和分析自己编写的connector数据源

混合计算
>>>>>>>>

   在数据库中每种类型的数据源都对应这一个特定的connector，用户可以根据业务需求在presto中针对一个特定的connector类型配置一个或者多个catalog，
   用户可以混合多个catalog进行join查询和计算。

高性能
>>>>>>

   经过facebook测试计算，presto的性能是hive的10被，是sparkSQL的三倍。

流水线
>>>>>>

   presto是使用pipeline来进行设计的，查询的时候客户不需要等待所有的结果出来完成之后才能获取到结果，而是计算出来一部分结果之后就反馈给
   客户，形成一个流水线操作。

