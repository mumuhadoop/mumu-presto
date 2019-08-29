presto客户端使用
================

presto提供了客户端查询进行sql查询。而且在客户端中可以自己指定catalog和schema，非常方便快速的定位到我们需要查询的表。

帮助文档
>>>>>>>>

::

    root@docker:/usr/local/presto-server-0.217# ./presto --help
    NAME
            presto - Presto interactive console

    SYNOPSIS
            presto [--access-token <access token>] [--catalog <catalog>]
                    [--client-info <client-info>]
                    [--client-request-timeout <client request timeout>]
                    [--client-tags <client tags>] [--debug] [--execute <execute>]
                    [(-f <file> | --file <file>)] [(-h | --help)]
                    [--http-proxy <http-proxy>] [--ignore-errors]
                    [--keystore-password <keystore password>]
                    [--keystore-path <keystore path>]
                    [--krb5-config-path <krb5 config path>]
                    [--krb5-credential-cache-path <krb5 credential cache path>]
                    [--krb5-disable-remote-service-hostname-canonicalization]
                    [--krb5-keytab-path <krb5 keytab path>]
                    [--krb5-principal <krb5 principal>]
                    [--krb5-remote-service-name <krb5 remote service name>]
                    [--log-levels-file <log levels file>] [--output-format <output-format>]
                    [--password] [--resource-estimate <resource-estimate>...]
                    [--schema <schema>] [--server <server>] [--session <session>...]
                    [--socks-proxy <socks-proxy>] [--source <source>]
                    [--truststore-password <truststore password>]
                    [--truststore-path <truststore path>] [--user <user>] [--version]

    OPTIONS
            --access-token <access token>
                Access token

            --catalog <catalog>
                Default catalog

            --client-info <client-info>
                Extra information about client making query

            --client-request-timeout <client request timeout>
                Client request timeout (default: 2m)

            --client-tags <client tags>
                Client tags

            --debug
                Enable debug information

            --execute <execute>
                Execute specified statements and exit

            -f <file>, --file <file>
                Execute statements from file and exit

            -h, --help
                Display help information

            --http-proxy <http-proxy>
                HTTP proxy to use for server connections

            --ignore-errors
                Continue processing in batch mode when an error occurs (default is
                to exit immediately)

            --keystore-password <keystore password>
                Keystore password

            --keystore-path <keystore path>
                Keystore path

            --krb5-config-path <krb5 config path>
                Kerberos config file path (default: /etc/krb5.conf)

            --krb5-credential-cache-path <krb5 credential cache path>
                Kerberos credential cache path

            --krb5-disable-remote-service-hostname-canonicalization
                Disable service hostname canonicalization using the DNS reverse
                lookup

            --krb5-keytab-path <krb5 keytab path>
                Kerberos key table path (default: /etc/krb5.keytab)

            --krb5-principal <krb5 principal>
                Kerberos principal to be used

            --krb5-remote-service-name <krb5 remote service name>
                Remote peer's kerberos service name

            --log-levels-file <log levels file>
                Configure log levels for debugging using this file

            --output-format <output-format>
                Output format for batch mode [ALIGNED, VERTICAL, CSV, TSV,
                CSV_HEADER, TSV_HEADER, NULL] (default: CSV)

            --password
                Prompt for password

            --resource-estimate <resource-estimate>
                Resource estimate (property can be used multiple times; format is
                key=value)

            --schema <schema>
                Default schema

            --server <server>
                Presto server location (default: localhost:8080)

            --session <session>
                Session property (property can be used multiple times; format is
                key=value; use 'SHOW SESSION' to see available properties)

            --socks-proxy <socks-proxy>
                SOCKS proxy to use for server connections

            --source <source>
                Name of source making query

            --truststore-password <truststore password>
                Truststore password

            --truststore-path <truststore path>
                Truststore path

            --user <user>
                Username

            --version
                Display version information and exit

使用方式
>>>>>>>>

::

    ./presto --server localhost:9001 --catalog hive --schema default


制作脚本
>>>>>>>>

::

    echo "./presto --server localhost:9001 --catalog hive --schema default" > presto-cli-hive
    chmod +x presto-cli-hive
    ln -s /usr/local/presto-server-0.217/presto-cli-hive /usr/local/bin/

执行sql语句
>>>>>>>>>>>

::

    root@docker:/usr/local/presto-server-0.217# ./presto --server localhost:9001 --schema default --catalog hive --execute "show catalogs";
    "elasticsearch"
    "hive"
    "kafka"
    "postgres"
    "system"
    root@docker:/usr/local/presto-server-0.217# ./presto --server localhost:9001 --schema default --catalog hive --execute "show tables;";
    "t_ods_industry_flow"
    root@docker:/usr/local/presto-server-0.217# ./presto --server localhost:9001 --schema default --catalog hive --execute "select count(1) from t_ods_industry_flow";
    "11904"


执行sql文件
>>>>>>>>>>>

::

    echo "select count(1) from t_ods_industry_flow;" > hive_default_flow.sql
    root@docker:/usr/local/presto-server-0.217# ./presto --server localhost:9001 --schema default --catalog hive --file hive_default_flow.sql
    "11904"


sql结果导出
>>>>>>>>>>>

::

    root@docker:/usr/local/presto-server-0.217# ./presto --server localhost:9001 --schema default --catalog hive --execute "select count(1) from t_ods_industry_flow" --output-format CSV > result.csv
    root@docker:/usr/local/presto-server-0.217# cat result.csv
    "11904"

    root@docker:/usr/local/presto-server-0.217# ./presto --server localhost:9001 --schema default --catalog hive --file hive_default_flow.sql --output-format CSV > result2.csv
    root@docker:/usr/local/presto-server-0.217# cat result2.csv
    "11904"

