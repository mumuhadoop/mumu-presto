presto 基本优化
===============

合理设置分区
>>>>>>>>>>>>

当使用hive作为数据源的时候，hive数据仓库中的数据可以通过某些事件、区域信息的字段进行分区，使用presto查询hive数据仓库中的数据时，通过在查询语句中根据
分区字段限定查询的范围，可以有效的避免读取不必要的数据。这样可以显著的提升查询的性能。

::

    hive> show partitions t_ods_industry_atd;
    event_type_id=1/ds=2019-07-07
    event_type_id=1/ds=2019-08-30
    event_type_id=10/ds=2019-07-07
    event_type_id=10/ds=2019-07-14
    event_type_id=10/ds=2019-08-30
    event_type_id=11/ds=2019-03-29
    event_type_id=11/ds=2019-07-07
    event_type_id=12/ds=2019-07-07
    event_type_id=12/ds=2019-07-08
    event_type_id=12/ds=2019-07-10
    event_type_id=12/ds=2019-07-11
    event_type_id=12/ds=2019-08-30
    event_type_id=13/ds=2019-07-07
    event_type_id=13/ds=2019-07-08
    event_type_id=13/ds=2019-07-10
    event_type_id=14/ds=2019-07-07
    event_type_id=14/ds=2019-07-08



group by字句优化
>>>>>>>>>>>>>>>>

合理安排group by字句中字段的顺序可以稍微提升查询性能，尤其是在一个特别大的表中两个字段值数量差别特别打的时候，如果group by子句设置不好的话，会导致查询内存溢出。

优化策略:如果group by子句中包含两个字段，其中column1中的distinct值的数量要远大于column2中distinct值的数量，则需要将column1放在column2的左边，也就是
group by字句中的字段按照每个字段distinct数据的多少进行降序排序。

::

    presto:industry> select approx_distinct(src_ip) from t_ods_industry_atd;
      _col0
    ---------
     1355237
    (1 row)

    Query 20190830_073515_32089_fqui7, FINISHED, 5 nodes
    Splits: 8,011 total, 8,011 done (100.00%)
    0:01 [105M rows, 691MB] [79.2M rows/s, 520MB/s]

    presto:industry> select approx_distinct(src_port) from t_ods_industry_atd;
     _col0
    -------
     67024
    (1 row)

    Query 20190830_073523_32111_fqui7, FINISHED, 5 nodes
    Splits: 8,011 total, 8,011 done (100.00%)
    0:02 [105M rows, 376MB] [63.5M rows/s, 227MB/s]

    presto:industry> select src_port,src_ip,count(1) as counter from t_ods_industry_atd group by src_port,src_ip order by counter desc limit 10;
     src_port |     src_ip      | counter
    ----------+-----------------+---------
     1        | 157.122.62.205  |  137192
     0        | 111.8.88.245    |  119173
     NULL     | NULL            |   84779
     0        | 111.6.87.71     |   62796
     8888     | 157.122.62.205  |   60358
     0        | 93.174.93.195   |   59618
     53       | 59.51.78.211    |   52472
     0        | 94.102.56.215   |   49422
     0        | 117.135.199.242 |   47077
     12080    | 113.240.233.124 |   46464
    (10 rows)

    Query 20190830_073824_32387_fqui7, FINISHED, 5 nodes
    Splits: 8,179 total, 8,179 done (100.00%)
    0:17 [105M rows, 1.03GB] [6.06M rows/s, 61.1MB/s]

    presto:industry> select src_ip,src_port,count(1) as counter from t_ods_industry_atd group by src_ip,src_port order by counter desc limit 10;
         src_ip      | src_port | counter
    -----------------+----------+---------
     157.122.62.205  | 1        |  137192
     111.8.88.245    | 0        |  119173
     NULL            | NULL     |   84779
     111.6.87.71     | 0        |   62796
     157.122.62.205  | 8888     |   60358
     93.174.93.195   | 0        |   59618
     59.51.78.211    | 53       |   52472
     94.102.56.215   | 0        |   49422
     117.135.199.242 | 0        |   47077
     113.240.233.124 | 12080    |   46464
    (10 rows)

    Query 20190830_073859_32410_fqui7, FINISHED, 5 nodes
    Splits: 8,179 total, 8,179 done (100.00%)
    0:15 [105M rows, 1.03GB] [6.91M rows/s, 69.6MB/s]



模糊聚合函数
>>>>>>>>>>>>

presto提供了一系列的模糊聚合函数，使用这些函数虽然会有一点误差，但是可以获得巨大的性能提升。例如使用模糊聚合函数approx_distinct()函数，你的查询性能相对于
count(distinct x)来说会获得巨大的提升，而且与count(distinct x)查询的结果误差率为2.3%。

::

    presto:industry> select count(1) from t_ods_industry_atd;
       _col0
    -----------
     105202099
    (1 row)

    Query 20190830_070255_29354_fqui7, FINISHED, 5 nodes
    Splits: 7,963 total, 7,963 done (100.00%)
    0:02 [105M rows, 0B] [52.8M rows/s, 0B/s]

    presto:industry> select count(distinct src_ip) from t_ods_industry_atd;
      _col0
    ---------
     1387045
    (1 row)

    Query 20190830_070219_29213_fqui7, FINISHED, 5 nodes
    Splits: 8,123 total, 8,123 done (100.00%)
    0:06 [105M rows, 687MB] [16.3M rows/s, 106MB/s]

    presto:industry> select approx_distinct(src_ip) from t_ods_industry_atd;
      _col0
    ---------
     1355237
    (1 row)

    Query 20190830_070200_29149_fqui7, FINISHED, 5 nodes
    Splits: 7,963 total, 7,963 done (100.00%)
    0:02 [105M rows, 691MB] [44.2M rows/s, 291MB/s]

合并多条like字句为一条regexp_like
>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

presto的查询优化器无法对使用很多like字句的查询进行优化。因此，如果你的查询语句中还有很多like字句，那么你的查询执行的性能会非常低下。为了提升性能，可以将
以or语句连接的多个like字句携程一个regexp_like字句。

::

    presto:industry> select count(1) from t_ods_industry_atd where regexp_like(src_port,'80|90|521');
      _col0
    ---------
     3810439
    (1 row)

    Query 20190830_070951_30233_fqui7, FINISHED, 5 nodes
    Splits: 7,970 total, 7,970 done (100.00%)
    0:02 [105M rows, 372MB] [46M rows/s, 163MB/s]

大表放在join字句左边
>>>>>>>>>>>>>>>>>>>>

在默认情况下，presto使用distributed hash join算法，在这种算法中，join左右两边的表都会根据join键的值进行分区。左表的每个分区都会被通过网络传入到右表所在
分区的worker节点上。也就是说，在进行join关联的时候，会先把右表的所有分区全部分布到各个计算节点上保存，然后等待将左表中的各个分区依次通过网络传输stream
到相应的计算节点上进行计算。由于右表的所有分区需要全部分布到各个节点上进行存储，所以有一个限制：就是集群中所有内存的代销一定要大于右表的大小。

如果你在执行join查询的时候看到错误：task exceeded max memory size，那么这经常意味着join连接的右表大于集群所有内存的大小。presto不会自动进行join两边表
顺序的优化，因此在执行join查询的时候，请确保大表放在join的左边，小表放在join右边。


关闭distributed hash join
>>>>>>>>>>>>>>>>>>>>>>>>>>

若数据存在数据倾斜，那么hash join的性能就会急剧下降。若表足够小以至于可以存储在一个节点的内存中(通常这种表小于2gb)，那么就可以将小表放在右边，然后用户
通过客户端内置的session参数将distributed hash join关闭。当将distributed hash join关闭之后，两个表均不会在进行进行hash重新分布，会将右表广播到针对与左表
source stage的每个节点上进行join操作。

::

    set distributed_join='false'

使用orc存储
>>>>>>>>>>>

由于orc是列式结构化存储，而且当数据量大时，orc文件的存储空间比lzo和text文件所使用的存储空间都要小。除此之外，presto对与orc文件的读取也做了特定的优化，因此
强力建议在hive中创建presto使用的表时，采用orc格式存储。

::

    create table t (...) store as orc;


采用orc格式存储有以下有点：

- orc列式存储，因此查询与居住哎读取数据时可以避免读取一行中不会被使用的列中的内容，减少无用数据的读取。
- orc是结构化存储文件，在文件头中存储了很多文件数据的源数据信息，因此在执行统计性查询的时候，其性能远远高于其他存储格式，例如执行count语句，你会发现orc格式的表的查询性能会非常高。
- orc文件的存储空间非常小，尤其随着单个文件存储的数据量越来越大，相比其他存储格式的文件，orc的有效存储率会越来越高。











