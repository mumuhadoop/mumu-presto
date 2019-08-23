RESTFUL架构
===========

|     在presto中几乎所有的操作都是依赖于AirLift构造的RESTful服务来完成的，包括worker节点的管理、查询语句的提交、查询状态的显示、各个task之间数据的传递等。因此
| presto中的RESTful服务是presto集群的基础。
|     presto中提供了四种类型的RESTful接口，分别是statement服务接口、query服务接口、stage服务接口、task服务接口。

statement服务接口
>>>>>>>>>>>>>>>>>

|     与sql语句相关的请求均由该服务接口处理，包括接收提交的sql语句、获取查询执行结果的语句、取消查询语句等。statement服务接口的实现类为StatementResource。


query服务接口
>>>>>>>>>>>>>

|     与查询相关的RESTful请求均由query服务接口处理，包裹sql语句的提交、获取查询执行的结果、取消查询等。query服务接口实现类为QueryResource。

stage服务接口
>>>>>>>>>>>>>

|     与stage相关的RESTful请求均由stage服务接口处理，其实该接口只提供了一个功能，就是取消或者结束一个指定的stage。stage服务接口的实现类为StageResource。


task服务接口
>>>>>>>>>>>>

|     与task相关的RESTful请求均由ask服务接口处理，包括task的创建、更新、状态查询和结果查询等。task服务接口的实现类为TaskResource。


**presto集群中的数据传输、节点通信、心跳感应、计算监控、计算调度和计算分布全部都是基于RESTful服务实现的，因此presto中的RESTful服务就是presto所有服务的基石。**
