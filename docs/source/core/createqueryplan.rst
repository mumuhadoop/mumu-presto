生成查询计划
============

|     本章主要讲述presto对一个传入的sql语句如何进行解析并生成最终的执行计划。

.. image:: presto-create-query.png

|     从上图可以看到，生成查询计划分成语法分析、词法分析、语义分析、执行计划生成、执行计划优化、执行计划分阶段执行。

基本概念
>>>>>>>>

node
::::

查询语句经过词法和语法分析之后，会生成抽象语法树(AST)，该语法树中的每一个几点都是一个Node(SQL语句的一部分，如select、where、group by等)。Node是一个抽象类，实现类如下:

#. approximate 用于近似查询
#. explainOption 标识explain语句的可选参数，有explainFormat和explainType两类。explainFormat标识输出结果的格式，有text和graphviz两种类型。explainType标识explain语句的类型，有logical和distributed两类，分别标识生成逻辑执行计划与生成分布式执行计划。
#. expression 标识sql语句中出现的表达式。
#. framebound 用于窗口函数中华东窗口的可选参数。
#. relation 是一个抽象类，标识多个节点之间的关系，如join、union等。
#. select 标识查询语句中的select部分。
#. selectitem 标识select语句中的列类型，有allcolumns和singlecolumns两种类型。
#. sortitem 标识排序的某一列及其类型。
#. statement 标识presto能使用的sql类型的sql语句。
#. tableelement 标识建表语句描述表的每一列，包括列名与类型。
#. window 表示一个窗口函数。
#. windowFrame 表示窗口函数中欢动窗口的可选参数。
#. with 表示一个查询中所有的with语句，主要元素有recursive、querys。
#. withquery 表示一个with语句，主要元素有name、query、columnNames。

metadata API
::::::::::::

| metadata API即是matadata接口，其提供了对源数据进行各种操作的接口，列如列出所有的数据库名、表名等。这些接口在对sql进行语义分宜以及某些ddl操作(如create table)的执行过程中会用到。
| metadata api将不同Connector对其元数据的各种啊哦做抽象成一了统一的接口，使得在使用这些接口时无需考虑具体的底层connector实现。
| metadata api除了提供对元数据操作的接口，还提供了一些通用的与connector无关的方法，例如列出presto支持的自定义函数等。


词法和语法分析
>>>>>>>>>>>>>>

获取查询执行引擎
>>>>>>>>>>>>>>>>

语义分析
>>>>>>>>

执行计划生成
>>>>>>>>>>>>

执行计划优化
>>>>>>>>>>>>

执行计划分段
>>>>>>>>>>>>