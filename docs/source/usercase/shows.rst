presto的show展示
================

presto使用show命令可以查看CATALOGS, COLUMNS, CREATE, FUNCTIONS, GRANTS, SCHEMAS, SESSION, STATS, TABLES信息。

catalogs
>>>>>>>>

查看presto服务中接入的catalog

::

    presto:default> show catalogs;
    Catalog
    ---------------
     elasticsearch
     hive
     kafka
     postgres
     system
    (5 rows)

    Query 20190829_090915_00005_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [0 rows, 0B] [0 rows/s, 0B/s]


schemas
>>>>>>>

::

    presto:default> show schemas;
       Schema
    --------------------
     default
     information_schema
     test
    (3 rows)

    Query 20190829_091122_00007_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [3 rows, 44B] [9 rows/s, 142B/s]

    presto:default> show schemas from hive;
           Schema
    --------------------
     default
     information_schema
     test
    (3 rows)

    Query 20190829_091132_00008_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [3 rows, 44B] [10 rows/s, 159B/s]


tables
>>>>>>

::

    presto:default> show tables;
        Table
    ---------------------
     t_ods_industry_flow
    (1 row)

    Query 20190829_091440_00017_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:01 [1 rows, 36B] [1 rows/s, 71B/s]

    presto:default> show tables from test;
                Table
    ------------------------------
     t_ods_industry_atd
     t_ods_industry_atd_sp
     t_ods_industry_flow
     t_ods_industry_flow_test
     t_ods_industry_gynetres
     t_ods_industry_gynetres_test
    (6 rows)

    Query 20190829_091448_00018_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:01 [6 rows, 217B] [4 rows/s, 175B/s]

    presto:default> show tables from elasticsearch.default;
            Table
    ----------------------
     guangdong_jmr_sample
     jmr_ipunit
    (2 rows)

    Query 20190829_091454_00019_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [2 rows, 64B] [5 rows/s, 179B/s]


columns
>>>>>>>

类似与 show create table table 和 desc table

::

    presto:information_schema> show COLUMNS from columns;
          Column      |  Type   | Extra | Comment
    ------------------+---------+-------+---------
     table_catalog    | varchar |       |
     table_schema     | varchar |       |
     table_name       | varchar |       |
     column_name      | varchar |       |
     ordinal_position | bigint  |       |
     column_default   | varchar |       |
     is_nullable      | varchar |       |
     data_type        | varchar |       |
     comment          | varchar |       |
     extra_info       | varchar |       |
    (10 rows)

    Query 20190829_092021_00037_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [10 rows, 822B] [22 rows/s, 1.82KB/s]

functions
>>>>>>>>>

::

    presto:test> show functions;
                Function             |         Return Type          |                                 Argument Type
    ---------------------------------+------------------------------+----------------------------------------------
     abs                             | bigint                       | bigint
     abs                             | decimal(p,s)                 | decimal(p,s)
     abs                             | double                       | double
     abs                             | integer                      | integer
     abs                             | real                         | real
     abs                             | smallint                     | smallint
     abs                             | tinyint                      | tinyint
     acos                            | double                       | double
     approx_distinct                 | bigint                       | T
     approx_distinct                 | bigint                       | T, double
     approx_percentile               | array(bigint)                | bigint, array(double)
     approx_percentile               | array(bigint)                | bigint, bigint, array(double)
     approx_percentile               | array(double)                | double, array(double)
     approx_percentile               | array(double)                | double, bigint, array(double)
     approx_percentile               | array(real)                  | real, array(double)
     approx_percentile               | array(real)                  | real, bigint, array(double)
     approx_percentile               | bigint                       | bigint, bigint, double
     approx_percentile               | bigint                       | bigint, bigint, double, double
     approx_percentile               | bigint                       | bigint, double
     approx_percentile               | double                       | double, bigint, double
     approx_percentile               | double                       | double, bigint, double, double
     approx_percentile               | double                       | double, double
     approx_percentile               | real                         | real, bigint, double

session
>>>>>>>

::

    presto:test> show session;
                          Name                       |         Value         |        Default        |  Type   |
    -------------------------------------------------+-----------------------+-----------------------+---------+---
     aggregation_operator_unspill_memory_limit       | 4MB                   | 4MB                   | varchar | Ex
     colocated_join                                  | false                 | false                 | boolean | Ex
     concurrent_lifespans_per_task                   | 0                     | 0                     | integer | Ex
     default_filter_factor_enabled                   | false                 | false                 | boolean | us
     dictionary_aggregation                          | false                 | false                 | boolean | En
     distributed_index_join                          | false                 | false                 | boolean | Di
     distributed_join                                |                       |                       | boolean | (D
     distributed_sort                                | true                  | true                  | boolean | Pa
     dynamic_schedule_for_grouped_execution          | false                 | false                 | boolean | Ex
     enable_intermediate_aggregations                | false                 | false                 | boolean | En
     enable_stats_calculator                         | true                  | true                  | boolean | Ex
     exchange_compression                            | false                 | false                 | boolean | En
     execution_policy                                | all-at-once           | all-at-once           | varchar | Po
     fast_inequality_joins                           | true                  | true                  | boolean | Us
     filter_and_project_min_output_page_row_count    | 256                   | 256                   | integer | Ex
     filter_and_project_min_output_page_size         | 500kB                 | 500kB                 | varchar | Ex
     grouped_execution_for_aggregation               | false                 | false                 | boolean | Us
     hash_partition_count                            | 100                   | 100                   | integer | Nu
     ignore_stats_calculator_failures                | true                  | true                  | boolean | Ig
     initial_splits_per_node                         | 16                    | 16                    | integer | Th
     iterative_optimizer_enabled                     | true                  | true                  | boolean | Ex
     iterative_optimizer_timeout                     | 3.00m                 | 3.00m                 | varchar | Ti
     join_distribution_type                          | PARTITIONED           | PARTITIONED           | varchar | Th

grants
>>>>>>

::

    presto:test> show GRANTS;
     Grantee | Catalog | Schema | Table | Privilege | Grantable
    ---------+---------+--------+-------+-----------+-----------
    (0 rows)

    Query 20190829_092709_00053_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:02 [0 rows, 0B] [0 rows/s, 0B/s]

