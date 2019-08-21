presto架构
==========

Presto是一个在一组机器上运行的分布式系统。 完整安装包括协调员和多个工作人员。 查询从客户端（Presto CLI、presto-jdbc）提交给协调器。 协调器解析，
分析和计划查询执行，然后将处理分发给worker工作进程。


presto硬件架构
>>>>>>>>>>>>>>

.. image:: presto-architecture-machine.png

presto软件架构
>>>>>>>>>>>>>>

.. image:: presto-architecture-soft.png
