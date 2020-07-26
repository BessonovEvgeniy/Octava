package octava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
public class BusinessHibernateConfig {

    @Autowired
    private Environment env;
//
//    @Bean
//    public EmptyInterceptor createInterceptor() {
//        return new HibernateInterceptorImpl();
//    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource, final JpaVendorAdapter jpaVendorAdapter) {
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        final Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.ejb.interceptor", createInterceptor());

        emf.setJpaPropertyMap(properties);
        emf.setPackagesToScan("*.model*");
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setDataSource(dataSource);

        return emf;
    }

    @Bean
    public DatabasePopulator createDatabasePopulator(final DataSource dataSource) {
        final ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.setContinueOnError(false);
        databasePopulator.addScript(new ClassPathResource("data-postgresql.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        return databasePopulator;
    }
}
