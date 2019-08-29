presto读取hive的parquet表
=========================

无法读取 Parquet 文件
>>>>>>>>>>>>>>>>>>>>>

::

    使用 Presto 查询 Hive 表，抛异常 com.facebook.presto.spi.PrestoException: Can not read value at 0 in block 0 in file hdfs

    Presto 版本 0.211

    Spark 版本 2.2.0

    导致问题的原因是 Spark 写入到 Hive 表中的 Parquet 文件版本过高，导致 Presto 无法读取

    解决办法：

    在构建 SparkSession 时，添加配置 spark.sql.parquet.writeLegacyFormat，一个🌰：

    SparkSession.builder()
      .master("local")
      .appName(appName)
      .enableHiveSupport()
      .config("spark.sql.parquet.writeLegacyFormat", true) // ①
      .getOrCreate()
    ① 在 Spark 2.2 中，在写入 Parquet 时，会启动 Legacy 模式，从而可以兼容 Spark 1.4 及之前的版本

Presto 查询到错误的列
>>>>>>>>>>>>>>>>>>>>>

::

    Presto 查询文件格式为 Parquet 的 Hive 表，查询其中一个字段却返回另一个字段的数据,数据格式匹配不上，无法查看到最新数据

    Presto 版本 0.211

    导致该问题的原因是查询 Hive 表 Schema 和底层 Parquet 数据文件 Schema 不一致导致的。改问题有两种解决方式：

    修改 Hive 表 Schema 保持与底层 Parquet 数据文件 Schema 一致；
    修改 Hive Catalog 配置文件，新增配置 hive.parquet.use-column-names=true
    关于 hive.parquet.use-column-names 属性，官方的解释：

    By default, columns in Parquet files are accessed by their ordinal position in the Hive table definition. To access columns based on the names recorded in the Parquet file, set hive.parquet.use-column-names=true in your Hive catalog properties file.

Hive 元数据缓存
>>>>>>>>>>>>>>>

::

    有时 Hive 底层数据文件发生变化，由于 Presto 缓存了 Hive 元数据。禁用 Hive 元数据缓存，编辑 Hive 配置文件，添加以下配置：

    hive.metastore-cache-ttl=0
    hive.metastore-refresh-interval=0

