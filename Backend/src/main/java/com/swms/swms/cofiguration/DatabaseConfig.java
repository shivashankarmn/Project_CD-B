package com.swms.swms.cofiguration;
 
import javax.sql.DataSource;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
 
import com.swms.swms.util.SecretsManagerUtil;
 
@Configuration
public class DatabaseConfig {
 
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String dbUsername;
 
    @Bean
    public DataSource dataSource() {
        String dbPassword = SecretsManagerUtil.getRDSPassword();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }
}