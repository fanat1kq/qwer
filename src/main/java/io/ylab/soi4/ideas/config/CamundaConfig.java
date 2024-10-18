package io.ylab.soi4.ideas.config;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Profile("dev")
public class CamundaConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${camunda.misc.schema}")
    private String camundaSchema;

    /**
     * Method for returning default DriverManagerDataSource for preparing desirable
     * Beans of Datasource
     *
     * @return DriverManagerDataSource
     */
    private DriverManagerDataSource getDefaultDataManagerDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * Datasource-bean for main-app with default params for db-connection
     *
     * @return datasource
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        return getDefaultDataManagerDataSource();
    }

    /**
     * Datasource-bean for Camunda with custom params for db-connection
     *
     * @return datasource
     */
    @Bean
    @Qualifier("camundaBpmDataSource")
    public DataSource camundaBpmDataSource() {
        DriverManagerDataSource dataSource = getDefaultDataManagerDataSource();
        dataSource.setUrl(url + "?currentSchema=" + camundaSchema);
        return dataSource;
    }

    /**
     * PlatformTransactionManager-bean for Camunda with custom params for db-connection
     *
     * @return PlatformTransactionManager
     */
    @Bean
    public PlatformTransactionManager camundaTransactionManager(
        @Qualifier("camundaBpmDataSource") DataSource camundaDataSource) {
        return new DataSourceTransactionManager(camundaDataSource);
    }

    /**
     * PlatformTransactionManager-bean for main-app with default params for db-connection
     *
     * @return PlatformTransactionManager
     */
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}