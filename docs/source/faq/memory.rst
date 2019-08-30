task exceeded max memory
========================

出错原因
::::::::

presto会将每个查询语句最终分解为在每个节点上运行的task，并且会在配置文件中配置每个task可以使用的最大内存大小。若某个查询语句中的某个task处理的数据超过给task可以使用的最大内存代销，则会跑出上面的错误。

解决方案
::::::::

修改presto配置文件config.properties，提供其中的配置属性task.max-memory的值。




