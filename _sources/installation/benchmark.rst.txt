benchmark性能测试
=================

benchmark下载
>>>>>>>>>>>>>

::

  wget https://repo1.maven.org/maven2/com/facebook/presto/presto-benchmark-driver/0.217/presto-benchmark-driver-0.217-executable.jar


制作可执行客户端
>>>>>>>>>>>>>>>>

::

  mv presto-benchmark-driver-0.217-executable.jar presto-benchmark-driver
  chmod +x presto-benchmark-driver

使用方式
>>>>>>>>

::

    root@docker:/usr/local/presto-server-0.217# ./presto-benchmark-driver --help
    NAME
            presto-benchmark - Presto benchmark driver

    SYNOPSIS
            presto-benchmark [--catalog <catalog>]
                    [--client-request-timeout <client request timeout>] [--debug]
                    [(-h | --help)] [--max-failures <max failures>] [--query <query>...]
                    [--runs <runs>] [--schema <schema>] [--server <server>]
                    [--session <session>...] [--socks <socks>] [--sql <sql>]
                    [--suite <suite>...] [--suite-config <suite-config>] [--user <user>]
                    [--warm <warm>]

    OPTIONS
            --catalog <catalog>
                Default catalog

            --client-request-timeout <client request timeout>
                Client request timeout (default: 2m)

            --debug
                Enable debug information (default: false)

            -h, --help
                Display help information

            --max-failures <max failures>
                Max number of consecutive failures before benchmark fails

            --query <query>
                Queries to execute

            --runs <runs>
                Number of times to run each query (default: 3)

            --schema <schema>
                Default schema

            --server <server>
                Presto server location (default: localhost:8080)

            --session <session>
                Session property (property can be used multiple times; format is
                key=value)

            --socks <socks>
                Socks proxy to use

            --sql <sql>
                Directory containing sql files (default: sql)

            --suite <suite>
                Suite to execute

            --suite-config <suite-config>
                Suites configuration file (default: suite.json)

            --user <user>
                Username

            --warm <warm>
                Number of times to run each query for a warm-up (default: 1)



创建suite.json
>>>>>>>>>>>>>>
::

    root@docker:/usr/local/presto-server-0.217# vim suite.json
    {
        "file_formats": {
             "query": ["test.*"],
             "schema": ["test.*"],
             "session": {}
        }
    }

- file_formats

  测试suite单元

- query

  匹配sql文件的sql正则表达式

- schema

  匹配的schema正则表达式

- session
  查询携带的参数信息

创建sql文件
>>>>>>>>>>>

::

    root@docker:mkdir sql

    root@docker:/usr/local/presto-server-0.217# vim sql/test.sql

    select count(1) from hive.test.t_ods_industry_atd;


执行benchmark
>>>>>>>>>>>>>

在同目录下创建sql目录，然后在目录里面添加test.sql,每个sql文件只能包含一条sql语句，不能以','号分割多条sql语句。

::

    ./presto-benchmark-driver --server 172.31.134.225:9001  --debug --suite-config suite.json  --catalog hive --warm 10

    root@docker:/usr/local/presto-server-0.217# ./presto-benchmark-driver --server 172.31.134.225:9001  --debug  --catalog hive  --warm 10
    2019-08-21T09:41:27.936+0800	INFO	main	io.airlift.log.Logging	Logging to stderr
    suite	query	compression	format	scale	wallTimeP50	wallTimeMean	wallTimeStd	processCpuTimeP50	processCpuTimeMean	processCpuTimeStd	queryCpuTimeP50	queryCpuTimeMean	queryCpuTimeStstatus	error
    file_formats	test				742	773	79	2630	2746	311	216	217   12	pass

- suite

  执行的suite

- query

  执行的sql文件

- compression

  压缩方式，none、snappy、zlib

- format

  文件存储格式

- scale

  执行次数，compression、format、scale参数都是从schema匹配 "tpch_sf(?<scale>.*)_(?<format>orc)_(?<compression>.*?)"获取的数据类型，
  如tpch_sf100_orc_snappy。

- WallTime

  用户要可以看到查询结果要等待的时间，median, mean and standard deviation of the query runs

- processCpuTime

  整个集群为助理查询而消耗的CPU时间，包含一些垃圾回收的时间，median, mean and standard deviation of the query runs

- queryCpuTime

  整个集群为助理查询而消耗的CPU时间，median, mean and standard deviation of the query runs