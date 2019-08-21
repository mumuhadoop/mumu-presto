package com.lovecws.mumu.presto.common.jdbc;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * jdbc 数据源配置信息
 */
public class JdbcConfig {

    private static final Map<String, javax.sql.DataSource> dataSourceMap = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(JdbcConfig.class);
    private static final String DEFAULT_CHARSET = "ISO-8859-1";

    private javax.sql.DataSource dataSource;
    private String jdbcType;
    private String database;
    public String charset;//数据库编码

    public JdbcConfig(String url, String driver, String user, String password, String charset) {
        //初始化问题
        synchronized (JdbcConfig.class) {
            String key = url + "_" + user + "_" + password;
            javax.sql.DataSource dataSource = dataSourceMap.get(key);
            if (dataSource == null) {
                dataSource = getDruidDataSource(url, driver, user, password);
            }
            this.dataSource = dataSource;
        }
        if (StringUtils.isEmpty(charset)) charset = DEFAULT_CHARSET;
        this.charset = charset;
        logger.debug("DataSource Inject Successfully...");
    }

    public JdbcConfig(String url, String driver, String user, String password) {
        this(url, driver, user, password, DEFAULT_CHARSET);
    }

    /**
     * 获取tomcat数据连接池
     *
     * @param url      url
     * @param driver   驱动
     * @param user     用户名
     * @param password 密码
     * @return
     */
    private DataSource getTomcatDataSource(String url, String driver, String user, String password) {
        DataSource dataSource = new DataSource();
        dataSource.setUrl(url);
        setJdbcUrl(url);
        dataSource.setDriverClassName(driver);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        logger.debug("DataSource Inject Successfully...");

        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnReturn(false);
        dataSource.setTestOnBorrow(false);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }

    /**
     * 获取druid连接池
     *
     * @param url      url
     * @param driver   驱动
     * @param user     用户名
     * @param password 密码
     * @return
     */
    private DruidDataSource getDruidDataSource(String url, String driver, String user, String password) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        setJdbcUrl(url);
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);

        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setValidationQuery("select 1");
        return druidDataSource;
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
        return null;
    }

    public void setJdbcUrl(String url) {
        if (url == null) {
            return;
        }
        String[] split = url.split(":");
        if (split.length >= 2) {
            jdbcType = split[1];
        }

        //获取数据库名称
        int i = url.lastIndexOf("/");
        if (i > -1) database = url.substring(i + 1);
    }

    public String getJdbcType() {
        return jdbcType == null ? "" : jdbcType;
    }

    public String getDatabase() {
        return database;
    }

    public String getCharset() {
        return charset;
    }
}