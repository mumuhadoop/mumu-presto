查询队列
========

presto队列是用于控制查询并发量和可接受的sql数量，可针对用户、提交来源、session等信息进行个性化配置。队列规则定义在一个json文件中。
可以在config.properties中通过query。queue-config-file来指定队列规则的json文件路径。

配置讲解
>>>>>>>>

::

    query.queue-config-file=etc/queues.json

queues队列定义
::::::::::::::

配置文件主要包括queues和rules两部分。其中queues详细说明队列规则的配置。
- queuename 队列名称美也就是每个队列的标签名称。
- maxConcurrent 该队列运行同时运行查询的最大并发数量
- maxQueued 该队列允许同时接受提交查询请求的最大数量。
::

    "global_queue":{
       "maxConcurrent":20,
       "maxQueued":1000
    }

其中global_queue就是queuename，该队列最多同时运行20个查询，最大的队列长度为1000。

rules规则定义
:::::::::::::

规则定义在rules标签下，每个规则可定义4个属性，每个属性的具体含义如下：

- user 用户名，取值来源于session中的user，若在cli中默认为操作系统用户：presto jdbc为传入的用户名。
- source sql来源，目前一般有两种来源， 目前一般有两种来源，即presto-cli和presto-jdbc，其内容支持正则表达式表示。
- session session参数定义，范围在system session中所包含的参数。
- queues 队列列表，可以定义多个。

::

    {
       "user":20,
       "source":1000,
       "session.experimental_big_query":"true",
       "queues":[
          "user.a",
          "global"
       ],
    }

一个提交查询请求在匹配队列时，按照以下的逻辑以此判断。

- 将查询请求中的user信息和规则中的user进行匹配，若匹配成功，则继续与查询请求中的source的内容即系匹配。如果匹配成功，则继续匹配session所定义的规则，如果session定义的规则也满足，则返回该规则下所定义的队列列表。
- 若user信息未匹配成功，则继续与查询请求中source的内容继续匹配，如果撇皮成功，则继续匹配session所定义的规则。如果session定义的规则也满足，则返回该规则下所定义的队列列表。
- 如果user和source信息均为匹配成功，则匹配session所定义的规则，如果满足则返回该规则下所定义的队列列表。
- 最终一个查询请求可能会匹配对个规则，默认取第一个。
- 最终所匹配到的规则中可能配置了多个队列，如果都满足条件则默认选择第一个队列。

队列加载
>>>>>>>>

presto队列信息的加载是在QueryQueueManager中完成的。而队列的加载是在SqlQueryQueueManager的构造方法中完成的。
如果未配置队列的配置文件，name系统会自动创建两个队列：global和big。big队列对session参数expermental_big_query为true的查询定义了限制规则，该队列限制查询的最大运行并发度为10，
最大排队并发度为500.不过这个session参数回叙版本将不会再启动。gloabal队列为其他的查询定义了限制规则，该队列限制查询的最大运行并发度为1000，最大排队并发度为50000。如果配置了
队列配置文件，则依次读取队列和规则信息，并将规则信息组装进QueryQueueRule列表中。



队列匹配
>>>>>>>>

presto获取到查询请求的session信息后，匹配规则信息。若满足某一队列，则返回当前队列中定义的队列。但将查询添加到队列中的时候需要检查当前QueryQueue集合中的队列已执行和排队的查询数据是否已经达到队列的maxConcurrent和maxQueued总和上限值，
只要QueryQueue集合中有一个队列的一致性和排队的查询数据达到maxConcurrent和maxQueued总和上限值，则本次查询请求提交时报。若都满足该要求，选择QueryQueue集合的第一个队列提交查询，提交前需要判断该队列以接受的查询请求数量是否已经达到当前
运行查询数量和以排队的查询数量上限值之和，若达到，name即使提交也会失败。

