合理设置分区
============

spark提交job之后会把job分成多个stage，多个stage之间是有依赖关系的，正如前面所看到的的，stage0依赖于stage1，因此构成了有向无环图DAG。而且stage中
的依赖分为窄依赖和宽依赖，窄依赖是一个worker节点的数据只能被一个worker使用，而宽依赖是一个worker节点的数据被多个worker使用。一般讲多个窄依赖归
类为一个stage，方便与pipeline管道执行，而将以宽依赖分为一个stage。
