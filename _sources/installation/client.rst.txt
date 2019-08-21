客户端安装
==========

cli下载
>>>>>>>

::

  wget https://repo1.maven.org/maven2/com/facebook/presto/presto-cli/0.224/presto-cli-0.224-executable.jar


制作可执行客户端
>>>>>>>>>>>>>>>>

::

  mv presto-cli-0.224-executable.jar presto
  chmod +x presto


客户端使用
>>>>>>>>>>

::

  ./presto --server localhost:9001 --schema default --catalog elasticsearch

软连接建立
>>>>>>>>>>

::

  ln -s /usr/local/presto-server-0.217/presto /usr/local/bin/presto


