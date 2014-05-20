package com.kk.jarvis.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@PropertySource("classpath:jarvis.properties")
public class MySqlConfiguration
{
    @Value("${jdbc.driverClassName:com.mysql.jdbc.Driver}")
    private String jdbcDriverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUserName;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource bean = new DriverManagerDataSource();
        bean.setDriverClassName(jdbcDriverClassName);
        bean.setUrl(jdbcUrl);
        bean.setUsername(jdbcUserName);
        bean.setPassword(jdbcPassword);
        return bean;
    }


    @Autowired
    @Bean
    public DataSourceTransactionManager transactionManager(DriverManagerDataSource p_DataSource)
    {
        DataSourceTransactionManager l_Result = new DataSourceTransactionManager();
        l_Result.setDataSource(p_DataSource);

        return l_Result;
    }

    @Autowired
    @Bean
    public TransactionTemplate transactionTemplate(DataSourceTransactionManager p_TransactionManager)
    {
        return new TransactionTemplate(p_TransactionManager);
    }

    @Autowired
    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource p_DataSource)
    {
        return new JdbcTemplate(p_DataSource);
    }
}
