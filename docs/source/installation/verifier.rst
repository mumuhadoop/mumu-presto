presto校验
==========

verifier下载
>>>>>>>>>>>>

::

  wget https://repo1.maven.org/maven2/com/facebook/presto/presto-verifier/0.217/presto-verifier-0.217-executable.jar


制作可执行客户端
>>>>>>>>>>>>>>>>

::

  mv presto-verifier-0.224-executable.jar verifier
  chmod +x verifier


表创建
>>>>>>

::

 CREATE TABLE verifier_queries (
    id int(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT,
    suite varchar(256) NOT NULL,
    name varchar(256) DEFAULT NULL,
    control_catalog varchar(256) NOT NULL,
    control_schema varchar(256) NOT NULL,
    control_query text NOT NULL,
    test_catalog varchar(256) NOT NULL,
    test_schema varchar(256) NOT NULL,
    test_query text NOT NULL,
    control_username varchar(256) NOT NULL DEFAULT 'verifier-test',
    control_password varchar(256) DEFAULT NULL,
    test_username varchar(256) NOT NULL DEFAULT 'verifier-test',
    test_password varchar(256) DEFAULT NULL,
    session_properties_json varchar(2048) DEFAULT NULL)

config.properties
>>>>>>>>>>>>>>>>>
::

    source-query.suite=my_suite
    source-query.database=jdbc:mysql://localhost:3306/my_database?user=my_username&password=my_password
    control.gateway=jdbc:presto://localhost:8080
    test.gateway=jdbc:presto://localhost:8081
    test-id=1

客户端使用
>>>>>>>>>>

::

    ./verifier verify config.properties

软连接建立
>>>>>>>>>>

::

  ln -s /usr/local/presto-server-0.217/verifier /usr/local/bin/verifier


