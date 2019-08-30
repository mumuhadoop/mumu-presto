system
======

system connector提供了了集群的运行指标及相关的源元数据信息，这些集群信息、运行状况信息也可以通过sql的方式进行查询。system connect不需要专门配置
catalog，由presto集群在启动的时候进行自动添加。

system connector主要包含information_schema、jdbc、metadata、runtime四个schema。information_schema主要包含本catalog下的信息，jdbc包含整个presto
集群的服务信息，metadata包含整个presto集群的元数据信息，runtime包含presto集群的运行状况信息。

::

    presto:system> show schemas from system;
           Schema
    --------------------
     information_schema
     jdbc
     metadata
     runtime
    (4 rows)

    Query 20190830_040205_00015_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [4 rows, 57B] [19 rows/s, 277B/s]

information_schema
>>>>>>>>>>>>>>>>>>

information_schema提供了presto catalog的元数据信息，默认情况下每个presto catalog都会存在一个information_schema。information_schema包含catalog下的
schema信息、function信息、catalog下的所有表信息、catalog下的视图信息、表权限信息、表的列字段信息。

::

    presto:system> show tables from system.information_schema;
          Table
    ------------------
     columns
     schemata
     table_privileges
     tables
     views
    (5 rows)

    Query 20190830_053818_00016_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [5 rows, 182B] [13 rows/s, 497B/s]

columns
:::::::

columns可以查看该catalog下的所有字段信息。show columns就是查看该表下的数据，只是添加了过滤条件。

::

    presto:system> select * from system.information_schema.columns;
     table_catalog |    table_schema    |    table_name     |         column_name          | ordinal_position | col
    ---------------+--------------------+-------------------+------------------------------+------------------+----
     system        | runtime            | queries           | query_id                     |                1 | NUL
     system        | runtime            | queries           | state                        |                2 | NUL
     system        | runtime            | queries           | user                         |                3 | NUL
     system        | runtime            | queries           | source                       |                4 | NUL
     system        | runtime            | queries           | query                        |                5 | NUL
     system        | runtime            | queries           | resource_group_id            |                6 | NUL
     system        | runtime            | queries           | queued_time_ms               |                7 | NUL
     system        | runtime            | queries           | analysis_time_ms             |                8 | NUL
     system        | runtime            | queries           | distributed_planning_time_ms |                9 | NUL
     system        | runtime            | queries           | created                      |               10 | NUL
     system        | runtime            | queries           | started                      |               11 | NUL
     system        | runtime            | queries           | last_heartbeat               |               12 | NUL
     system        | runtime            | queries           | end                          |               13 | NUL
     system        | runtime            | transactions      | transaction_id               |                1 | NUL
     system        | runtime            | transactions      | isolation_level              |                2 | NUL
     system        | runtime            | transactions      | read_only                    |                3 | NUL
     system        | runtime            | transactions      | auto_commit_context          |                4 | NUL
     system        | runtime            | transactions      | create_time                  |                5 | NUL
     system        | runtime            | transactions      | idle_time_secs               |                6 | NUL
     system        | runtime            | transactions      | written_catalog              |                7 | NUL
     system        | runtime            | transactions      | catalogs                     |                8 | NUL
     system        | jdbc               | types             | type_name                    |                1 | NUL
     system        | jdbc               | types             | data_type                    |                2 | NUL
     system        | jdbc               | types             | precision                    |                3 | NUL
     system        | jdbc               | types             | literal_prefix               |                4 | NUL
     system        | jdbc               | types             | literal_suffix               |                5 | NUL
     system        | jdbc               | types             | create_params                |                6 | NUL
     system        | jdbc               | types             | nullable                     |                7 | NUL
     system        | jdbc               | types             | case_sensitive               |                8 | NUL
     system        | jdbc               | types             | searchable                   |                9 | NUL
     system        | jdbc               | types             | unsigned_attribute           |               10 | NUL
     system        | jdbc               | types             | fixed_prec_scale             |               11 | NUL
     system        | jdbc               | types             | auto_increment               |               12 | NUL
     system        | jdbc               | types             | local_type_name              |               13 | NUL
     system        | jdbc               | types             | minimum_scale                |               14 | NUL
     system        | jdbc               | types             | maximum_scale                |               15 | NUL
     system        | jdbc               | types             | sql_data_type                |               16 | NUL
     system        | jdbc               | types             | sql_datetime_sub             |               17 | NUL
     system        | jdbc               | types             | num_prec_radix               |               18 | NUL
     system        | jdbc               | udts              | type_cat                     |                1 | NUL
     system        | jdbc               | udts              | type_schem                   |                2 | NUL
     system        | jdbc               | udts              | type_name                    |                3 | NUL
     system        | jdbc               | udts              | class_name                   |                4 | NUL
     system        | jdbc               | udts              | data_type                    |                5 | NUL
     system        | jdbc               | udts              | remarks                      |                6 | NUL
     system        | jdbc               | udts              | base_type                    |                7 | NUL
     system        | metadata           | column_properties | catalog_name                 |                1 | NUL
     system        | metadata           | column_properties | property_name                |                2 | NUL
     system        | metadata           | column_properties | default_value                |                3 | NUL
     system        | metadata           | column_properties | type                         |                4 | NUL
     system        | metadata           | column_properties | description                  |                5 | NUL
     system        | jdbc               | super_types       | type_cat                     |                1 | NUL
     system        | jdbc               | super_types       | type_schem                   |                2 | NUL
     system        | jdbc               | super_types       | type_name                    |                3 | NUL
     system        | jdbc               | super_types       | supertype_cat                |                4 | NUL
     system        | jdbc               | super_types       | supertype_schem              |                5 | NUL
     system        | jdbc               | super_types       | supertype_name               |                6 | NUL
     system        | information_schema | views             | table_catalog                |                1 | NUL
     system        | information_schema | views             | table_schema                 |                2 | NUL
     system        | information_schema | views             | table_name                   |                3 | NUL
     system        | information_schema | views             | view_definition              |                4 | NUL
     system        | jdbc               | procedure_columns | procedure_cat                |                1 | NUL
     system        | jdbc               | procedure_columns | procedure_schem              |                2 | NUL
     system        | jdbc               | procedure_columns | procedure_name               |                3 | NUL
     system        | jdbc               | procedure_columns | column_name                  |                4 | NUL
     system        | jdbc               | procedure_columns | column_type                  |                5 | NUL
     system        | jdbc               | procedure_columns | data_type                    |                6 | NUL
     system        | jdbc               | procedure_columns | type_name                    |                7 | NUL
     system        | jdbc               | procedure_columns | precision                    |                8 | NUL
     system        | jdbc               | procedure_columns | length                       |                9 | NUL
     system        | jdbc               | procedure_columns | scale                        |               10 | NUL
     system        | jdbc               | procedure_columns | radix                        |               11 | NUL
     system        | jdbc               | procedure_columns | nullable                     |               12 | NUL
     system        | jdbc               | procedure_columns | remarks                      |               13 | NUL
     system        | jdbc               | procedure_columns | column_def                   |               14 | NUL
     system        | jdbc               | procedure_columns | sql_data_type                |               15 | NUL
     system        | jdbc               | procedure_columns | sql_datetime_sub             |               16 | NUL
     system        | jdbc               | procedure_columns | char_octet_length            |               17 | NUL
     system        | jdbc               | procedure_columns | ordinal_position             |               18 | NUL
     system        | jdbc               | procedure_columns | is_nullable                  |               19 | NUL
     system        | jdbc               | procedure_columns | specific_name                |               20 | NUL
     system        | information_schema | schemata          | catalog_name                 |                1 | NUL
     system        | information_schema | schemata          | schema_name                  |                2 | NUL
     system        | jdbc               | procedures        | procedure_cat                |                1 | NUL
     system        | jdbc               | procedures        | procedure_schem              |                2 | NUL
     system        | jdbc               | procedures        | procedure_name               |                3 | NUL
     system        | jdbc               | procedures        | remarks                      |                4 | NUL
     system        | jdbc               | procedures        | procedure_type               |                5 | NUL
     system        | jdbc               | procedures        | specific_name                |                6 | NUL
     system        | information_schema | columns           | table_catalog                |                1 | NUL
     system        | information_schema | columns           | table_schema                 |                2 | NUL
     system        | information_schema | columns           | table_name                   |                3 | NUL
     system        | information_schema | columns           | column_name                  |                4 | NUL
     system        | information_schema | columns           | ordinal_position             |                5 | NUL
     system        | information_schema | columns           | column_default               |                6 | NUL
     system        | information_schema | columns           | is_nullable                  |                7 | NUL
     system        | information_schema | columns           | data_type                    |                8 | NUL
     system        | information_schema | columns           | comment                      |                9 | NUL
     system        | information_schema | columns           | extra_info                   |               10 | NUL
     system        | information_schema | table_privileges  | grantor                      |                1 | NUL
     system        | information_schema | table_privileges  | grantee                      |                2 | NUL
     system        | information_schema | table_privileges  | table_catalog                |                3 | NUL
     system        | information_schema | table_privileges  | table_schema                 |                4 | NUL
     system        | information_schema | table_privileges  | table_name                   |                5 | NUL
     system        | information_schema | table_privileges  | privilege_type               |                6 | NUL
     system        | information_schema | table_privileges  | is_grantable                 |                7 | NUL
     system        | information_schema | table_privileges  | with_hierarchy               |                8 | NUL
     system        | jdbc               | pseudo_columns    | table_cat                    |                1 | NUL
     system        | jdbc               | pseudo_columns    | table_schem                  |                2 | NUL
     system        | jdbc               | pseudo_columns    | table_name                   |                3 | NUL
     system        | jdbc               | pseudo_columns    | column_name                  |                4 | NUL
     system        | jdbc               | pseudo_columns    | data_type                    |                5 | NUL
     system        | jdbc               | pseudo_columns    | column_size                  |                6 | NUL
     system        | jdbc               | pseudo_columns    | decimal_digits               |                7 | NUL
     system        | jdbc               | pseudo_columns    | num_prec_radix               |                8 | NUL
     system        | jdbc               | pseudo_columns    | column_usage                 |                9 | NUL
     system        | jdbc               | pseudo_columns    | remarks                      |               10 | NUL
     system        | jdbc               | pseudo_columns    | char_octet_length            |               11 | NUL
     system        | jdbc               | pseudo_columns    | is_nullable                  |               12 | NUL
     system        | jdbc               | tables            | table_cat                    |                1 | NUL
     system        | jdbc               | tables            | table_schem                  |                2 | NUL
     system        | jdbc               | tables            | table_name                   |                3 | NUL
     system        | jdbc               | tables            | table_type                   |                4 | NUL
     system        | jdbc               | tables            | remarks                      |                5 | NUL
     system        | jdbc               | tables            | type_cat                     |                6 | NUL
     system        | jdbc               | tables            | type_schem                   |                7 | NUL
     system        | jdbc               | tables            | type_name                    |                8 | NUL
     system        | jdbc               | tables            | self_referencing_col_name    |                9 | NUL
     system        | jdbc               | tables            | ref_generation               |               10 | NUL
     system        | runtime            | tasks             | node_id                      |                1 | NUL
     system        | runtime            | tasks             | task_id                      |                2 | NUL
     system        | runtime            | tasks             | stage_id                     |                3 | NUL
     system        | runtime            | tasks             | query_id                     |                4 | NUL
     system        | runtime            | tasks             | state                        |                5 | NUL
     system        | runtime            | tasks             | splits                       |                6 | NUL
     system        | runtime            | tasks             | queued_splits                |                7 | NUL
     system        | runtime            | tasks             | running_splits               |                8 | NUL
     system        | runtime            | tasks             | completed_splits             |                9 | NUL
     system        | runtime            | tasks             | split_scheduled_time_ms      |               10 | NUL
     system        | runtime            | tasks             | split_cpu_time_ms            |               11 | NUL
     system        | runtime            | tasks             | split_blocked_time_ms        |               12 | NUL
     system        | runtime            | tasks             | raw_input_bytes              |               13 | NUL
     system        | runtime            | tasks             | raw_input_rows               |               14 | NUL
     system        | runtime            | tasks             | processed_input_bytes        |               15 | NUL
     system        | runtime            | tasks             | processed_input_rows         |               16 | NUL
     system        | runtime            | tasks             | output_bytes                 |               17 | NUL
     system        | runtime            | tasks             | output_rows                  |               18 | NUL
     system        | runtime            | tasks             | physical_written_bytes       |               19 | NUL
     system        | runtime            | tasks             | created                      |               20 | NUL
     system        | runtime            | tasks             | start                        |               21 | NUL
     system        | runtime            | tasks             | last_heartbeat               |               22 | NUL
     system        | runtime            | tasks             | end                          |               23 | NUL
     system        | metadata           | catalogs          | catalog_name                 |                1 | NUL
     system        | metadata           | catalogs          | connector_id                 |                2 | NUL
     system        | jdbc               | attributes        | type_cat                     |                1 | NUL
     system        | jdbc               | attributes        | type_schem                   |                2 | NUL
     system        | jdbc               | attributes        | type_name                    |                3 | NUL
     system        | jdbc               | attributes        | attr_name                    |                4 | NUL
     system        | jdbc               | attributes        | data_type                    |                5 | NUL
     system        | jdbc               | attributes        | attr_type_name               |                6 | NUL
     system        | jdbc               | attributes        | attr_size                    |                7 | NUL
     system        | jdbc               | attributes        | decimal_digits               |                8 | NUL
     system        | jdbc               | attributes        | num_prec_radix               |                9 | NUL
     system        | jdbc               | attributes        | nullable                     |               10 | NUL
     system        | jdbc               | attributes        | remarks                      |               11 | NUL
     system        | jdbc               | attributes        | attr_def                     |               12 | NUL
     system        | jdbc               | attributes        | sql_data_type                |               13 | NUL
     system        | jdbc               | attributes        | sql_datetime_sub             |               14 | NUL
     system        | jdbc               | attributes        | char_octet_length            |               15 | NUL
     system        | jdbc               | attributes        | ordinal_position             |               16 | NUL
     system        | jdbc               | attributes        | is_nullable                  |               17 | NUL
     system        | jdbc               | attributes        | scope_catalog                |               18 | NUL
     system        | jdbc               | attributes        | scope_schema                 |               19 | NUL
     system        | jdbc               | attributes        | scope_table                  |               20 | NUL
     system        | jdbc               | attributes        | source_data_type             |               21 | NUL
     system        | jdbc               | super_tables      | table_cat                    |                1 | NUL
     system        | jdbc               | super_tables      | table_schem                  |                2 | NUL
     system        | jdbc               | super_tables      | table_name                   |                3 | NUL
     system        | jdbc               | super_tables      | supertable_name              |                4 | NUL
     system        | runtime            | nodes             | node_id                      |                1 | NUL
     system        | runtime            | nodes             | http_uri                     |                2 | NUL
     system        | runtime            | nodes             | node_version                 |                3 | NUL
     system        | runtime            | nodes             | coordinator                  |                4 | NUL
     system        | runtime            | nodes             | state                        |                5 | NUL
     system        | information_schema | tables            | table_catalog                |                1 | NUL
     system        | information_schema | tables            | table_schema                 |                2 | NUL
     system        | information_schema | tables            | table_name                   |                3 | NUL
     system        | information_schema | tables            | table_type                   |                4 | NUL
     system        | metadata           | table_properties  | catalog_name                 |                1 | NUL
     system        | metadata           | table_properties  | property_name                |                2 | NUL
     system        | metadata           | table_properties  | default_value                |                3 | NUL
     system        | metadata           | table_properties  | type                         |                4 | NUL
     system        | metadata           | table_properties  | description                  |                5 | NUL
     system        | jdbc               | schemas           | table_schem                  |                1 | NUL
     system        | jdbc               | schemas           | table_catalog                |                2 | NUL
     system        | jdbc               | catalogs          | table_cat                    |                1 | NUL
     system        | jdbc               | columns           | table_cat                    |                1 | NUL
     system        | jdbc               | columns           | table_schem                  |                2 | NUL
     system        | jdbc               | columns           | table_name                   |                3 | NUL
     system        | jdbc               | columns           | column_name                  |                4 | NUL
     system        | jdbc               | columns           | data_type                    |                5 | NUL
     system        | jdbc               | columns           | type_name                    |                6 | NUL
     system        | jdbc               | columns           | column_size                  |                7 | NUL
     system        | jdbc               | columns           | buffer_length                |                8 | NUL
     system        | jdbc               | columns           | decimal_digits               |                9 | NUL
     system        | jdbc               | columns           | num_prec_radix               |               10 | NUL
     system        | jdbc               | columns           | nullable                     |               11 | NUL
     system        | jdbc               | columns           | remarks                      |               12 | NUL
     system        | jdbc               | columns           | column_def                   |               13 | NUL
     system        | jdbc               | columns           | sql_data_type                |               14 | NUL
     system        | jdbc               | columns           | sql_datetime_sub             |               15 | NUL
     system        | jdbc               | columns           | char_octet_length            |               16 | NUL
     system        | jdbc               | columns           | ordinal_position             |               17 | NUL
     system        | jdbc               | columns           | is_nullable                  |               18 | NUL
     system        | jdbc               | columns           | scope_catalog                |               19 | NUL
     system        | jdbc               | columns           | scope_schema                 |               20 | NUL
     system        | jdbc               | columns           | scope_table                  |               21 | NUL
     system        | jdbc               | columns           | source_data_type             |               22 | NUL
     system        | jdbc               | columns           | is_autoincrement             |               23 | NUL
     system        | jdbc               | columns           | is_generatedcolumn           |               24 | NUL
     system        | jdbc               | table_types       | table_type                   |                1 | NUL
     system        | metadata           | schema_properties | catalog_name                 |                1 | NUL
     system        | metadata           | schema_properties | property_name                |                2 | NUL
     system        | metadata           | schema_properties | default_value                |                3 | NUL
     system        | metadata           | schema_properties | type                         |                4 | NUL
     system        | metadata           | schema_properties | description                  |                5 | NUL
    (226 rows)

schemata
::::::::

查看该catalog下的所有schemas，show schemas就是查看该表下的数据。

::

    presto:system> select * from system.information_schema.schemata;
     catalog_name |    schema_name
    --------------+--------------------
     system       | information_schema
     system       | jdbc
     system       | metadata
     system       | runtime
    (4 rows)

    Query 20190830_054251_00018_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [4 rows, 101B] [25 rows/s, 638B/s]

table_privileges
::::::::::::::::

查看表权限信息，show grants 就是查看该表的信息。
::

    presto:system> select * from system.information_schema.table_privileges;
     grantor | grantee | table_catalog | table_schema | table_name | privilege_type | is_grantable | with_hierarchy
    ---------+---------+---------------+--------------+------------+----------------+--------------+---------------
    (0 rows)

    Query 20190830_054526_00019_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:04 [0 rows, 0B] [0 rows/s, 0B/s]

tables
::::::

查看该catalog下的所有表名。

::

    presto:system> select * from system.information_schema.tables;
     table_catalog |    table_schema    |    table_name     | table_type
    ---------------+--------------------+-------------------+------------
     system        | runtime            | queries           | BASE TABLE
     system        | runtime            | transactions      | BASE TABLE
     system        | jdbc               | types             | BASE TABLE
     system        | jdbc               | udts              | BASE TABLE
     system        | metadata           | column_properties | BASE TABLE
     system        | jdbc               | super_types       | BASE TABLE
     system        | information_schema | views             | BASE TABLE
     system        | jdbc               | procedure_columns | BASE TABLE
     system        | information_schema | schemata          | BASE TABLE
     system        | jdbc               | procedures        | BASE TABLE
     system        | information_schema | columns           | BASE TABLE
     system        | information_schema | table_privileges  | BASE TABLE
     system        | jdbc               | pseudo_columns    | BASE TABLE
     system        | jdbc               | tables            | BASE TABLE
     system        | runtime            | tasks             | BASE TABLE
     system        | metadata           | catalogs          | BASE TABLE
     system        | jdbc               | attributes        | BASE TABLE
     system        | jdbc               | super_tables      | BASE TABLE
     system        | runtime            | nodes             | BASE TABLE
     system        | information_schema | tables            | BASE TABLE
     system        | metadata           | table_properties  | BASE TABLE
     system        | jdbc               | schemas           | BASE TABLE
     system        | jdbc               | catalogs          | BASE TABLE
     system        | jdbc               | columns           | BASE TABLE
     system        | jdbc               | table_types       | BASE TABLE
     system        | metadata           | schema_properties | BASE TABLE
    (26 rows)

    Query 20190830_054741_00020_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:03 [26 rows, 1.36KB] [8 rows/s, 437B/s]

views
:::::

查看该catalog下的所有视图信息，视图功能是要看connector是否支持视图功能。

::

    presto:system> select * from system.information_schema.views;
     table_catalog | table_schema | table_name | view_definition
    ---------------+--------------+------------+-----------------
    (0 rows)

    Query 20190830_054831_00021_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [0 rows, 0B] [0 rows/s, 0B/s]

jdbc
>>>>
|     jdbc查看presto集群的服务信息，如属性信息、catalogs信息、所有catalogs下的表字段信息、所有catalogs下的表信息、presto的所有类型信息、presto所有自定义函数。
| 总之jdbc是对整个presto集群进行信息展示的。而information_schema是对当前catalog进行信息展示。

::

    presto:system> show tables from system.jdbc;
           Table
    -------------------
     attributes
     catalogs
     columns
     procedure_columns
     procedures
     pseudo_columns
     schemas
     super_tables
     super_types
     table_types
     tables
     types
     udts
    (13 rows)

    Query 20190830_054959_00022_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [13 rows, 304B] [36 rows/s, 846B/s]

attributes
::::::::::

::

    presto:system> select * from system.jdbc.attributes;
     type_cat | type_schem | type_name | attr_name | data_type | attr_type_name | attr_size | decimal_digits | num_
    ----------+------------+-----------+-----------+-----------+----------------+-----------+----------------+-----
    (0 rows)

    Query 20190830_055703_00030_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:02 [0 rows, 0B] [0 rows/s, 0B/s]

catalogs
::::::::

::

    presto:system> select * from system.jdbc.catalogs;
       table_cat
    ---------------
     elasticsearch
     hive
     kafka
     postgres
     system
    (5 rows)

    Query 20190830_055734_00031_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [5 rows, 36B] [36 rows/s, 265B/s]

tables
::::::

::

    presto:system> select * from system.jdbc.columns;
       table_cat   |    table_schem     |          table_name          |           column_name           | data_typ
    ---------------+--------------------+------------------------------+---------------------------------+---------
     elasticsearch | information_schema | table_privileges             | grantor                         |        1
     elasticsearch | information_schema | table_privileges             | grantee                         |        1
     elasticsearch | information_schema | table_privileges             | table_catalog                   |        1
     elasticsearch | information_schema | table_privileges             | table_schema                    |        1
     elasticsearch | information_schema | table_privileges             | table_name                      |        1
     elasticsearch | information_schema | table_privileges             | privilege_type                  |        1
     elasticsearch | information_schema | table_privileges             | is_grantable                    |        1
     elasticsearch | information_schema | table_privileges             | with_hierarchy                  |        1
     elasticsearch | information_schema | tables                       | table_catalog                   |        1
     elasticsearch | information_schema | tables                       | table_schema                    |        1
     elasticsearch | information_schema | tables                       | table_name                      |        1
     elasticsearch | information_schema | tables                       | table_type                      |        1
     elasticsearch | information_schema | views                        | table_catalog                   |        1
     elasticsearch | information_schema | views                        | table_schema                    |        1
     elasticsearch | information_schema | views                        | table_name                      |        1
     elasticsearch | information_schema | views                        | view_definition                 |        1
     elasticsearch | information_schema | columns                      | table_catalog                   |        1
     elasticsearch | information_schema | columns                      | table_schema                    |        1
     elasticsearch | information_schema | columns                      | table_name                      |        1
     elasticsearch | information_schema | columns                      | column_name                     |        1
     elasticsearch | information_schema | columns                      | ordinal_position                |        -
     elasticsearch | information_schema | columns                      | column_default                  |        1
     elasticsearch | information_schema | columns                      | is_nullable                     |        1
     elasticsearch | information_schema | columns                      | data_type                       |        1
     elasticsearch | information_schema | columns                      | comment                         |        1
    (query aborted by user)

    Query 20190830_055802_00032_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:02 [1.06K rows, 99.5KB] [472 rows/s, 44.4KB/s]


procedure_columns
:::::::::::::::::

::

    presto:system> select * from system.jdbc.procedure_columns;
     procedure_cat | procedure_schem | procedure_name | column_name | column_type | data_type | type_name | precisi
    ---------------+-----------------+----------------+-------------+-------------+-----------+-----------+--------
    (0 rows)

    Query 20190830_055858_00033_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:02 [0 rows, 0B] [0 rows/s, 0B/s]

procedures
::::::::::

::

    presto:system> select * from system.jdbc.procedures;
     procedure_cat | procedure_schem | procedure_name | remarks | procedure_type | specific_name
    ---------------+-----------------+----------------+---------+----------------+---------------
    (0 rows)

    Query 20190830_055942_00034_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [0 rows, 0B] [0 rows/s, 0B/s]

pseudo_columns
::::::::::::::

::

    presto:system> select * from system.jdbc.pseudo_columns;
     table_cat | table_schem | table_name | column_name | data_type | column_size | decimal_digits | num_prec_radix
    -----------+-------------+------------+-------------+-----------+-------------+----------------+---------------
    (0 rows)

    Query 20190830_060051_00035_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:01 [0 rows, 0B] [0 rows/s, 0B/s]

schemas
:::::::

::

    presto:system> select * from system.jdbc.schemas;
        table_schem     | table_catalog
    --------------------+---------------
     default            | elasticsearch
     information_schema | elasticsearch
     default            | hive
     information_schema | hive
     test               | hive
     industry           | kafka
     information_schema | kafka
     information_schema | postgres
     pg_catalog         | postgres
     public             | postgres
     information_schema | system
     jdbc               | system
     metadata           | system
     runtime            | system
    (14 rows)

    Query 20190830_060113_00036_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [14 rows, 247B] [61 rows/s, 1.05KB/s]


super_tables
::::::::::::

::

    presto:system> select * from system.jdbc.super_tables;
     table_cat | table_schem | table_name | supertable_name
    -----------+-------------+------------+-----------------
    (0 rows)

    Query 20190830_060136_00037_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [0 rows, 0B] [0 rows/s, 0B/s]

super_types
:::::::::::

::

    presto:system> select * from system.jdbc.super_types;
     type_cat | type_schem | type_name | supertype_cat | supertype_schem | supertype_name
    ----------+------------+-----------+---------------+-----------------+----------------
    (0 rows)

    Query 20190830_060157_00038_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [0 rows, 0B] [0 rows/s, 0B/s]

table_types
:::::::::::

::

    presto:system> select * from system.jdbc.table_types;
     table_type
    ------------
     TABLE
     VIEW
    (2 rows)

    Query 20190830_060213_00039_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [2 rows, 9B] [13 rows/s, 62B/s]

tables
::::::

::

    presto:system> select * from system.jdbc.tables;
       table_cat   |    table_schem     |          table_name          | table_type | remarks | type_cat | type_sch
    ---------------+--------------------+------------------------------+------------+---------+----------+---------
     elasticsearch | information_schema | columns                      | TABLE      | NULL    | NULL     | NULL
     elasticsearch | information_schema | tables                       | TABLE      | NULL    | NULL     | NULL
     elasticsearch | information_schema | views                        | TABLE      | NULL    | NULL     | NULL
     elasticsearch | information_schema | schemata                     | TABLE      | NULL    | NULL     | NULL
     elasticsearch | information_schema | table_privileges             | TABLE      | NULL    | NULL     | NULL
     elasticsearch | default            | guangdong_jmr_sample         | TABLE      | NULL    | NULL     | NULL
     elasticsearch | default            | jmr_ipunit                   | TABLE      | NULL    | NULL     | NULL
     hive          | information_schema | columns                      | TABLE      | NULL    | NULL     | NULL
     hive          | information_schema | tables                       | TABLE      | NULL    | NULL     | NULL
     hive          | information_schema | views                        | TABLE      | NULL    | NULL     | NULL
     hive          | information_schema | schemata                     | TABLE      | NULL    | NULL     | NULL
     hive          | information_schema | table_privileges             | TABLE      | NULL    | NULL     | NULL
     hive          | default            | t_ods_industry_flow          | TABLE      | NULL    | NULL     | NULL
     hive          | test               | t_ods_industry_flow          | TABLE      | NULL    | NULL     | NULL
     hive          | test               | t_ods_industry_atd           | TABLE      | NULL    | NULL     | NULL
     hive          | test               | t_ods_industry_flow_test     | TABLE      | NULL    | NULL     | NULL
     hive          | test               | t_ods_industry_atd_sp        | TABLE      | NULL    | NULL     | NULL
     hive          | test               | t_ods_industry_gynetres_test | TABLE      | NULL    | NULL     | NULL
     hive          | test               | t_ods_industry_gynetres      | TABLE      | NULL    | NULL     | NULL
     kafka         | information_schema | columns                      | TABLE      | NULL    | NULL     | NULL
     kafka         | information_schema | tables                       | TABLE      | NULL    | NULL     | NULL
     kafka         | information_schema | views                        | TABLE      | NULL    | NULL     | NULL
     kafka         | information_schema | schemata                     | TABLE      | NULL    | NULL     | NULL
     kafka         | information_schema | table_privileges             | TABLE      | NULL    | NULL     | NULL
     kafka         | industry           | nsfocus                      | TABLE      | NULL    | NULL     | NULL

    Query 20190830_060232_00040_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:02 [62 rows, 2.04KB] [33 rows/s, 1.1KB/s]

types
:::::

::

    presto:system> select * from system.jdbc.types;
            type_name         | data_type | precision  | literal_prefix | literal_suffix | create_params | nullable
    --------------------------+-----------+------------+----------------+----------------+---------------+---------
     time                     |        92 |          8 | NULL           | NULL           | NULL          |        1
     color                    |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     HyperLogLog              |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     varbinary                |        -3 | 2147483647 | NULL           | NULL           | NULL          |        1
     timestamp with time zone |      2014 |         29 | NULL           | NULL           | NULL          |        1
     KdbTree                  |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     BingTile                 |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     boolean                  |        16 | NULL       | NULL           | NULL           | NULL          |        1
     CodePoints               |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     time with time zone      |      2013 |         14 | NULL           | NULL           | NULL          |        1
     JsonPath                 |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     Regressor                |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     SphericalGeography       |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     real                     |         7 |         24 | NULL           | NULL           | NULL          |        1
     JoniRegExp               |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     json                     |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     unknown                  |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     ipaddress                |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     ObjectId                 |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     bigint                   |        -5 |         19 | NULL           | NULL           | NULL          |        1
     interval year to month   |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     SetDigest                |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     P4HyperLogLog            |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     Re2JRegExp               |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     double                   |         8 |         53 | NULL           | NULL           | NULL          |        1
     Geometry                 |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     interval day to second   |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     Model                    |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     smallint                 |         5 |          5 | NULL           | NULL           | NULL          |        1
     tinyint                  |        -6 |          3 | NULL           | NULL           | NULL          |        1
     timestamp                |        93 |         23 | NULL           | NULL           | NULL          |        1
     date                     |        91 |         14 | NULL           | NULL           | NULL          |        1
     LikePattern              |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     integer                  |         4 |         10 | NULL           | NULL           | NULL          |        1
     array                    |      2003 | NULL       | NULL           | NULL           | NULL          |        1
     varchar                  |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     function                 |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     char                     |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     Classifier               |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     qdigest                  |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     row                      |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     decimal                  |      2000 | NULL       | NULL           | NULL           | NULL          |        1
     map                      |      2000 | NULL       | NULL           | NULL           | NULL          |        1
    (43 rows)

    Query 20190830_060257_00041_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:04 [43 rows, 2.28KB] [11 rows/s, 606B/s]

udts
::::

::

    presto:system> select * from system.jdbc.udts;
     type_cat | type_schem | type_name | class_name | data_type | remarks | base_type
    ----------+------------+-----------+------------+-----------+---------+-----------
    (0 rows)

    Query 20190830_060326_00042_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [0 rows, 0B] [0 rows/s, 0B/s]

metadata
>>>>>>>>

查询集群可用的catalog、schema、table和columns的元数据信息。

::

    presto:system> show tables from system.metadata;
           Table
    -------------------
     catalogs
     column_properties
     schema_properties
     table_properties
    (4 rows)

    Query 20190830_060659_00044_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [4 rows, 130B] [8 rows/s, 290B/s]

catalogs
::::::::

查看presto集群所有的catalog。

::

    presto:system> select * from system.metadata.catalogs;
     catalog_name  | connector_id
    ---------------+---------------
     elasticsearch | elasticsearch
     hive          | hive
     kafka         | kafka
     postgres      | postgres
     system        | system
    (5 rows)

    Query 20190830_060812_00045_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [5 rows, 72B] [22 rows/s, 330B/s]

column_properties
:::::::::::::::::

查看字段元数据信息

::

    presto:system> select * from system.metadata.column_properties;
     catalog_name | property_name | default_value | type | description
    --------------+---------------+---------------+------+-------------
    (0 rows)

    Query 20190830_060904_00046_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [0 rows, 0B] [0 rows/s, 0B/s]



schema_properties
:::::::::::::::::

查看schema元数据信息

::

    presto:system> select * from system.metadata.schema_properties;
     catalog_name | property_name | default_value |  type   |          description
    --------------+---------------+---------------+---------+-------------------------------
     hive         | location      |               | varchar | Base file system location URI
    (1 row)

    Query 20190830_060921_00047_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [1 rows, 48B] [7 rows/s, 357B/s]


table_properties
::::::::::::::::

查看表元数据信息

::

    presto:system> select * from system.metadata.table_properties;
     catalog_name |      property_name       | default_value |      type      |                 description
    --------------+--------------------------+---------------+----------------+------------------------------------
     hive         | avro_schema_url          |               | varchar        | URI pointing to Avro schema for the
     hive         | bucket_count             | 0             | integer        | Number of buckets
     hive         | bucketed_by              | []            | array(varchar) | Bucketing columns
     hive         | external_location        |               | varchar        | File system location URI for extern
     hive         | format                   | ORC           | varchar        | Hive storage format for the table
     hive         | orc_bloom_filter_columns | []            | array(varchar) | ORC Bloom filter index columns
     hive         | orc_bloom_filter_fpp     | 0.05          | double         | ORC Bloom filter false positive pro
     hive         | partitioned_by           | []            | array(varchar) | Partition columns
     hive         | sorted_by                | []            | array(varchar) | Bucket sorting columns
    (9 rows)

    Query 20190830_060950_00048_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:03 [9 rows, 533B] [3 rows/s, 203B/s]


runtime
>>>>>>>

runtime schema展示presto集群的运行状况信息，如presto节点信息、查询语句信息、presto任务信息、presto事务信息等。

::

    presto:system> show tables from system.runtime;
        Table
    --------------
     nodes
     queries
     tasks
     transactions
    (4 rows)

    Query 20190830_061154_00049_rewpf, FINISHED, 1 node
    Splits: 19 total, 19 done (100.00%)
    0:00 [4 rows, 97B] [9 rows/s, 225B/s]

nodes
:::::

查看集群节点信息。可以通过sql方便的查看到presto集群的版本号，哪个node是主节点，节点运行状况，节点的服务连接。

::

    presto:system> select *from system.runtime.nodes;
                   node_id                |          http_uri          | node_version | coordinator | state
    --------------------------------------+----------------------------+--------------+-------------+--------
     ffffffff-ffff-ffff-ffff-ffffffffff12 | http://172.31.134.225:9001 | 0.217        | true        | active
    (1 row)

    Query 20190830_061354_00051_rewpf, FINISHED, 1 node
    Splits: 17 total, 17 done (100.00%)
    0:00 [1 rows, 74B] [6 rows/s, 483B/s]

    presto:default> select *from system.runtime.nodes;
                node_id                |         http_uri         | node_version | coordinator | state
    ---------------------------------------+--------------------------+--------------+-------------+--------
     ffffffff-ffff-ffff-ffff-fffffffffff03 | http://192.168.10.3:9001 | 0.211        | false       | active
     ffffffff-ffff-ffff-ffff-fffffffffff05 | http://192.168.10.5:9001 | 0.211        | false       | active
     ffffffff-ffff-ffff-ffff-fffffffffff2  | http://192.168.10.1:9001 | 0.211        | false       | active
     ffffffff-ffff-ffff-ffff-fffffffffff1  | http://192.168.10.8:9001 | 0.211        | true        | active
     ffffffff-ffff-ffff-ffff-fffffffffff02 | http://192.168.10.2:9001 | 0.211        | false       | active
     ffffffff-ffff-ffff-ffff-fffffffffff04 | http://192.168.10.4:9001 | 0.211        | false       | active
    (6 rows)

    Query 20190830_061521_26124_fqui7, FINISHED, 2 nodes
    Splits: 17 total, 17 done (100.00%)
    0:00 [6 rows, 436B] [60 rows/s, 4.32KB/s]

queries
:::::::

可以查询presto集群正在运行(RUNING)、运行完成(FINISHED)和运行失败(FAILED)的查询。queries记录查询语句，默认最多记录100条查询语句。

::

    presto:default> select *from system.runtime.queries;
                   node_id                |          query_id           |  state   | user |   source    |
    --------------------------------------+-----------------------------+----------+------+-------------+-------------------------------------------------------------------------------------------------
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060142_24670_fqui7 | FINISHED | hive | presto-jdbc | PREPARE statement219 FROM SELECT
                                          |                             |          |      |             | attacked_industry as name,count(1) as value
                                          |                             |          |      |             |   FROM industry.t_ods_industry_atd
                                          |                             |          |      |             |  WHERE  event_time BETWEEN ? AND ? AND attacked_industry IS NOT NULL AND attacked_industry IN (?
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060406_25192_fqui7 | FINISHED | hive | presto-jdbc | PREPARE statement69 FROM select count(1) from (
                                          |                             |          |      |             |           select id from industry.t_ods_industry_atd
                                          |                             |          |      |             |            WHERE  event_time  >=  ?
                                          |                             |          |      |             |                    AND event_time  <=  ?
                                          |                             |          |      |             |                    AND cloud_platform_name = ?
                                          |                             |          |      |             |           )
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060147_24683_fqui7 | FINISHED | hive | presto-jdbc | EXECUTE statement18 USING '2019-08-24', '2019-08-30', '食品制造业', '台湾', '香港', '澳门'
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060449_25349_fqui7 | FINISHED | hive | presto-jdbc | EXECUTE statement74 USING '2019-08-30', '2019-08-29', TIMESTAMP '2019-08-29 14:04:49.326', TIMES
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060705_25700_fqui7 | FINISHED | hive | presto-jdbc | EXECUTE statement86 USING '2019-07-30', '2019-08-30', '汽车制造业', '台湾', '香港', '澳门'
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060212_24756_fqui7 | FINISHED | hive | presto-jdbc | PREPARE statement288 FROM SELECT
                                          |                             |          |      |             | count(distinct attacked_corpname) as value
                                          |                             |          |      |             |   FROM industry.t_ods_industry_atd
                                          |                             |          |      |             |  WHERE  ds IN (?,?) AND event_time BETWEEN ? AND ? AND attacked_industry = ? AND attacked_provin
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060548_25551_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement103
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_061251_25997_fqui7 | FINISHED | hive | presto-jdbc | EXECUTE statement383 USING '2019-08-30', '2019-08-29', TIMESTAMP '2019-08-29 14:12:49.116', TIME
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060331_25057_fqui7 | FINISHED | hive | presto-jdbc | PREPARE statement52 FROM SELECT
                                          |                             |          |      |             | count(distinct attacked_corpname) as value
                                          |                             |          |      |             |   FROM industry.t_ods_industry_atd
                                          |                             |          |      |             |  WHERE  ds BETWEEN ? AND ? AND attacked_industry = ? AND attacked_province NOT IN (?,?,?)
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060422_25251_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement79
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060446_25336_fqui7 | FINISHED | hive | presto-jdbc | PREPARE statement65 FROM SELECT
                                          |                             |          |      |             | count(distinct attacked_corpname) as value
                                          |                             |          |      |             |   FROM industry.t_ods_industry_atd
                                          |                             |          |      |             |  WHERE  ds BETWEEN ? AND ? AND attacked_industry = ? AND attacked_province NOT IN (?,?,?)
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060217_24769_fqui7 | FINISHED | hive | presto-jdbc | EXECUTE statement23 USING TIMESTAMP '2019-07-30 00:00:00.000', TIMESTAMP '2019-08-30 23:59:59.99
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060427_25264_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement61
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060443_25323_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement92
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_061212_25947_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement366
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_061229_25968_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement373
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060226_24807_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement289
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060151_24696_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement17
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060555_25572_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement327
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060307_24976_fqui7 | FINISHED | hive | presto-jdbc | PREPARE statement54 FROM SELECT
                                          |                             |          |      |             | count(distinct attacked_corpname) as value
                                          |                             |          |      |             |   FROM industry.t_ods_industry_atd
                                          |                             |          |      |             |  WHERE  ds IN (?,?) AND event_time BETWEEN ? AND ? AND attacked_industry = ? AND attacked_provin
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060439_25310_fqui7 | FINISHED | hive | presto-jdbc | EXECUTE statement90 USING TIMESTAMP '2019-07-30 00:00:00.000', TIMESTAMP '2019-08-30 23:59:59.99
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060343_25103_fqui7 | FINISHED | hive | presto-jdbc | EXECUTE statement304 USING '2019-08-30', '2019-08-29', TIMESTAMP '2019-08-29 14:03:39.358', TIME
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_061427_26109_fqui7 | FINISHED | hive | presto-jdbc | DEALLOCATE PREPARE statement420
     ffffffff-ffff-ffff-ffff-fffffffffff1 | 20190830_060451_25357_fqui7 | FINISHED | hive | presto-jdbc | PREPARE statement66 FROM SELECT
                                          |                             |          |      |             | count(distinct attacked_corpname) as value

tasks
:::::

tasks信息记录了task的明细信息，包括split分片信息、task运行的节点信息、运行时间、task处理的节点信息

::

    presto:default> select *from system.runtime.tasks;
                    node_id                |             task_id             |           stage_id            |          query_id           |  state   | splits | queued_splits | running_splits | complete
    ---------------------------------------+---------------------------------+-------------------------------+-----------------------------+----------+--------+---------------+----------------+---------
     ffffffff-ffff-ffff-ffff-fffffffffff1  | 20190830_061940_26127_fqui7.1.5 | 20190830_061940_26127_fqui7.1 | 20190830_061940_26127_fqui7 | RUNNING  |      1 |             0 |              1 |
     ffffffff-ffff-ffff-ffff-fffffffffff1  | 20190830_061845_26126_fqui7.1.0 | 20190830_061845_26126_fqui7.1 | 20190830_061845_26126_fqui7 | FINISHED |      1 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff1  | 20190830_061612_26125_fqui7.1.0 | 20190830_061612_26125_fqui7.1 | 20190830_061612_26125_fqui7 | FINISHED |      1 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff1  | 20190830_061512_26123_fqui7.1.0 | 20190830_061512_26123_fqui7.1 | 20190830_061512_26123_fqui7 | FINISHED |      1 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff1  | 20190830_061521_26124_fqui7.1.0 | 20190830_061521_26124_fqui7.1 | 20190830_061521_26124_fqui7 | FINISHED |      1 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060448_25347_fqui7.2.4 | 20190830_060448_25347_fqui7.2 | 20190830_060448_25347_fqui7 | FINISHED |    430 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061352_26069_fqui7.2.4 | 20190830_061352_26069_fqui7.2 | 20190830_061352_26069_fqui7 | FINISHED |    420 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060606_25610_fqui7.0.0 | 20190830_060606_25610_fqui7.0 | 20190830_060606_25610_fqui7 | FINISHED |     17 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061246_25991_fqui7.2.4 | 20190830_061246_25991_fqui7.2 | 20190830_061246_25991_fqui7 | FINISHED |    600 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060523_25469_fqui7.2.4 | 20190830_060523_25469_fqui7.2 | 20190830_060523_25469_fqui7 | FINISHED |   1119 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060546_25548_fqui7.1.4 | 20190830_060546_25548_fqui7.1 | 20190830_060546_25548_fqui7 | FINISHED |   1912 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061444_26120_fqui7.3.0 | 20190830_061444_26120_fqui7.3 | 20190830_061444_26120_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061122_25893_fqui7.1.1 | 20190830_061122_25893_fqui7.1 | 20190830_061122_25893_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060656_25690_fqui7.1.2 | 20190830_060656_25690_fqui7.1 | 20190830_060656_25690_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061304_26012_fqui7.1.2 | 20190830_061304_26012_fqui7.1 | 20190830_061304_26012_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061052_25860_fqui7.3.3 | 20190830_061052_25860_fqui7.3 | 20190830_061052_25860_fqui7 | FINISHED |    601 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060546_25550_fqui7.2.4 | 20190830_060546_25550_fqui7.2 | 20190830_060546_25550_fqui7 | FINISHED |    540 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060530_25499_fqui7.1.3 | 20190830_060530_25499_fqui7.1 | 20190830_060530_25499_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060839_25773_fqui7.0.0 | 20190830_060839_25773_fqui7.0 | 20190830_060839_25773_fqui7 | FINISHED |     17 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061416_26099_fqui7.1.0 | 20190830_061416_26099_fqui7.1 | 20190830_061416_26099_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060728_25724_fqui7.1.1 | 20190830_060728_25724_fqui7.1 | 20190830_060728_25724_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061235_25976_fqui7.2.4 | 20190830_061235_25976_fqui7.2 | 20190830_061235_25976_fqui7 | FINISHED |    640 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061249_25994_fqui7.2.4 | 20190830_061249_25994_fqui7.2 | 20190830_061249_25994_fqui7 | FINISHED |    446 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061011_25806_fqui7.1.4 | 20190830_061011_25806_fqui7.1 | 20190830_061011_25806_fqui7 | FINISHED |   2197 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_060512_25430_fqui7.1.3 | 20190830_060512_25430_fqui7.1 | 20190830_060512_25430_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061000_25797_fqui7.1.4 | 20190830_061000_25797_fqui7.1 | 20190830_061000_25797_fqui7 | FINISHED |     32 |             0 |              0 |
     ffffffff-ffff-ffff-ffff-fffffffffff2  | 20190830_061314_26024_fqui7.0.0 | 20190830_061314_26024_fqui7.0 | 20190830_061314_26024_fqui7 | FINISHED |     17 |             0 |              0 |

transactions
::::::::::::

记录了presto集群执行事务的操作语句。

::

    presto:default> select *from system.runtime.transactions;
                transaction_id            | isolation_level  | read_only | auto_commit_context |       create_time       | idle_time_secs | written_catalog |                   catalogs
    --------------------------------------+------------------+-----------+---------------------+-------------------------+----------------+-----------------+---------------------------------------------
     576da0b3-574d-4192-9a73-2b1ea89cc731 | READ UNCOMMITTED | false     | true                | 2019-08-29 15:52:09.176 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
     a21c82d8-f1ce-4c74-9824-3dd1c99fec1f | READ UNCOMMITTED | false     | true                | 2019-08-30 14:20:50.532 |              0 | NULL            | [system, $info_schema@system, $system@system
     9f20bf0e-c926-4f0b-a041-3c2f25563ff6 | READ UNCOMMITTED | false     | true                | 2019-08-29 14:58:25.993 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
     a24c83b1-d213-40c9-93f9-f7c646a3afb1 | READ UNCOMMITTED | false     | true                | 2019-08-30 14:20:42.027 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
     3e41255e-aea4-473d-85d7-1288e27c67ad | READ UNCOMMITTED | false     | true                | 2019-08-30 14:20:45.545 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
     23127584-a460-4e35-819e-5622a83cfe0f | READ UNCOMMITTED | false     | true                | 2019-08-30 14:20:49.987 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
     8eab0d82-1211-4888-a24a-c84c6ea17502 | READ UNCOMMITTED | false     | true                | 2019-08-29 15:13:47.390 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
     1905dafd-718c-45d4-9ffb-1ad5bfe75814 | READ UNCOMMITTED | false     | true                | 2019-08-30 12:04:01.266 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
     0892e64f-9ca3-4c4f-acf7-a2bdd189c8c7 | READ UNCOMMITTED | false     | true                | 2019-08-29 15:12:51.571 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
     5043f6fa-55bb-4d60-9f53-4ebd4f89681a | READ UNCOMMITTED | false     | true                | 2019-08-29 15:02:31.807 |              0 | NULL            | [$info_schema@hive, $system@hive, hive]
    (10 rows)

    Query 20190830_062050_26191_fqui7, FINISHED, 2 nodes
    Splits: 17 total, 17 done (100.00%)
    0:02 [10 rows, 1.16KB] [5 rows/s, 622B/s]
