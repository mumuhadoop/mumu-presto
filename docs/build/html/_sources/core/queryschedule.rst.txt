查询调度
========

presto根据生成的逻辑执行计划将其拆分成多个且有层级关系的stage，presto的各个stage都可以理解为将整个查询的执行计划分成了若干段，实际就是将整个sql处理过程
拆分成具有各自功能的执行阶段。每个执行阶段都会被进一步分解为若干个task，presto将查询请求解析成各个执行阶段后，变回将各个阶段分配到各个计算节点中执行，这
个分配的过程实际是基于stage进行的，每个stage的调度过程是基于split分配worker node的过程，不同的stage有不同的调度策略。

生成调度自行器
>>>>>>>>>>>>>>

stage层级关系的形式是通过查询执行计划演变而来的，对于每个stage，presto都会生成一个与其对应的SqlStageExecution实例，SqlStageExecution承载咳stage的启动和任务
的调度，也可称之为整个查询的调度执行器。sql语句经过优化执行后，获取sql的逻辑执行计划subPlan。


查询调度过程
>>>>>>>>>>>>

SqlStageExecution内部主要由一下组件组成:

- nodeScheduler 将task分配给node的核心模块。
    - nodeManager 获取存活的节点列表，将其保存在NodeMap中，并且定时更新列表中的缓存。
    - nodeMap 用于存储presto节点信息，包含ip端口组成的列表、ip组成的节点列表、rack组成的节点列表。其中rack只是简单的将节点inetAddress封装成rackId，并且一个节点对应一个rack。
    - nodeSchedulerConfig 配置调度的相关参数
    - nodeSelector 提供了各个stage中task分配节点的算法。

- nodeTaskMap 保存了当前stage分配的task和几点的映射列表，其中NodeTasks维护了一个节点对应的task列表，并且会对每个task注册状态监听器，确保task完成后去task列表中移除。
- remoteTaskFactory 生成RemoteTask的工厂类。
- stageStateMachinestage状态监听器


presto的查询调度本质上就是split分配到各个节点的过程，每个阶段一句本身所陈丹的职责，调度方式有所区别，从整体上来说，split分配节点的方式基本为随机选择的策略，在次基础上尽量保证
每个节点处理的split相对平均。
