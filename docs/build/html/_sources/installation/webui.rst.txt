webUI界面
=========

aripal webUI
>>>>>>>>>>>>

aripal是airbnb专门为presto开发的一个web界面，可以通过web界面，对presto指定查询，做bi分析。详情查看: `https://github.com/airbnb/airpal`__。

.. __: https://github.com/airbnb/airpal

aripal下载
::::::::::
::

    wget https://github.com/airbnb/airpal/archive/master.zip

aripal环境
::::::::::
::

    apt install nodejs
    apt install npm
    apt install gradle


aripal编译
::::::::::
::

    unzip master.zip
    cd airpal-master

    修改build.gradle 添加postgresql依赖
    compile group: 'mysql', name: 'mysql-connector-java', version:'5.1.34'
    compile group: 'org.postgresql', name: 'postgresql', version:'42.2.5'
    compile group: 'com.h2database', name: 'h2', version:'1.4.190'

    ./gradlew clean shadowJar

aripal配置
::::::::::

::

    root@docker:/usr/local/soft/airpal-master# vim reference.yml
    # Logging settings
    logging:

      loggers:
         org.apache.shiro: INFO

     # The default level of all loggers. Can be OFF, ERROR, WARN, INFO, DEBUG, TRACE, or ALL.
      level: INFO

    # HTTP-specific options.
    server:
      applicationConnectors:
        - type: http
          port: 8081
          idleTimeout: 10 seconds

      adminConnectors:
        - type: http
          port: 8082

    shiro:
      iniConfigs: ["classpath:shiro_allow_all.ini"]

    dataSourceFactory:
      driverClass: org.postgresql.Driver
      user: ads
      password: ads@123
      url: jdbc:postgresql://172.31.134.225:5432/hue

    flywayFactory:
      locations: ["classpath:db.migration.common","classpath:db.migration.mysql", "classpath:org.postgresql"]

    # The URL to the Presto coordinator.
    prestoCoordinator: http://172.31.134.225:9001


aripal运行
::::::::::

::

  java -Duser.timezone=UTC -cp build/libs/airpal-*-all.jar com.airbnb.airpal.AirpalApplication db migrate reference.yml

  java -server -Duser.timezone=UTC -cp build/libs/airpal-*-all.jar com.airbnb.airpal.AirpalApplication server reference.yml


.. image:: presto-airpall.png

shib webUI
>>>>>>>>>>

shib是专门为SQL-Like 大数据分析组件设计的来进行数据查询、数据统计的bi工具，支持hive、hive2、presto、bigQuery等大数据工具。详情查看: `https://github.com/tagomoris/shib`__。

.. __: https://github.com/tagomoris/shib

shib下载
::::::::

::

    wget https://github.com/tagomoris/shib/archive/v1.0.2.tar.gz

shib依赖环境
::::::::::::
::

    apt install nodejs
    apt install npm
    npm install -g cnpm --registry=https://registry.npm.taobao.org

shib安装
::::::::

::

    tar -xzvf shib-1.0.2.tar.gz -C /usr/local
    cd /usr/local/
    cnpm install

hiveserver2配置
:::::::::::::::

::

    vi config.js

    var servers = exports.servers = {
      listen: 3000,
      fetch_lines: 1000,
      query_timeout: 30, // 30 seconds for Presto query timeouts (it will fail)
      setup_queries: [],
      storage: {
        datadir: './var'
      },
      engines: [
        { label: 'hivecluster1',
          executer: {
            name: 'presto',
            host: '172.31.134.225',
            port: 9001,
            user: 'admin',
            catalog: 'hive',
            support_database: true,
            default_database: 'test'
          },
          monitor: null
        },
      ],
    };

presto配置
::::::::::

::

    vi config.js

    var servers = exports.servers = {
      listen: 3000,
      fetch_lines: 1000,
      query_timeout: 30,
      setup_queries: [],
      storage: {
        datadir: './var'
      },
      engines: [
        { label: 'prestocluster1',
          executer: {
            name: 'presto',
            host: '172.31.134.225',
            port: 9001,
            user: 'admin',
            catalog: 'hive',
            support_database: true,
            default_database: 'test'
          },
          monitor: null
        },
      ],
    };

shib运行
::::::::

::

    npm start


.. image:: presto-shib.png

hue webUI
>>>>>>>>>

Hue是cdh专门的一套web管理器，它包括3个部分hue ui，hue server，hue db。hue提供所有的cdh组件的shell界面的接口。你可以在hue编写mr，
查看修改hdfs的文件，管理hive的元数据，运行Sqoop，编写Oozie工作流等大量工作。详情查看: `https://docs.gethue.com/`__。

.. __: https://docs.gethue.com/

hue暂时不支持查询presto，但是可以通过以下方式使其支持查询。


Kubernetes
::::::::::

::

    helm repo add gethue https://helm.gethue.com
    helm repo update
    helm install gethue/hue

docker安装
::::::::::

::

    docker run -it -p 8888:8888 gethue/hue:latest

