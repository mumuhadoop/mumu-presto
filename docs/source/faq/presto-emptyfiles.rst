prsto查询hive的空文件报错
=========================

|    presto查询hive的connector的表数据的时候，如果hive表中存在空文件会导致presto查询报错。所以我们需要找到hdfs里面的空文件，并且将该空文件删除掉。目前我是编写了一个python
| 脚本程序进行自动查找空文件和删除空文件。

报错异常
>>>>>>>>

::

    xxxx.parquet file is small or is not a parquet file


解决办法
>>>>>>>>

编写python脚本查找和删除hdfs空文件

::

    #!/usr/bin/env python
    # -*- coding: utf-8 -*-
    # @Time    : 2019/7/26 11:36
    # @Author  : ganliang
    # @File    : hdfsemptytool.py
    # @Desc    : HDFS工具類，提供根据文件大小查找文件，删除文件等功能

    import logging
    import os
    import re
    import sys

    LOGGING_CONFIG = {
        # "filename": "config.log",
        # "filemode": "w",
        "format": "%(asctime)s|%(process)d|%(thread)d|%(filename)s[%(funcName)s:%(lineno)d]|%(levelname)s|%(message)s",
        "level": logging.INFO
    }
    logging.basicConfig(**LOGGING_CONFIG)


    def find_file_bysize(urls=None, empty_files=None, beginsize=0L, endsize=0L):
        """
        根据问价的大小来匹配文件
        :param urls: 需要收集的url列表
        :param empty_files: 收集到的空文件列表
        :param beginsize: 从多少字节开始
        :param endsize: 从多少字节结束
        :return:
        """
        if endsize == 0L: endsize = beginsize
        if endsize < beginsize: raise Exception("endsize is letter than beginsize")

        for url in urls:
            hdfs_cmd = "hdfs dfs -ls {0}".format(url)
            logging.debug(hdfs_cmd)
            std_in, std_out = os.popen2(hdfs_cmd)
            lines = std_out.readlines()

            for line in lines:
                line = line.strip()
                hdfs_attrs = re.split("\s+", line)
                logging.debug(line)
                if len(hdfs_attrs) < 6: continue
                url = hdfs_attrs[-1]
                permissiong = hdfs_attrs[0]
                # 目录
                if permissiong.startswith("d"):
                    find_file_bysize([url], empty_files, beginsize, endsize)
                # 文件
                else:
                    size = hdfs_attrs[4]
                    if int(size) >= beginsize and int(size) <= endsize:
                        empty_files.append(url)
                        logging.info("find:{0}".format(line))
        return empty_files


    def remover_file(empty_files=None):
        """
        删除空文件
        :param empty_files: 收集的空文件
        :return:
        """
        for empty_file in empty_files:
            delete_cmd = "hdfs dfs -rm -skipTrash {0}".format(empty_file)
            logging.info(delete_cmd)
            dstd_in, dstd_out = os.popen2(delete_cmd)
            logging.info("".join(dstd_out.readlines()))


    def merge_files(url):
        """
        合并目录下的文件
        :param url: 目录文件
        :return:
        """
        pass


    if __name__ == "__main__":

        args = sys.argv[1:]

        operation = str(args[0]).upper() if len(args) > 0 else ""


        def _find_hdfs_file_(args):
            urls, beginsize, endsize = args[1], 0L, 0L
            if len(args) >= 3: beginsize = args[2]
            if len(args) >= 4: endsize = args[3]
            hdfs_urls = [url for url in urls.split(",")]
            logging.info("\n".join(hdfs_urls))
            empty_files = find_file_bysize(hdfs_urls, [], beginsize, endsize)
            logging.info("total empty files:{0}".format(len(empty_files)))
            return empty_files


        ##找到匹配的文件
        if operation == "FIND":
            if len(args) < 2:
                logging.error("usage: python hdfstool.py find urls beginsize[0] endsize[0]")
                sys.exit(-1)
            _find_hdfs_file_(args)
        # 删除指定的文件
        elif operation == "REMOVE":
            if len(args) < 2:
                logging.error("usage: python hdfstool.py remover urls beginsize[0] endsize[0]")
                sys.exit(-1)
            empty_files = _find_hdfs_file_(args)
            remover_file(empty_files)
        else:
            logging.error("usage: python hdfstool.py find|remove urls beginsize[0] endsize[0]")
