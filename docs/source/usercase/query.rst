sql基本查询
===========

presto的sql遵循sql99规范，基本sql查询都是和关系型数据库保持一致的。

列表查询
>>>>>>>>

::

    presto:test> select *from t_ods_industry_flow limit 10;
     all_count | all_flow | actual_all_count | actual_all_flow |                 uuid                 | all_count_t
    -----------+----------+------------------+-----------------+--------------------------------------+------------
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
     95351     | 65447202 | 779672           | 536157640       | e7d2c211-804f-4d1a-a94f-b05715cb2129 | NULL
    (10 rows)

数量计算
>>>>>>>>

::

    presto:test> select count(1) from t_ods_industry_flow;
     _col0
    -------
     39680
    (1 row)

    Query 20190829_093923_00065_rewpf, FINISHED, 1 node
    Splits: 37 total, 37 done (100.00%)
    0:01 [39.7K rows, 0B] [47K rows/s, 0B/s]

聚合计算
>>>>>>>>

::

    presto:test> select protocol_name,count(1) counter from t_ods_industry_flow group by protocol_name order by counter desc limit 10;
     protocol_name | counter
    ---------------+---------
     HTTP          |   33600
     ICMP4         |    3960
     IPV4          |     840
     FTP           |     560
     UDP           |     400
     GRE           |     320
    (6 rows)

    Query 20190829_094129_00068_rewpf, FINISHED, 1 node
    Splits: 69 total, 69 done (100.00%)
    0:01 [39.7K rows, 1.58KB] [36.6K rows/s, 1.46KB/s]


多数据源混合查询
>>>>>>>>>>>>>>>>

::

    presto:test> select device.service,count(1) counter from postgres.public.tb_protocol_device device left join hive.test.t_ods_industry_atd atd on (device.service=atd.dst_service) group by device.service order by counter desc limit 10;
            service        | counter
    -----------------------+---------
     general-electric-srtp |       1
     bachmann-tcp          |       1
     bacnet                |       1
     CoAP                  |       1
     modbus                |       1
     codesys               |       1
     onvif                 |       1
     XMPP                  |       1
     bachmann-udp          |       1
     cspv4                 |       1
    (10 rows)

    Query 20190829_095118_00081_rewpf, FINISHED, 1 node
    Splits: 108 total, 108 done (100.00%)
    0:02 [130K rows, 8.52KB] [66.8K rows/s, 4.37KB/s]

