package octava.config;

import com.google.common.primitives.Ints;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@PropertySource("classpath:rdbmsDev.properties")
public class BusinessHibernateConfig {

    @Autowired
    private Environment env;

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        Properties createStrategy = new Properties();
        createStrategy.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        emf.setJpaProperties(createStrategy);
        emf.setPackagesToScan("*.model*");
        emf.setJpaVendorAdapter(getJpaVendorAdapter());
        BasicDataSource dataSource = getDataSource();
        emf.setDataSource(dataSource);

        return emf;
    }

    @Bean
    public DatabasePopulator createDatabasePopulator(BasicDataSource dataSource) {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(false);
        databasePopulator.addScript(new ClassPathResource("create.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        return databasePopulator;
    }

    private JpaVendorAdapter getJpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(false);
        jpaVendorAdapter.setDatabasePlatform(env.getProperty("hibernate.dialect"));
        return jpaVendorAdapter;
    }

    @Bean
    public BasicDataSource getDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        basicDataSource.setUsername(env.getProperty("jdbc.username"));
        basicDataSource.setPassword(env.getProperty("jdbc.password"));
        basicDataSource.setUrl(env.getProperty("jdbc.url"));
        basicDataSource.setInitialSize(Ints.tryParse(env.getProperty("connection.init_size")));
        basicDataSource.setMaxIdle(Ints.tryParse(env.getProperty("connection.pool_size")));
        return basicDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
